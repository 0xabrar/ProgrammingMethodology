/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - ((NBRICKS_PER_ROW - 1) * BRICK_SEP)) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;

/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
			setup();
			runGame();
		
	}
	
	//method is used to setup the initial breakout world prior to gameplay//
	//that includes the bricks as well as the paddle
	private void setup() {
		int y = BRICK_Y_OFFSET;
		for (int i = 0; i < NBRICK_ROWS; i++) {
			int x = BRICK_SEP / 2;
			//switch will alter the brick colours on the desired rows
			//default allows for 2 rows in a series to be 1 colour
			switch (i) {
				case 0: color = color.red; break;
				case 2: color = color.orange; break;
				case 4: color = color.yellow; break;
				case 6: color = color.green; break;
				case 8: color = color.cyan; break;
				default: color = color; break;
			}
			//a series of bricks will go across the entire screen width on a set y value
			for (int j = 0; j < NBRICKS_PER_ROW; j++) {
				GRect brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
				brick.setFilled(true);
				brick.setFillColor(color);
				//will remove the black outline of the squares, then add the brick
				brick.setColor(Color.WHITE);
				add(brick);
				x += BRICK_WIDTH + BRICK_SEP;
				brickCount++;
			}
			y += BRICK_HEIGHT;
			
		}
		
		//paddle will be added at the bottom of the screen, centered//
		y = HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT;
		paddleX = (WIDTH / 2) - (PADDLE_WIDTH / 2); 
		paddle = new GRect(paddleX, y, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		add(paddle);
		
		
	}
	
	private void runGame() {
		addMouseListeners();
		GLabel waiting = new GLabel("Press the left mouse button to start the turn.");
		double x = WIDTH / 2 - waiting.getWidth() / 2;
		double y = HEIGHT / 2 - waiting.getHeight() / 2;
		add(waiting, x, y);
		while (true) {
			if (clickedStart) {
				remove(waiting);
				break;
			} pause(300);
		}
		//game will only run if the player has not already lost
		if (numberOfTurns < NTURNS) {
			//ball will be added to the total centre of the screen//
			y = HEIGHT / 2  - BALL_RADIUS;
			x = WIDTH / 2 - BALL_RADIUS;
			ball = new GOval(x, y, BALL_RADIUS*2, BALL_RADIUS*2);
			ball.setFilled(true);
			add(ball);
			
			
			
			//velocity of ball is only initilized once per game run / once per turn
			vy = 3.0;
			vx = rgen.nextDouble(1.0, 3.0);
			if (rgen.nextBoolean(0.5)) vx = -vx;
			
			//ball will continue to move until it hits the ground or all bricks are hit
			while (true) {
				ball.move(vx, vy);
				//ball.setLocation(ball.getX() + vx, ball.getY() + vy);
				checkForWall();
				GObject collider = getCollidingObject();
				//react with objects will occur if the ball touches an object
				if (collider != null) {
					if (collider == paddle) {
						vy = -vy;
						bounceClip.play();
						colliderFix = true;
					} else {
						vy = -vy;
						remove(collider);
						bounceClip.play();
						brickCount--;
					}
				}
				if (ball.getY() + (4 * BALL_RADIUS) > HEIGHT) {
					numberOfTurns++;
					break;
				} else if (brickCount == 0) {
					numberOfTurns = 3;
					break;
				}
				pause(10);
				
			}
			//start next turn of game
			remove(ball);
			pause(1000);
			clickedStart = false;
			runGame();
		}
		
		//condtionals will display 2 different messages dependant on whether the player has won or lost the game
		else if (brickCount == 0) {
			GLabel youWin = new GLabel("You have just won the game.");
			x = WIDTH / 2 - youWin.getWidth() / 2;
			y = HEIGHT / 2 - youWin.getHeight() / 2;
			add(youWin, x, y);
			
		} else {
			GLabel youLose = new GLabel("You have just lost the game.");
			x = WIDTH / 2 - youLose.getWidth() / 2;
			y = HEIGHT / 2 - youLose.getHeight() / 2;
			add(youLose, x, y);
		}
		

	}
	
	//method which alternates the velocities to opposites x or y values if a wall is hit
	private void checkForWall() {
		if (ball.getY() < 0) {
			vy = -vy;
		} else if ((ball.getX() + (2 * BALL_RADIUS) > WIDTH) || (ball.getX() < 0)) {
			vx = -vx;
		}
	}
	
	//this method will return the value of any object that the ball may have collided with
	//a finding on any corner stops all other checks
	private GObject getCollidingObject() {
			  //special conditional put in to stop ball from 'glueing' to paddle
			  if (colliderFix) {
				  colliderFixInt += 1;
				  if (colliderFixInt == 6) {
					  colliderFix = false;
					  colliderFixInt = 0;
				  }
				  return null;
			   //check the bottom left corner
			  } else if (getElementAt(ball.getX(), (ball.getY() + (2 * BALL_RADIUS))) != null) {
				return getElementAt(ball.getX(), (ball.getY() + (2 * BALL_RADIUS)));
		      //check the bottom right corner
			} else if (getElementAt(ball.getX() + (2 * BALL_RADIUS), (ball.getY() + (2 * BALL_RADIUS))) != null) {
				return getElementAt(ball.getX() + (2 * BALL_RADIUS), (ball.getY() + (2 * BALL_RADIUS)));
			  //check the top right corner
			} else if (getElementAt(ball.getX(), ball.getY()) != null ) {
				 return getElementAt(ball.getX(), ball.getY());
		       //check the top right corner
			 } else if (getElementAt(ball.getX() + (2 * BALL_RADIUS), ball.getY()) != null) {
				 return getElementAt(ball.getX() + (2 * BALL_RADIUS), ball.getY());
			   //check the bottom left corner
			 } 
			 //return null otherwise
			 return null;
		
	}
	
	//this method will be used to control the paddle at the bottom of the screen//
	public void mouseMoved (MouseEvent e) {
		paddleX = e.getX() - (PADDLE_WIDTH / 2) ;
		//conditions put in place so that paddle doesnt extend beyond the screen
		if (paddleX < (PADDLE_WIDTH / 2)) {
			paddleX = 0;
		} else if (paddleX > (WIDTH - PADDLE_WIDTH)) {
			paddleX = WIDTH - PADDLE_WIDTH;
		}
		if (clickedStart) {
			paddle.setLocation(paddleX, (HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT));
		}
		
	}
	
	//mouse event used to start a game turn
	public void mouseClicked (MouseEvent e) {
		clickedStart = true;
	}
	
	
	//private instance variable referring to number of turns left in game
	private int numberOfTurns = 0;
	//private instance varialble referring to bricks
	private int brickCount;
	private Color color;
	//private instance variables for the paddle
	private int paddleX;
	private GRect paddle;
	//special instance variables put in to fix up 'glueing' of the ball to the collider
	private boolean colliderFix;
	private int colliderFixInt = 0;
	//private instance variables for the ball
	private GOval ball;
	private double vx;
	private double vy;
	//used for audio in the game
	AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
	
	private boolean clickedStart;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	

}
