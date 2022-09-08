package trs.controllers;

import javax.swing.JOptionPane;

import trs.models.Confirmation;


public class ConfirmationController{
	private PurchaseController purchaseController;
	private Confirmation confirmation;
	
	
	public ConfirmationController(PurchaseController purchaseController) {
		this.purchaseController = purchaseController;
	}
	
	/**
	 * This is a helper method that sets the confirmation message to be displayed
	 * on the dialog box.
	 */
	private void message() {
		confirmation = new Confirmation();
		String[] message = purchaseController.getTicketInfo();
		String finalMessage ="";
		
		for(int i = 0; i < message.length; i++) {
			finalMessage += message[i] + "\n";
		}
		// System.out.println(finalMessage);
		confirmation.setMessage(finalMessage);
	}
	
	/**
	 * This method displays a dialog box to the user asking them to confirm their purchase.
	 * If the user selects YES, another dialog box will appear showing that the booking is confirmed.
	 * If the user selects NO, no action is taken.
	 */
	public void confirmPurchase() {
		message();
		JOptionPane.showMessageDialog(null, "Booking Confirmed \n"+ confirmation.getMessage(), "Booking Confirmation", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * This method displays a dialog box to the user asking them to confirm the ticket
	 * cancellation. If the user selects YES, another dialog box will appear showing that
	 * the ticket has been cancelled. 
	 * If the user selects NO, no action is taken.
	 */
	public void confirmCancellation(int couponCode) {
		message();
		JOptionPane.showMessageDialog(null, "Cancellation completed! Remember your coupon code (" + couponCode + ") to get $10 dollars off your next ticket \n"+ confirmation.getMessage(), "Cancellation Confirmation", JOptionPane.INFORMATION_MESSAGE);

	}
	
	
}
