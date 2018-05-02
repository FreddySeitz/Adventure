package derbyDatabase;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ycp.edu.cs320.adventure.game.Map;
import ycp.edu.cs320.adventure.realdatabase.DerbyDatabase;

public class TestGameLogs {
	DerbyDatabase database;
	int game_id;
	String string1;
	String string2;
	String string3;
	
	@Before
	public void setUp() {
		database = new DerbyDatabase();
		game_id = -999;
		string1 = "one";
		string2 = "two";
		string3 = "three";
	}
	
	@Test
	public void testEverything() {
		//create datalog
		database.addGameLog(game_id, string1);
		database.addGameLog(game_id, string2);
		database.addGameLog(game_id, string3);
		
		//get gamelog
		String result = database.getGameLog(game_id);
		assertEquals("three<br/><br/>two<br/><br/>one<br/><br/>", result);
		
		//get gamelog list
		List<String> list = database.getGameLogList(game_id);
		assertEquals(3, list.size());
		assertEquals("one", list.get(0));
		
		//remove
		database.removeGameLog(game_id);
		
		List<String> list2= database.getGameLogList(game_id);
		assertEquals(0, list2.size());
		
	}
}
