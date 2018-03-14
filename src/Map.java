
public class Map {
//A collection of tiles that contain all actors and map objects.
	private Tile[][] map;
	int SIZE = 50;
	
	public Map(){
		//creates square map
		for(int i = 0; i < SIZE; i++){
			for(int j = 0; j < SIZE; j++){
				map[i][j] = new Tile();
			}
		}
	}
	
	
}
