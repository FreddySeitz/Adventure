package derbyDatabase;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import ycp.edu.cs320.adventure.realdatabase.DerbyDatabase;

public class TestAccount {
	DerbyDatabase database;
	int account_id;

	@Before
	public void setUp() {
		database = new DerbyDatabase();
		account_id = -10;
	}
	////////////IMPORTANT
	//order of these database tests matters
	////////////

	////////////ALSO IMPORTANT
	//don't use any of these usernames, plz
	////////////
	
	//test conducted in huge chunks, since it needed to remember the accountId it got.
		//Every test defaulted to the constructor value
	@Test
	public void testCreateAccount() {
		boolean created = database.createAccount("aweqewgwgiagnppasdng", "gurhgwygu43rebguayrgw");

		assertEquals(true, created);
	}
	@Test
	public void testGetAccountAndUsernamePassword(){
		account_id = database.getAccount("aweqewgwgiagnppasdng");
		assertTrue(account_id != -1);
		
		
	}
	@Test
	public void testAccountExists() {
		boolean exists = database.accountExists("aweqewgwgiagnppasdng");

		assertEquals(true, exists);
	}
	@Test
	public void testAccountVerify() {
		boolean verify = database.accountVerify("aweqewgwgiagnppasdng", "gurhgwygu43rebguayrgw");

		assertEquals(true, verify);
	}




	@Test
	public void testUpdateAndRemove() {
		//get id
		account_id = database.getAccount("aweqewgwgiagnppasdng");
		//change username and password
		boolean update = database.updateAccountUsername("ahsaoughwgiahogashid", account_id);
		boolean update2 = database.updateAccountPassword("giiiwrnoigaogsigisga", account_id);
		//check if new username exists
		boolean exists = database.accountExists("ahsaoughwgiahogashid");
		//check that old username doesn't exist
		boolean exists2 = database.accountExists("aweqewgwgiagnppasdng");
		//checking verify method
		boolean verify = database.accountVerify("ahsaoughwgiahogashid", "giiiwrnoigaogsigisga");
		//checking getUsername and password.  Ensures that verify also worked
		String username = database.getAccountUsername(account_id);
		String password = database.getAccountPassword(account_id);
		
		assertEquals(true, update);
		assertEquals(true, update2);
		assertEquals(true, exists);
		assertEquals(false, exists2);
		assertEquals(true, verify);
		assertEquals("ahsaoughwgiahogashid", username);
		assertEquals("giiiwrnoigaogsigisga", password);
		
		//testing updateAll
		boolean update3 = database.updateAccountAll("awgwowowofsjdsojsdfj", "ghoszzzdosjsohd", account_id);
		//testing that updateAll worked
		boolean verify2 = database.accountVerify("awgwowowofsjdsojsdfj", "ghoszzzdosjsohd");  //verifying change was successful from updateAll
		assertEquals(true, update3);
		assertEquals(true, verify2);
		
		//remove account
		boolean remove = database.removeAccount("awgwowowofsjdsojsdfj");
		assertEquals(true, remove);
		
		//checking existence of any used username
		boolean exists3 = database.accountExists("awgwowowofsjdsojsdfj");
		boolean exists4 = database.accountExists("aweqewgwgiagnppasdng");
		boolean exists5 = database.accountExists("ahsaoughwgiahogashid");

		assertEquals(false, exists3);
		assertEquals(false, exists4);
		assertEquals(false, exists5);
	}
}
