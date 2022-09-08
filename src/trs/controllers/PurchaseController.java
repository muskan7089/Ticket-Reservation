package trs.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import trs.models.Seat;
import trs.models.Ticket;

public class PurchaseController {
	
	private ArrayList<Ticket> purchasedTickets;
	
	private PaymentController paymentController; //handles the payment
	private UserController userController; //used to check if user is registered or not
	private SeatController seatController; //used to change status of a seat after cancellation
	private MovieController movieController; // used to link ticket to movie
	private double price, discount;
	private int selectedSeatNumber;
	private String selectedShowTime; 
	private boolean priority;
	
	public PurchaseController(PaymentController payC, UserController userC, SeatController seatC, MovieController movieC) {
		paymentController = payC;
		userController = userC;
		seatController = seatC;
		movieController = movieC;
		resetPrice();
	}

	/**
	 * Reset the price and discount
	 */
	public void resetPrice() {
		price = 20;
		discount = 0;
	}
	
	/**
	 * Creates a string array with all of the user's selections (movie name, theater name, seat number, show time, show date, priority status)
	 * @return the ticket information in a string array
	 */
	public String[] getTicketInfo() {
		String [] message = new String[7];
		
		Seat selectedSeat = seatController.getSelectedSeats(); //get the user's selected seat from seat controller

		selectedSeatNumber = selectedSeat.getSeatNumber(); 
		selectedShowTime = selectedSeat.ShowtimetoString();
		
		ResultSet showtimeRes = DbController.getInstance().query("SELECT * FROM showtime WHERE showtime = ?;",
				selectedShowTime);
		try {
			if (showtimeRes.next()) {
				message[0] = "Movie: " + showtimeRes.getString("movieName");
				message[1] = "Theatre: "+ showtimeRes.getString("theatre");
				message[2] = "Seat: " + selectedSeatNumber;
				message[3] = "Showtime: " + selectedShowTime;
				message[4] = "Show date: " + showtimeRes.getDate("showDate");
				message[5] = "Price: $" + (price - discount);
 			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ResultSet seatRes = DbController.getInstance().query("SELECT * FROM seat WHERE seatNumber = ?;",
				selectedSeatNumber);
		priority = false;
		try {
			if (seatRes.next()) {
				if(seatRes.getBoolean("priority")) {
					message[6] = "Priority Seat Selected";
					priority = true;
				}else {
					message[6] = "Regular Seat Selected";
				}
 			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return message;
	}
	
	/**
	 * Updates the price if coupon code is valid
	 * @param couponCode Coupon code to look for in the database
	 * @return true if coupon was found in database, false otherwise
	 */
	public boolean discount(String couponCodeA) {
		int couponCode = 0;
		try {
			couponCode = Integer.parseInt(couponCodeA);
		}catch(NumberFormatException ex) {
			return false;
		}
		ResultSet res = DbController.getInstance().query("SELECT * FROM coupon WHERE code = ?;",
				couponCode);
		discount = 0;
		try {
			if (res.next()) {
				discount = res.getInt("amount");
			}else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	/**
	 * Checks if the current user is a registered user
	 * Called in the BuyTicketsForm after user clicks the proceed to payment button?
	 * @return true if registered, false otherwise 
	 */
	public boolean checkIfRegistered() {
		if(!userController.isLoggedIn()) { //means regular user
			return false;
		}
		return true;
	}
	
	/**
	 * Proceeds to payment for a registered user
	 * @return ticketID if paymentController successfully made the payment, -1 otherwise
	 */
	public int proceedToPayment() {
		String cardNum = userController.getCurrentUser().getCardNumber();
		int pin = userController.getCurrentUser().getPin();
		if(paymentController.payTicket(price, cardNum,pin)) { 
			seatController.updateSeatStatus();
			return storeTicket(selectedSeatNumber, selectedShowTime, priority);
		}
		else 
			return -1;
		
	}
	
	/**
	 * Proceeds to payment for a regular user
	 * Called after regular user has entered their payment information 
	 * @param cardNum User's card number
	 * @param pin User's pin
	 * @return ticketID if paymentController successfully made the payment, -1 otherwise
	 */
	public int proceedToPayment(String cardNum, String pinA) {
		int pin = 0;
		try {
			pin = Integer.parseInt(pinA);
		}catch(NumberFormatException ex) {
			return -1;
		}
		
		if(paymentController.payTicket(price, cardNum,pin)) { 
			seatController.updateSeatStatus();
			return storeTicket(selectedSeatNumber, selectedShowTime, priority);
		}
		else 
			return -1;
		
	}
	
	/**
	 * Stores the newly purchased ticket in the database 
	 * @param selectedSeatNumber
	 * @param selectedShowTime
	 * @param priorityStatus
	 * @return the ticketID
	 */
	private int storeTicket(int selectedSeatNumber, String selectedShowTime, boolean priorityStatus) {
		String movieName = movieController.getSelectedMovie().getMovieName();
		String theatre = movieController.getSelectedShowtime().getTheatre().getTheatreName();
		Date showDate = movieController.getSelectedShowtime().getDate();

		DbController.getInstance().execute(
				"INSERT INTO ticket (seatNumber, showtime, priority, movieName, theatre, showDate) VALUES(?,?,?,?,?,?);",
				selectedSeatNumber, selectedShowTime, priorityStatus, movieName, theatre, showDate);
		ResultSet res = DbController.getInstance().query("SELECT * FROM ticket WHERE seatNumber = ?;",
				selectedSeatNumber);
		int ticketID = 0;
		try {
			if (res.next()) {
				ticketID = res.getInt("ticketId");
 			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ticketID;
		
	}
	
	
	public ArrayList<Ticket> getTickets() {
		return purchasedTickets;
	}

	public void setTickets(ArrayList<Ticket> tickets) {
		this.purchasedTickets = tickets;
	}
	
	public UserController getUserController() {
		return userController;
	}

	public SeatController getSeatController() {
		return seatController;
	}

	public void setSeatController(SeatController seatController) {
		this.seatController = seatController;
	}
}
