package ycp.edu.cs320.adventure.game;
import java.util.Random;

public class Trap {
//a spot on the map that threatens an actor.
	int traptype;
	boolean active;
	boolean visible;
	double damage;
	String question;
	String answer;
	
	
	public Trap(){
		Random rand = new Random();
		traptype = rand.nextInt(5)+1;	//5 kinds of traps
		active = true;
		visible = false;
		damage = 1;
	}
	
	public void Trigger(){
		//print riddle and check for answer
		switch(traptype){
		case 1: traptype = 1;
			question = "1 + 1 = ?";
			answer = "2";
		case 2: traptype = 2;
			//riddle 2
		case 3: traptype = 3;
			//riddle 3
		case 4: traptype = 4;
			//riddle 4
		case 5: traptype = 5;
			//riddle 5
		}
	}
	
	public void setActive(boolean a){
		active = a;
	}
	
	public boolean getActive(){
		return active;
	}
	
	public void setType(int t){
		traptype = t;
	}
	public int getType(){
		return traptype;
	}
	
	public void setDamage(double d){
		damage = d;
	}
	
	public double getDamage(){
		return damage;
	}
	
	public void setVisible(boolean v){
		visible = v;
	}
	
	public boolean getVisible(){
		return visible;
	}
}
