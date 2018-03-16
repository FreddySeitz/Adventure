package database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import game.Map;
import game.Tile;

public class InitialData {
	public static List<Map> getMap() throws IOException {
		List<Map> mapList = new ArrayList<Map>();
		ReadCSV readMap = new ReadCSV("Map.csv");
		int count = 0;
		try {
			while (true) {
				List<String> tuple = readMap.next();
				if (tuple == null) {
					break;
				}
				if(count == 0){

				}
				else if(count == 0){

				}
				else if(count == 0){

				}
				Iterator<String> i = tuple.iterator();
				Map map = new Map();				
				mapList.add(map);
			}
			return mapList;
		} finally {
			readMap.close();
		}
	}


}
