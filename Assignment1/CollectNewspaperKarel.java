/*
 * File: CollectNewspaperKarel.java
 * --------------------------------
 * At present, the CollectNewspaperKarel subclass does nothing.
 * Your job in the assignment is to add the necessary code to
 * instruct Karel to walk to the door of its house, pick up the
 * newspaper (represented by a beeper, of course), and then return
 * to its initial position in the upper left corner of the house.
 */

import stanford.karel.*; //importing stanford's class
//karel will look for original run() method in the source code to start the program
//run will make karel move to the newspaper, pick up the paper, then move back to the 
//original position
public class CollectNewspaperKarel extends SuperKarel {
	public void run() {
		moveToPaper();
		pickUpPaper();
		moveToStart();
	}
//moves karel over to the (x,y) coordinate where the newspaper (beeper) is located	
	private void moveToPaper() {
		move();
		move();
		turnRight();
		move();
		turnLeft();
		move();
	}
//karel will pick up the beeper only if a beeper is present on the current (x,y) coordinate
	private void pickUpPaper() {
		if (beepersPresent()); {
			pickBeeper();
		}
	}
//karel will move back to the original position and coordinate	
	private void moveToStart() {
		turnAround();
		move();
		move();
		move();
		turnRight();
		move();
		turnRight();
	}
}
