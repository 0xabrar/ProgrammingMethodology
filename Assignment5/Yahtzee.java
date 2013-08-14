/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}
	
	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	private void playGame() {
            initPlayerScores();
            /* Game will run until all of the categories have been filled out. */
            for (int i = 0; i < N_SCORING_CATEGORIES; i++) {
                /* The turn process is repeated for each player in the game. */
                for (int j = 1; j <= nPlayers; j++) {
                    /* All values of the isDieNotSelected array are set to be true for the first roll. */
                    for (int k = 0; k < N_DICE; k++) {
                            isDieSelected[k] = true;
                    }
                    iteratePlayerTurn(j);
                    updateScoreDisplay(j);
                }
            }
            
            /* The final bonus check is added in. */
            int bonus = 0;
            for (int i = 1; i <= nPlayers; i++) {
                if (lowerscore > 63) bonus = 35;
                display.updateScorecard(UPPER_BONUS, i, bonus);
                updateScoreDisplay(i);
            }
            
            /* A message indicating who has won the game is displayed. */
            int winnerScore = 0;
            int whichWinner = 0;
            for (int i = 0; i < totalscores.length - 1; i++) {
                if (totalscores[i] > winnerScore) {
                    winnerScore = i;
                    whichWinner = i;
                    break;
                }
            }
            display.printMessage("Congratulations, " + playerNames[whichWinner] + " you're the winner"
                    + " with a total score of " + totalscores[whichWinner] + "!");
            
            
            
            
	}
        
        /* This method will proces a player's turn, dependent on the variable player. */
        private void iteratePlayerTurn(int player ) {
            
            display.printMessage(playerNames[player - 1] + "'s turn! 'Roll Dice' button to roll the dice.");
            display.waitForPlayerToClickRoll(player);
            rollDice();
            
            /* For loop in which the user is allowed to select their choice in die changes twice. */
            for (int i = 0; i < 2; i++) {
                display.printMessage("Select the dice you wish to re-roll and click 'Roll Again.' ");
                display.waitForPlayerToSelectDice();
                /* A check will be done to see if any die have been selected. */
                for (int j = 0; j < N_DICE; j++) {
                    if (display.isDieSelected(j)) isDieSelected[j] = true;
                }
                rollDice();
            } 
            
            /* All actions regarding category selection is done here. */
            display.printMessage("Select a category for this roll.");
            int category = display.waitForPlayerToSelectCategory();
            /* A check for the category validity is put in place here */
            while (!isCategoryValid(player, category)) {
                display.printMessage("Please select a valid category.");
                category = display.waitForPlayerToSelectCategory();
            }
            
            int score = getRolledScore(category);
            display.updateScorecard(category, player, score);
            /* The tracking system of the player's scores are updated. */
            updatePlayerScoreArray(player, category, score);
            
        }
        
        /* This method will return an integer value of the score value of the rolled dice. */
        private int getRolledScore(int category) {
            int score = 0;
            /* Depending on the category which the user had selected, the score will be calculated in a different way. */
            switch (category) {
                case THREE_OF_A_KIND: 
                    if (!YahtzeeMagicStub.checkCategory(dice, THREE_OF_A_KIND)) break;
                    for (int i = 0; i < N_DICE; i++) score += dice[i]; break;
                case FOUR_OF_A_KIND: 
                    if (!YahtzeeMagicStub.checkCategory(dice, FOUR_OF_A_KIND)) break;
                    for (int i = 0; i < N_DICE; i++) score += dice[i]; break;
                case FULL_HOUSE:
                    if (!YahtzeeMagicStub.checkCategory(dice, FULL_HOUSE)) break;
                    score = 25; break;
                case SMALL_STRAIGHT:
                    if (!YahtzeeMagicStub.checkCategory(dice, SMALL_STRAIGHT)) break;
                    score = 30; break;
                case LARGE_STRAIGHT:
                    if (!YahtzeeMagicStub.checkCategory(dice, LARGE_STRAIGHT)) break;
                    score = 40; break;
                case YAHTZEE: 
                    if (!YahtzeeMagicStub.checkCategory(dice, YAHTZEE)) break;
                    score = 50; break;
                case CHANCE:
                    for (int i = 0; i < N_DICE; i++) score += dice[i]; break;
                /* Normally, all of the values of the selected category are added together. */    
                default: 
                    score = addDiceSum(category); break;
            }
            return score;
        }
        
        /* This method will iterate through the dice array, and add together whichValue numbers. */
        private int addDiceSum (int whichValue) {
            int x = 0;
            for (int i = 0; i < dice.length; i++) {
                if (dice[i] == whichValue) x+= whichValue;
            } return x;
        }
        
        /* This method will create new values for the dice randomly. The dice will then be displayed. */
        private void rollDice () {
            for (int i = 0; i < N_DICE; i++) {
                /* Die will only create a new value if it has been selected */
                if (isDieSelected[i]) dice[i] = rgen.nextInt(1,6);
            }
            display.displayDice(dice);
            resetDiceSelection();
        }
        
        /*This private method initilizes the array which keeps track of all of the player scores. */
        private void initPlayerScores () {
            playerScores = new int [nPlayers][N_SCORING_CATEGORIES];
            for (int i = 0; i < nPlayers; i++) {
                for (int j = 0; j < N_SCORING_CATEGORIES; j++) {
                    playerScores[i][j] = -1;
                }
            }
            
            totalscores = new int[nPlayers];
            for (int i = 0; i < nPlayers; i++) {
                totalscores[i] = 0;
            }
        }
        
        private void updatePlayerScoreArray(int player, int category, int score) {
            
            int playertmp = player - 1;
            int categorytmp = category - 1;
            
            /* The playerScores array is updated. */
            if (category > SIXES) categorytmp -= 2;
            playerScores[playertmp][categorytmp] = score;

        }
        
        /* This method will update the total scores for the game. */
        private void updateScoreDisplay(int player) {
            int playertmp = player - 1;
            
            /* The graphical upper total scores is updated using our score matrix. */
            int upperscore = 0;
            for (int i = 0; i < 6; i++) {
                if (playerScores[playertmp][i] != -1) upperscore += playerScores[playertmp][i]; 
            } display.updateScorecard(UPPER_SCORE, player, upperscore);
            
            /* The graphical lower total score is updated using our score matrix. */
            lowerscore = 0;
             for (int i = 6; i < N_SCORING_CATEGORIES; i++) {
                if (playerScores[playertmp][i] != -1)
                    
                    lowerscore += playerScores[playertmp][i];
                
            } display.updateScorecard(LOWER_SCORE, player, lowerscore);
            /* The total score is updated. */
            totalscores[playertmp] = upperscore + lowerscore;
            display.updateScorecard(TOTAL, player, totalscores[playertmp]);
            
        }
        
        /*This method will be used to reset the dice selection. */
        private void resetDiceSelection() {
            for (int i = 0; i < N_DICE; i++) {
                isDieSelected[i] = false;
            }
        }
        

        
        
        /* This method will return true or false based on whether the user-selected category has been selected before. */
        private boolean isCategoryValid(int player, int category) {
            /* Conversion is done to make sure that category values match playerScore array values. */
            player -= 1;
            category -= 1;
            if (category > SIXES) category -= 2;
            if (playerScores[player][category] != -1) return false;
            return true;
        }
        
      
		
        /* Private instance variables */ 
        private int lowerscore = 0;
	private int nPlayers;
        private int[] dice = new int[N_DICE];
        private boolean[] isDieSelected = new boolean[N_DICE];
	private String[] playerNames;
        private int[][] playerScores;
        private int[] totalscores;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();

}
