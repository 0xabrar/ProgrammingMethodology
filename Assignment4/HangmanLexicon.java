/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import acm.util.*;
import acm.program.*;
import java.util.*;
import java.io.*;


public class HangmanLexicon extends ConsoleProgram {

	/* This is the HangmanLexicon constructor. */
	public HangmanLexicon() {
		String file = "HangmanLexicon.txt";
		BufferedReader rd = openFileReader(file);
		/* Error checking is mandatory, though it is unlikely an error will occur. */
		try {
			/* While the text file still has lines of texts to iterate through, the lines will be added to an array. */
			while (true) {
				String line = rd.readLine();
				if (line == null) break;
				wordList.add(line);
			} rd.close(); /* The connection to the text file must be closed */
		} catch (IOException ex) {
			throw new ErrorException(ex);
		}
	}
	
/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return wordList.size();
	}

/** Returns the word at the specified index. */
	public String getWord(int index) {
                /* Conditional put in place to make sure that an appropriate value of index is put in. */
		if (index > wordList.size()) throw new ErrorException("getWord: Illegal index");
		return wordList.get(index);
		
	}
	
	/* This private method will return a BufferedReader with the value of the Lexicon text file. */
	 private BufferedReader openFileReader(String prompt) {
			BufferedReader rd = null;
			while (rd == null) {
				try {
					String name = prompt;
					rd = new BufferedReader(new FileReader(name));
				} catch (IOException ex){
					println("Can't open that file");
					throw new ErrorException(ex);
				}
			} return rd;
		}
	
	/* Instance variable used to hold all of the possible different Hangman words. */
	private ArrayList<String> wordList = new ArrayList<String>();
}

