package trs.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import trs.models.Cancellation;
import trs.models.Coupon;

public class CancellationController {
	private ArrayList<Coupon> coupons;
	private Cancellation cancellation;
	private int currentCancelledTicketID;
	private PurchaseController purchaseController; // check if user is registered or regular AND changes status of
													// cancelled seat
	private PaymentController paymentController; // needed for when a regular user pays admin fee

	public CancellationController(PurchaseController purchaseController, PaymentController paymentController) {
		this.purchaseController = purchaseController;
		this.paymentController = paymentController;
		cancellation = new Cancellation();
		coupons = new ArrayList<Coupon>();
	}

	/**
	 * Checks if the ticket the user wants to cancel is in the database
	 * 
	 * @param ticketID ID of the ticket to cancel
	 * @return true if found, false otherwise
	 */
	public boolean verifyCancellation(int ticketID) {
		ResultSet res = DbController.getInstance().query("SELECT * FROM ticket WHERE ticketId = ?;", ticketID);

		try {
			if (!res.next()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		currentCancelledTicketID = ticketID;
		return true;
	}

	/**
	 * Verifies that user is canceling a ticket up to 72 hours of show time
	 * 
	 * @return true if more than 72 hours, false otherwise
	 */
	@SuppressWarnings("deprecation")
	public boolean verifyTime(int ticketID) {
		ResultSet res = DbController.getInstance().query("SELECT * FROM ticket WHERE ticketId = ?;", ticketID);

		try {
			if (res.next()) {
				String showtime = res.getString("showtime");
				Date showDate = res.getDate("showDate");

				SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.US);
				Date time = timeFormat.parse(showtime);

				Calendar cal = Calendar.getInstance();
				cal.setTime(showDate);
				cal.set(Calendar.HOUR, time.getHours());
				cal.set(Calendar.MINUTE, time.getMinutes());

				// Cannot cancel from past
				if (new Date().getTime() >= cal.getTime().getTime()) {
					return false;
				}

				// Only allow if more than 3 days prior to show
				long days = TimeUnit.DAYS.convert(cal.getTime().getTime() - new Date().getTime(),
						TimeUnit.MILLISECONDS);

				if (days < 3) {
					return false;
				}

				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Checks if the current user is registered
	 * 
	 * @return true if registered, false otherwise
	 */
	public boolean checkIfReg() {
		if (purchaseController.getUserController().isLoggedIn())
			return true;
		return false;
	}

	/**
	 * Cancels a ticket for a regular user Cancellation process consists of paying
	 * an admin fee -> changing seat of cancelled ticket to vacant- >giving user a
	 * coupon
	 * 
	 * @param cardNum User's card number
	 * @param pin     User's pin
	 * @return coupon code if payAdminFee was able to make payment, 0 otherwise
	 */
	public int cancelTicket(String cardNum, int pin) {
		if (paymentController.payAdminFee(cardNum, pin)) {
			changeSeatStatus();
			int couponCode = createCoupon();
			return couponCode;
		} else
			return 0;
	}

	/**
	 * Cancels a ticket for a registered user Cancellation process consists of
	 * change seat of cancelled ticket to vacant -> giving user a coupon
	 * 
	 * @return coupon code
	 */
	public int cancelTicket() {
		changeSeatStatus();
		int couponCode = createCoupon();
		return couponCode;
	}

	/**
	 * Changes the status of the seat from the cancelled ticket to vacant
	 */
	private void changeSeatStatus() {
		int cancelledSeatNum = 0;
		String cancelledShowTime = null;
		ResultSet res = DbController.getInstance().query("SELECT * FROM ticket WHERE ticketId = ?;",
				currentCancelledTicketID);
		try {
			if (res.next()) {
				cancelledSeatNum = res.getInt("seatNumber");
				cancelledShowTime = res.getString("showtime");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// update seat in database
		DbController.getInstance().execute("UPDATE seat SET occupied = false WHERE seatNumber = ? AND showtime = ?;",
				cancelledSeatNum, cancelledShowTime);

		// add to cancellation table in database
		DbController.getInstance().execute("INSERT INTO cancellation (ticketId) VALUES(?);", currentCancelledTicketID);
	}

	/**
	 * Checks if the ticket has already been cancelled
	 * 
	 * @return Whether the ticket has already been cancelled
	 */
	public boolean alreadyCancelled(int tickedId) {
		ResultSet res = DbController.getInstance().query("SELECT * FROM cancellation WHERE ticketId = ?;", tickedId);

		try {
			if (res.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Creates a new coupon and stores it in database
	 * 
	 * @return coupon code
	 */
	@SuppressWarnings("deprecation")
	private int createCoupon() {
		Random rand = new Random(); // instance of random class

		int r = rand.nextInt(100);
		int couponAmount = cancellation.getCredits();
		Date expiration = new Date(2021, 12, 12);
		int code = expiration.getDate() + expiration.getDay() + r;

		// insert new coupon into database
		DbController.getInstance().execute("INSERT INTO coupon (code, amount, expiration) VALUES(?,?,?);", code,
				couponAmount, expiration);

		coupons.add(new Coupon(code, code, expiration));

		return code;
	}

}
