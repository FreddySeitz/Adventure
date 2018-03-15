package game;

public class Tile {
//An arbitrary space on the map that can contain anything.
	private int type = 0;
	//0 = unpassable space.  1 = empty room.  2 = trap
	
	public int getType(){
		return type;
	}
	
	public void setType(int t){
		type = t;
	}
}
