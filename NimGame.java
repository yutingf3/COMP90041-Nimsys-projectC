/* Nimsys.java
 * This class controls the overall Nim game process
 * Author: Yuting Feng
 * Student ID:896336
 * Created on 17/5/18
 */
import java.io.Serializable;

public class NimGame implements Serializable{
	public NimGame() {}
	private int upperBound = 0;
	private int theRestOfStoneCount = 0;
	private int player1 = 0;
	private int player2 = 0;
	
	//the upper bound of stones
	public int getUpperBound() {
		return upperBound; 
	}
	public void setUpperBound(int upperBound){
		this.upperBound = upperBound;
	}
	
	//the number of initial stones
	public int getTheRestOfStoneCount() {
		return theRestOfStoneCount;
	}
	public void setTheRestOfStoneCount(int theRestOfStoneCount) {
		this.theRestOfStoneCount = theRestOfStoneCount;
	}
	
	//set Player1 
	public int getPlayer1() {
		return player1;
	}
	public void setPlayer1(int player1Index) {
		this.player1 = player1Index; 
	}
	
	//set Player2 
	public int getPlayer2() {
		return player2;
	}
	public void setPlayer2(int player2Index) {
		this.player2 = player2Index; 
	}
	
	//display stones
	public void displayStone(int theRestOfStoneCount){
		int asterisks = theRestOfStoneCount;
		while(asterisks > 0) {
			System.out.print(" *");
			asterisks --;
		}
	}

	//judge whether the remove number of stone is correct
	public boolean correctStone(int numberOfRemove) {
		if((numberOfRemove > upperBound) || (numberOfRemove > theRestOfStoneCount) || numberOfRemove <= 0) {
			if(theRestOfStoneCount < upperBound) {
				System.out.println();
				System.out.println("Invalid move. You must remove between 1 and "+ theRestOfStoneCount +" stones.");
			}
			else{
				System.out.println();
				System.out.println("Invalid move. You must remove betweem 1 and "+ upperBound +" stones.");
			}
			return false;
		}
		else {
			theRestOfStoneCount -= numberOfRemove;
			return true;
		}
	}
	
}
