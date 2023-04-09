package fr.nacvs.ied_mediator.app;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.nacvs.ied_mediator.api.response_writer.ResponseWriter;
import fr.nacvs.ied_mediator.api.responses.film_by_title.RespFilmsByTitle;
import fr.nacvs.ied_mediator.api.responses.film_of_actor.RespFilmsOfActor;
import fr.nacvs.ied_mediator.config.SpringIoc;
import fr.nacvs.ied_mediator.mediator.Mediator;

public class MediatorApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(MediatorApplication.class);

	private final Mediator mediator;
	private final CommandLineParser commandLineParser;

	public MediatorApplication(Mediator mediator, CommandLineParser commandLineParser) {
		super();
		this.mediator = mediator;
		this.commandLineParser = commandLineParser;
	}

	public void launchApp(String[] args) throws Exception {
		RequestParameters requestParams = commandLineParser.parse(args);
		String query = requestParams.getQuery();
		ResponseWriter responseWriter = requestParams.getOutputFormat().createWriter();
		String outputFile = responseWriter.renameFilename(requestParams.getOutputPath());
		Path outputPath = Paths.get(outputFile);
		LOGGER.info("Output path : " + outputFile);
		switch (requestParams.getRequestType()) {
		case FILMS_BY_TITLE:
			handleFilmsByTitle(query, responseWriter, outputPath);
			break;
		case FILMS_BY_ACTOR:
		default:
			handleFilmsByActor(query, responseWriter, outputPath);
			break;
		}
		System.out.println("Output file written to " + outputPath);
	}

	private void handleFilmsByTitle(String query, ResponseWriter responseWriter, Path outputPath) throws IOException {
		RespFilmsByTitle respByFilm = mediator.findFilmsByTitle(query);
		try (Writer writer = new BufferedWriter(new FileWriter(outputPath.toFile()))) {
			responseWriter.write(respByFilm, writer);
		}
	}

	private void handleFilmsByActor(String query, ResponseWriter responseWriter, Path outputPath) throws IOException {
		RespFilmsOfActor respByActor = mediator.findFilmsOfActor(query);
		try (Writer writer = new BufferedWriter(new FileWriter(outputPath.toFile()))) {
			responseWriter.write(respByActor, writer);
		}
	}

	public static void main(String[] args) {
		MediatorApplication app = SpringIoc.getBean(MediatorApplication.class);
		try {
			app.launchApp(args);
		} catch (Exception exception) {
			LOGGER.error("Error while executing mediator : " + exception.getMessage());
			exception.printStackTrace();
			System.exit(1);
		}
	}

}
