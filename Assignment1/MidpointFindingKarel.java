/*
 * File: MidpointFindingKarel.java
 * -------------------------------
 * When you finish writing it, the MidpointFindingKarel class should
 * leave a beeper on the corner closest to the center of 1st Street
 * (or either of the two central corners if 1st Street has an even
 * number of corners).  Karel can put down additional beepers as it
 * looks for the midpoint, but must pick them up again before it
 * stops.  The world may be of any size, but you are allowed to
 * assume that it is at least as tall as it is wide.
 */

import stanford.karel.*;

public class MidpointFindingKarel extends SuperKarel {
	
	/*
	 * karel will do 3 basic steps in order to find the midpoint
	 * karel will:
	 * place beepers on every corner in the entire row
	 * take 2 beepers away - one from the most right corner, one from the most left corner
	 * will travel back and fourth between the 2 sides removing beepers one at a time
	 * add a final beeper once the midpoint is found
	 */
	public void run() { 
		placeBeepers();
		setupForRetrieval();
		retrieveBeepers();
		putBeeper();		//due to the code of retrieveBeepers, a final beeper must be added
	}
	
	private void placeBeepers() {
		while (frontIsClear()) { 	//karel will continue to place a beeper along the row 
			putBeeper();  			//and then move forward 1 corner while frontIsClear
			move();
		}
		putBeeper();				//fixes the fence post error
	}
	private void setupForRetrieval() {	//karel will pick up beepers from the total right
		pickBeeper();					//and total left corners of the row
		turnAround();
		while (frontIsClear()) {		
			move();
		}
		pickBeeper();					
		turnAround();					//karel will return to the original position
		move();							//and then move forward a space
	}
	private void retrieveBeepers() {
		while (beepersPresent()) {
			move();
			if (noBeepersPresent()) {		
				removeBeeper();	//as soon as the previous loop ends, a call is made to a method
			}					//to remove the nearest beeper
		}
	}
	
	private void removeBeeper() {		//this method executes all of the regular karel 
		turnAround();					//commands needed to remove the nearest beeper
		move();
		pickBeeper();
		move();
		checkForMidpoint();				//after every time a beeper is removed, there will be 
	}									//a check to see if we have attained the midpoint
	private void checkForMidpoint() {
		if (noBeepersPresent()) {
			turnAround();				//motion turns karel back towards the midpoint, as 
			move();						//previously it was 1 corner away by default in all 
		}								//scenarious
	}
	

}
