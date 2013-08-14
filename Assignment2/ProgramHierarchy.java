/*
 * File: ProgramHierarchy.java
 * Name: 
 * Section Leader: 
 * ---------------------------
 * This file is the starter file for the ProgramHierarchy problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;
//this code may be inefficient, and will need to be revised in the future
//comments have been added 
public class ProgramHierarchy extends GraphicsProgram {	
	
	private static final double WINDOW_HEIGHT = 50;
	private static final double WINDOW_WIDTH = 140;
	
	public void run() {
		//the following lines will create all of the variables necessary to create our hierarchy
		double currentX = getWidth() / 2 - WINDOW_WIDTH * 2;
		double currentY = getHeight() / 2 + WINDOW_HEIGHT / 2;
		double lineX = getWidth() / 2;
		double lineY = getHeight() / 2 - WINDOW_HEIGHT / 2;
		String currentString = ("");	//creates the string once for efficiency 
		boolean lastBoxCheck = false; //special boolean used for last box (program box)
		
		for (int i = 0; i != 4; i++) {
			//the value of the string we had created earlier will change according to the number
			//of the iteration, or i
			switch (i) {
			case 0: currentString = "GraphicsProgram"; break;
			case 1: currentString = "ConsoleProgram"; break;
			case 2: currentString = "DialogProgram"; break;
			//this iteration is an exception; this is created so that 
			//on the final iteration the value of y will change to increase a level in the hierarchy
			default: {
				currentString = "Program";
				currentX = lineX - WINDOW_WIDTH / 2;
				currentY = lineY - WINDOW_HEIGHT;
				lastBoxCheck = true;
			}
			
			}
			//a box will be added as well as a line that goes to the program box in the hierarchy
			add(new GRect(currentX, currentY, WINDOW_WIDTH, WINDOW_HEIGHT));
			if (lastBoxCheck) {
				//nothing will happen if it is the program box
			} else add(new GLine (currentX + WINDOW_WIDTH / 2, currentY, lineX, lineY));
			GLabel label = new GLabel(currentString);
			//will position the label in the centre of their respective boxes
			add(label, currentX + WINDOW_WIDTH / 2 - label.getWidth() / 2,currentY + WINDOW_HEIGHT / 2 + label.getAscent() / 2);
			//currentX is increased in order for the next iteration of the loop
			//to create the next box
			currentX += WINDOW_WIDTH * 1.5;
		}
	}
}

