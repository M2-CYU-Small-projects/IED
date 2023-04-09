package fr.nacvs.ied_mediator.sources.film_data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import fr.nacvs.ied_mediator.business.FilmData;

public class FilmDataConverter {

	public static FilmData fromResultSet(ResultSet resultSet) throws SQLException {
		String filmTitle = resultSet.getString("title");
		LocalDate date = resultSet.getDate("date").toLocalDate();
		String distributor = resultSet.getString("distributor");
		String genre = resultSet.getString("genre");
		long budget = resultSet.getLong("budget");
		long usGross = resultSet.getLong("us_gross");
		long wordlwideGross = resultSet.getLong("worldwide_gross");
		return new FilmData(filmTitle, date, genre, distributor, budget, usGross, wordlwideGross);
	}
}
