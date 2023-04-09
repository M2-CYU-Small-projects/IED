package fr.nacvs.ied_mediator.sources.film_data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.nacvs.ied_mediator.business.FilmData;

/**
 * An iterator wrapping the jdbc result set on the film_data table
 * 
 * @author Aldric Vitali Silvestre
 */
public class FilmDataCursor implements Iterator<FilmData> {

	private static Logger LOGGER = LoggerFactory.getLogger(FilmDataCursor.class);
	
	private Statement statement;
	private ResultSet resultSet;
	private boolean hasNext;
	
	public FilmDataCursor(Statement statement) {
		super();
		this.statement = statement;
		this.resultSet = getResultSet(statement);
		goToNext();
	}

	private ResultSet getResultSet(Statement statement) {
		try {
			return statement.getResultSet();
		} catch (SQLException exception) {
			LOGGER.error("Error while opening result set : " + exception.getMessage());
			throw new RuntimeException(exception);
		}
	}

	private void goToNext() {
		try {
			this.hasNext = resultSet.next();
			if (!hasNext) {
				statement.close();
			}
		} catch (SQLException exception) {
			LOGGER.error("Error while iterating results : " + exception.getMessage());
			throw new RuntimeException(exception);
		}
	}

	@Override
	public boolean hasNext() {
		return hasNext;
	}

	@Override
	public FilmData next() {
		try {
			FilmData filmData = FilmDataConverter.fromResultSet(resultSet);
			goToNext();
			return filmData;
		} catch (SQLException exception) {
			LOGGER.error("Error while iterating results : " + exception.getMessage());
			throw new RuntimeException(exception);
		}
	}
	
}
