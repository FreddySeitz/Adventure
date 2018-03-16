package game;

import java.util.ArrayList;

public class Tile {
//An arbitrary space on the map that can contain anything.
	private int type = 0;
	private ArrayList<Item> items;
	//0 = unpassable space.  1 = empty room.  2 = trap
	
	public Tile(){	
	}
	
	public int getType(){
		return type;
	}
	
	public void setType(int t){
		type = t;
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
