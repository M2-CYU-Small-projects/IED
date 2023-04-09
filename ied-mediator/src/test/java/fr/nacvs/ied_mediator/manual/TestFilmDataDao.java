package fr.nacvs.ied_mediator.manual;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.nacvs.ied_mediator.config.SpringIoc;
import fr.nacvs.ied_mediator.dao.FilmDataDao;
import fr.nacvs.ied_mediator.sources.film_data.FilmDataJdbcSource;
import fr.nacvs.ied_mediator.util.DateUtils;

public class TestFilmDataDao {

	private static Logger LOGGER = LoggerFactory.getLogger(TestFilmDataDao.class);

	public static void main(String[] args) {

		String title = "Avatar";
		LOGGER.info("Find all films with title {}", title);
		FilmDataDao dao = SpringIoc.getBean(FilmDataDao.class);
		dao.findByTitle(title)
				.forEachRemaining(System.out::println);

		String title2 = "John Carter";
		LocalDate date = DateUtils.toDate("2012-03-09");
		LOGGER.info("Find film with title {} and date {}", title2, date);
		dao.findByTitleAndDate(title2, date)
				.ifPresentOrElse(System.out::println, actionIfNotFound());

		LOGGER.info("Find non-present film");
		dao.findByTitleAndDate("blblb", LocalDate.now())
				.ifPresentOrElse(System.out::println, actionIfNotFound());
	}

	private static Runnable actionIfNotFound() {
		return () -> System.out.println("The film wanted cannot be found");
	}
}
