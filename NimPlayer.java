/* Nimsys.java
 * This class controls the overall Nim game process
 * Author: Yuting Feng
 * Student ID:896336
 * Created on 17/5/18
 */
import java.io.*;
import java.util.Scanner;

public class NimPlayer implements Serializable{
	private static final long serialVersionUID = 1420672609912364060L;
	private String userName;
	private String givenName;
	private String familyName;
	private int gamePlayed = 0;
	private int gameWon = 0;
	
	
	//player's user name
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	//player's given name
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
		
	//player's family name
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
		
	//reset game's statistics
	public void resetStatistics() {
		setGamePlayed(0);
		setGameWon(0);
	}
		
	//the number of game win
	public int getGameWon() {
		return gameWon;
	}
	public void setGameWon(int gameWon) {
		this.gameWon = gameWon;
	}
	
	//the number of game played
	public int getGamePlayed() {
		return gamePlayed;
	}
	public void setGamePlayed(int gamePlayed) {
		this.gamePlayed = gamePlayed;
	}
	
	//percentage of game won in format
	public int percentageOfGameWon() {
		if(gamePlayed > 0) {
			return Math.round(((float)gameWon / gamePlayed) * 100);	
		}
		else
			return 0;
	}
	//percentage of game won in exact values
	public double percentage() {
		if(gamePlayed > 0) {
		return (double)gameWon / gamePlayed;
		}
		else
			return 0;
	}
	
	//return the number of stones remove
	public int removeStone(Scanner numberOfRemove, int upperBound, int theRestOfStoneCount) {
		return Integer.parseInt(numberOfRemove.nextLine());
	}
		
	//if game win
	public void win() {
		gamePlayed += 1;
		gameWon += 1;
		System.out.println(this.givenName + " " + this.familyName + " wins!");
	}
	
	//if game lose
	public void lose() {
		gamePlayed += 1;
	}
}

