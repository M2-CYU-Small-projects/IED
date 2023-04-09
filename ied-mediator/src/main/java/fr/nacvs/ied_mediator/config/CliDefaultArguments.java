package fr.nacvs.ied_mediator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class CliDefaultArguments {

	@Value("${cli.default.request_type}")
	private String requestType;
	
	@Value("${cli.default.output_format}")
	private String outputFormat;
	
	@Value("${cli.default.output_path}")
	private String outputPath;

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getOutputFormat() {
		return outputFormat;
	}

	public void setOutputFormat(String outputType) {
		this.outputFormat = outputType;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}
}
