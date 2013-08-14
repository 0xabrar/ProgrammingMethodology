import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import acm.util.ErrorException;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

public class NameSurferDataBase implements NameSurferConstants {
	
/* Constructor: NameSurferDataBase(filename) */
/**
 * Creates a new NameSurferDataBase and initializes it using the
 * data in the specified file.  The constructor throws an error
 * exception if the requested file does not exist or if an error
 * occurs as the file is being read.
 */
	public NameSurferDataBase(String filename) {
		String value = "";
		database = new HashMap<String, String>();
		BufferedReader rd = openFileReader(filename);
		/* This conditional will add the entire text file contents to our database. */
		while (true) {
			try {
				value = rd.readLine();
				if (value == null) break; // Conditional only happens if database entries are done. //
				StringTokenizer tokenizer = new StringTokenizer(value);
                                String key = tokenizer.nextToken();
                                database.put(key, value);
			} catch (IOException ex) {
				throw new ErrorException(ex);
			} 
			
		}
	}
	
/* Method: findEntry(name) */
/**
 * Returns the NameSurferEntry associated with this name, if one
 * exists.  If the name does not appear in the database, this
 * method returns null.
 */
	public NameSurferEntry findEntry(String name) {
            if (database.containsKey(name)) {
                NameSurferEntry entry = new NameSurferEntry(database.get(name));
                return entry;
            } return null;
            
            
	}
	
	/* This method is used to associate a BufferedReader to a file. */
	private BufferedReader openFileReader(String prompt) {
		BufferedReader rd = null;
		while (rd == null) {
			try {
				rd = new BufferedReader(new FileReader(prompt));
			} catch (IOException ex) {
				throw new ErrorException(ex);
			}
		}
		return rd;
	}
	
	private Map<String, String> database;
}

