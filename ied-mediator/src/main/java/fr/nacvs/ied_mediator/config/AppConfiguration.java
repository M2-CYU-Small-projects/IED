package fr.nacvs.ied_mediator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fr.nacvs.ied_mediator.app.CommandLineParser;
import fr.nacvs.ied_mediator.app.MediatorApplication;
import fr.nacvs.ied_mediator.dao.FilmDataDao;
import fr.nacvs.ied_mediator.dao.FilmPeopleDao;
import fr.nacvs.ied_mediator.dao.FilmSummaryDao;
import fr.nacvs.ied_mediator.mediator.Mediator;
import fr.nacvs.ied_mediator.mediator.multi_source.MultiSourceMediator;

@Configuration
public class AppConfiguration {

	@Bean
	public Mediator mediator(FilmDataDao dataDao, FilmPeopleDao peopleDao, FilmSummaryDao summaryDao) {
		return new MultiSourceMediator(dataDao, peopleDao, summaryDao);
	}
	
	@Bean
	public CommandLineParser commandLineParser(CliDefaultArguments args) {
		return new CommandLineParser(args);
	}
	
	@Bean
	public MediatorApplication mediatorApplication(Mediator mediator, CommandLineParser commandLineParser) {
		return new MediatorApplication(mediator, commandLineParser);
	}
}
