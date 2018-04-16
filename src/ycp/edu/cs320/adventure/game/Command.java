package ycp.edu.cs320.adventure.game;

// Command class file
public class Command{
	private String command;
	private String description;
	
	// Constructor
	public Command() {
		command = "";
	}
	
	// Sets command
	public void setCommand(String command) {
		this.command = command;
	}
	
	// Gets command
	public String getCommand() {
		return command;
	}
	
	// Sets description
	public void setDescription(String description) {
		this.description = description;
	}
	
	// Gets command
	public String getDescription() {
		return description;
	}
}