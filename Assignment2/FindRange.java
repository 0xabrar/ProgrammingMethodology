/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {
	
	private static final int SENTINEL = 0;
	
	public void run() {
		//states all of the variables which will be needed in this program
		//all variables start off with the same value as the SENTINEL in case the user
		//inputs the SENTINEL on the first iteration
		int lowestNumber = 0;
		int highestNumber = 0;
		int currentNumber = 0;
		
		println("This program finds the largest and smallest numbers. The SENTINEL is " + SENTINEL);
		
		while (true) {
			currentNumber = readInt("?");
			if (currentNumber == SENTINEL) break; //exit loop if SENTINEL is typed
			//will check if the current number is a canidate for lowest number or highest number
			if (currentNumber > highestNumber) highestNumber = currentNumber;
			if (currentNumber < lowestNumber || (currentNumber == highestNumber && lowestNumber == 0)) lowestNumber = currentNumber;

		}
		//extra conditional put in place just in case the user
		//inputs the SENTINEL on the first iteration
		if ((lowestNumber == 0) && (highestNumber == 0)){
			println ("You had entered the SENTINEL number on your first iteration.");
		} else {
			println("The lowest number is " + lowestNumber + " and the highest number is " + highestNumber + ".");			
		}

	}
}

