/*
 * File: Pyramid.java
 * Name: 
 * Section Leader: 
 * ------------------
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Pyramid extends GraphicsProgram {

/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 3;

/** Width of each brick in pixels */
	private static final int BRICK_HEIGHT = 2;

/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 500;
	
	public void run() {
		/*
		 * an extra variable is created to calculate the current row's number of bricks
		 * currentY starts off substracting the height as a fix so that the 
		 * starting row starts not at the very bottom of the window, but just a bit above
		 * 
		 */
		int currentBricks = BRICKS_IN_BASE;	
		int currentY = getHeight() - BRICK_HEIGHT;	
		
		//will repeat this process for all the predetermined rows of the pyramid
		while (currentBricks != 0) {
			//currentX first goes halfway to the screen, then has half of the current row's size subtracted as well
			int currentX = getWidth() / 2 - ((BRICK_WIDTH * currentBricks) / 2);
			//process will repeat dependent on current number of bricks in the row
			for (int i = 0; i != currentBricks; i++) {
				add(new GRect(currentX, currentY, BRICK_WIDTH, BRICK_HEIGHT));
				currentX += BRICK_WIDTH;	//fixes x position for next GRect object
			}
			currentY -= BRICK_HEIGHT;	//fixes y position for next row of pyramid
			currentBricks--;
		}
		
		
		
		
	}
}

