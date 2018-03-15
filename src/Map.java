
public class Map {
//A collection of tiles that contain all actors and map objects.
	private Tile[][] map;
	int SIZE = 50;
	private int[][] mapData;
	public Map(){
		//creates empty square map
		for(int i = 0; i < SIZE; i++){
			for(int j = 0; j < SIZE; j++){
				map[i][j] = new Tile();
			}
		}
	}
	
	public Tile[][] getMap(){
		return map;
	}
	
	public void setMap(int i, int j, Tile t){	//i=column		j = row
		map[i][j] = t;
	}
	
	public void retrieveMapData(){
		//collect double array of tile types from database.  Used to populate all tiles on map.
	}
	
	public int[][] getMapData(){
		return mapData;
	}
	
	public void setMapData(int[][] data){
		//modifies one tile (from game editor)
		mapData = data;
	}
	
	
}
