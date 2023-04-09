package fr.nacvs.ied_mediator.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtils {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	/**
	 * Formatter for dates like 14 Jun 2011
	 */
	public static final DateTimeFormatter FORMATTER_SHORT_MONTH = DateTimeFormatter.ofPattern("d MMM yyyy")
			.withLocale(Locale.US);

	/**
	 * Create a new instance of LocalDate from a string in the "yyyy-mm-dd" format
	 */
	public static LocalDate toDate(String date) {
		return LocalDate.parse(date, FORMATTER);
	}

	/**
	 * Create a new instance of LocalDate from a string in the "d MMM yyyy" format
	 */
	public static LocalDate toDateShortMonth(String date) {
		return LocalDate.parse(date, FORMATTER_SHORT_MONTH);
	}

	/**
	 * format the date to a string in the "yyyy-mm-dd" format
	 */
	public static String toString(LocalDate date) {
		return date.format(FORMATTER);
	}
}
