package trs.controllers;
import java.util.ArrayList;
import trs.models.RegisteredUser;
import trs.models.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentController {
	
	private ArrayList<Transaction> transactions;
	
	public PaymentController() {
		transactions = new ArrayList<Transaction>();
	}
	/**
	 * Pays the account fee of the given Registered User
	 * 
	 * Called during the last step of the registration process when the regular user has to 
	 * pay the account fee, OR called when an already registered user is renewing their account
	 * 
	 * @param theUser The registered user who's account fee needs to be paid
	 * @return true if user's payment info was valid, false otherwise 
	 */
	public boolean payAccountFee(RegisteredUser theUser) {
		String username = theUser.getUsername();
		ResultSet res = DbController.getInstance().query("SELECT * FROM user WHERE username = ?;",
				username);
		String cardNum = "";
		int pin_digits = 0;
		try {
			if (res.next()) {
				cardNum = res.getString("cardNumber");
				pin_digits = String.valueOf(res.getInt("pin")).length();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	

		if(cardNum.length()>=6 && pin_digits>=4)
			return true;
		
		return false;
	}
	
	/**
	 * Pays the admin fee
	 * Called during the cancellation process for a regular user
	 * 
	 * @param cardNumber User's card number
	 * @param pin User's pin
	 * @return true if given payment information was valid, false otherwise 
	 */
	public boolean payAdminFee(String cardNumber, int pin) {
		int pin_digits = String.valueOf(pin).length();
		if(cardNumber.length()>=6 && pin_digits>=4)
			return true;
		return false;
	}
	
	
	/**
	 * Processes the ticket payment by verifying the given payment information. 
	 * Stores the newly created transaction in the database
	 * @param amount Price of the ticket/purchase
	 * @param cardNumber User's card number
	 * @param pin User's pin
	 * @return true if payment is verified and transaction successfully stored in database, false otherwise 
	 */
	public boolean payTicket(double amount, String cardNumber, int pin) {
		int pin_digits = String.valueOf(pin).length();
		
		if(cardNumber.length()<6 || pin_digits<4) { 
			return false;//payment information was NOT valid so return false
		}else {
			//add new transaction to database
			DbController.getInstance().execute(
					"INSERT INTO transaction (amount, cardNumber, pin) VALUES(?,?,?);",
					amount, cardNumber, pin);
			
			//creating a transaction object to store in array list
			ResultSet res = DbController.getInstance().query("SELECT * FROM transaction WHERE cardNumber = ? AND pin = ?;",
					cardNumber, pin);
			int tID = 0;
			try {
				if (res.next()) {
					 tID = res.getInt("transactionId");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Transaction t = new Transaction(tID, cardNumber, pin, amount);
			transactions.add(t);
			return true; //payment verified and transaction successfully stored in database
		}
		
	}
	
	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(ArrayList<Transaction> transactions) {
		this.transactions = transactions;
	} 
}
