package fr.nacvs.ied_mediator.api.response_writer;

import java.io.IOException;
import java.io.Writer;

import fr.nacvs.ied_mediator.api.responses.film_by_title.RespFilmsByTitle;
import fr.nacvs.ied_mediator.api.responses.film_of_actor.RespFilmsOfActor;

/**
 * Used to write a response to a specific file format 
 * 
 * @author Aldric Vitali Silvestre
 */
public interface ResponseWriter {
	
	/**
	 * Change or add the right extension to the given filename
	 */
	String renameFilename(String filename);

	void write(RespFilmsOfActor response, Writer writer) throws IOException;
	
	void write(RespFilmsByTitle response, Writer writer) throws IOException;
}
