package trs.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 * Manages accesses to the MySQL database.
 *
 */
public class DbController {
	private static DbController instance;
	private Connection connection;
	
	/**
	 * Return global instance of DbController
	 * @return DbController singleton instance
	 */
	public static DbController getInstance() {
		if (instance == null) {
			instance = new DbController();
		}
		
		return instance;
	}

	/**
	 * Constructs a DbController.
	 */
	private DbController() {
		String url = System.getenv("ENSF_DB_URL");
		String user = System.getenv("ENSF_DB_USER");
		String password = System.getenv("ENSF_DB_PASSWORD");

		if (url == null || user == null || password == null) {
			System.err.println("Environment variables for database not set");
			JOptionPane.showMessageDialog(null, "DB Environment variables not set!", "Database Connection", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			connection = DriverManager.getConnection(url, user, password);
			
			// Create database tables
			executeFile("/init.sql");
			
			// Create test data ONLY if the database is empty
			if (isDbEmpty()) {
				System.out.println("Seeded database with data");
				executeFile("/data.sql");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Checks if there are any entries in database - used to determine if to add test data or not
	 * 
	 * @return Whether there are any rows in the database
	 */
	private boolean isDbEmpty() {
		ResultSet res = query("SELECT * FROM theatre;");

		try {
			if (res.next()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return true;
	}
	
	/**
	 * Attempt to close MySQL database connection
	 */
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Reads and executes SQL from a file (in the resources folder)
	 * 
	 * @param filePath the filepath.
	 */
	public void executeFile(String filePath) {
		String content = "";

		try {
			InputStream stream = getClass().getResourceAsStream(filePath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

			while (reader.ready()) {
				content += reader.readLine();
			}

			reader.close();

			String[] parts = content.split(";");
			for (String s : parts) {
				execute(s.trim());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds information to the database given a query and arguments.
	 * 
	 * Example usage:
	 * execute("INSERT INTO offerings (number, capacity) VALUES (?, ?)", secNum, secCap);
	 * 
	 * @param query the command given to the database.
	 * @param args  the arguments given to the database.
	 * @return returns 0.
	 */
	public int execute(String query, Object... args) {
		try {
			PreparedStatement s = connection.prepareStatement(query);

			for (int i = 0; i < args.length; i++) {
				s.setObject(i + 1, args[i]);
			}

			s.execute();
			return s.getUpdateCount();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * Requests data from the database given a query and arguments.
	 * 
	 * Example usage:
	 * ResultSet set = query("SELECT * FROM courses WHERE name=? AND number=?", nameVariable, courseNum);
	 * 
	 * @param query the query
	 * @param args  the object array of arguments.
	 * @return returns a ResultSet containing the desired information, if fails,
	 *         returns null.
	 */
	public ResultSet query(String query, Object... args) {
		try {
			PreparedStatement s = connection.prepareStatement(query);

			for (int i = 0; i < args.length; i++) {
				s.setObject(i + 1, args[i]);
			}

			ResultSet res = s.executeQuery();
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}
