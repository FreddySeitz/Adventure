package ycp.edu.cs320.adventure.game;

// Players are the Actors that Users will control
public class Player extends Actor {
	private int score;
	
	public Player(){
		
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
