package fakeDatabase;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ycp.edu.cs320.adventure.database.InitialData;
import ycp.edu.cs320.adventure.game.Account;
import ycp.edu.cs320.adventure.game.Map;

public class InitialDataTest {
	private InitialData init;
	
	@Before
	public void setUp() {
		init = new InitialData();
	}
	
	@Test
	public void getAccountsTest(){
		try {
			List<Account> a = init.getAccount();
			System.out.println(a.size());
			System.out.println(a.get(0).getId());
			System.out.println(a.get(0).getUsername());
			System.out.println(a.get(0).getPassword());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getMapsTest(){
		try {
			List<Map> a = init.getMap();
			System.out.println(a.size());
			System.out.println(a.get(0).getGameId());
			System.out.println(a.get(0).getWidth());
			//System.out.println(a.get(0).getTile(0, 0).getType());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
