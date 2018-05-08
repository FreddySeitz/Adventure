package ycp.edu.cs320.adventure.game;

import java.util.ArrayList;
import java.util.HashMap;

public class Tile {
//An arbitrary space on the map that can contain anything.
	private int type; //0 = unpassable space.  1 = empty room.  2 = trap. 3 = exit room.
	private boolean visible;
	
	//trap varaibles
	private boolean hidden;
	private boolean active;
	private boolean prompt;
	private int question;
	
	private ArrayList<Item> items;
	private int x;	//tiles knows where it is in the map
	private int y;
	private String description;
	private int damage;
	
	private int gameId;
	private int tileId;
	
	public Tile(){	
		type = 0;
		visible = false;
		hidden = false;
		active = false;
		prompt = false;
		question = 0;
		x = 0;
		y = 0;
		items = new ArrayList<Item>();
		damage = 0;
		description = "This area is barren and there are no signs that it was ever important.";
	}
	
	public void setGameId(int g){
		gameId = g;
	}
	
	public int getGameId(){
		return gameId;
	}
	
	public void setTileId(int t){
		tileId = t;
	}
	
	public int getTileId(){
		return tileId;
	}
	
	public int getType(){
		return type;
	}
	
	public void setType(int t){
		type = t;
	}
	
	public boolean getVisible(){
		return visible;
	}
	
	public void setVisible(boolean v){
		visible = v;
	}
	
	public boolean getHidden(){
		return hidden;
	}
	
	public void setHidden(boolean h){
		hidden = h;
	}
	
	public boolean getActive(){
		return active;
	}
	
	public void setActive(boolean a){
		active = a;
	}
	
	public boolean getPrompt(){
		return prompt;
	}
	
	public void setPrompt(boolean p){
		prompt = p;
	}
	
	public int getQuestion(){
		return question;
	}
	
	public void setQuestion(int q){
		question = q;
	}
	
	public int getX(){
		return x;
	}
	
	public void setX(int ex){
		x = ex;
	}
	
	public int getY(){
		return y;
	}
	
	public void setY(int wye){
		y = wye;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String d){
		description = d;
	}
	
	public void addItem(Item i){
		items.add(i);
	}
	
	public Item removeItem(int index){
		return items.remove(index);
	}
	
	public ArrayList<Item> getItemList(){
		return items;
	}
	public void setDamage(int d){
		damage = d;
	}
	
	public int getDamage(){
		return damage;
	}
	
	public String getRiddle(){
		String[] questions = {"1 + 1 = ?", 
				"What is the Power Factor at 36.86\u00b0", 
				"What is the only fourier calculation that can be calculated perfectly by a computer?"};
		
		return questions[question];
	}
	
	public boolean checkAnswer(String response){
		String[] answer1 = {"2", "0.2"};
		String[] answer2 = {"0.8", ".8"};
		String[] answer3 = {"discrete time fourier transform", "DTFT"};
		HashMap<Integer, String[]> answers = new HashMap<Integer, String[]>();
		answers.put(answers.size(), answer1);
		answers.put(answers.size(), answer2);
		answers.put(answers.size(), answer3);
		
		//checks all variations of answers to the question they were given
		for(int i = 0; i < answers.get(question).length; i++){
			if(response.equalsIgnoreCase(answers.get(question)[i])){
				return true;
			}
		}

		return false;
	}
	
	public String getAnswer(){
		String[] answer1 = {"2", "0.2"};
		String[] answer2 = {"0.8", ".8"};
		String[] answer3 = {"discrete time fourier transform", "DTFT"};
		
		HashMap<Integer, String[]> answers = new HashMap<Integer, String[]>();
		answers.put(answers.size(), answer1);
		answers.put(answers.size(), answer2);
		answers.put(answers.size(), answer3);
		
		return answers.get(question)[0];
	}
}
