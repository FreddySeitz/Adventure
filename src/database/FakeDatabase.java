package database;

import java.io.IOException;
import java.util.List;

import game.Tile;

public class FakeDatabase {
	private List<Tile> tiles;
	
	public FakeDatabase(){
		readInitialData();
	}
	
	public void readInitialData() {
		try {
			tiles.addAll(InitialData.getTiles());
		} catch (IOException e) {
			throw new IllegalStateException("Couldn't read initial data", e);
		}
	}
}
