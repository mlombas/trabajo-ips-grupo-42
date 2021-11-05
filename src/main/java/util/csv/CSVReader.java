package util.csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CSVReader {

	private Scanner reader;
	private String splitPattern;

	public CSVReader(File file, String splitPattern) throws FileNotFoundException {
		if(!file.exists())
			throw new FileNotFoundException();
		
		reader = new Scanner(file);
		this.splitPattern = splitPattern;
	}
	
	public CSVTable<String, String> read() {
		if(!reader.hasNextLine())
			throw new RuntimeException("CSV has no heads");
		
		String line = reader.nextLine();
		List<String> heads = Arrays.asList(line.split(splitPattern));
		
		CSVTable<String, String> table = new CSVTable<>(heads);
		
		while(reader.hasNextLine())
			table.addRow(
					Arrays.asList(
						reader.nextLine().split(line)
					));
		
		return table;
	}
	
	public void close() {
		reader.close();
	}

}
