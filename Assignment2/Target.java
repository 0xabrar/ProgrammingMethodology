/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 */




import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Target extends GraphicsProgram {	
//	this code may be inefficient and will most likely need to be 
//	rewritten in the future
//majority of this is uncommented
	public void run() {
		//variables which will determine the size of the 3 different circles
		int largeCircle = 72;
		double mediumCircle =  0.65 * largeCircle;
		double smallCircle =  0.3 * largeCircle; 
		
		//will be used for target positions
		int currentX = 0;
		int currentY = 0;

		//will repeat the code for the 3 different parts of the target
		for (int i = 0; i != 3; i++) {
			switch (i) {
			case 0:
				currentX = getWidth() / 2 - largeCircle / 2;
				currentY = getHeight() / 2 - largeCircle / 2;
				GOval target = new GOval (currentX, currentY, largeCircle , largeCircle);
				target.setFilled(true);
				target.setFillColor(Color.RED);
				add (target);
			case 1:
				currentX = getWidth() / 2 - (int)mediumCircle / 2;
				currentY = getHeight() / 2 - (int)mediumCircle / 2;
				GOval target2 = new GOval(currentX, currentY, mediumCircle , mediumCircle);
				target2.setFilled(true);
				target2.setFillColor(Color.WHITE);
				add(target2);
			default: 
				currentX = getWidth() / 2 - (int)smallCircle / 2;
				currentY = getHeight() / 2 - (int)smallCircle / 2;
				GOval target3 = new GOval(currentX, currentY, smallCircle , smallCircle);
				target3.setFilled(true);
				target3.setFillColor(Color.RED);
				add(target3);
			 
			}
			
		}
	}
}
