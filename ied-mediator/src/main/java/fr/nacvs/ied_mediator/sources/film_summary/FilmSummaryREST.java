package fr.nacvs.ied_mediator.sources.film_summary;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.nacvs.ied_mediator.business.FilmSummary;
import fr.nacvs.ied_mediator.config.FilmSummaryProperties;
import fr.nacvs.ied_mediator.dao.FilmSummaryDao;
import fr.nacvs.ied_mediator.sources.film_people.FilmPeopleSparql;
import fr.nacvs.ied_mediator.util.DateUtils;

/**
 * This implentation takes data from the OMD REST API.
 * If we try to get a film by title, this will only return one film, even if
 * multiple films have this title.
 * So, in order to circumvent that, the process will be done in 3 steps :
 * <ul>
 * <li>
 * Call the api in "search" mode : it returns a list of corresponding film (we
 * maybe have to call this multiple times in order to have all pages)
 * </li>
 * <li>
 * Find with XPath all films that have exact same title as the query
 * </li>
 * <li>
 * Call the api in "id" mode : put the name of the film with the date (and the
 * imdb id if have it) in order to have details.</li>
 * </ul>
 */
public class FilmSummaryREST implements FilmSummaryDao {

	private static Logger LOGGER = LoggerFactory.getLogger(FilmPeopleSparql.class);

	private FilmSummaryProperties properties;
	private Client client = ClientBuilder.newClient();
	private XmlHelper xmlHelper = new XmlHelper();

	public FilmSummaryREST(FilmSummaryProperties properties) {
		super();
		this.properties = properties;
	}

	@Override
	public Optional<FilmSummary> findByTitleAndDate(String title, LocalDate date) {
		LOGGER.debug("Find film summary by title and date");
		return findImdbIdsByTitle(title)
				.stream()
				.map(this::performFilmDetailsRequest)
				.filter(r -> haveCorrectDate(r, date))
				.findFirst()
				.map(this::createFromDetails);
	}

	@Override
	public Optional<FilmSummary> findByTitleAndDirector(String title, String director) {
		LOGGER.debug("Find film summary by title and director");
		return findImdbIdsByTitle(title)
				.stream()
				.map(this::performFilmDetailsRequest)
				.filter(r -> haveCorrectDirector(r, director))
				.findFirst()
				.map(this::createFromDetails);
	}

	private List<String> findImdbIdsByTitle(String title) {
		Document response = performSearchRequest(title);
		if (!isResponsePositive(response)) {
			LOGGER.warn("No film found : " + findErrorMessage(response));
			return List.of();
		}
		// Find all nodes with precise title
		// Escaped because we want to avoid error with quotes etc
		String titleEscaped = StringEscapeUtils.escapeHtml4(title.replaceAll(":([^\\s])", ": $1"));
		String queryFilms = String.format("/root/result[@title = \"%s\"]", titleEscaped);
		NodeList nodeList = xmlHelper.findNodeList(response, queryFilms);
		// Retrieve imdb ids in order to perform next requests
		List<String> imdbIds = new ArrayList<>();
		for (int index = 0; index < nodeList.getLength(); index++) {
			Node node = nodeList.item(index);
			String imdbId = node.getAttributes().getNamedItem("imdbID").getNodeValue();
			imdbIds.add(imdbId);
		}
		return imdbIds;
	}

	// ==== REQUESTS ====

	private Document performSearchRequest(String title) {
		String stringResponse = client.target(properties.getBaseUrl())
				.queryParam("apiKey", properties.getApiKey())
				.queryParam("s", title.toLowerCase())
				.queryParam("r", "xml")
				.queryParam("type", "movie")
				.request()
				.accept(MediaType.APPLICATION_XML)
				.get(String.class);
		return xmlHelper.loadXml(stringResponse);
	}

	private Document performFilmDetailsRequest(String imdbId) {
		String stringResponse = client.target(properties.getBaseUrl())
				.queryParam("apiKey", properties.getApiKey())
				.queryParam("i", imdbId)
				.queryParam("r", "xml")
				.queryParam("plot", "full")
				.queryParam("type", "movie")
				.request()
				.accept(MediaType.APPLICATION_XML)
				.get(String.class);
		return xmlHelper.loadXml(stringResponse);
	}

	// ==== XML RELATIVE ====

	// ---- On search response ----
	private boolean isResponsePositive(Document response) {
		return xmlHelper.findBoolean(response, "/root/@response");
	}

	private String findErrorMessage(Document response) {
		return xmlHelper.findString(response, "/root/error");
	}

	// ==== On details response
	private String findTitle(Document response) {
		// We want to have spaces after each ":"
		return xmlHelper.findString(response, "/root/movie/@title").replaceAll(":([^\\s])", ": $1");
	}

	private LocalDate findReleaseDate(Document response) {
		String dateStr = xmlHelper.findString(response, "/root/movie/@released");
		return DateUtils.toDateShortMonth(dateStr);
	}

	private String findDirector(Document response) {
		return xmlHelper.findString(response, "/root/movie/@director");
	}

	private String findSummary(Document response) {
		return xmlHelper.findString(response, "/root/movie/@plot");
	}

	private boolean haveCorrectDate(Document response, LocalDate date) {
		return date.getYear() == findReleaseDate(response).getYear();
	}

	private boolean haveCorrectDirector(Document response, String director) {
		// The api does not return the full name of the director, bu only the begin and
		// the end, with the begin altered (as a surname generally). So we can only
		// trust the last name, i.e the last word found
		String directorFound = findDirector(response);
		String[] split = directorFound.split(" ");
		return director.endsWith(split[split.length - 1]);
	}

	private FilmSummary createFromDetails(Document response) {
		LocalDate date = findReleaseDate(response);
		String director = findDirector(response);
		String title = findTitle(response);
		String summary = findSummary(response);
		return new FilmSummary(title, date, director, summary);
	}
}
