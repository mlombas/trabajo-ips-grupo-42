package util.csv;

import java.io.File;
import java.io.FileNotFoundException;

public class CSVCreator {
	public static CSVTable<String, String> read(String fname) throws FileNotFoundException {
		return read(fname, "\s*,\s*");
	}
	
	public static CSVTable<String, String> read(String fname, String split) throws FileNotFoundException {
		File f = new File(fname);
		CSVReader reader = new CSVReader(f, split);
		
		CSVTable<String, String> table = reader.read();
		
		reader.close();
		
		return table;
	}
}
