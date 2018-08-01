/*
	NimAIPlayer.java
	
	This class is provided as a skeleton code for the tasks of 
	Sections 2.4, 2.5 and 2.6 in Project C. Add code (do NOT delete any) to it
	to finish the tasks. 
*/
import java.util.Scanner;

public class NimAIPlayer extends NimPlayer implements Testable {
	public NimAIPlayer() {
				
	}
	
	//the number of stones to remove
	public int removeStone(Scanner numberOfRemove, int upperBound, int theRestOfStoneCount) {
		for(int RemoveByAI = 1; RemoveByAI <= upperBound && RemoveByAI < theRestOfStoneCount; RemoveByAI++) {
			//ensure that the rival player is always left with k(M+1)+1 stones
			if((theRestOfStoneCount - RemoveByAI - 1) % (upperBound + 1) == 0) {
				return RemoveByAI;
			}
		}
		return 0;
	}
	
	public String advancedMove(boolean[] available, String lastMove) {
		// the implementation of the victory
		// guaranteed strategy designed by you
		String move = "";
		return move;
	}
}