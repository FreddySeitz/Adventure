package ycp.edu.cs320.adventure.realdatabase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ycp.edu.cs320.adventure.game.Item;
import ycp.edu.cs320.adventure.realdatabase.DBUtil;

public class DerbyDatabase {
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
	public void createAccount(final String username, final String password) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" INSERT INTO accounts (USERNAME, PASSWORD) " + 
									"	VALUES (?, ?);	"
							);
					stmt.setString(1, username);
					stmt.setString(2, password);

					stmt.executeQuery();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public void getAccount(final String username) {
		executeTransaction(new Transaction<Integer>() {
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

					return resultSet.getInt(1);

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public void createGame(final int account_id) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" INSERT INTO games (account_id) " + 
									"	VALUES (?);	"
							);
					stmt.setInt(1, account_id);

					stmt.executeQuery();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public void getGame(final int account_id) {
		executeTransaction(new Transaction<List<Integer>>() {
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
				}
			}
		});
	}
	
	//@Override
		public void removeGame(final int game_id) {
			executeTransaction(new Transaction<Boolean>() {
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

						stmt.executeQuery();

						return true;

					} finally {
						DBUtil.closeQuietly(stmt);
					}
				}
			});
		}

	//@Override
	public void createItem(final int game_id, final String name, final String description, final int weight, final int damage, final int health, final int quest_id, final int value) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" INSERT INTO items (game_id, name, description, weight, damage, health, quest_id, value) " + 
									"	VALUES (?);	"
							);
					stmt.setInt(1, game_id);
					stmt.setString(2, name);
					stmt.setString(3, description);
					stmt.setInt(4, weight);
					stmt.setInt(5, damage);
					stmt.setInt(6, health);
					stmt.setInt(7, quest_id);
					stmt.setInt(8, value);

					stmt.executeQuery();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public void updateItemAll(final int item_id, final int game_id, final String name, final String description, final int weight, final int damage, final int health, final int quest_id, final int value) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					// retreive all attributes from both Books and Authors tables
					stmt = conn.prepareStatement(
							" UPDATE items " +
									"(game_id = ?, name = ?, description = ?, weight = ?, damage = ?, health = ?, quest_id = ?, value = ?) " + 
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

					stmt.executeQuery();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public void removeItem(final int item_id) {
		executeTransaction(new Transaction<Boolean>() {
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

					stmt.executeQuery();

					return true;

				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	//@Override
	public void getItem(final int item_id) {
		executeTransaction(new Transaction<Item>() {
			@Override
			public Item execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					// retrieve a specific item
					stmt = conn.prepareStatement(
							" SELECT items.item_id " +
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
				}
			}
		});
	}

	//@Override
	public void getAllItems(final int game_id) {
		executeTransaction(new Transaction<List<Item>>() {
			@Override
			public List<Item> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					// retrieve a specific item
					stmt = conn.prepareStatement(
							" SELECT items.item_id " +
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
				}
			}
		});
	}

	private void loaditem(Item item, ResultSet resultSet, int index) throws SQLException {
		item.setAccountId(resultSet.getInt(index++));
		item.setDescription(resultSet.getString(index++));
		item.setName(resultSet.getString(index++));
		item.setWeight(resultSet.getInt(index++));
		item.setDamage(index++);
		item.setHealth(index++);
		item.setQuestId(index++);
		item.setValue(index++);
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
									"	game_id integer, " +
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
									"	map_id integer, " +
									"	type integer," +
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
				}
			}
		});
	}

	// The main method creates the database tables and loads the initial data.
	public static void main(String[] args) throws IOException {
		System.out.println("Creating tables...");
		DerbyDatabase db = new DerbyDatabase();

		db.createTables();
		//db.removeTables();	//to delete all tables, comment out create tables first.

		System.out.println("Success!");
	}
}
