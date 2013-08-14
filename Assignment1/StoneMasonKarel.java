/*
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass as it appears here does nothing.
 * When you finish writing it, it should solve the "repair the quad"
 * problem from Assignment 1.  In addition to editing the program,
 * you should be sure to edit this comment so that it no longer
 * indicates that the program does nothing.
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {
/*karel will look towards the public method run first to initilize the program
 *in the run method, while karel's front is cleared, he will travel up the column and back 
 *down, while placing beepers anywhere where one must be placed to fix the quad
 */
	public void run() {
		while (frontIsClear()) {
			ascendColumn();
			descendColumn();
			moveToNextColumn();
		}
		ascendColumn(); //for the final column, because the conditional will not be true,
		descendColumn();//an extra check is put in place after the while loop exits for the 
						//last column
	}
	
	//this method will make karel ascend the column while karel's front is cleared, 
	//then checkForBeepers at every space
	private void ascendColumn() {
		checkForBeeper(); //a special check is put in for the first corner in the column
		turnLeft();
		while (frontIsClear()) {
			move();
			checkForBeeper();
		}
	}
	//this method will make karel descend the column while it's front is cleared, then turn left
	private void descendColumn() {
		turnAround();
		while (frontIsClear()) {
			move();
		}
		turnLeft();
	}
	//this method will make karel move to the next column by moving forward 4 spaces
	private void moveToNextColumn() {
		move();
		move();
		move();
		move();
	}
	//this method will check if a beeper is not present on the current corner, 
	//then place a beeper if the condtional is true
	private void checkForBeeper() {
		if (noBeepersPresent()) {
			putBeeper();
		}
	}
}
