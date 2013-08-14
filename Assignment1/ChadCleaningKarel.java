/*
 * File: ChadCleaningKarel.java
 * --------------------------
 *this file will go about cleaning a voting ballot card, removing any extra chad
 *karel may count on the following in the world:
 *will begin one square left of the first rectangle and end one square right of last rectangle
 *ballot rectangle is exactly one space wide and 3 spaces high as shown previously
 *Karel begins immediately to the left of the first ballot rectangle
 *Karel must finish execution facing east at the rightmost edge of the ballo
 */

import stanford.karel.*;

	public class ChadCleaningKarel extends SuperKarel {
		
		public void run() {
			while (frontIsClear()) {	//willcontinue cleaning until end of ballet is reached
				move();
				if (noBeepersPresent()) { 	//conditional to check if vote had been intended  
					cleanChad();			//Karel will check and clean chad on the ballot
				}
				move();
			}
		}
		
		private void cleanChad() {		
			travelToFirstSpot();		//movement for travel to first spot
			while (beepersPresent()) {	//will continue removing beepers until none are left
				pickBeeper();
			}
			travelToSecondSpot();		//movement for travel to second spot
			while (beepersPresent()) {
				pickBeeper();
			}
			returnToOriginalSpot();		//will return karel to position when first entering 
										//the ballot space
		}


		private void travelToFirstSpot() {
			turnLeft();
			move();
		}
		private void travelToSecondSpot() {
			turnAround();
			move();
			move();
		}
		private void returnToOriginalSpot() {
			turnAround();				//karel is returned to the original position 
			move();						//when entering the ballot space
			turnRight();				
		}								
	}