package fr.nacvs.ied_mediator.mediator.multi_source;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.nacvs.ied_mediator.api.responses.film_by_title.RespFilmsByTitle;
import fr.nacvs.ied_mediator.api.responses.film_by_title.RespOneFilmByTitle;
import fr.nacvs.ied_mediator.api.responses.film_of_actor.RespFilmsOfActor;
import fr.nacvs.ied_mediator.api.responses.film_of_actor.RespOneFilmOfActor;
import fr.nacvs.ied_mediator.business.FilmData;
import fr.nacvs.ied_mediator.business.FilmPeople;
import fr.nacvs.ied_mediator.dao.FilmDataDao;
import fr.nacvs.ied_mediator.dao.FilmPeopleDao;
import fr.nacvs.ied_mediator.dao.FilmSummaryDao;
import fr.nacvs.ied_mediator.mediator.Mediator;

public class MultiSourceMediator implements Mediator {

	private static Logger LOGGER = LoggerFactory.getLogger(MultiSourceMediator.class);

	private final FilmDataDao filmDataDao;
	private final FilmPeopleDao filmPeopleDao;
	private final FilmSummaryDao filmSummaryDao;

	public MultiSourceMediator(FilmDataDao filmDataDao, FilmPeopleDao filmPeopleDao, FilmSummaryDao filmSummaryDao) {
		super();
		this.filmDataDao = filmDataDao;
		this.filmPeopleDao = filmPeopleDao;
		this.filmSummaryDao = filmSummaryDao;
	}

	@Override
	public RespFilmsByTitle findFilmsByTitle(String title) {
		LOGGER.info("Find films by title \"{}\"", title);
		// Search on filmData dao, use those results for searching in other sources
		Iterator<FilmData> filmDataIterator = filmDataDao.findByTitle(title);
		List<RespOneFilmByTitle> films = createStream(filmDataIterator)
				.map(this::mergeWithOtherSources)
				.collect(Collectors.toList());
		return new RespFilmsByTitle(films);
	}

	@Override
	public RespFilmsOfActor findFilmsOfActor(String actor) {
		LOGGER.info("Find films of actor \"{}\"", actor);
		// Search on filmPeople dao, use those results for searching in other sources
		Iterator<FilmPeople> filmPeopleIterator = filmPeopleDao.findByActor(actor);
		List<RespOneFilmOfActor> films = createStream(filmPeopleIterator)
				.map(this::mergeWithOtherSources)
				.collect(Collectors.toList());
		return new RespFilmsOfActor(films);
	}

	private RespOneFilmByTitle mergeWithOtherSources(FilmData filmData) {
		// merge with summary source, then with people source
		String title = filmData.getTitle();
		LocalDate date = filmData.getDate();
		RespOneFilmByTitle resp = new RespOneFilmByTitle();
		resp.fillDataInfos(filmData);
		filmSummaryDao.findByTitleAndDate(title, date)
				.ifPresent(fs -> {
					resp.fillSummaryInfos(fs);
					filmPeopleDao.findByTitleAndDirector(fs.getTitle(), fs.getDirector())
							.ifPresent(resp::fillPeopleInfos);
				});
		return resp;
	}

	private RespOneFilmOfActor mergeWithOtherSources(FilmPeople filmPeople) {
		String title = filmPeople.getTitle();
		String director = filmPeople.getDirector();
		RespOneFilmOfActor resp = new RespOneFilmOfActor();
		resp.fillPeopleInfos(filmPeople);
		filmSummaryDao.findByTitleAndDirector(title, director)
				.ifPresent(fs -> {
					resp.fillSummaryInfos(fs);
					filmDataDao.findByTitleAndDate(fs.getTitle(), fs.getDate())
							.ifPresent(resp::fillDataInfos);
				});
		return resp;
	}

	private <T> Stream<T> createStream(Iterator<? extends T> iterator) {
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
	}

}
