package fr.nacvs.ied_mediator.manual;

import fr.nacvs.ied_mediator.business.FilmPeople;
import fr.nacvs.ied_mediator.business.FilmSummary;
import fr.nacvs.ied_mediator.config.SpringIoc;
import fr.nacvs.ied_mediator.dao.FilmPeopleDao;
import fr.nacvs.ied_mediator.sources.film_people.FilmPeopleSparql;
import fr.nacvs.ied_mediator.sources.film_summary.FilmSummaryREST;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFilmPeopleDao {

	private static Logger LOGGER = LoggerFactory.getLogger(TestFilmPeopleDao.class);

	public static void main(String[] args) {
		LOGGER.info("--> Test on FindByActor");
		FilmPeopleDao dao = SpringIoc.getBean(FilmPeopleDao.class);
		dao.findByActor("Johnny Depp")
				.forEachRemaining(System.out::println);
		LOGGER.info("--> Test on FindByNameAndDirector");
		Optional<FilmPeople> fp = dao.findByTitleAndDirector("Ed Wood", "Tim Burton");
		LOGGER.info(fp.toString());

		

	}
}
