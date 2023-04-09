package fr.nacvs.ied_mediator.sources.film_data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.nacvs.ied_mediator.business.FilmData;
import fr.nacvs.ied_mediator.config.FilmDataSourceProperties;
import fr.nacvs.ied_mediator.dao.FilmDataDao;

public class FilmDataJdbcSource implements FilmDataDao {

	private static final String QUERY_BY_TITLE = "SELECT * FROM film_data WHERE title=?";
	private static final String QUERY_BY_TITLE_AND_DATE = "SELECT * FROM film_data WHERE title=? AND YEAR(date)=?";

	private static Logger LOGGER = LoggerFactory.getLogger(FilmDataJdbcSource.class);

	private DatabaseConnection dbConnection;
	
	public FilmDataJdbcSource(FilmDataSourceProperties properties) {
		this.dbConnection = new DatabaseConnection(
				properties.getDriver(),
				properties.getHost(),
				properties.getPort(),
				properties.getUser(),
				properties.getPassword(),
				properties.getDatabaseName(),
				properties.getOptionalParams());
	}

	@Override
	public Iterator<FilmData> findByTitle(String title) {
		LOGGER.info("Find from film data source by title");
		Connection connection = dbConnection.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(QUERY_BY_TITLE);
			statement.setString(1, title);
			statement.execute();
			return new FilmDataCursor(statement);
		} catch (SQLException exception) {
			LOGGER.error("Error while creating request : " + exception.getMessage());
			throw new RuntimeException(exception);
		}
	}

	@Override
	public Optional<FilmData> findByTitleAndDate(String title, LocalDate date) {
		LOGGER.debug("Find from film data source by title and date");
		Connection connection = dbConnection.getConnection();
		try (PreparedStatement statement = connection.prepareStatement(QUERY_BY_TITLE_AND_DATE)){
			statement.setString(1, title);
			statement.setInt(2, date.getYear());
			statement.execute();
			ResultSet resultSet = statement.getResultSet();
			FilmData filmData = null;
			if (resultSet.next()) {
				filmData = FilmDataConverter.fromResultSet(resultSet);
			}
			resultSet.close();
			return Optional.ofNullable(filmData);
		} catch (SQLException exception) {
			LOGGER.error("Error while creating request : " + exception.getMessage());
			throw new RuntimeException(exception);
		}
	}

}
