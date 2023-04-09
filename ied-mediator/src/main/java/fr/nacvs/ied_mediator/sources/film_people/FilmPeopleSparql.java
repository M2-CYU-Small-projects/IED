package fr.nacvs.ied_mediator.sources.film_people;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.jena.query.ARQ;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.nacvs.ied_mediator.business.FilmPeople;
import fr.nacvs.ied_mediator.dao.FilmPeopleDao;

public class FilmPeopleSparql implements FilmPeopleDao {

	private static Logger LOGGER = LoggerFactory.getLogger(FilmPeopleSparql.class);
	
	public FilmPeopleSparql() {
		ARQ.init();
	}

	@Override
	public Iterator<FilmPeople> findByActor(String actor) {
		LOGGER.info("Calling findByActor on actor : " + actor);
		// add underscore where we have spaces on actor
		actor = actor.replaceAll("\\ ", "_");
		LOGGER.info("Spaces in actor name replaces by underscore : " + actor);
		// create request
		LOGGER.info(" creating request ");
		String serviceUrl = "https://dbpedia.org/sparql";

		// execute request
		String queryString = getPrefix() +
				"SELECT \n" +
				"?real \n" +
				"?f \n" +
				"(GROUP_CONCAT( DISTINCT ?lp;separator=\"|\") AS ?prod)\n" +
				"(GROUP_CONCAT( ?t;separator=\"|\") AS ?title) \n" +
				"WHERE {\n" +
				"            ?f rdf:type dbo:Film.\n" +
				"            ?f foaf:name ?t.\n" +
				"            ?f dbo:director ?s.\n" +
				"            ?s dbo:birthName ?real.\n" +
				"            ?f dbo:producer ?p.\n" +
				"            ?p dbo:birthName ?lp.\n" +
				"            ?f dbo:starring dbr:" + actor + "\n" +
				"} GROUP BY ?f ?real";
		QueryExecution qe = QueryExecution.service(serviceUrl)
				.query(queryString)
				.build();
		ResultSet results = qe.execSelect();

		// create query output
		List<FilmPeople> outputList = new ArrayList<>();
		// ResultSetFormatter.out(System.out, results, query);

		for (; results.hasNext();) {
			QuerySolution soln = results.next();
			String titre = Arrays.stream(soln.get("title").toString().split("\\|"))
					.distinct()
					.collect(Collectors.joining(""));
			String real = soln.get("real").toString().replaceAll("@en", "");
			String[] producers = soln.get("prod").toString().split("\\|");
			FilmPeople fp = new FilmPeople();
			fp.setTitle(titre);
			fp.setDirector(real);
			List<String> plist = new ArrayList<>();
			for (String elt : producers) {
				plist.add(elt);
			}
			fp.setProducers(plist);
			outputList.add(fp);

		}
		qe.close();
		LOGGER.info("End of findByActor ");

		return outputList.iterator();
	}

	@Override
	public Optional<FilmPeople> findByTitleAndDirector(String title, String director) {
		LOGGER.info("Calling findByTitleAndDirector : title :" + title + " -- director : " + director);
		// create request
		LOGGER.info(" creating request ");
		String serviceUrl = "https://dbpedia.org/sparql";

		// execute request
		String queryString = getPrefix() +
				"SELECT \n" +
				"?real \n" +
				"?f \n" +
				"(GROUP_CONCAT( DISTINCT ?lp;separator=\"|\") AS ?prod)\n" +
				"(GROUP_CONCAT( ?t;separator=\"|\") AS ?title) \n" +
				"(GROUP_CONCAT( ?a;separator=\"|\") AS ?actors) \n" +
				"WHERE {\n" +
				"            ?f rdf:type dbo:Film.\n" +
				"            ?f foaf:name \"" + title + "\"@en.\n" +
				"            ?f foaf:name ?t.\n" +

				"            ?f dbo:starring ?as.\n" +
				"            ?as foaf:name ?a.\n" +

				"            ?f dbo:director ?ts.\n" +
				"            ?ts foaf:name \"" + director + "\"@en. \n" +

				"            ?f dbo:director ?tr.\n" +
				"            ?tr foaf:name ?real.\n" +

				"            ?f dbo:producer ?p.\n" +
				"            ?p foaf:name ?lp.\n" +
				"} GROUP BY ?f ?real";
		QueryExecution qe = QueryExecution.service(serviceUrl)
				.query(queryString)
				.build();
		ResultSet results = qe.execSelect();
		// create query output
		List<FilmPeople> outputList = new ArrayList<>();
		// ResultSetFormatter.out(System.out, results, query);

		for (; results.hasNext();) {
			QuerySolution soln = results.next();
			String titre = Arrays.stream(soln.get("title").toString().split("\\|"))
					.distinct()
					.collect(Collectors.joining(""));
			String real = soln.get("real").toString().replaceAll("@en", "");
			String[] producers = soln.get("prod").toString().split("\\|");
			String[] actors = soln.get("actors").toString().split("\\|");

			FilmPeople fp = new FilmPeople();
			fp.setTitle(titre);
			fp.setDirector(real);
			fp.setProducers(Arrays.stream(producers).distinct().collect(Collectors.toList()));
			fp.setActors(Arrays.stream(actors).distinct().collect(Collectors.toList()));
			outputList.add(fp);

		}
		qe.close();
		LOGGER.info("End of findByActor ");
		if (outputList.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(outputList.get(0));
	}

	private String getPrefix() {
		return "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
				"PREFIX dc: <http://purl.org/dc/elements/1.1/>\n" +
				"PREFIX : <http://dbpedia.org/resource/>\n" +
				"PREFIX dbpedia2: <http://dbpedia.org/property/>\n" +
				"PREFIX dbpedia: <http://dbpedia.org/>\n" +
				"PREFIX skos: <http://www.w3.org/2004/02/skos/core#>" +
				"PREFIX dbo: <http://dbpedia.org/ontology/>" +
				"PREFIX dbr:<http://dbpedia.org/resource/>" +
				"PREFIX dbp:<https://dbpedia.org/property/>";
	}

}
