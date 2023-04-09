package fr.nacvs.ied_mediator.api.response_writer;

import java.io.IOException;
import java.io.Writer;

import org.apache.commons.io.FilenameUtils;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import fr.nacvs.ied_mediator.api.responses.film_by_title.RespFilmsByTitle;
import fr.nacvs.ied_mediator.api.responses.film_of_actor.RespFilmsOfActor;

public class XmlResponseWriter implements ResponseWriter {
	
	private XmlMapper xmlMapper;
	
	public XmlResponseWriter() {
		this.xmlMapper = new XmlMapper();
	}

	@Override
	public String renameFilename(String filename) {
		return FilenameUtils.removeExtension(filename) + ".xml";
	}

	@Override
	public void write(RespFilmsOfActor response, Writer writer) throws IOException {
		xmlMapper.writeValue(writer, response);
	}

	@Override
	public void write(RespFilmsByTitle response, Writer writer) throws IOException {
		xmlMapper.writeValue(writer, response);
	}
}
