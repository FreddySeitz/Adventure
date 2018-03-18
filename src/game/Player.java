package game;

// Players are the Actors that Users will control
public class Player extends Actor {
	private int score;
	
	public Player(){
		
	}
	
	public void setDefault(){
		super.setHealth(100);
		//super.setLocation();	//needs to access game class to get specific tile from map
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
