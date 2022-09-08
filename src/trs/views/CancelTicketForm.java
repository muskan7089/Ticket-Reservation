package trs.views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import trs.controllers.CancellationController;
import trs.controllers.ConfirmationController;
import trs.controllers.PanelController;

@SuppressWarnings("serial")
public class CancelTicketForm extends Panel{
	
    private CancellationController cancelController;
    private ConfirmationController confirmationController;
    private JButton cancelButton, backButton;
    private JTextField ticketID;
	private JPanel title, input, buttons;
	
	public CancelTicketForm(PanelController panelController, CancellationController cancelController, ConfirmationController confirmationController) {
		super(panelController);
		this.cancelController = cancelController;
		this.confirmationController = confirmationController;
		setupTitle();
		setupInput();
		setupButtons();
	}

	/**
	 * Sets up the title
	 */
	private void setupTitle() {
		title = new JPanel();
		title.setBorder(new EmptyBorder(5, 5, 5, 5));
		title.add(new JLabel("Cancel Your Ticket"));
		add(title, BorderLayout.NORTH);
	}
	
	/**
	 * Sets up the input for ticket id
	 */
	private void setupInput() {
		input = new JPanel();
		input.setBorder(new EmptyBorder(10, 20, 20, 20));
		input.setLayout(new BoxLayout(input, BoxLayout.Y_AXIS));

		ticketID = new JTextField(30);
		input.add(new JLabel("Enter the ticket ID number of the ticket you want to cancel"));
		input.add(ticketID);

		add(input, BorderLayout.CENTER);
		
	}

	/**
	 * Sets up the buttons
	 */
	private void setupButtons() {
		buttons = new JPanel();

		cancelButton = new JButton("Cancel Ticket");
		cancelButton.addActionListener((ActionEvent e) -> {
			try {
				int tID = Integer.parseInt(ticketID.getText());
				
				if(!cancelController.verifyCancellation(tID)) { //invalid transaction ID
					JOptionPane.showMessageDialog(getRootPane(), "Ticket not found. Please double check your ticket ID and try again", "Cancel Ticket", JOptionPane.ERROR_MESSAGE);
				}else {
					if (cancelController.alreadyCancelled(tID)) {
						JOptionPane.showMessageDialog(getRootPane(), "Ticket already cancelled", "Cancel Ticket", JOptionPane.ERROR_MESSAGE);
						return;
					}

					if(!cancelController.verifyTime(tID)) { //invalid time
						JOptionPane.showMessageDialog(getRootPane(), "Sorry, you can only cancel a ticket up to 72 hours of the show time", "Cancel Ticket", JOptionPane.ERROR_MESSAGE);
						changeView("main");
					}else {
						int couponCode;
						if(cancelController.checkIfReg()) { //if user is registered
							couponCode = cancelController.cancelTicket();
						}else { //if user is unregistered, need to pay an admin fee
							String cardNum = JOptionPane.showInputDialog("Enter card number to pay the admin fee");
							String pin = JOptionPane.showInputDialog("Enter pin");
							couponCode = cancelController.cancelTicket(cardNum, Integer.parseInt(pin));
						}
						if(couponCode!=0) {
							confirmationController.confirmCancellation( couponCode);
						}else {
							JOptionPane.showMessageDialog(getRootPane(), "Payment information invalid, ticket was not cancelled", "Payment Confirmation", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(getRootPane(), "Please enter valid ticket ID", "Cancel Ticket", JOptionPane.ERROR_MESSAGE);
			}
		});
		buttons.add(cancelButton);

		backButton = new JButton("Back");
		
		backButton.addActionListener((ActionEvent e) -> {
			changeView("main");
		});
		
		buttons.add(backButton);
		add(buttons, BorderLayout.SOUTH);
		
	}
	

}
