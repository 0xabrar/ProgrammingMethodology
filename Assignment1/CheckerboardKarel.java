/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment 1.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {
	/*
	 * run method will continue to run  while the path infront of karel is clear 
	 *karel will:
	 *place all beepers in the current row,
	 *position itself for the new line, 
	 *move to the new line as well as reposition on new line
	 */
	public void run() {
		if (frontIsBlocked()) { //special code added in for single column checkerboard
			turnLeft();			//this workaround seems like bad code practice
			placeBeepers();
		}
		while (frontIsClear()) {
			placeBeepers();
			faceNewDirection();
			if (frontIsClear()) {  //only if frontIsClear will karel move to the 
				moveToNewRow();    //next row and reposition
			}
		}
		
	}
	
	private void placeBeepers() { //this method will place beepers amongst the entire row
		while (frontIsClear()) {  	//while the front is cleared, karel will put a beeper, 
			putBeeper();          	//and then check to see if it can move forward 2 spaces
			checkToMove();
		}
		oddCheck(); 			  //extra check added in to see if checkerboard is odd in (x,y)
	}
	private void faceNewDirection() { //karel will position itself to face the next row
		if (facingEast()) { 		  
			turnLeft();
		}	
		else {
			turnRight();
		}
	}
	private void moveToNewRow() {   //karel will move to the next row, and reposition
		if (beepersPresent()) {			//there will be a check to see if a beeper is present
			move();						
			repositionKarel();			
			move();						//karel will move one extra space if beeper present
		}
		else {
			move();
			repositionKarel();			
		}
	}

	private void checkToMove() {	//this is the method which checks if the path is clear to
		if (frontIsClear()) {		//move in while adding beepers to a row
			move();
		}
		if (frontIsClear()) {
			move();
		}
	}
	private void oddCheck() {
		oddCheckMovement();		//karel turns around, and moves 2 corners
		if (beepersPresent()) { //if beepers are present, karel will move back and add a beeper
			oddCheckMovement();	
			putBeeper();
		}
		else {					//if not, then karel will simply move back	
			oddCheckMovement();
		}
	}
	private void repositionKarel() {	//will reposition karel on a new row to face the 
		if (leftIsBlocked()) {			//correct direction
			turnRight();
		}
		else {
			turnLeft();
		}
	}
	
	private void oddCheckMovement() {  //movement for the oddCheck
		turnAround();
		move();
		move();
	}
}
