/* Nimsys.java
 * This class controls the overall Nim game process
 * Author: Yuting Feng
 * Student ID:896336
 * Created on 17/5/18
 */
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class Nimsys {
	NimPlayer[] playerList = new NimPlayer[100];	
	Scanner kb = new Scanner(System.in);
	Boolean isPlaying = true;
	
	enum Cmd{
		ADDPLAYER,
		ADDAIPLAYER,
		REMOVEPLAYER,
		EDITPLAYER,
		RESETSTATS,
		DISPLAYPLAYER,
		RANKINGS,
		STARTGAME,
		EXIT
	}
	
	public static void main(String[]args) {
		Nimsys nimsys = new Nimsys();
		nimsys.playGame();
	}
	
	public void playGame() {
		
		//download the data from the existed file
		try{
			//String currentPath = getClass().getResource(".").getFile().toString();
			//String filePath = currentPath+"/players.dat";
			
			//FileInputStream readGame = new FileInputStream(filePath);
			
			FileInputStream fis = new FileInputStream("players.dat");
			ObjectInputStream readGame = new ObjectInputStream(fis);
			playerList = (NimPlayer[])readGame.readObject();
			readGame.close();
		}catch(IOException noFile){
			//System.err.println(noFile.getMessage());
		}catch(ClassNotFoundException noClass) {
			System.err.println("Readable class not found: " + noClass.getMessage());
		}
		System.out.println("Welcome to Nim");
		while(isPlaying) {
			System.out.print("\n$");
			//input command
			String inputLine = kb.nextLine();
			//cut down the String to several short String
			String[] lineSplit = inputLine.split(" ");
			Cmd inputCmd;
			try {
				inputCmd = Cmd.valueOf(lineSplit[0].toUpperCase());
			}catch(IllegalArgumentException noCmd) {
				System.err.println("'" + lineSplit[0] + "' is not a valid command.");
				continue;
			}
			
			switch(inputCmd) {
				case ADDPLAYER:{
					try {
						String[] newPlayerName = lineSplit[1].split(",");
						addPlayer(newPlayerName[0], newPlayerName[1], newPlayerName[2], false);
					}catch(ArrayIndexOutOfBoundsException noIn){
						System.err.println("Incorrect number of arguments supplied to command.");
					}
					break;
				}
				case ADDAIPLAYER:{
					try {
						String[] newPlayerName = lineSplit[1].split(",");
						addPlayer(newPlayerName[0], newPlayerName[1], newPlayerName[2], true);
					}catch(ArrayIndexOutOfBoundsException noIn){
						System.err.println("Incorrect number of arguments supplied to command.");
					}
					break;
				}
				case REMOVEPLAYER:{
					if(lineSplit.length == 1) {
						System.out.println("Are you sure you want to remove all players? (y/n)");
						if(kb.nextLine().equals("y")) {
							for(int i = 0; i < playerList.length; i++) {
								if(playerList[i] != null) {
									removePlayer(playerList[i].getUserName());
								}
							}
						}
						break;
					}
					else{
							removePlayer(lineSplit[1]);
					}
					break;
				}
				case EDITPLAYER:{
					try {
						String[] newPlayerName = lineSplit[1].split(",");
						editPlayer(newPlayerName[0], newPlayerName[1], newPlayerName[2]);
					}catch(ArrayIndexOutOfBoundsException noIn){
						System.err.println("Incorrect number of arguments supplied to command.");
					}
					break;
				}
				case RESETSTATS:{
					if(lineSplit.length == 1) {
						System.out.println("Are you sure you want to reset all player statistics? (y/n)");
						if(kb.nextLine().equals("y")) {
							for(int i = 0; i < playerList.length; i++) {
								if(playerList[i] != null) {
									resetStats(playerList[i].getUserName());
								}
							}
						}
						break;
					}
					else {
						resetStats(lineSplit[1]);
					}
					break;
				}
				case DISPLAYPLAYER:{
					if(lineSplit.length == 1) {
						for(int i = 0; i < playerList.length - 1; i++) {
							if(playerList[i] != null) {
								displayPlayer(playerList[i].getUserName());
							}
						}
						break;
					}
					else {
						displayPlayer(lineSplit[1]);
					}
					break;
				}
				case RANKINGS:{
					if(lineSplit.length == 1 || lineSplit[1].equals("desc")) {
						rankingsDesc();
					}
					else {
						rankingsAsc();
					}
					break;
				}
				case STARTGAME:{
					try {
						String[] playingUser = lineSplit[1].split(",");
						startGame(Integer.parseInt(playingUser[0]), Integer.parseInt(playingUser[1]), playingUser[2], playingUser[3]);
					}catch(ArrayIndexOutOfBoundsException noIn){
						System.err.println("Incorrect number of arguments supplied to command.");
					}catch(NumberFormatException e) {
					};
					break;
				}
				case EXIT:{
					System.out.println();
					try {
						FileOutputStream fos = new FileOutputStream("players.dat");
						ObjectOutputStream saveGame = new ObjectOutputStream(fos);
						saveGame.writeObject(playerList);
						saveGame.close();
					}catch(IOException noFile) {
						System.err.println(noFile.getMessage());
					}
					System.exit(0);
					break;
				}
				default:{
					break;
				}
			}
		}
	}
	
	//judge if user input exists in player list
	public boolean userExist(String userName, String User) {
		if(userName.equals(User)) {
			return true;
		}
		return false;
	}
	//judge if a user name exists in a user array
	public boolean userNameExist(String[] userName, String User) {
		for(int i = 0; i < userName.length; i++) {
			if(userName[i] != null){
				if(userName[i].equals(User))
						return true;
			}
		}
		return false;
	}

	//add a player
	public void addPlayer(String UserName, String FamilyName, String GivenName, boolean isAI) {
		for(int i = 0; i < playerList.length; i++) {
			if(playerList[i] == null) {
				if(isAI) {
					playerList[i] = new NimAIPlayer();
				}
				else {
					playerList[i] = new NimPlayer(); 
				}
				playerList[i] = new NimPlayer();
				playerList[i].setUserName(UserName);
				playerList[i].setFamilyName(FamilyName);
				playerList[i].setGivenName(GivenName);
				break;
			}
			else {
				//judge whether this player has existed
				if(userExist(playerList[i].getUserName(), UserName)) {
					System.out.println("The player already exists.");
					return;
				}
			}
		}
		
		//sort the players' list alphabetically
		NimPlayer mediate = new NimPlayer();
		for(int i = 0; i < playerList.length - 1; i++) {
			for(int j = 0; j < playerList.length - 1 - i; j++) {
				if(playerList[j] != null && playerList[j + 1] != null) {
					if(playerList[j + 1].getUserName().compareTo(playerList[j].getUserName()) < 0) {
						mediate = playerList[j + 1];
						playerList[j + 1] = playerList[j];
						playerList[j] = mediate;
					}
				}
			}
		}
	}
	
	//remove a player
	public void removePlayer(String lineSplit) {
		for(int i = 0; i < playerList.length; i++) {
			if(playerList[i] != null && userExist(playerList[i].getUserName(), lineSplit)) {
					playerList[i] = null;
					break;
			}
			else {
				if(i == playerList.length - 1) {
					System.out.println("The player does not exist.");
				}
			}
		}
	}
	 
	//edit a player
	public void editPlayer(String UserName, String FamilyName, String GivenName) {
		for(int i = 0; i < playerList.length; i++) {
			if(playerList[i] != null && userExist(playerList[i].getUserName(), UserName)) {
					playerList[i].setFamilyName(FamilyName);
					playerList[i].setGivenName(GivenName);
					break;
			}
			else {
				if(i == playerList.length -1) {
					System.out.println("The player does not exist.");
				}
			}
		}
	}
	
	//reset player's statistics
	public void resetStats(String lineSplit) {
		for(int i = 0; i < playerList.length; i++) {
			if(playerList[i] != null && userExist(playerList[i].getUserName(), lineSplit)) {
					playerList[i].resetStatistics();
					break;
			}
			else {
				if(i == playerList.length -1) {
					System.out.println("The player does not exist.");
				}
			}
		}
	}
	
	//display a user
	public void displayPlayer(String lineSplit) {
		for(int i = 0; i < playerList.length; i++) {
			if(playerList[i] != null && userExist(playerList[i].getUserName(), lineSplit)) {
				System.out.println(playerList[i].getUserName() + "," 		
								+ playerList[i].getGivenName() + "," 
								+ playerList[i].getFamilyName() + "," 								
								+ playerList[i].getGamePlayed() + " games," 
								+ playerList[i].getGameWon() + " wins");
				break;
				}
			else {
				if(i == playerList.length - 1) {
				  	System.out.println("The player does not exist.");
				}
			}
		}
	}
	
	//rank all users in descending order
	public void rankingsDesc() {
		NimPlayer mediate = new NimPlayer();
		String percentage;
		//sort the playerList[]
		for(int i = 0; i < playerList.length - 1; i++) {
			for(int j = 0; j < playerList.length - 1 - i; j++) {
				if(playerList[j + 1] != null && playerList[j] != null) {
					if(playerList[j + 1].percentage() > playerList[j].percentage()) {
						mediate = playerList[j + 1];
						playerList[j + 1] = playerList[j];
						playerList[j] = mediate;
					}
					else if(playerList[j + 1].percentage() == playerList[j].percentage()) {
						if(playerList[j + 1].getUserName().compareTo(playerList[j].getUserName()) < 0) {
							mediate = playerList[j + 1];
							playerList[j + 1] = playerList[j];
							playerList[j] = mediate;
						}
					}
				}
			}
		}
		//limit the number of displaying the players
		if(playerList.length <= 10) {
			for(int i = 0; i < playerList.length; i++) {
				if(playerList[i] != null) {
					percentage = playerList[i].percentageOfGameWon() + "%";
					System.out.println(String.format("%-5s", percentage) + "| " 
									+ String.format("%02d", playerList[i].getGamePlayed()) + " games | " 
									+ playerList[i].getGivenName() 
									+ " " + playerList[i].getFamilyName());
				}
			}
		}
		else {
			for(int i = 0; i < 10; i++) {
				if(playerList[i] != null) {
					percentage = playerList[i].percentageOfGameWon() + "%";
					System.out.println(String.format("%-5s", percentage) + "| " 
									+ String.format("%02d", playerList[i].getGamePlayed()) + " games | " 
									+ playerList[i].getGivenName() 
									+ " " + playerList[i].getFamilyName());
				}
			}
		}
		//sort the players' list alphabetically				
		for(int i = 0; i < playerList.length - 1; i++) {
			for(int j = 0; j < playerList.length - 1 - i; j++) {
				if(playerList[j] != null && playerList[j + 1] != null) {
					if(playerList[j + 1].getUserName().compareTo(playerList[j].getUserName()) < 0) {
						mediate = playerList[j + 1];
						playerList[j + 1] = playerList[j];
						playerList[j] = mediate;
					}
				}
			}
		}
	}
	
	//rank all users in ascending order
	public void rankingsAsc() {
		NimPlayer mediate = new NimPlayer();
		String percentage;
		//sort the playerList[]
		for(int i = 0; i < playerList.length - 1; i++) {
			for(int j = 0; j < playerList.length - 1 - i; j++) {
				if(playerList[j + 1] != null && playerList[j] != null) {
					if(playerList[j + 1].percentage() < playerList[j].percentage()) {
						mediate = playerList[j + 1];
						playerList[j + 1] = playerList[j];
						playerList[j] = mediate;
					}
					else if(playerList[j + 1].percentage() == playerList[j].percentage()) {
						if(playerList[j + 1].getUserName().compareTo(playerList[j].getUserName()) < 0) {
							mediate = playerList[j + 1];
							playerList[j + 1] = playerList[j];
							playerList[j] = mediate;
						}
					}
				}
			}
		}
		//limit the number of displaying the players
		if(playerList.length <= 10) {
			for(int i = 0; i < playerList.length; i++) {
				if(playerList[i] != null) {
					percentage = playerList[i].percentageOfGameWon() + "%";
					System.out.println(String.format("%-5s", percentage) + "| " 
									+ String.format("%02d", playerList[i].getGamePlayed()) + " games | " 
									+ playerList[i].getGivenName() 
									+ " " + playerList[i].getFamilyName());
				}
			}
		}
		else {
			for(int i = 0; i < 10; i++) {
				if(playerList[i] != null) {
					percentage = playerList[i].percentageOfGameWon() + "%";
					System.out.println(String.format("%-5s", percentage) + "| " 
									+ String.format("%02d", playerList[i].getGamePlayed()) + " games | "
									+ playerList[i].getGivenName() 
									+ " " + playerList[i].getFamilyName());
				}
			}
		}
		//sort the players' list alphabetically				
		for(int i = 0; i < playerList.length - 1; i++) {
			for(int j = 0; j < playerList.length - 1 - i; j++) {
				if(playerList[j] != null && playerList[j + 1] != null) {
					if(playerList[j + 1].getUserName().compareTo(playerList[j].getUserName()) < 0) {
						mediate = playerList[j + 1];
						playerList[j + 1] = playerList[j];
						playerList[j] = mediate;
					}
				}
			}
		}
	}

	//start game
	public void startGame(int stone, int upperBound, String player1UserName, String player2UserName) {
		NimGame gamePlaying = new NimGame();
		boolean isPlayer1Turn = true;
		int numberOfRemove = 0;
		String[] userList = new String[playerList.length];
		for(int i = 0; i < playerList.length; i++) {
			if(playerList[i] != null) {
				userList[i] = playerList[i].getUserName();
			}
		}
		if(userNameExist(userList, player1UserName) && userNameExist(userList, player2UserName)) {
			gamePlaying.setTheRestOfStoneCount(stone);
			gamePlaying.setUpperBound(upperBound);
			//match the player 1 and 2 in players' list
			for(int i = 0; i < playerList.length; i++) {
				if(playerList[i] != null && playerList[i].getUserName().equals(player1UserName)) {
					gamePlaying.setPlayer1(i);
				}
				if(playerList[i] != null && playerList[i].getUserName().equals(player2UserName)) {
					gamePlaying.setPlayer2(i);
				}
			}
			//print the information of the game
			System.out.println();
			System.out.println("Initial stone count: " + gamePlaying.getTheRestOfStoneCount());
			System.out.println("Maximum stone removal: " + gamePlaying.getUpperBound());
			System.out.println("Player 1: " + playerList[gamePlaying.getPlayer1()].getGivenName() + " " + playerList[gamePlaying.getPlayer1()].getFamilyName());
			System.out.println("Player 2: " + playerList[gamePlaying.getPlayer2()].getGivenName() + " " + playerList[gamePlaying.getPlayer2()].getFamilyName());
			
			//game begins
			while(gamePlaying.getTheRestOfStoneCount() > 0) {
				System.out.println();
				System.out.print(gamePlaying.getTheRestOfStoneCount() + " stones left:");
				gamePlaying.displayStone(gamePlaying.getTheRestOfStoneCount());
				System.out.println();
				if(isPlayer1Turn) {
					System.out.println(playerList[gamePlaying.getPlayer1()].getGivenName() + "'s turn - remove how many?");
					try{
						numberOfRemove = playerList[gamePlaying.getPlayer1()].removeStone(kb, gamePlaying.getUpperBound(), gamePlaying.getTheRestOfStoneCount());
						isPlayer1Turn = !gamePlaying.correctStone(numberOfRemove);
					}catch(NumberFormatException badNum){
						System.err.println("Bad int exception: " + badNum.getMessage());
					}
				}
				else {
					System.out.println(playerList[gamePlaying.getPlayer2()].getGivenName() + "'s turn - remove how many?");
					try {
						numberOfRemove = playerList[gamePlaying.getPlayer2()].removeStone(kb, gamePlaying.getUpperBound(), gamePlaying.getTheRestOfStoneCount());
						isPlayer1Turn = gamePlaying.correctStone(numberOfRemove);
					}catch(NumberFormatException badNum){
						System.err.println("Bad int exception: " + badNum.getMessage());
					}
				}
			}
			//String junk = kb.nextLine();//to get rid of '\n'
			//game has finished, print result and reset the statistics
			System.out.println();
			System.out.println("Game Over");
			if(isPlayer1Turn) {
				playerList[gamePlaying.getPlayer1()].win();
				playerList[gamePlaying.getPlayer2()].lose();
			}
			else {
				playerList[gamePlaying.getPlayer2()].win();
				playerList[gamePlaying.getPlayer1()].lose();
			}
		}
		else {
			System.out.println("One of the players does not exist.");
		}
	}	
}
