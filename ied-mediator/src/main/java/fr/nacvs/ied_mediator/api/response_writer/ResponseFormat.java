package fr.nacvs.ied_mediator.api.response_writer;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * This enum is capable of creating corresponding implementations
 * 
 * @author Aldric Vitali Silvestre
 */
public enum ResponseFormat {
	JSON(JsonResponseWriter::new),
	XML(XmlResponseWriter::new)
	;
	
	private Supplier<ResponseWriter> supplier;

	private ResponseFormat(Supplier<ResponseWriter> supplier) {
		this.supplier = supplier;
	}
	
	public ResponseWriter createWriter() {
		return this.supplier.get();
	}
	
	public static ResponseFormat find(String format) {
		return Arrays.stream(ResponseFormat.values())
				.filter(e -> e.name().equals(format.toUpperCase()))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("No format found for string " + format));
	}
	
	public static List<String> getAllPossibleFormats() {
		return Arrays.stream(ResponseFormat.values())
				.map(ResponseFormat::name)
				.collect(Collectors.toList());
	}
}
