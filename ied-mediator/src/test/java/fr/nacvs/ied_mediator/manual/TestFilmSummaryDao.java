package fr.nacvs.ied_mediator.manual;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.nacvs.ied_mediator.config.SpringIoc;
import fr.nacvs.ied_mediator.dao.FilmSummaryDao;
import fr.nacvs.ied_mediator.util.DateUtils;

public class TestFilmSummaryDao {

	private static Logger LOGGER = LoggerFactory.getLogger(TestFilmSummaryDao.class);

	public static void main(String[] args) {
		LOGGER.info("--> Test of filmSummaryREST");
		FilmSummaryDao dao = SpringIoc.getBean(FilmSummaryDao.class);
		LOGGER.info("Find Ed Wood film");
		dao.findByTitleAndDate("Ed Wood", DateUtils.toDateShortMonth("07 Oct 1994"))
				.ifPresent(System.out::println);

		LOGGER.info("Find The Avengers (04 May 2012) film");
		dao.findByTitleAndDate("The Avengers", DateUtils.toDateShortMonth("04 May 2012"))
				.ifPresent(System.out::println);

		LOGGER.info("Find The Avengers (14 Aug 1998) film");
		dao.findByTitleAndDate("The Avengers", DateUtils.toDateShortMonth("14 Aug 1998"))
				.ifPresent(System.out::println);

		LOGGER.info("Find The Avengers with his director");
		dao.findByTitleAndDirector("The Avengers", "Jeremiah S. Chechik")
				.ifPresent(System.out::println);
	}
}
