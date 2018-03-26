package database;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ycp.edu.cs320.adventure.database.InitialData;

public class InitialDataTest {
	private InitialData init;
	
	@Before
	public void setUp() {
		init = new InitialData();
	}
}
