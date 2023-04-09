package fr.nacvs.ied_mediator.app;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import fr.nacvs.ied_mediator.api.response_writer.ResponseFormat;

public class RequestParameters {

	private RequestType requestType;
	private String query = "";
	private ResponseFormat outputFormat;
	private String outputPath;

	/**
	 * This constructor maps strings to valid enums. It also checks if the output
	 * path is valid (the parent folder exists).
	 * 
	 * @throws IllegalArgumentException if no enum can be found for a given string,
	 *                                  or the output path is invalid
	 */
	public RequestParameters(String requestType, String query, String responseFormat, String outputPath) {
		this.requestType = RequestType.findByCommand(requestType);
		this.query = query;
		this.outputFormat = ResponseFormat.find(responseFormat);
		this.outputPath = outputPath;
		assertOutputPathValid();
	}
	
	public void assertOutputPathValid() {
		Optional.ofNullable(Paths.get(outputPath).toAbsolutePath()) 
			.map(Path::getParent) // can be null if path have no parent
			.map(Path::toFile)
			.filter(File::isDirectory) // Checks also if exists
			.orElseThrow(() -> new IllegalArgumentException("The output path is invalid : do the parent folder exists ?"));
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public String getQuery() {
		return query;
	}

	public ResponseFormat getOutputFormat() {
		return outputFormat;
	}

	public String getOutputPath() {
		return outputPath;
	}
}
