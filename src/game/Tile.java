package game;

import java.util.ArrayList;

public class Tile {
//An arbitrary space on the map that can contain anything.
	private int type;
	private ArrayList<Item> items;
	private int x;	//tiles knows where it is in the map
	private int y;
	//0 = unpassable space.  1 = empty room.  2 = trap
	
	public Tile(){	
		type = 0;
		x = 0;
		y = 0;
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
	
	public void addItem(Item i){
		items.add(i);
	}
	
	public Item removeItem(int index){
		return items.remove(index);
	}
	
	public ArrayList<Item> getItemList(){
		return items;
	}
}
