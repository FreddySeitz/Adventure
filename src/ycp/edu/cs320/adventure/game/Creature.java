package ycp.edu.cs320.adventure.game;

// Creatures are interactive Actors that will have an affect on the Player
public class Creature extends Actor {
	private int movementSpeed;
	private int creatureId;
	
	// Parameterless Constructor
	public Creature() {
		
	}
	
	// Constructor
	public Creature(int movementSpeed) {
		this.movementSpeed = movementSpeed;
	}
	
	public int getCreatureId(){
		return creatureId;
	}
	
	public void setCreatureId(int c){
		creatureId = c;
	}
	
	// Sets the movementSpeed of the current Creature object
	public void setMovementSpeed(int movementSpeed) {
		this.movementSpeed = movementSpeed;
	}
	
	// Gets the movementSpeed of the current Creature objects
	public int getMovementSpeed() {
		return movementSpeed;
	}
}
