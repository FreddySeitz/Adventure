package game;

import java.util.Random;

public class Map {
	//A collection of tiles that contain all actors and map objects.
	private Tile[][] map;
	private int height;
	private int width;
	private int[][] mapData;	//the tile type for every space in the map.
	
	private int accountId;		//what account the map is connected to
	
	public Map(){

	}
	//if new map is being made, call buildDefault().
	//When edits are made, set new width and height, set mapData or singleMapData to edit tile types, then rebuildMap()
	//or when pulled from the database.


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

	public void throwGoldEverywhere(){	//puts treasure all over the map
		int treasure = 20;
		Random rand = new Random();
		while(treasure > 0){
			int x = rand.nextInt(height)-1;
			int y = rand.nextInt(width)-1;
			//randomly searches map for spaces (rooms or traps) without treasure already present.
			if(map[x][y].getType() != 0 && map[x][y].getItemList().size() == 0){
				treasure--;
				//map[x][y].addItem(TREASURE);	//TODO: create treasure item
			}
		}
	}

	public int getAccountId(){
		return accountId;
	}
	
	public void setAccountId(int i){
		accountId = i;
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

	public int getWidth(){
		return width;
	}

	public void setWidth(int w){
		width = w;
	}
	
	public String compileTiles(){	//gets data from all tiles on map
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				builder.append(map[i][j].getType());
				builder.append(",");	// , after every variable in Tile
				for(int k = 0; k < map[i][j].getItemList().size(); k++){	//stores all items separated by '/'
					builder.append(map[i][j].getItemList().get(k).toString());
					if(k != map[i][j].getItemList().size()-1){
						builder.append('/');
					}
				}
				builder.append(",");
				builder.append('.');	// . indicates tile is done, following text is the next tile
			}
		}
		
		return builder.toString();
	}
	
	public void decompileTiles(String data){	//decompresses data from database into a double array of tiles.
		map = new Tile[height][width];
		Tile tile = new Tile();
		StringBuilder builder = new StringBuilder();
		int chunk = 0;		//which part of the tile is being analyzed 0=tileType, 1=itemList, etc.
		int mapslot = 0;	//which index of map of the currently constructed tile.
		for(int i = 0; i < data.length(); i++){
			if(data.indexOf(i) == '.'){
				i++;
				map[mapslot/width][mapslot%width] = tile;	//width determines when to move to next row. width = 20, slot 23 = [1][3]
			}
			else if(data.indexOf(i) == ','){	//constructing one tile
				if(chunk == 0){	//tile type
					tile.setType(Integer.parseInt(builder.toString()));
					builder.delete(0, builder.length());
					chunk++;
				}
				else if(chunk == 1){
					while(data.indexOf(i) != ','){
						if(data.indexOf(i) == '/'){
							//tile.addItem( TODO: get item by its name or id );
							builder.delete(0, builder.length());
							i++;
						}
						else{
							builder.append(data.indexOf(i));
						}
					}
					i++;	//avoid ,
					chunk = 0; //found all data for tile, start over for next tile.
				}
			}
			else{	//builder does not append , or .
				builder.append(data.indexOf(i));
			}
		}
	}
	
	//default map before editing
	public void buildDefault(){
		height = 20;
		width = 20;
		map = new Tile[height][width];
		mapData = new int[height][width];

		mapData[0][0] = 1;
		mapData[0][1] = 1;
		mapData[0][2] = 1;
		mapData[0][3] = 1;
		mapData[0][4] = 1;
		mapData[0][5] = 1;
		mapData[0][6] = 1;
		mapData[0][7] = 1;
		mapData[0][8] = 1;
		mapData[0][9] = 1;
		mapData[0][10] = 1;
		mapData[0][16] = 1;
		mapData[0][17] = 1;
		mapData[0][18] = 1;
		mapData[0][19] = 1;
		mapData[1][0] = 1;
		mapData[1][4] = 1;
		mapData[1][9] = 1;
		mapData[1][10] = 1;
		mapData[1][12] = 1;
		mapData[1][16] = 1;
		mapData[1][17] = 1;
		mapData[1][18] = 1;
		mapData[1][19] = 1;
		mapData[2][0] = 1;
		mapData[2][4] = 1;
		mapData[2][5] = 2;
		mapData[2][6] = 1;
		mapData[2][9] = 2;
		mapData[2][10] = 1;
		mapData[2][12] = 1;
		mapData[2][14] = 1;
		mapData[2][16] = 1;
		mapData[2][17] = 1;
		mapData[2][18] = 1;
		mapData[2][19] = 1;
		mapData[3][0] = 1;
		mapData[3][1] = 1;
		mapData[3][2] = 1;
		mapData[3][6] = 1;
		mapData[3][7] = 1;
		mapData[3][10] = 1;
		mapData[3][11] = 1;
		mapData[3][12] = 1;
		mapData[3][13] = 1;
		mapData[3][14] = 1;
		mapData[3][15] = 1;
		mapData[3][16] = 1;
		mapData[3][17] = 1;
		mapData[3][18] = 1;
		mapData[3][19] = 1;
		mapData[4][0] = 1;
		mapData[4][1] = 1;
		mapData[4][2] = 1;
		mapData[4][6] = 1;
		mapData[4][7] = 1;
		mapData[4][10] = 1;
		mapData[4][11] = 1;
		mapData[4][13] = 1;
		mapData[4][17] = 2;
		mapData[4][18] = 2;
		mapData[5][0] = 1;
		mapData[5][1] = 1;
		mapData[5][2] = 1;
		mapData[5][3] = 2;
		mapData[5][4] = 1;
		mapData[5][7] = 1;
		mapData[5][13] = 2;
		mapData[5][17] = 1;
		mapData[5][18] = 1;
		mapData[6][0] = 1;
		mapData[6][7] = 1;
		mapData[6][8] = 1;
		mapData[6][13] = 1;
		mapData[6][17] = 1;
		mapData[6][18] = 1;
		mapData[7][0] = 1;
		mapData[7][8] = 1;
		mapData[7][11] = 1;
		mapData[7][12] = 1;
		mapData[7][13] = 1;
		mapData[7][16] = 1;
		mapData[7][17] = 1;
		mapData[7][18] = 1;
		mapData[7][19] = 1;
		mapData[8][0] = 1;
		mapData[8][1] = 1;
		mapData[8][2] = 1;
		mapData[8][3] = 1;
		mapData[8][4] = 1;
		mapData[8][5] = 1;
		mapData[8][6] = 1;
		mapData[8][7] = 1;
		mapData[8][8] = 1;
		mapData[8][13] = 1;
		mapData[8][15] = 1;
		mapData[8][16] = 1;
		mapData[8][17] = 1;
		mapData[8][19] = 1;
		mapData[9][0] = 1;
		mapData[9][1] = 1;
		mapData[9][4] = 1;
		mapData[9][5] = 1;
		mapData[9][6] = 1;
		mapData[9][7] = 1;
		mapData[9][10] = 1;
		mapData[9][11] = 1;
		mapData[9][13] = 1;
		mapData[9][14] = 1;
		mapData[9][15] = 1;
		mapData[9][16] = 1;
		mapData[9][19] = 1;
		mapData[10][0] = 1;
		mapData[10][6] = 2;
		mapData[10][10] = 1;
		mapData[10][11] = 1;
		mapData[10][14] = 1;
		mapData[10][19] = 1;
		mapData[11][0] = 1;
		mapData[11][1] = 1;
		mapData[11][4] = 1;
		mapData[11][5] = 1;
		mapData[11][6] = 1;
		mapData[11][10] = 1;
		mapData[11][14] = 1;
		mapData[11][19] = 1;
		mapData[12][1] = 1;
		mapData[12][5] = 1;
		mapData[12][6] = 1;
		mapData[12][10] = 2;
		mapData[12][11] = 1;
		mapData[12][12] = 1;
		mapData[12][13] = 1;
		mapData[12][14] = 1;
		mapData[12][19] = 1;
		mapData[13][1] = 1;
		mapData[13][5] = 1;
		mapData[13][12] = 1;
		mapData[13][19] = 1;
		mapData[14][12] = 1;
		mapData[14][14] = 1;
		mapData[14][15] = 1;
		mapData[14][16] = 1;
		mapData[14][19] = 1;
		mapData[15][12] = 1;
		mapData[15][13] = 1;
		mapData[15][14] = 1;
		mapData[15][15] = 1;
		mapData[15][16] = 1;
		mapData[15][19] = 1;
		mapData[16][0] = 1;
		mapData[16][1] = 1;
		mapData[16][2] = 1;
		mapData[16][3] = 1;
		mapData[16][4] = 1;
		mapData[16][5] = 1;
		mapData[16][6] = 1;
		mapData[16][7] = 1;
		mapData[16][8] = 1;
		mapData[16][14] = 1;
		mapData[16][15] = 1;
		mapData[16][16] = 1;
		mapData[16][17] = 1;
		mapData[17][0] = 1;
		mapData[17][2] = 1;
		mapData[17][3] = 1;
		mapData[17][4] = 1;
		mapData[17][5] = 1;
		mapData[17][8] = 1;
		mapData[17][9] = 1;
		mapData[17][15] = 1;
		mapData[17][17] = 1;
		mapData[17][18] = 1;
		mapData[17][19] = 1;
		mapData[18][2] = 1;
		mapData[18][3] = 1;
		mapData[18][4] = 1;
		mapData[18][7] = 1;
		mapData[18][8] = 1;
		mapData[18][9] = 1;
		mapData[19][3] = 1;
		mapData[19][4] = 1;
		mapData[19][5] = 1;
		mapData[19][6] = 1;
		mapData[19][7] = 1;

		for(int i = 0; i < height; i++){	//setting rest of tiles to type 0
			for(int j = 0; j < width; j++){
				if(mapData[i][j] != 1 && mapData[i][j] != 2){
					mapData[i][j] = 0;
				}
			}
		}
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				map[i][j] = new Tile();
				map[i][j].setType(mapData[i][j]);
			}
		}
		throwGoldEverywhere();
	}
}
