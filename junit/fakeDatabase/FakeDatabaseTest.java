package fakeDatabase;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ycp.edu.cs320.adventure.database.FakeDatabase;
import ycp.edu.cs320.adventure.database.InitialData;

public class FakeDatabaseTest {
private FakeDatabase database;
	
	@Before
	public void setUp() {
		database = new FakeDatabase();
	}
	
	@Test
	public void Initialize(){
		assertTrue(true);
	}
}
