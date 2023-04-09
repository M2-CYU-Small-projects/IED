package fr.nacvs.ied_mediator.app;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum RequestType {
	FILMS_BY_TITLE("film"),
	FILMS_BY_ACTOR("actor");
	
	private String command;

	private RequestType(String command) {
		this.command = command;
	}

	public String getCommand() {
		return command;
	}
	
	public static RequestType findByCommand(String command) {
		return Arrays.stream(RequestType.values())
				.filter(e -> e.getCommand().equals(command.toLowerCase()))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("No request type found for string " + command));
	}
	
	public static List<String> getAllPossibleCommands() {
		return Arrays.stream(RequestType.values())
				.map(RequestType::getCommand)
				.collect(Collectors.toList());
	}
}
