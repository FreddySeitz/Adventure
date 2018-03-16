package database;

import java.io.IOException;
import java.util.List;

import game.Map;
import game.Tile;

public class FakeDatabase {
	private List<Map> maps;
	
	public FakeDatabase(){
		readInitialData();
	}
	
	public void readInitialData() {
		try {
			maps.addAll(InitialData.getMap());
		} catch (IOException e) {
			throw new IllegalStateException("Couldn't read initial data", e);
		}
	}
	
	//insert queries to gather data from lists
}
