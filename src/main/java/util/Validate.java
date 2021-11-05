package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class Validate {

	public static boolean validateEmail(String email) {
		Pattern pattern = Pattern.compile("^(.+)@(.+)$");
		return pattern.matcher(email).matches();
	}
	
	public static LocalDate validateFecha(String fecha) {
		LocalDate date;
		
		try {
			date = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("d/M/yyyy"));
		} catch(DateTimeParseException dfe) {
			return null;
		}
		
		return date;
	}
	
}
