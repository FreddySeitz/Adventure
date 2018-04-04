package ycp.edu.cs320.adventure.game;

import java.util.ArrayList;

// Players are the Actors that Users will control
public class Player extends Actor {
	private int score;
	
	// Parameterless Constructor
	public Player(){
		
	}
	
	// Constructor
	public Player(int score) {
		this.score = score;
	}
	
	// Sets a new score
	public void setScore(int s) {
		this.score = s;
	}
		
	// Returns score of Player
	public int getScore() {
		return score;
	}
}
