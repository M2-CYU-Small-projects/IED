package fr.nacvs.ied_mediator.sources.film_data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DatabaseConnection {

	private static Logger LOGGER = LoggerFactory.getLogger(DatabaseConnection.class);

	private String driver;
	private String host;
	private String port;
	private String optionalParams;
	private String username;
	private String password;
	private String dbName;

	private Connection connection;

	public DatabaseConnection(String driver, String host, String port, String username, String password, String dbName, String optionalParams) {
		super();
		this.driver = driver;
		this.host = host;
		this.port = port;
		this.optionalParams = optionalParams;
		this.username = username;
		this.password = password;
		this.dbName = dbName;
		connection = createConnection();
	}

	private Connection createConnection() {
		LOGGER.info("Try connecting to database " + dbName);
		try {
			String url = createUrl();
			Connection conn = DriverManager.getConnection(url, username, password);
			LOGGER.info("Successfully connected to database " + dbName);
			return conn;
		} catch (SQLException e) {
			// Cannot do more here, we have to stop the program
			LOGGER.error("Error while connecting to database : " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	private String createUrl() {
		return "jdbc:" + driver + "://" + host + ":" + port + "/" + dbName + optionalParams;
	}

	public Connection getConnection() {
		return connection;
	}
}
