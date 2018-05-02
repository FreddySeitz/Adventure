package ycp.edu.cs320.adventure.realdatabase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ycp.edu.cs320.adventure.game.Creature;
import ycp.edu.cs320.adventure.game.Item;
import ycp.edu.cs320.adventure.game.Map;
import ycp.edu.cs320.adventure.game.Player;
import ycp.edu.cs320.adventure.game.Tile;
import ycp.edu.cs320.adventure.realdatabase.DBUtil;

public class DerbyDatabase implements IDatabase{
	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			throw new IllegalStateException("Could not load Derby driver");
		}
	}

	private interface Transaction<ResultType> {
		public ResultType execute(Connection conn) throws SQLException;
	}

	private static final int MAX_ATTEMPTS = 10;

	//@Override
	public boolean createAccount(final String username, final String password) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" INSERT INTO accounts (USERNAME, PASSWORD) " + 
									"	VALUES (?, ?)"
							);
					stmt.setString(1, username);
					stmt.setString(2, password);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean updateAccountAll(final String username, final String password, final int account_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE accounts " + 
									"SET username = ?, password = ?	" +
									"WHERE accounts.account_id = ?"
							);
					stmt.setString(1, username);
					stmt.setString(2, password);
					stmt.setInt(3, account_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean updateAccountUsername(final String username, final int account_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE accounts " + 
									"SET username = ? " +
									"WHERE accounts.account_id = ?"
							);
					stmt.setString(1, username);
					stmt.setInt(2, account_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean updateAccountPassword(final String password, final int account_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE accounts " + 
									"SET password = ?	" +
									"WHERE accounts.account_id = ?"
							);
					stmt.setString(1, password);
					stmt.setInt(2, account_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean removeAccount(final String username) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" DELETE FROM accounts " +
									"WHERE accounts.username = ?"
							);
					stmt.setString(1, username);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public int getAccount(final String username) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" SELECT accounts.account_id " + 
									"FROM accounts " + 
									"WHERE accounts.username = ? "
							);
					stmt.setString(1, username);

					resultSet = stmt.executeQuery();

					resultSet.next();
					return resultSet.getInt(1);

				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(resultSet);	
				}
			}
		});
	}

	//@Override
	public String getAccountUsername(final int account_id) {
		return executeTransaction(new Transaction<String>() {
			@Override
			public String execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" SELECT accounts.username " + 
									"FROM accounts " + 
									"WHERE accounts.account_id = ? "
							);
					stmt.setInt(1, account_id);

					resultSet = stmt.executeQuery();

					resultSet.next();
					return resultSet.getString(1);

				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(resultSet);	
				}
			}
		});
	}

	//@Override
	public String getAccountPassword(final int account_id) {
		return executeTransaction(new Transaction<String>() {
			@Override
			public String execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" SELECT accounts.password " + 
									"FROM accounts " + 
									"WHERE accounts.account_id = ? "
							);
					stmt.setInt(1, account_id);

					resultSet = stmt.executeQuery();

					resultSet.next();
					return resultSet.getString(1);

				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(resultSet);	
				}
			}
		});
	}

	//@Override
	public boolean accountExists(final String username) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" SELECT accounts.account_id " + 
									"FROM accounts " + 
									"WHERE accounts.username = ? "
							);
					stmt.setString(1, username);

					resultSet = stmt.executeQuery();

					int count = 0;
					while (resultSet.next()) {
						count++;
					}

					if(count > 0){
						return true;
					}
					else{
						return false;
					}

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean accountVerify(final String username, final String password) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" SELECT accounts.account_id " + 
									"FROM accounts " + 
									"WHERE accounts.username = ? AND accounts.password = ? "
							);
					stmt.setString(1, username);
					stmt.setString(2, password);

					resultSet = stmt.executeQuery();

					int count = 0;
					while (resultSet.next()) {
						count++;
					}

					if(count > 0){
						return true;
					}
					else{
						return false;
					}

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public int createGame(final int account_id) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement id = null;
				ResultSet resultSet = null;

				try {
					conn.setAutoCommit(true);

					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" INSERT INTO games (account_id) " + 
									"	VALUES (?)	"
							);
					stmt.setInt(1, account_id);
					stmt.executeUpdate();

					id = conn.prepareStatement(
							" SELECT games.game_id " +
									"FROM games " + 
									"WHERE games.account_id = ?"
									
							);
					id.setInt(1, account_id);
					resultSet = id.executeQuery();
					
					int result = -1;
					while(resultSet.next()){
						result = resultSet.getInt(1);
					}
					

					return result;

				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(id);
					DBUtil.closeQuietly(resultSet);
				}
			}
		});
	}

	//@Override
	public List<Integer> getGames(final int account_id) {
		return executeTransaction(new Transaction<List<Integer>>() {
			@Override
			public List<Integer> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" SELECT games.game_id " +
									"FROM games " + 
									"WHERE games.account_id = ?"
							);
					stmt.setInt(1, account_id);

					resultSet = stmt.executeQuery();

					List<Integer> result = new ArrayList<Integer>();

					while (resultSet.next()) {
						result.add(resultSet.getInt(1));
					}

					return result;

				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(resultSet);	
				}
			}
		});
	}

	//@Override
	public boolean removeGame(final int game_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" DELETE FROM games " +
									"WHERE games.game_id = ?"
							);
					stmt.setInt(1, game_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public int countAllGames() {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" SELECT games.game_id " +
									"FROM games "
							);

					resultSet = stmt.executeQuery();

					int count = 0;
					while (resultSet.next()) {
						count++;
					}

					return count;

				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(resultSet);	
				}
			}
		});
	}

	//@Override
	public int createItem(final int game_id, final String name, final String description, final int weight, final int damage, final int health, final int quest_id, final int value) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement id = null;
				ResultSet resultSet = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" INSERT INTO items (game_id, name, description, weight, damage, health, quest_id, value) " + 
									"	VALUES (?, ?, ?, ?, ?, ?, ?, ?)	"
							);
					stmt.setInt(1, game_id);
					stmt.setString(2, name);
					stmt.setString(3, description);
					stmt.setInt(4, weight);
					stmt.setInt(5, damage);
					stmt.setInt(6, health);
					stmt.setInt(7, quest_id);
					stmt.setInt(8, value);
					
					stmt.executeUpdate();
					
					id = conn.prepareStatement(
							" SELECT items.item_id " +
									"FROM items " + 
									"WHERE items.game_id = ?"
									
							);
					id.setInt(1, game_id);
					resultSet = id.executeQuery();
					
					int result = -1;
					while(resultSet.next()){
						result = resultSet.getInt(1);
					}

					

					return result;

				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(id);
					DBUtil.closeQuietly(resultSet);
				}
			}
		});
	}

	//@Override
	public boolean updateItemAll(final int item_id, final int game_id, final String name, final String description, final int weight, final int damage, final int health, final int quest_id, final int value) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE items " +
									"SET game_id = ?, name = ?, description = ?, weight = ?, damage = ?, health = ?, quest_id = ?, value = ? " + 
									"WHERE items.item_id = ?"
							);
					stmt.setInt(1, game_id);
					stmt.setString(2, name);
					stmt.setString(3, description);
					stmt.setInt(4, weight);
					stmt.setInt(5, damage);
					stmt.setInt(6, health);
					stmt.setInt(7, quest_id);
					stmt.setInt(8, value);
					stmt.setInt(8, item_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean updateItemName(final int item_id, final String name) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE items " +
									"SET name = ? " + 
									"WHERE items.item_id = ?"
							);
					stmt.setString(1, name);
					stmt.setInt(2, item_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean updateItemDescription(final int item_id, final String description) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE items " +
									"SET description = ? " + 
									"WHERE items.item_id = ?"
							);
					stmt.setString(1, description);
					stmt.setInt(2, item_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean updateItemWeight(final int item_id, final int weight) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE items " +
									"SET weight = ? " + 
									"WHERE items.item_id = ?"
							);
					stmt.setInt(1, weight);
					stmt.setInt(2, item_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean updateItemDamage(final int item_id, final int damage) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE items " +
									"SET damage = ? " + 
									"WHERE items.item_id = ?"
							);
					stmt.setInt(1, damage);
					stmt.setInt(2, item_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean updateItemHealth(final int item_id, final int health) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE items " +
									"SET health = ? " + 
									"WHERE items.item_id = ?"
							);
					stmt.setInt(1, health);
					stmt.setInt(2, item_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean updateItemQuestId(final int item_id, final int quest_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE items " +
									"SET quest_id = ? " + 
									"WHERE items.item_id = ?"
							);
					stmt.setInt(1, quest_id);
					stmt.setInt(2, item_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean updateItemValue(final int item_id, final int value) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE items " +
									"SET value = ? " + 
									"WHERE items.item_id = ?"
							);
					stmt.setInt(1, value);
					stmt.setInt(2, item_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean removeItem(final int item_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" DELETE FROM items " +
									"WHERE items.item_id = ?"
							);
					stmt.setInt(1, item_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public Item getItem(final int item_id) {
		return executeTransaction(new Transaction<Item>() {
			@Override
			public Item execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					// retrieve a specific item
					stmt = conn.prepareStatement(
							" SELECT * " +
									"FROM items " + 
									"WHERE items.item_id = ?"
							);
					stmt.setInt(1, item_id);

					resultSet = stmt.executeQuery();

					Item item = new Item();
					resultSet.next();
					loaditem(item, resultSet, 1);

					return item;

				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(resultSet);	
				}
			}
		});
	}

	//@Override
	public List<Item> getAllItems(final int game_id) {
		return executeTransaction(new Transaction<List<Item>>() {
			@Override
			public List<Item> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					// retrieve a specific item
					stmt = conn.prepareStatement(
							" SELECT * " +
									"FROM items " + 
									"WHERE items.game_id = ?"
							);
					stmt.setInt(1, game_id);

					resultSet = stmt.executeQuery();
					List<Item> result = new ArrayList<Item>();
					while (resultSet.next()) {
						Item item = new Item();
						loaditem(item, resultSet, 1);
						result.add(item);
					}


					return result;

				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(resultSet);					
				}
			}
		});
	}

	private void loaditem(Item item, ResultSet resultSet, int index) throws SQLException {
		item.setItemId(resultSet.getInt(index++));
		item.setGameId(resultSet.getInt(index++));
		item.setName(resultSet.getString(index++));
		item.setDescription(resultSet.getString(index++));
		item.setWeight(resultSet.getInt(index++));
		item.setDamage(resultSet.getInt(index++));
		item.setHealth(resultSet.getInt(index++));
		item.setQuestId(resultSet.getInt(index++));
		item.setValue(resultSet.getInt(index++));
	}

	//@Override
	public boolean addToPlayerInventory(final int player_id, final int item_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" INSERT INTO inventories (creature_id, player_id, tile_id, item_id) " + 
									"	VALUES (0, ?, 0, ?)	"
							);
					stmt.setInt(1, player_id);
					stmt.setInt(2, item_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean existsPlayerInventoryItem(final int player_id, final int item_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					// retrieve a specific item
					stmt = conn.prepareStatement(
							" SELECT inventories.item_id " +
									"FROM inventories " + 
									"WHERE inventories.player_id = ? AND inventories.item_id = ?"
							);
					stmt.setInt(1, player_id);
					stmt.setInt(2, item_id);

					resultSet = stmt.executeQuery();

					int count = 0;
					while(resultSet.next()){
						count++;
					}

					if(count > 0){
						return true;
					}
					else{
						return false;
					}


				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(resultSet);	
				}
			}
		});
	}

	//@Override
	public boolean removeFromPlayerInventory(final int player_id, final int inventory_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" DELETE FROM inventories " +
									"WHERE inventories.player_id = ? AND inventories.inventory_id = ?"
							);
					stmt.setInt(1, player_id);
					stmt.setInt(2, inventory_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean removeAllFromPlayerInventory(final int player_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" DELETE FROM inventories " +
									"WHERE inventories.player_id = ?"
							);
					stmt.setInt(1, player_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public List<Item> getPlayerInventory(final int player_id) {
		return executeTransaction(new Transaction<List<Item>>() {
			@Override
			public List<Item> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					// retrieve a specific item
					stmt = conn.prepareStatement(
							" SELECT inventories.item_id, inventories.inventory_id " +
									"FROM inventories " + 
									"WHERE inventories.player_id = ?"
							);
					stmt.setInt(1, player_id);

					resultSet = stmt.executeQuery();

					List<Item> result = new ArrayList<Item>();
					while(resultSet.next()){
						Item item = (getItem(resultSet.getInt(1)));
						item.setInventoryId(resultSet.getInt(2));
						result.add(item);
					}

					return result;

				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(resultSet);	
				}
			}
		});
	}

	//@Override
	public boolean addToCreatureInventory(final int creature_id, final int item_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" INSERT INTO inventories (creature_id, player_id, tile_id, item_id) " + 
									"	VALUES (?, 0, 0, ?)	"
							);
					stmt.setInt(1, creature_id);
					stmt.setInt(2, item_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean existsCreatureInventoryItem(final int creature_id, final int item_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					// retrieve a specific item
					stmt = conn.prepareStatement(
							" SELECT inventories.item_id " +
									"FROM inventories " + 
									"WHERE inventories.creature_id = ? AND inventories.item_id = ?"
							);
					stmt.setInt(1, creature_id);
					stmt.setInt(2, item_id);

					resultSet = stmt.executeQuery();

					int count = 0;
					while(resultSet.next()){
						count++;
					}

					if(count > 0){
						return true;
					}
					else{
						return false;
					}


				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(resultSet);	
				}
			}
		});
	}

	//@Override
	public boolean removeFromCreatureInventory(final int creature_id, final int inventory_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" DELETE FROM inventories " +
									"WHERE inventories.creature_id = ? AND inventories.inventory_id = ?"
							);
					stmt.setInt(1, creature_id);
					stmt.setInt(2, inventory_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean removeAllFromCreatureInventory(final int creature_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" DELETE FROM inventories " +
									"WHERE inventories.creature_id = ?"
							);
					stmt.setInt(1, creature_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public List<Item> getCreatureInventory(final int creature_id) {
		return executeTransaction(new Transaction<List<Item>>() {
			@Override
			public List<Item> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					// retrieve a specific item
					stmt = conn.prepareStatement(
							" SELECT inventories.item_id, inventories.inventory_id " +
									"FROM inventories " + 
									"WHERE inventories.creature_id = ?"
							);
					stmt.setInt(1, creature_id);

					resultSet = stmt.executeQuery();

					List<Item> result = new ArrayList<Item>();
					while(resultSet.next()){
						Item item = (getItem(resultSet.getInt(1)));
						item.setInventoryId(resultSet.getInt(2));
						result.add(item);
					}

					return result;

				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(resultSet);	
				}
			}
		});
	}

	//@Override
	public boolean addToTileInventory(final int tile_id, final int item_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" INSERT INTO inventories (creature_id, player_id, tile_id, item_id) " + 
									"	VALUES (0, 0, ?, ?)	"
							);
					stmt.setInt(1, tile_id);
					stmt.setInt(2, item_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean existsTileInventoryItem(final int tile_id, final int item_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					// retrieve a specific item
					stmt = conn.prepareStatement(
							" SELECT inventories.item_id " +
									"FROM inventories " + 
									"WHERE inventories.tile_id = ? AND inventories.item_id = ?"
							);
					stmt.setInt(1, tile_id);
					stmt.setInt(2, item_id);

					resultSet = stmt.executeQuery();

					int count = 0;
					while(resultSet.next()){
						count++;
					}

					if(count > 0){
						return true;
					}
					else{
						return false;
					}


				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(resultSet);	
				}
			}
		});
	}


	//@Override
	public boolean removeFromTileInventory(final int tile_id, final int inventory_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" DELETE FROM inventories " +
									"WHERE inventories.tile_id = ? AND inventories.inventory_id = ?"
							);
					stmt.setInt(1, tile_id);
					stmt.setInt(2, inventory_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean removeAllFromTileInventory(final int tile_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" DELETE FROM inventories " +
									"WHERE inventories.tile_id = ?"
							);
					stmt.setInt(1, tile_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public List<Item> getTileInventory(final int tile_id) {
		return executeTransaction(new Transaction<List<Item>>() {
			@Override
			public List<Item> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					// retrieve a specific item
					stmt = conn.prepareStatement(
							" SELECT inventories.item_id, inventories.inventory_id " +
									"FROM inventories " + 
									"WHERE inventories.tile_id = ?"
							);
					stmt.setInt(1, tile_id);

					resultSet = stmt.executeQuery();

					List<Item> result = new ArrayList<Item>();
					while(resultSet.next()){
						Item item = (getItem(resultSet.getInt(1)));
						item.setInventoryId(resultSet.getInt(2));
						result.add(item);
					}

					return result;

				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(resultSet);	
				}
			}
		});
	}

	//@Override
	public Item getInventoryItem(final int inventory_id) {
		return executeTransaction(new Transaction<Item>() {
			@Override
			public Item execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					// retrieve a specific item
					stmt = conn.prepareStatement(
							" SELECT inventories.item_id " +
									"FROM inventories " + 
									"WHERE inventories.inventory_id = ?"
							);
					stmt.setInt(1, inventory_id);

					resultSet = stmt.executeQuery();

					resultSet.next();
					Item item = (getItem(resultSet.getInt(1)));

					return item;

				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(resultSet);	
				}
			}
		});
	}

	//@Override
	public int createMap(final int game_id, final int height, final int width) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement id = null;
				ResultSet resultSet = null;
				
				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" INSERT INTO maps (game_id, height, width) " + 
									"	VALUES (?, ?, ?)	"
							);
					stmt.setInt(1, game_id);
					stmt.setInt(2, height);
					stmt.setInt(3, width);

					stmt.executeUpdate();
					
					id = conn.prepareStatement(
							" SELECT maps.map_id " +
									"FROM maps " + 
									"WHERE maps.game_id = ?"
									
							);
					id.setInt(1, game_id);
					resultSet = id.executeQuery();
					
					int result = -1;
					while(resultSet.next()){
						result = resultSet.getInt(1);
					}

					

					return result;

				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(id);
					DBUtil.closeQuietly(resultSet);
				}
			}
		});
	}

	//@Override
	public boolean updateMapAll(final int game_id, final int height, final int width) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE maps " + 
									"SET height = ?, width = ? " +
									"WHERE maps.game_id = ?"
							);
					stmt.setInt(1, height);
					stmt.setInt(2, width);
					stmt.setInt(3, game_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean updateMapHeight(final int game_id, final int height) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE maps " + 
									"SET height = ? " +
									"WHERE maps.game_id = ?"
							);
					stmt.setInt(1, height);
					stmt.setInt(2, game_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}


	//@Override
	public boolean updateMapWidth(final int game_id, final int width) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE maps " + 
									"SET width = ? " +
									"WHERE maps.game_id = ?"
							);
					stmt.setInt(1, width);
					stmt.setInt(2, game_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean removeMap(final int game_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" DELETE FROM maps " +
									"WHERE maps.game_id = ?"
							);
					stmt.setInt(1, game_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public Map getMap(final int game_id) {
		return executeTransaction(new Transaction<Map>() {
			@Override
			public Map execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					// retrieve a specific item
					stmt = conn.prepareStatement(
							" SELECT *" +
									"FROM maps " + 
									"WHERE maps.game_id = ?"
							);
					stmt.setInt(1, game_id);

					resultSet = stmt.executeQuery();

					Map map = new Map();
					resultSet.next();
					loadmap(map, resultSet, 1);

					return map;

				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(resultSet);
				}
			}
		});
	}

	private void loadmap(Map map, ResultSet resultSet, int index) throws SQLException {
		index++; //skip map_id
		map.setGameId(resultSet.getInt(index++));
		map.setHeight(resultSet.getInt(index++));
		map.setWidth(resultSet.getInt(index++));
		map.setMap(new Tile[map.getWidth()][map.getHeight()]);
	}

	//@Override
	public int createTile(final int game_id, final int type, final boolean visible, final String description, final int damage, final int x, final int y) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement id = null;
				ResultSet resultSet = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" INSERT INTO tiles (game_id, type, visible, description, damage, x, y) " + 
									"	VALUES (?, ?, ?, ?, ?, ?, ?)	"
							);
					stmt.setInt(1, game_id);
					stmt.setInt(2, type);
					stmt.setBoolean(3, visible);
					stmt.setString(4, description);
					stmt.setInt(5, damage);
					stmt.setInt(6, x);
					stmt.setInt(7, y);

					stmt.executeUpdate();

					id = conn.prepareStatement(
							" SELECT tiles.tile_id " +
									"FROM tiles " + 
									"WHERE tiles.game_id = ?"
									
							);
					id.setInt(1, game_id);
					resultSet = id.executeQuery();
					
					int result = -1;
					while(resultSet.next()){
						result = resultSet.getInt(1);
					}

					return result;

				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(id);
					DBUtil.closeQuietly(resultSet);
				}
			}
		});
	}

	//@Override
	public boolean updateTileAll(final int type, final boolean visible, final String description, final int x, final int y, final int tile_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE tiles " + 
									"SET type = ?, visible = ?, description = ?, x = ?, y = ? " +
									"WHERE tiles.tile_id = ?"
							);
					stmt.setInt(1, type);
					stmt.setBoolean(2, visible);
					stmt.setString(3, description);
					stmt.setInt(4, x);
					stmt.setInt(5, y);
					stmt.setInt(6, tile_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean updateTileType(final int type, final int tile_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE tiles " + 
									"SET type = ? " +
									"WHERE tiles.tile_id = ?"
							);
					stmt.setInt(1, type);
					stmt.setInt(2, tile_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	//@Override
		public boolean updateTileVisible(final boolean visible, final int tile_id) {
			return executeTransaction(new Transaction<Boolean>() {
				@Override
				public Boolean execute(Connection conn) throws SQLException {
					PreparedStatement stmt = null;

					try {
						// retreive all attributes from both Books and Authors tables
						stmt = conn.prepareStatement(
								" UPDATE tiles " + 
										"SET visible = ? " +
										"WHERE tiles.tile_id = ?"
								);
						stmt.setBoolean(1, visible);
						stmt.setInt(2, tile_id);

						stmt.executeUpdate();

						return true;

					} finally {
						DBUtil.closeQuietly(stmt);
					}
				}
			});
		}

	//@Override
	public boolean updateTileDescription(final String description, final int tile_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE tiles " + 
									"SET description = ? " +
									"WHERE tiles.tile_id = ?"
							);
					stmt.setString(1, description);
					stmt.setInt(2, tile_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean updateTileDamage(final int damage, final int tile_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE tiles " + 
									"SET damage = ? " +
									"WHERE tiles.tile_id = ?"
							);

					stmt.setInt(1, damage);
					stmt.setInt(2, tile_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean updateTileX(final int x, final int tile_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE tiles " + 
									"SET x = ? " +
									"WHERE tiles.tile_id = ?"
							);

					stmt.setInt(1, x);
					stmt.setInt(2, tile_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean updateTileY(final int y, final int tile_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE tiles " + 
									"SET y = ? " +
									"WHERE tiles.tile_id = ?"
							);

					stmt.setInt(1, y);
					stmt.setInt(2, tile_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean tileExists(final int game_id, final int x, final int y) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {

				conn.setAutoCommit(false);

				PreparedStatement gettile = null;
				ResultSet resultTile = null;

				try {
					// retreive all attributes from both Books and Authors tables
					gettile = conn.prepareStatement(
							" SELECT * " +
									"FROM tiles " + 
									"WHERE tiles.game_id = ? AND tiles.x = ? AND tiles.y = ?"
							);

					gettile.setInt(1, game_id);
					gettile.setInt(2, x);
					gettile.setInt(3, y);

					resultTile = gettile.executeQuery();

					int count = 0;
					while(resultTile.next()){
						count++;
					}

					if(count > 0){
						return true;
					}
					else{
						return false;
					}


				} finally {
					DBUtil.closeQuietly(gettile);
					DBUtil.closeQuietly(resultTile);
				}
			}
		});
	}

	//@Override
	public boolean removeTile(final int tile_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" DELETE FROM tiles " +
									"WHERE tiles.tile_id = ?"
							);
					stmt.setInt(1, tile_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public Tile getTile(final int game_id, final int x, final int y) {
		return executeTransaction(new Transaction<Tile>() {
			@Override
			public Tile execute(Connection conn) throws SQLException {

				PreparedStatement gettile = null;
				ResultSet resultTile = null;

				try {
					// retreive all attributes from both Books and Authors tables
					gettile = conn.prepareStatement(
							" SELECT * " +
									"FROM tiles " + 
									"WHERE tiles.game_id = ? AND tiles.x = ? AND tiles.y = ?"
							);

					gettile.setInt(1, game_id);
					gettile.setInt(2, x);
					gettile.setInt(3, y);

					resultTile = gettile.executeQuery();
					resultTile.next();

					Tile tile = new Tile();
					loadtile(tile, resultTile, 1);

					return tile;

				} finally {
					DBUtil.closeQuietly(gettile);
					DBUtil.closeQuietly(resultTile);
				}
			}
		});
	}

	//@Override
	public List<Tile> getAllTiles(final int game_id) {
		return executeTransaction(new Transaction<List<Tile>>() {
			@Override
			public List<Tile> execute(Connection conn) throws SQLException {

				PreparedStatement gettile = null;
				ResultSet resultTile = null;

				try {
					// retreive all attributes from both Books and Authors tables
					gettile = conn.prepareStatement(
							" SELECT * " +
									"FROM tiles " + 
									"WHERE tiles.game_id = ?"
							);

					gettile.setInt(1, game_id);

					resultTile = gettile.executeQuery();

					List<Tile> result = new ArrayList<Tile>();
					while(resultTile.next()){
						Tile tile = new Tile();
						loadtile(tile, resultTile, 1);
						result.add(tile);
					}

					return result;

				} finally {
					DBUtil.closeQuietly(gettile);
					DBUtil.closeQuietly(resultTile);
				}
			}
		});
	}

	private void loadtile(Tile tile, ResultSet resultSet, int index) throws SQLException {
		tile.setTileId(resultSet.getInt(index++));
		tile.setGameId(resultSet.getInt(index++));
		tile.setType(resultSet.getInt(index++));
		tile.setVisible(resultSet.getBoolean(index++));
		tile.setDescription(resultSet.getString(index++));
		tile.setDamage(resultSet.getInt(index++));
		tile.setX(resultSet.getInt(index++));
		tile.setY(resultSet.getInt(index++));
	}

	//@Override
	public int createCreature(final int game_id, final int equippedItem, final int health, final int x, final int y, final int baseDamage, final int moveSpeed) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement id = null;
				ResultSet resultSet = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" INSERT INTO creatures (game_id, equippedItem, health, x_location, y_location, baseDamage, moveSpeed) " + 
									"	VALUES (?, ?, ?, ?, ?, ?, ?)	"
							);
					stmt.setInt(1, game_id);
					stmt.setInt(2, equippedItem);
					stmt.setInt(3, health);
					stmt.setInt(4, x);
					stmt.setInt(5, y);
					stmt.setInt(6, baseDamage);
					stmt.setInt(7, moveSpeed);

					stmt.executeUpdate();

					id = conn.prepareStatement(
							" SELECT creatures.creature_id " +
									"FROM creatures " + 
									"WHERE creatures.creature_id = ?"
									
							);
					id.setInt(1, game_id);
					resultSet = id.executeQuery();
					
					int result = -1;
					while(resultSet.next()){
						result = resultSet.getInt(1);
					}

					return result;

				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(id);
					DBUtil.closeQuietly(resultSet);
				}
			}
		});
	}

	//@Override
	public boolean updateCreatureEquippedItem(final int creature_id, final int equippedItem) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE creatures " + 
									"SET equippedItem = ? " +
									"WHERE creatures.creature_id = ?"
							);

					stmt.setInt(1, equippedItem);
					stmt.setInt(2, creature_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean updateCreatureHealth(final int creature_id, final int health) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE creatures " + 
									"SET health = ? " +
									"WHERE creatures.creature_id = ?"
							);

					stmt.setInt(1, health);
					stmt.setInt(2, creature_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean updateCreatureX(final int creature_id, final int x) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE creatures " + 
									"SET x_location = ? " +
									"WHERE creatures.creature_id = ?"
							);

					stmt.setInt(1, x);
					stmt.setInt(2, creature_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean updateCreatureY(final int creature_id, final int y) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE creatures " + 
									"SET y_location = ? " +
									"WHERE creatures.creature_id = ?"
							);

					stmt.setInt(1, y);
					stmt.setInt(2, creature_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean updateCreatureBaseDamage(final int creature_id, final int baseDamage) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE creatures " + 
									"SET baseDamage = ? " +
									"WHERE creatures.creature_id = ?"
							);

					stmt.setInt(1, baseDamage);
					stmt.setInt(2, creature_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean updateCreatureMoveSpeed(final int creature_id, final int moveSpeed) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE creatures " + 
									"SET moveSpeed = ? " +
									"WHERE creatures.creature_id = ?"
							);

					stmt.setInt(1, moveSpeed);
					stmt.setInt(2, creature_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean removeCreature(final int creature_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" DELETE FROM creatures " +
									"WHERE creatures.creature_id = ?"
							);
					stmt.setInt(1, creature_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public Creature getCreature(final int creature_id) {
		return executeTransaction(new Transaction<Creature>() {
			@Override
			public Creature execute(Connection conn) throws SQLException {

				PreparedStatement gettile = null;
				ResultSet resultTile = null;

				try {
					// retreive all attributes from both Books and Authors tables
					gettile = conn.prepareStatement(
							" SELECT * " +
									"FROM creatures " + 
									"WHERE creatures.creature_id = ?"
							);

					gettile.setInt(1, creature_id);

					resultTile = gettile.executeQuery();
					resultTile.next();

					Creature creature = new Creature();
					loadcreature(creature, resultTile, 1);

					return creature;

				} finally {
					DBUtil.closeQuietly(gettile);
					DBUtil.closeQuietly(resultTile);
				}
			}
		});
	}

	//@Override
	public List<Creature> getAllCreatures(final int game_id) {
		return executeTransaction(new Transaction<List<Creature>>() {
			@Override
			public List<Creature> execute(Connection conn) throws SQLException {

				PreparedStatement gettile = null;
				ResultSet resultTile = null;

				try {
					// retreive all attributes from both Books and Authors tables
					gettile = conn.prepareStatement(
							" SELECT * " +
									"FROM creatures " + 
									"WHERE creatures.game_id = ?"
							);

					gettile.setInt(1, game_id);

					resultTile = gettile.executeQuery();

					List<Creature> result = new ArrayList<Creature>();
					while(resultTile.next()){
						Creature creature = new Creature();
						loadcreature(creature, resultTile, 1);
						result.add(creature);
					}

					return result;

				} finally {
					DBUtil.closeQuietly(gettile);
					DBUtil.closeQuietly(resultTile);
				}
			}
		});
	}

	//@Override
	public List<Creature> getAllCreaturesAtLocation(final int game_id, final int x, final int y) {
		return executeTransaction(new Transaction<List<Creature>>() {
			@Override
			public List<Creature> execute(Connection conn) throws SQLException {

				PreparedStatement gettile = null;
				ResultSet resultTile = null;

				try {
					// retreive all attributes from both Books and Authors tables
					gettile = conn.prepareStatement(
							" SELECT * " +
									"FROM creatures " + 
									"WHERE creatures.game_id = ? AND creatures.x_location = ? AND creatures.y_location = ?"
							);

					gettile.setInt(1, game_id);
					gettile.setInt(2, x);
					gettile.setInt(3, y);

					resultTile = gettile.executeQuery();

					List<Creature> result = new ArrayList<Creature>();
					while(resultTile.next()){
						Creature creature = new Creature();
						loadcreature(creature, resultTile, 1);
						result.add(creature);
					}

					return result;

				} finally {
					DBUtil.closeQuietly(gettile);
					DBUtil.closeQuietly(resultTile);
				}
			}
		});
	}

	private void loadcreature(Creature creature, ResultSet resultSet, int index) throws SQLException {
		creature.setCreatureId((resultSet.getInt(index++)));
		creature.setGameId(resultSet.getInt(index++));
		int item_id = resultSet.getInt(index++);
		if(item_id > 0){	//if item exists
			creature.setEquippedItem(getItem(item_id));
		}
		creature.setHealth(resultSet.getInt(index++));
		creature.setLocation(getTile(creature.getGameId(), resultSet.getInt(index++), resultSet.getInt(index++)));
		creature.setBaseDamage(resultSet.getInt(index++));
		creature.setMovementSpeed((resultSet.getInt(index++)));
	}

	//@Override
	public int createPlayer(final int game_id, final int equippedItem, final int health, final int x, final int y, final int baseDamage, final int score) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement id = null;
				ResultSet resultSet = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" INSERT INTO players (game_id, equippedItem, health, x_location, y_location, baseDamage, score) " + 
									"	VALUES (?, ?, ?, ?, ?, ?, ?)	"
							);
					stmt.setInt(1, game_id);
					stmt.setInt(2, equippedItem);
					stmt.setInt(3, health);
					stmt.setInt(4, x);
					stmt.setInt(5, y);
					stmt.setInt(6, baseDamage);
					stmt.setInt(7, score);

					stmt.executeUpdate();

					id = conn.prepareStatement(
							" SELECT items.item_id " +
									"FROM items " + 
									"WHERE items.game_id = ?"
									
							);
					id.setInt(1, game_id);
					resultSet = id.executeQuery();
					
					int result = -1;
					while(resultSet.next()){
						result = resultSet.getInt(1);
					}					

					return result;

				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(id);
					DBUtil.closeQuietly(resultSet);
				}
			}
		});
	}

	//@Override
	public boolean updatePlayerEquippedItem(final int player_id, final int equippedItem) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE players " + 
									"SET equippedItem = ? " +
									"WHERE players.player_id = ?"
							);

					stmt.setInt(1, equippedItem);
					stmt.setInt(2, player_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean updatePlayerHealth(final int player_id, final int health) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE players " + 
									"SET health = ? " +
									"WHERE players.player_id = ?"
							);

					stmt.setInt(1, health);
					stmt.setInt(2, player_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean updatePlayerX(final int player_id, final int x) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE players " + 
									"SET x_location = ? " +
									"WHERE players.player_id = ?"
							);

					stmt.setInt(1, x);
					stmt.setInt(2, player_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean updatePlayerY(final int player_id, final int y) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE players " + 
									"SET y_location = ? " +
									"WHERE players.player_id = ?"
							);

					stmt.setInt(1, y);
					stmt.setInt(2, player_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean updatePlayerBaseDamage(final int player_id, final int baseDamage) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE players " + 
									"SET baseDamage = ? " +
									"WHERE players.player_id = ?"
							);

					stmt.setInt(1, baseDamage);
					stmt.setInt(2, player_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean updatePlayerScore(final int player_id, final int score) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE players " + 
									"SET score = ? " +
									"WHERE players.player_id = ?"
							);

					stmt.setInt(1, score);
					stmt.setInt(2, player_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public boolean removePlayer(final int player_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" DELETE FROM players " +
									"WHERE players.player_id = ?"
							);
					stmt.setInt(1, player_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public Player getPlayer(final int game_id) {
		return executeTransaction(new Transaction<Player>() {
			@Override
			public Player execute(Connection conn) throws SQLException {

				PreparedStatement gettile = null;
				ResultSet resultTile = null;

				try {
					// retreive all attributes from both Books and Authors tables
					gettile = conn.prepareStatement(
							" SELECT * " +
									"FROM players " + 
									"WHERE players.game_id = ?"
							);

					gettile.setInt(1, game_id);

					resultTile = gettile.executeQuery();
					resultTile.next();

					Player player = new Player();
					loadplayer(player, resultTile, 1);

					return player;

				} finally {
					DBUtil.closeQuietly(gettile);
					DBUtil.closeQuietly(resultTile);
				}
			}
		});
	}

	//@Override
	public boolean addGameLog(final int game_id, final String text) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" INSERT INTO gameLogs (game_id, text) " + 
									"	VALUES (?, ?)	"
							);
					stmt.setInt(1, game_id);
					stmt.setString(2, text);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public String getGameLog(final int game_id) {
		return executeTransaction(new Transaction<String>() {
			@Override
			public String execute(Connection conn) throws SQLException {

				PreparedStatement gettile = null;
				ResultSet resultTile = null;

				try {
					// retreive all attributes from both Books and Authors tables
					gettile = conn.prepareStatement(
							" SELECT gameLogs.text " +
									"FROM gameLogs " + 
									"WHERE gameLogs.game_id = ?"
							);

					gettile.setInt(1, game_id);

					resultTile = gettile.executeQuery();

					StringBuilder builder = new StringBuilder();
					String[] strings = new String[0];
					while(resultTile.next()){
						strings = Arrays.copyOf(strings, strings.length+1);
						strings[strings.length-1] = (resultTile.getString(1) + "<br/><br/>");
					}
					
					for(int i = strings.length-1; i >= 0; i--){
						builder.append(strings[i]);
					}

					return builder.toString();

				} finally {
					DBUtil.closeQuietly(gettile);
					DBUtil.closeQuietly(resultTile);
				}
			}
		});
	}

	//@Override
	public List<String> getGameLogList(final int game_id) {
		return executeTransaction(new Transaction<List<String>>() {
			@Override
			public List<String> execute(Connection conn) throws SQLException {

				PreparedStatement gettile = null;
				ResultSet resultTile = null;

				try {
					// retreive all attributes from both Books and Authors tables
					gettile = conn.prepareStatement(
							" SELECT gameLogs.text " +
									"FROM gameLogs " + 
									"WHERE gameLogs.game_id = ?"
							);

					gettile.setInt(1, game_id);

					resultTile = gettile.executeQuery();

					List<String> result = new ArrayList<String>();
					while(resultTile.next()){
						result.add(resultTile.getString(1));
					}

					return result;

				} finally {
					DBUtil.closeQuietly(gettile);
					DBUtil.closeQuietly(resultTile);
				}
			}
		});
	}

	//@Override
	public boolean removeGameLog(final int game_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" DELETE FROM gameLogs " +
									"WHERE gameLogs.game_id = ?"
							);
					stmt.setInt(1, game_id);

					stmt.executeUpdate();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	private void loadplayer(Player player, ResultSet resultSet, int index) throws SQLException {
		player.setPlayerId(resultSet.getInt(index++));
		player.setGameId(resultSet.getInt(index++));
		int item_id = resultSet.getInt(index++);
		if(item_id > 0){	//if item exists
			player.setEquippedItem(getItem(item_id));
		}
		player.setHealth(resultSet.getInt(index++));
		player.setLocation(getTile(player.getGameId(), resultSet.getInt(index++), resultSet.getInt(index++)));
		player.setBaseDamage(resultSet.getInt(index++));
		player.setScore(resultSet.getInt(index++));
	}

	public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new PersistenceException("Transaction failed", e);
		}
	}

	public<ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
		Connection conn = connect();
		try {
			int numAttempts = 0;
			boolean success = false;
			ResultType result = null;
			while (!success && numAttempts < MAX_ATTEMPTS) {
				try {
					result = txn.execute(conn);
					conn.commit();
					success = true;
				} catch (SQLException e) {
					if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
						// Deadlock: retry (unless max retry count has been reached)
						numAttempts++;
					} else {
						// Some other kind of SQLException
						throw e;
					}
				}
			}
			if (!success) {
				throw new SQLException("Transaction failed (too many retries)");
			}
			// Success!
			return result;
		} finally {
			DBUtil.closeQuietly(conn);
		}
	}

	private Connection connect() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:derby:test.db;create=true");

		// Set autocommit to false to allow execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);

		return conn;
	}

	public void createTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt4 = null;
				PreparedStatement stmt5 = null;
				PreparedStatement stmt6 = null;
				PreparedStatement stmt7 = null;
				PreparedStatement stmt8 = null;
				PreparedStatement stmt9 = null;

				try {
					stmt1 = conn.prepareStatement(
							"create table accounts (" +
									"	account_id integer primary key " +
									"		generated always as identity (start with 1, increment by 1), " +									
									"	username varchar(40) UNIQUE," +
									"	password varchar(40)" +
									")"
							);
					stmt1.executeUpdate();

					stmt2 = conn.prepareStatement(
							"create table games (" +
									"	game_id integer primary key " +
									"		generated always as identity (start with 1, increment by 1), " +									
									"	account_id integer " +
									")"
							);
					stmt2.executeUpdate();

					stmt3 = conn.prepareStatement(
							"create table items (" +
									"	item_id integer primary key " +
									"		generated always as identity (start with 1, increment by 1), " +
									"	game_id integer, " +
									"	name varchar(35)," +
									"   description varchar(300)," +
									"   weight integer," +
									"   damage integer," +
									"   health integer," +
									"   quest_id integer, " +
									"   value integer " +
									")"
							);
					stmt3.executeUpdate();

					stmt4 = conn.prepareStatement(
							"create table inventories (" +
									"	inventory_id integer primary key " +
									"		generated always as identity (start with 1, increment by 1), " +
									"	creature_id integer, " +
									"	player_id integer, " +
									"	tile_id integer, " +
									"	item_id integer " +
									")"
							);
					stmt4.executeUpdate();

					stmt5 = conn.prepareStatement(
							"create table maps (" +
									"	map_id integer primary key " +
									"		generated always as identity (start with 1, increment by 1), " +
									"	game_id integer, " +
									"	height integer, " +
									"   width integer " +
									")"
							);
					stmt5.executeUpdate();

					stmt6 = conn.prepareStatement(
							"create table tiles (" +
									"	tile_id integer primary key " +
									"		generated always as identity (start with 1, increment by 1), " +
									"	game_id integer, " +
									"	type integer," +
									"	visible boolean," +
									"	description varchar(500), " +
									"	damage integer," +
									"   x integer," +
									"   y integer" +
									")"
							);
					stmt6.executeUpdate();

					stmt7 = conn.prepareStatement(
							"create table creatures (" +
									"	creature_id integer primary key " +
									"		generated always as identity (start with 1, increment by 1), " +
									"	game_id integer, " +
									"   equippedItem integer," +
									"   health integer," +
									"   x_location integer," +
									"   y_location integer," +
									"   baseDamage integer, " +
									"	moveSpeed integer " +
									")"
							);
					stmt7.executeUpdate();

					stmt8 = conn.prepareStatement(
							"create table players (" +
									"	player_id integer primary key " +
									"		generated always as identity (start with 1, increment by 1), " +
									"	game_id integer, " +
									"   equippedItem integer," +
									"   health integer," +
									"   x_location integer," +
									"   y_location integer," +
									"   baseDamage integer, " +
									"	score integer " +
									")"
							);
					stmt8.executeUpdate();

					stmt9 = conn.prepareStatement(
							"create table gameLogs (" +
									"	game_id integer, " +
									"   text varchar(5000) " +
									")"
							);
					stmt9.executeUpdate();

					return true;
				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
					DBUtil.closeQuietly(stmt4);
					DBUtil.closeQuietly(stmt5);
					DBUtil.closeQuietly(stmt6);
					DBUtil.closeQuietly(stmt7);
					DBUtil.closeQuietly(stmt8);
					DBUtil.closeQuietly(stmt9);
				}
			}
		});
	}

	public void removeTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt4 = null;
				PreparedStatement stmt5 = null;
				PreparedStatement stmt6 = null;
				PreparedStatement stmt7 = null;
				PreparedStatement stmt8 = null;
				PreparedStatement stmt9 = null;

				try {
					stmt1 = conn.prepareStatement(
							"drop table accounts"
							);
					stmt1.executeUpdate();

					stmt2 = conn.prepareStatement(
							"drop table games"
							);
					stmt2.executeUpdate();

					stmt3 = conn.prepareStatement(
							"drop table items"
							);
					stmt3.executeUpdate();

					stmt4 = conn.prepareStatement(
							"drop table inventories"
							);
					stmt4.executeUpdate();

					stmt5 = conn.prepareStatement(
							"drop table maps"
							);
					stmt5.executeUpdate();

					stmt6 = conn.prepareStatement(
							"drop table tiles"
							);
					stmt6.executeUpdate();

					stmt7 = conn.prepareStatement(
							"drop table creatures"
							);
					stmt7.executeUpdate();

					stmt8 = conn.prepareStatement(
							"drop table players"
							);
					stmt8.executeUpdate();

					stmt9 = conn.prepareStatement(
							"drop table gameLogs"
							);
					stmt9.executeUpdate();

					return true;
				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
					DBUtil.closeQuietly(stmt4);
					DBUtil.closeQuietly(stmt5);
					DBUtil.closeQuietly(stmt6);
					DBUtil.closeQuietly(stmt7);
					DBUtil.closeQuietly(stmt8);
					DBUtil.closeQuietly(stmt9);
				}
			}
		});
	}

	public void clearTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt4 = null;
				PreparedStatement stmt5 = null;
				PreparedStatement stmt6 = null;
				PreparedStatement stmt7 = null;
				PreparedStatement stmt8 = null;
				PreparedStatement stmt9 = null;

				try {
					stmt1 = conn.prepareStatement(
							"delete from accounts"
							);
					stmt1.executeUpdate();

					stmt2 = conn.prepareStatement(
							"delete from games"
							);
					stmt2.executeUpdate();

					stmt3 = conn.prepareStatement(
							"delete from items"
							);
					stmt3.executeUpdate();

					stmt4 = conn.prepareStatement(
							"delete from inventories"
							);
					stmt4.executeUpdate();

					stmt5 = conn.prepareStatement(
							"delete from maps"
							);
					stmt5.executeUpdate();

					stmt6 = conn.prepareStatement(
							"delete from tiles"
							);
					stmt6.executeUpdate();

					stmt7 = conn.prepareStatement(
							"delete from creatures"
							);
					stmt7.executeUpdate();

					stmt8 = conn.prepareStatement(
							"delete from players"
							);
					stmt8.executeUpdate();

					stmt9 = conn.prepareStatement(
							"delete from gameLogs"
							);
					stmt9.executeUpdate();

					return true;
				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
					DBUtil.closeQuietly(stmt4);
					DBUtil.closeQuietly(stmt5);
					DBUtil.closeQuietly(stmt6);
					DBUtil.closeQuietly(stmt7);
					DBUtil.closeQuietly(stmt8);
					DBUtil.closeQuietly(stmt9);
				}
			}
		});
	}

	// The main method creates the database tables and loads the initial data.
	public static void main(String[] args) throws IOException {
		System.out.println("Creating tables...");
		DerbyDatabase db = new DerbyDatabase();

		//db.createTables();
		//db.removeTables();	//to delete all tables
		db.clearTables();		//to clear all tables of all data

		System.out.println("Success!");
	}
}
