/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.program.*;
import acm.util.*;


public class Hangman extends ConsoleProgram {

    public void run() {
    	setup();
    	/* Game will end only if the player has run out of guesses or if they've guessed the word correctly. */
    	while (guessesLeft != 0) {
    		runGame();
    		if (hasWon()) break;
    	}
    	/* A victory of defeat message will be printed dependent on the number of guesses left. */
    	if (guessesLeft > 0) {
    		println("You guessed the word: " + word);
    		println("You win.");
    	}
    	else {
    		println("The word was: " + word);
    		println("You have just lost the game.");
    	}
    }
    
    /* Used to add the graphical window to the game */
	public void init() {
    	canvas = new HangmanCanvas();
    	add(canvas);
    }
   
	
    /* This method will be used to setup the game. This includes setup of the random word and empty array. */
    private void setup() {
    	canvas.reset();
    	println("Welcome to Hangman.");
    	word = lexicon.getWord(rgen.nextInt(0, lexicon.getWordCount()));
    	wordGuessed = new String[word.length()];
    	guessesLeft = 8;
    	/*Every element in the empty array 'wordGuessed' is then filled with a '-' character. */
    	for (int i = 0; i != word.length(); i++) {
    		wordGuessed[i] = "-";
    	}
    	/* The next line will display the guessedWord on the canvas. */
    	canvas.displayWord(contentsWordGuessed());
    }
    
    private void runGame() {
    	println("The word now looks like this: " + contentsWordGuessed());
    	println("You have " + guessesLeft + " guesses left.");
    	String currentGuess = readLine("Your guess: ");
    	/* User will be asked to input guess again while guess is not valid. */
    	while (!isValidGuess(currentGuess)) {
    		println("That was not a valid guess. Enter a single character which you have not yet guessed correctly.");
    		currentGuess = readLine("Your guess: ");
    	}
    	checkWord(currentGuess);
    }
    
    /* This method will go through the game word and check each character to see if the guess matches any of them. 
     * Then, the wordGuessed array will be updated with the new letters. If no letters are found, 
     * the guessesLeft will change accoridngly, and the user will be notified (parts added to canvas). */
    private void checkWord(String guess) {
    	boolean lettersFound = false;
    	/* This part goes through all of the word and checks the guess. */
    	for (int i = 0; i < word.length(); i++) {
    		char tmp = word.charAt(i);
    		String currentLetter = Character.toString(tmp);
    		/* This conditional updates the wordGuessed array accordingly to the guess and the word. */
    		if (currentLetter.equalsIgnoreCase(guess)) {
    			wordGuessed[i] = guess.toUpperCase();
    			lettersFound = true;
    		}
    	}
    	/* Canvas word is updated in next line. */
    	canvas.displayWord(contentsWordGuessed());
    	if (!lettersFound) hangmanMore(guess);
    	else println("That guess is correct.");
    }
    
    /* This method executes when the user guesses incorrectly. */
    private void hangmanMore(String guess) {
    	/* Canvas object is hung more, and a message is displayed showing that the guess was wrong. guessesLeft is reduced. */
    	char letter = guess.charAt(0);
    	letter = Character.toUpperCase(letter);
    	println("There are no " + letter + "'s in the word."); 
    	guessesLeft--;
    	canvas.noteIncorrectGuess(letter);
    }
    
    /* This  method will simply return the contents of the wordGuessed array. */
    private String contentsWordGuessed() {
    	String contentsWordGuessed = "";
    	/* Each element of the wordGuessed array is added to a String. */
    		for (int i = 0; i < wordGuessed.length; i++) {
    			contentsWordGuessed += wordGuessed[i];
    		}
    	return contentsWordGuessed;
    }
    
    /* This predicate method will return false in any sitaution that the following conditions aren't met.
     * A: The guess is not a single letter.
     * B: The guess is not a letter at all.
     * c: The letter has already been guessed correctly. */
    private boolean isValidGuess(String guess) {
    	if (guess.length() != 1) return false;
    	char letter = guess.charAt(0);
    	if (!Character.isLetter(letter)) return false;
    	for (int i = 0; i < wordGuessed.length; i++) {
    		if (wordGuessed[i].equals(guess.toUpperCase())) return false;
    	}
    	/* Method will return true only if all specifications of the guess have been validated. */
    	return true;
    }
    
    /* Method used to check if the user has guessed the entire word correctly. */
    private boolean hasWon() {
    	/* Every letter of the array wordGuessed will be checked to see if '-' exists in it; in which case, false is returned */
    	for (int i = 0; i < wordGuessed.length; i++) {
    		if (wordGuessed[i].equals("-")) return false;
    	}return true;
    }
 
    
    /* Private instance variables */
    private RandomGenerator rgen = RandomGenerator.getInstance();
    
    /*Instance variable for the Hangman canvas */
    private HangmanCanvas canvas;
    
    /* Used to store the value of the word in the game */
    private HangmanLexicon lexicon = new HangmanLexicon();
    private String word = "";
    /* Used to store the value of the guessed word in the game. It has equal elements to the length of word. */
    private String[] wordGuessed = new String[word.length()];
    /* Used to track the number of guesses the user has */;
    private int guessesLeft = 8;
    
    
}
