package fr.nacvs.ied_mediator.api.response_writer;

import java.io.IOException;
import java.io.Writer;

import org.apache.commons.io.FilenameUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.nacvs.ied_mediator.api.responses.film_by_title.RespFilmsByTitle;
import fr.nacvs.ied_mediator.api.responses.film_of_actor.RespFilmsOfActor;

public class JsonResponseWriter implements ResponseWriter {
	
	private ObjectMapper objectMapper;

	public JsonResponseWriter() {
		this.objectMapper = new ObjectMapper();
	}

	@Override
	public void write(RespFilmsOfActor response, Writer writer) throws IOException {
		objectMapper.writeValue(writer, response);
	}

	@Override
	public void write(RespFilmsByTitle response, Writer writer) throws IOException {
		objectMapper.writeValue(writer, response);
	}

	@Override
	public String renameFilename(String filename) {
		return FilenameUtils.removeExtension(filename) + ".json";
	}

}
