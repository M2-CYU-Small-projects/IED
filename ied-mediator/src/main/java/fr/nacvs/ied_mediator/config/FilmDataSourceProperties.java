package fr.nacvs.ied_mediator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class FilmDataSourceProperties {

	@Value("${film_data.database.driver}")
	private String driver;
	
	@Value("${film_data.database.host}")
	private String host;
	
	@Value("${film_data.database.port}")
	private String port;
	
	@Value("${film_data.database.user}")
	private String user;
	
	@Value("${film_data.database.password}")
	private String password;
	
	@Value("${film_data.database.name}")
	private String databaseName;
	
	@Value("${film_data.database.optional_url_params}")
	private String optionalParams;

	public String getDriver() {
		return driver;
	}

	public String getHost() {
		return host;
	}

	public String getPort() {
		return port;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public String getOptionalParams() {
		return optionalParams;
	}
}
