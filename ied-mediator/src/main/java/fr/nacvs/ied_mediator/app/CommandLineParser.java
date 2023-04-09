package fr.nacvs.ied_mediator.app;

import java.util.stream.Collectors;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import fr.nacvs.ied_mediator.api.response_writer.ResponseFormat;
import fr.nacvs.ied_mediator.config.CliDefaultArguments;

public class CommandLineParser {

	private static final String HELP_OPT = "help";
	private static final String OUTPUT_FILENAME_OPT = "output_filename";
	private static final String OUTPUT_FORMAT_OPT = "output_format";
	private static final String QUERY_OPT = "query";
	private static final String REQUEST_OPT = "request";
	private CliDefaultArguments defaultArgs;

	public CommandLineParser(CliDefaultArguments args) {
		this.defaultArgs = args;
	}

	public RequestParameters parse(String args[]) throws ParseException {
		var parser = new DefaultParser();

		Options firstOptions = createFirstOptions();
		Options options = createOptions();
		CommandLine firstArgumentsParsed = parser.parse(firstOptions, args, true);
		handleFirstOptions(firstArgumentsParsed, options);

		CommandLine parsedArguments = parser.parse(options, args);

		String requestType = parsedArguments.getOptionValue(REQUEST_OPT, defaultArgs.getRequestType());
		String query = parsedArguments.getOptionValue(QUERY_OPT);
		String responseFormat = parsedArguments.getOptionValue(OUTPUT_FORMAT_OPT, defaultArgs.getOutputFormat());
		String outputPath = parsedArguments.getOptionValue(OUTPUT_FILENAME_OPT, defaultArgs.getOutputPath());

		return new RequestParameters(requestType, query, responseFormat, outputPath);
	}

	/**
	 * Non-related options, such as "help" option
	 */
	private Options createFirstOptions() {
		Option helpFileOption = Option.builder("h")
				.longOpt(HELP_OPT)
				.desc("Display help message")
				.build();
		Options firstOptions = new Options();
		firstOptions.addOption(helpFileOption);
		return firstOptions;
	}

	private void handleFirstOptions(CommandLine firstArgumentsParsed, Options options) {
		if (firstArgumentsParsed.hasOption(HELP_OPT)) {
			HelpFormatter helpFormatter = new HelpFormatter();
			helpFormatter.printHelp("java -jar mediator.jar", options, true);
			System.exit(0);
		}
	}

	private Options createOptions() {
		Options options = new Options();

		String requestTypeDescritpion = "Set the request type, defaults to " + defaultArgs.getRequestType()
				+ "\nAccepted arguments : " 
				+ RequestType.getAllPossibleCommands()
						.stream()
						.collect(Collectors.joining(","));
		Option requestType = Option.builder("r")
				.required(false)
				.longOpt(REQUEST_OPT)
				.desc(requestTypeDescritpion)
				.hasArg()
				.type(String.class)
				.build();
		options.addOption(requestType);

		Option query = Option.builder("q")
				.required()
				.longOpt(QUERY_OPT)
				.desc("Query to process")
				.hasArg()
				.type(String.class)
				.build();
		options.addOption(query);

		String outputFormatDescritpion = "Output format wanted, defaults to " + defaultArgs.getOutputFormat()
			+"\nAccepted arguments : "
			+ ResponseFormat.getAllPossibleFormats()
				.stream()
				.collect(Collectors.joining(","));
		Option outputType = Option.builder("f")
				.required(false)
				.longOpt(OUTPUT_FORMAT_OPT)
				.desc(outputFormatDescritpion)
				.hasArg()
				.type(String.class)
				.build();
		options.addOption(outputType);

		Option outputPath = Option.builder("o")
				.required(false)
				.longOpt(OUTPUT_FILENAME_OPT)
				.desc("Output filename. The parent folder must exist")
				.hasArg()
				.type(String.class)
				.build();
		options.addOption(outputPath);
		return options;
	}
}
