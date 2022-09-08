package trs.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import trs.models.RegisteredUser;

public class UserController {
	private RegisteredUser currentUser;

	/**
	 * Checks if currently logged in
	 * 
	 * @return Whether you are logged in or not
	 */
	public boolean isLoggedIn() {
		return currentUser != null;
	}

	/**
	 * Logs out and sets current user to null
	 */
	public void logout() {
		currentUser = null;
	}

	/**
	 * Logs in user with given username and password
	 * 
	 * @param username Username to look for
	 * @param password Password to look for
	 * @return Logged in user, if found - null otherwise
	 */
	public RegisteredUser login(String username, String password) {
		ResultSet res = DbController.getInstance().query("SELECT * FROM user WHERE username = ? AND password = ?;",
				username, password);

		try {
			if (res.next()) {
				int id = res.getInt(1);
				String name = res.getString(2);
				String email = res.getString(3);
				username = res.getString(4);
				password = res.getString(5);
				String cardNumber = res.getString(6);
				int pin = res.getInt(7);

				RegisteredUser user = new RegisteredUser(username, password);
				user.setUserId(id);
				user.setName(name);
				user.setEmail(email);
				user.setCardNumber(cardNumber);
				user.setPin(pin);

				currentUser = user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return currentUser;
	}

	/**
	 * Creates a new user with parameters in given user object, logs them in, and
	 * returns the user
	 * 
	 * @param user User to create
	 * @return Newly registered user, or null if failed
	 */
	public RegisteredUser register(RegisteredUser user) {
		if (user == null) {
			currentUser = null;
			return null;
		}

		int count = DbController.getInstance().execute(
				"INSERT INTO user (name, email, username, password, cardNumber, pin) VALUES (?, ?, ?, ?, ?, ?);",
				user.getName(), user.getEmail(), user.getUsername(), user.getPassword(), user.getCardNumber(),
				user.getPin());

		if (count == 0) {
			currentUser = null;
			return null;
		}

		return login(user.getUsername(), user.getPassword());
	}

	/**
	 * Updates logged in user with information given in the new user object
	 * 
	 * @param user New information to update user with
	 * @return The new logged in user
	 */
	public RegisteredUser update(RegisteredUser user) {
		if (user == null || !isLoggedIn()) {
			return null;
		}

		int count = DbController.getInstance().execute(
				"UPDATE user SET name = ?, email = ?, username = ?, password = ?, cardNumber = ?, pin = ? WHERE id = ?;",
				user.getName(), user.getEmail(), user.getUsername(), user.getPassword(), user.getCardNumber(),
				user.getPin(), currentUser.getUserId());

		if (count == 0) {
			return null;
		}

		return login(user.getUsername(), user.getPassword());
	}

	/**
	 * Gets currently logged in user
	 * 
	 * @return Currently logged in user, or null if none
	 */
	public RegisteredUser getCurrentUser() {
		return currentUser;
	}
}
