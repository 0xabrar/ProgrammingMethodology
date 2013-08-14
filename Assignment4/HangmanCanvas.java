/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;
import java.awt.*;

public class HangmanCanvas extends GCanvas {

/** Resets the display so that only the scaffold appears */
	public void reset() {
		removeAll();
		int x = (getWidth() / 2) - BEAM_LENGTH;
		int y = (getHeight() / 4) - (SCAFFOLD_HEIGHT / 4);
		add(new GLine(x, y, x, y + SCAFFOLD_HEIGHT));
		add(new GLine(x, y, x + BEAM_LENGTH, y));
		x += BEAM_LENGTH;
		add(new GLine (x, y, x, y + ROPE_LENGTH));
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		label.setVisible(false);
		label = new GLabel(word);
		label.setFont(new Font("Georgia", Font.BOLD, 30));
		double x = (getWidth() / 2) - (label.getWidth() / 2);
		double y = (getHeight() - 50);
		add(label, x, y);
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(char letter) {
		updateIncorrectLetters(letter);
		/* Dependent on which run of the method this is, a certain body part of the man will be added. */
		/* All of these switch cases will execute in the exact order that they have been coded. */
		/* The parts will be added in the following order: 
		 * Head, body, left arm, right arm, left leg, right leg, left foot, right foot. */
		switch (guessesWrong) {
			case 0: {
				x = getWidth() / 2; //Head//
				y = (getHeight() / 4) - (SCAFFOLD_HEIGHT / 4) + ROPE_LENGTH;
				add(new GOval(x - (HEAD_RADIUS / 2), y, HEAD_RADIUS, HEAD_RADIUS));
				y += HEAD_RADIUS; 
				break;
			}
			case 1: { //Body//
				add(new GLine(x, y, x, x + BODY_LENGTH - LEG_LENGTH));
				y += ARM_OFFSET_FROM_HEAD;
				break;
			}
			case 2: { //Left Arm//
				add(new GLine(x, y, x - UPPER_ARM_LENGTH, y));
				add(new GLine(x - UPPER_ARM_LENGTH, y, x - UPPER_ARM_LENGTH, y + LOWER_ARM_LENGTH));
				break;
			}
			case 3: { //Right Arm//
				add(new GLine(x, y, x + UPPER_ARM_LENGTH, y));
				add(new GLine(x + UPPER_ARM_LENGTH, y, x + UPPER_ARM_LENGTH, y + LOWER_ARM_LENGTH));
				y += BODY_LENGTH - ARM_OFFSET_FROM_HEAD;
				break;
			}
			case 4: { //Left Leg//
				add(new GLine(x, y, x - (HIP_WIDTH), y));
				add(new GLine(x - (HIP_WIDTH), y, x - (HIP_WIDTH), y + LEG_LENGTH));
				break;
			}
			case 5: { //Right Leg//
				add(new GLine(x, y, x + (HIP_WIDTH), y));
				add(new GLine(x + (HIP_WIDTH), y, x + (HIP_WIDTH), y + LEG_LENGTH));
				y += LEG_LENGTH;
				break;
			}
			case 6: { // Left Foot//
				add(new GLine(x - (HIP_WIDTH), y, x - (HIP_WIDTH) - FOOT_LENGTH, y));
				break;
			}
			case 7: { //Right Foot//
				add(new GLine(x + (HIP_WIDTH), y, x + (HIP_WIDTH) + FOOT_LENGTH, y));
				break;
			}
			default: return;
		}
		guessesWrong++;
	}
	
	/* This method will update the list of incorrectly guessed words. */
	private void updateIncorrectLetters (char letter) {
		/* If the letter has already been incorrectly guessed, it is not added to this list. */
		if (!letterDisplayed(letter)) {
			displayedLettersGuessed.setVisible(false);
			lettersGuessed += letter;
			displayedLettersGuessed = new GLabel(lettersGuessed);
			displayedLettersGuessed.setFont(new Font("Georgia", Font.PLAIN, 16));
			double x = (getWidth() / 2) - (displayedLettersGuessed.getWidth() / 2);
			double y = (getHeight() - 25);
			add(displayedLettersGuessed, x, y);
		}
	}
	
	
	/* Predicate method which checks if the current letter has already been guessed incorrectly. */
	private boolean letterDisplayed (char letter) {
		String tempLetter = Character.toString(letter);
		if (lettersGuessed.contains(tempLetter)) return true;
		return false;
	}
	
	
	
	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	
	/* Private instance variables which are used for graphical application. */
	private GLabel label = new GLabel("");
	private String lettersGuessed = "";
	private GLabel displayedLettersGuessed = new GLabel(lettersGuessed);
	private int guessesWrong = 0;
	
	private int x;
	private int y;
	

}
