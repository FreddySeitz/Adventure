package game;

public class Map {
//A collection of tiles that contain all actors and map objects.
	private Tile[][] map;
	private int height;
	private int width;
	private int[][] mapData;	//the tile type for every space in the map.
	public Map(){
		
	}
	//if new map is being made, call buildDefault().
	//When edits are made, set new width and height, set mapData, then rebuildMap()
		//or when pulled from the database.
	public void buildDefault(){
		height = 20;
		width = 20;
	}
	
	public void rebuildMap(){
		//call after making edits.  Does not delete mapData outside map bounds.
		map = new Tile[height][width];
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				map[i][j] = new Tile();
				map[i][j].setType(mapData[i][j]);
			}
		}
	}
	
	public Tile[][] getMap(){
		return map;
	}
	
	public void setMap(int i, int j, Tile t){	//i=column(height)		j = row(width)
		map[i][j] = t;
	}
	
	public int[][] getMapData(){
		return mapData;
	}
	
	public void setMapData(int[][] data){
		mapData = data;
	}
	
	public void setSingleMapData(int height, int width, int data){	//expected to be used during map editing
		mapData[height][width] = data;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void setHeight(int h){
		height = h;
	}
	
	public int setWidth(){
		return width;
	}
	
	public void setWidth(int w){
		width = w;
	}
	
}
