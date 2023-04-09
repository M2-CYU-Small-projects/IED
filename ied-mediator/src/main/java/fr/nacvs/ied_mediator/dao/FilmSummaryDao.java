package fr.nacvs.ied_mediator.dao;

import java.time.LocalDate;
import java.util.Optional;

import fr.nacvs.ied_mediator.business.FilmSummary;

public interface FilmSummaryDao {

	Optional<FilmSummary> findByTitleAndDate(String title, LocalDate date);
	
	Optional<FilmSummary> findByTitleAndDirector(String title, String director);
}
