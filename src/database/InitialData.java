package database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import game.Tile;

public class InitialData {
	public static List<Tile> getTiles() throws IOException {
		List<Tile> tileList = new ArrayList<Tile>();
		ReadCSV readAuthors = new ReadCSV("database.csv");
		try {
			while (true) {
				List<String> tuple = readAuthors.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Tile tile = new Tile();
				tile.setType(Integer.parseInt(i.next()));				
				tileList.add(tile);
			}
			return tileList;
		} finally {
			readAuthors.close();
		}
	}
	
	
}
