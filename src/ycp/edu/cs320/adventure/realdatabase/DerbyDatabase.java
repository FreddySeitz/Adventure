package ycp.edu.cs320.adventure.realdatabase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

//	@Override
//	public List<Pair<Author, Book>> findAuthorAndBookByTitle(final String title) {
//		return executeTransaction(new Transaction<List<Pair<Author,Book>>>() {
//			@Override
//			public List<Pair<Author, Book>> execute(Connection conn) throws SQLException {
//				PreparedStatement stmt = null;
//				ResultSet resultSet = null;
//
//				try {
//					// retreive all attributes from both Books and Authors tables
//					stmt = conn.prepareStatement(
//							"select authors.*, books.* " +
//									"  from authors, books " +
//									" where authors.author_id = books.author_id " +
//									"   and books.title = ?"
//							);
//					stmt.setString(1, title);
//
//					List<Pair<Author, Book>> result = new ArrayList<Pair<Author,Book>>();
//
//					resultSet = stmt.executeQuery();
//
//					// for testing that a result was returned
//					Boolean found = false;
//
//					while (resultSet.next()) {
//						found = true;
//
//						// create new Author object
//						// retrieve attributes from resultSet starting with index 1
//						Author author = new Author();
//						loadAuthor(author, resultSet, 1);
//
//						// create new Book object
//						// retrieve attributes from resultSet starting at index 4
//						Book book = new Book();
//						loadBook(book, resultSet, 4);
//
//						result.add(new Pair<Author, Book>(author, book));
//					}
//
//					// check if the title was found
//					if (!found) {
//						System.out.println("<" + title + "> was not found in the books table");
//					}
//
//					return result;
//				} finally {
//					DBUtil.closeQuietly(resultSet);
//					DBUtil.closeQuietly(stmt);
//				}
//			}
//		});
//	}

//	public List<Pair<Author, Book>> BooksByAuthorLastNameQuery(final String lastname) {
//		return executeTransaction(new Transaction<List<Pair<Author,Book>>>() {
//			@Override
//			public List<Pair<Author, Book>> execute(Connection conn) throws SQLException {
//				PreparedStatement stmt = null;
//				ResultSet resultSet = null;
//
//				try {
//					// retreive all attributes from both Books and Authors tables
//					stmt = conn.prepareStatement(
//							"select authors.*, books.* " +
//									"  from authors, books " +
//									" where authors.author_id = books.author_id " +
//									"   and authors.lastname = ?" +
//									" order by books.title asc"
//							);
//					stmt.setString(1, lastname);
//
//					List<Pair<Author, Book>> result = new ArrayList<Pair<Author,Book>>();
//
//					resultSet = stmt.executeQuery();
//
//					// for testing that a result was returned
//					Boolean found = false;
//
//					while (resultSet.next()) {
//						found = true;
//
//						// create new Author object
//						// retrieve attributes from resultSet starting with index 1
//						Author author = new Author();
//						loadAuthor(author, resultSet, 1);
//
//						// create new Book object
//						// retrieve attributes from resultSet starting at index 4
//						Book book = new Book();
//						loadBook(book, resultSet, 4);
//
//						result.add(new Pair<Author, Book>(author, book));
//					}
//
//					// check if the title was found
//					if (!found) {
//						System.out.println("<" + lastname + "> was not found in the authors table");
//					}
//
//					return result;
//				} finally {
//					DBUtil.closeQuietly(resultSet);
//					DBUtil.closeQuietly(stmt);
//				}
//			}
//		});
//	}

//	@Override
//	public void InsertNewBookWithAuthor(String lastname, String firstname, String title, String isbn, String year) {
//		Connection conn = null;
//		PreparedStatement checkexistence = null;
//		PreparedStatement addauthor = null;
//		PreparedStatement obtainauthor = null;
//		PreparedStatement addbook = null;	
//		ResultSet checkexistenceresult = null;
//		ResultSet getauthorresult = null;
//		try {
//			conn = DriverManager.getConnection("jdbc:derby:test.db;create=true");
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
//		try {
//			//			conn.setAutoCommit(false);
//			conn.setAutoCommit(false);
//
//			//checking if author already exists
//			checkexistence = conn.prepareStatement(
//					"select authors.author_id"
//							+ "  from authors"
//							+ "  where authors.lastname = ? and authors.firstname = ?"
//					);
//			checkexistence.setString(1, lastname);
//			checkexistence.setString(2, firstname);
//			checkexistenceresult = checkexistence.executeQuery();
//			String authorid = "";
//			//checkexistenceresult.next();
//			ResultSetMetaData existenceSchema = checkexistence.getMetaData();
//			while (checkexistenceresult.next()) {
//				for (int i = 1; i <= existenceSchema.getColumnCount(); i++) {
//					Object obj = checkexistenceresult.getObject(i);
//					authorid = obj.toString();
//				}
//			}
//			if(!authorid.equals("")){
//				System.out.println("Adding book to existing author");
//			}
//			else{
//				//adding new author
//				addauthor = conn.prepareStatement(
//						"INSERT INTO authors (FIRSTNAME, LASTNAME)"
//								+ "VALUES (?, ?)"	
//						);
//				addauthor.setString(1, firstname);
//				addauthor.setString(2, lastname);
//				addauthor.executeUpdate();
//				System.out.println("New author has been added.");
//
//				//getting authorID
//				obtainauthor = conn.prepareStatement(
//						"select authors.author_id"
//								+ " from authors"
//								+ " where authors.firstname = ? and authors.lastname = ?"
//						);
//				obtainauthor.setString(1, firstname);
//				obtainauthor.setString(2, lastname);
//				getauthorresult = obtainauthor.executeQuery();
//				getauthorresult.next();
//				Object addobject = getauthorresult.getObject(1);
//				authorid = addobject.toString();
//			}
//			// a canned query to find book information (including author name) from title
//			addbook = conn.prepareStatement(
//					"INSERT INTO books (AUTHOR_ID, TITLE, ISBN, PUBLISHED)"
//							+ " VALUES (?, ?, ?, ?)"
//					);
//
//			// substitute the title entered by the user for the placeholder in the query
//			addbook.setString(1, authorid);
//			addbook.setString(2, title);
//			addbook.setString(3, isbn);
//			addbook.setString(4, year);
//
//			// execute the query
//			addbook.executeUpdate();
//			System.out.println("Book added");
//			
//			conn.commit();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			// close result set, statement, connection
//			DBUtil.closeQuietly(checkexistenceresult);
//			DBUtil.closeQuietly(checkexistence);
//			DBUtil.closeQuietly(addauthor);
//			DBUtil.closeQuietly(obtainauthor);
//			DBUtil.closeQuietly(getauthorresult);
//			DBUtil.closeQuietly(conn);
//		}
//	}



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

//	private void loadAuthor(Author author, ResultSet resultSet, int index) throws SQLException {
//		author.setAuthorId(resultSet.getInt(index++));
//		author.setLastname(resultSet.getString(index++));
//		author.setFirstname(resultSet.getString(index++));
//	}

//	private void loadBook(Book book, ResultSet resultSet, int index) throws SQLException {
//		book.setBookId(resultSet.getInt(index++));
//		book.setAuthorId(resultSet.getInt(index++));
//		book.setTitle(resultSet.getString(index++));
//		book.setIsbn(resultSet.getString(index++));
//		book.setPublished(resultSet.getInt(index++));		
//	}

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
					try {
					stmt1 = conn.prepareStatement(
							"create table accounts (" +
									"	account_id integer primary key " +
									"		generated always as identity (start with 1, increment by 1), " +									
									"	username varchar(40)," +
									"	password varchar(40)" +
									")"
							);
					stmt1.executeUpdate();

					stmt2 = conn.prepareStatement(
							"create table games (" +
									"	game_id integer primary key " +
									"		generated always as identity (start with 1, increment by 1), " +									
									"	account_id integer constraint account_id references accounts" +
									")"
							);	
					stmt2.executeUpdate();
					
					stmt3 = conn.prepareStatement(
							"create table items (" +
									"	item_id integer primary key " +
									"		generated always as identity (start with 1, increment by 1), " +
									"	game_id integer constraint game_id references games, " +
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
							"create table maps (" +
									"	map_id integer primary key " +
									"		generated always as identity (start with 1, increment by 1), " +
									"	game_id integer constraint game_id references games, " +
									"	height integer," +
									"   width integer" +
									")"
							);
					stmt4.executeUpdate();
					
					stmt5 = conn.prepareStatement(
							"create table tiles (" +
									"	tile_id integer primary key " +
									"		generated always as identity (start with 1, increment by 1), " +
									"	map_id integer constraint map_id references maps, " +
									"	type integer," +
									"   x integer," +
									"   y integer," +
									"   items varchar(200)" +
									")"
							);
					stmt5.executeUpdate();
					
					stmt6 = conn.prepareStatement(
							"create table creatures (" +
									"	creature_id integer primary key " +
									"		generated always as identity (start with 1, increment by 1), " +
									"	game_id integer constraint game_id references games, " +
									"	inventory varchar(50)," +
									"   equippedItem integer," +
									"   health integer," +
									"   x_location integer," +
									"   y_location integer," +
									"   baseDamage integer " +
									")"
							);
					stmt6.executeUpdate();
					
					stmt7 = conn.prepareStatement(
							"create table players (" +
									"	player_id integer primary key " +
									"		generated always as identity (start with 1, increment by 1), " +
									"	game_id integer constraint game_id references games, " +
									"	inventory varchar(50)," +
									"   equippedItem integer," +
									"   health integer," +
									"   x_location integer," +
									"   y_location integer," +
									"   baseDamage integer " +
									")"
							);
					stmt7.executeUpdate();
					
					

					return true;
				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
				}
			}
		});
	}

//	public void loadInitialData() {
//		executeTransaction(new Transaction<Boolean>() {
//			@Override
//			public Boolean execute(Connection conn) throws SQLException {
//				List<Author> authorList;
//				List<Book> bookList;
//
//				try {
//					authorList = InitialData.getAuthors();
//					bookList = InitialData.getBooks();
//				} catch (IOException e) {
//					throw new SQLException("Couldn't read initial data", e);
//				}
//
//				PreparedStatement insertAuthor = null;
//				PreparedStatement insertBook   = null;
//
//				try {
//					// populate authors table (do authors first, since author_id is foreign key in books table)
//					insertAuthor = conn.prepareStatement("insert into authors (lastname, firstname) values (?, ?)");
//					for (Author author : authorList) {
//						//						insertAuthor.setInt(1, author.getAuthorId());	// auto-generated primary key, don't insert this
//						insertAuthor.setString(1, author.getLastname());
//						insertAuthor.setString(2, author.getFirstname());
//						insertAuthor.addBatch();
//					}
//					insertAuthor.executeBatch();
//
//					// populate books table (do this after authors table,
//					// since author_id must exist in authors table before inserting book)
//					insertBook = conn.prepareStatement("insert into books (author_id, title, isbn, published) values (?, ?, ?, ?)");
//					for (Book book : bookList) {
//						//						insertBook.setInt(1, book.getBookId());		// auto-generated primary key, don't insert this
//						insertBook.setInt(1, book.getAuthorId());
//						insertBook.setString(2, book.getTitle());
//						insertBook.setString(3, book.getIsbn());
//						insertBook.setInt(4,  book.getPublished());
//						insertBook.addBatch();
//					}
//					insertBook.executeBatch();
//
//					return true;
//				} finally {
//					DBUtil.closeQuietly(insertBook);
//					DBUtil.closeQuietly(insertAuthor);
//				}
//			}
//		});
//	}

	// The main method creates the database tables and loads the initial data.
	public static void main(String[] args) throws IOException {
		System.out.println("Creating tables...");
		DerbyDatabase db = new DerbyDatabase();
		db.createTables();

		System.out.println("Loading initial data...");
		//db.loadInitialData();

		System.out.println("Success!");
	}
}
