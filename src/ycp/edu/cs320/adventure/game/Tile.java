package ycp.edu.cs320.adventure.game;

import java.util.ArrayList;

public class Tile {
//An arbitrary space on the map that can contain anything.
	private int type; //0 = unpassable space.  1 = empty room.  2 = trap
	private ArrayList<Item> items;
	private int x;	//tiles knows where it is in the map
	private int y;
	private String description;
	private int damage;
	
	private int gameId;
	private int tileId;
	
	
	public Tile(){	
		type = 0;
		x = 0;
		y = 0;
		items = new ArrayList<Item>();
		damage = 0;
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
}
