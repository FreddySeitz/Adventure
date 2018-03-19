package database;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import database.InitialData;

public class InitialDataTest {
	private InitialData init;
	
	@Before
	public void setUp() {
		init = new InitialData();
	}
}
