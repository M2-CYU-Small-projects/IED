package fr.nacvs.ied_mediator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fr.nacvs.ied_mediator.dao.FilmDataDao;
import fr.nacvs.ied_mediator.dao.FilmPeopleDao;
import fr.nacvs.ied_mediator.dao.FilmSummaryDao;
import fr.nacvs.ied_mediator.sources.film_data.FilmDataJdbcSource;
import fr.nacvs.ied_mediator.sources.film_people.FilmPeopleSparql;
import fr.nacvs.ied_mediator.sources.film_summary.FilmSummaryREST;

@Configuration
public class DaoConfiguration {

	@Bean
	public FilmDataDao filmDataDao(FilmDataSourceProperties properties) {
		return new FilmDataJdbcSource(properties);
	}
	
	@Bean
	public FilmPeopleDao filmPeopleDao() {
		return new FilmPeopleSparql();
	}
	
	@Bean
	public FilmSummaryDao filmSummaryDao(FilmSummaryProperties properties) {
		return new FilmSummaryREST(properties);
	}
}
