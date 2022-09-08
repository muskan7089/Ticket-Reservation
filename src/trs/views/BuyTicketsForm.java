package trs.views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import trs.controllers.PanelController;
import trs.controllers.PurchaseController;
import trs.controllers.*;
@SuppressWarnings("serial")
public class BuyTicketsForm extends Panel{
	
	private PurchaseController purchaseController;
	private ConfirmationController confirmationController;
	private JButton payment, couponButton, backButton, backToMain;
	private JPanel title, ticketDisplay, buttons;
	
	public BuyTicketsForm(PanelController panelController, PurchaseController purchaseController, ConfirmationController confirmationController) {
		super(panelController);
		this.purchaseController = purchaseController;
		this.confirmationController = confirmationController;
		setLayout(new BorderLayout());
		setupTitle();
		setupButtons();
	}

	/**
	 * When the view is changed, setupTicketDisplay() is called 
	 */
	@Override
	public void onViewChanged(JFrame frame) {
		// Refresh the panel every time it's loaded
		removeAll();
		setupTitle();
		setupTicketDisplay();
		setupButtons();
		
		frame.pack();
	}
	
	/**
	 * Sets up the title 
	 */
	private void setupTitle() {
		title = new JPanel();
		title.setBorder(new EmptyBorder(5, 5, 5, 5));
		title.add(new JLabel("Review your purchase: "));
		add(title, BorderLayout.NORTH);
		
	}
	
	/**
	 * Sets up the display for the ticket information
	 */
	private void setupTicketDisplay() {
		ticketDisplay = new JPanel();
		ticketDisplay.setBorder(new EmptyBorder(10,10,10,10));
		ticketDisplay.setLayout(new BoxLayout(ticketDisplay, BoxLayout.Y_AXIS));

		String[] ticketInformation = purchaseController.getTicketInfo();
		
		for(int i = 0; i<ticketInformation.length; i++) {
		  ticketDisplay.add(new JLabel(ticketInformation[i]));
		}
		
		add(ticketDisplay, BorderLayout.CENTER);
	}
	
	/**
	 * Sets up all the buttons 
	 */
	private void setupButtons() {
		buttons = new JPanel();

		payment = new JButton("Proceed to Payment");
		payment.addActionListener((ActionEvent e) -> {
			//when proceed to payment is pressed, first step is to check if user is registered or not
			//System.out.println("proceed to payment clicked...");
			if(purchaseController.checkIfRegistered()) { //if registered user
				int ticketID = purchaseController.proceedToPayment();
				if(ticketID!=-1) {
					JOptionPane.showMessageDialog(getRootPane(), "Payment completed! Please remember your ticket ID: " + ticketID, "Payment Confirmation", JOptionPane.INFORMATION_MESSAGE);
					confirmationController.confirmPurchase();
					changeView("main");
				}
				else 
					JOptionPane.showMessageDialog(getRootPane(), "Invalid information", "Payment Confirmation", JOptionPane.INFORMATION_MESSAGE);
					
			}else {//if NOT registered user
				String cardNum = JOptionPane.showInputDialog("Enter card number");
				if(cardNum!=null) { //if user presses cancel
					String pin = JOptionPane.showInputDialog("Enter pin");
					if(pin!=null) { //if user presses cancel
						if(cardNum.length()==0 || pin.length()==0) { //if user presses ok without entering anything
							JOptionPane.showMessageDialog(getRootPane(), "Please try entering your card number and pin again","Error", JOptionPane.ERROR_MESSAGE);
						}else {
							int ticketID = purchaseController.proceedToPayment(cardNum,pin);
							if(ticketID!=-1)
							{
								JOptionPane.showMessageDialog(getRootPane(), "Payment completed! Please remember your ticket ID: " + ticketID, "Payment Confirmation", JOptionPane.INFORMATION_MESSAGE);
								confirmationController.confirmPurchase();
								changeView("main");
							}else 
								JOptionPane.showMessageDialog(getRootPane(), "Invalid information", "Payment Confirmation", JOptionPane.INFORMATION_MESSAGE);
						}
						
					}
					
				}
				
					
			}
		});
		buttons.add(payment);

		couponButton = new JButton("Enter Coupon Code");
		couponButton.addActionListener((ActionEvent e) -> {
			String couponCode = JOptionPane.showInputDialog("Enter Coupon Code");
			if(couponCode!=null) {
				if(purchaseController.discount(couponCode)) {
					JOptionPane.showMessageDialog(getRootPane(), "Your ticket price has been discounted!", "Discount Confirmation", JOptionPane.INFORMATION_MESSAGE);
					changeView("buyTickets");
				}else {
					JOptionPane.showMessageDialog(getRootPane(), "Coupon code not valid", "Discount Confirmation", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		buttons.add(couponButton);
		
		backButton = new JButton("Back");
		backButton.addActionListener((ActionEvent e) -> {
			changeView("seat");
		});
		buttons.add(backButton);

		backToMain = new JButton("Back to Main Menu");
		backToMain.addActionListener((ActionEvent e) -> {
			changeView("main");
		});
		buttons.add(backToMain);
		add(buttons, BorderLayout.SOUTH);
		
	}
	
}
