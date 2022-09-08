package trs.views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import trs.controllers.PanelController;
import trs.controllers.PaymentController;
import trs.controllers.UserController;
import trs.models.RegisteredUser;

@SuppressWarnings("serial")
public class PayAccountFeeForm extends Panel {
	private UserController userController;
	private PaymentController paymentController;
	private JButton payButton, backButton;
	private JTextField cardNumber, pin;
	private JPanel title, inputs, buttons;

	/**
	 * Creates the PayAccountFeeForm and initializes the controllers/GUI components
	 * 
	 * @param panelController The PanelController
	 * @param userController  The UserController
	 */
	public PayAccountFeeForm(PanelController panelController, UserController userController,
			PaymentController paymentController) {
		super(panelController);
		this.userController = userController;
		this.paymentController = paymentController;

		setLayout(new BorderLayout());
		setupTitle();
		setupButtons();
	}

	/**
	 * When the view is changed, the frame size is set to 350 x 200
	 */
	@Override
	public void onBeforeViewChanged(JFrame frame) {
		if (inputs != null) {
			remove(inputs);
		}

		setupInputs();
	}

	/**
	 * Sets up title text for panel
	 */
	private void setupTitle() {
		title = new JPanel();
		title.setBorder(new EmptyBorder(5, 5, 5, 5));
		title.add(new JLabel("Pay account fee"));
		add(title, BorderLayout.NORTH);
	}

	/**
	 * Sets up pin and cardNumber inputs
	 */
	private void setupInputs() {
		inputs = new JPanel();
		inputs.setBorder(new EmptyBorder(10, 20, 20, 20));
		inputs.setLayout(new BoxLayout(inputs, BoxLayout.Y_AXIS));

		cardNumber = new JTextField(50);
		cardNumber.setEditable(false);
		inputs.add(new JLabel("Card number"));
		inputs.add(cardNumber);

		pin = new JTextField(50);
		pin.setEditable(false);
		inputs.add(new JLabel("PIN"));
		inputs.add(pin);

		RegisteredUser user = userController.getCurrentUser();

		if (user != null) {
			cardNumber.setText(user.getCardNumber());
			pin.setText("" + user.getPin());
		}

		add(inputs, BorderLayout.CENTER);
	}

	/**
	 * Sets up parts of the panel
	 */
	private void setupButtons() {
		buttons = new JPanel();

		payButton = new JButton("Pay Fee");
		payButton.addActionListener((ActionEvent e) -> {
			int res = JOptionPane.showConfirmDialog(getRootPane(), "Pay account fee?", "Account Fee",
					JOptionPane.YES_NO_OPTION);

			if (res != JOptionPane.YES_OPTION) {
				return;
			}

			if (paymentController.payAccountFee(userController.getCurrentUser())) {
				JOptionPane.showMessageDialog(getRootPane(), "Account fee paid successfully", "Account Fee",
						JOptionPane.INFORMATION_MESSAGE);
				changeView("main");
			} else {
				JOptionPane.showMessageDialog(getRootPane(), "Failed to pay account fee", "Account Fee",
						JOptionPane.ERROR_MESSAGE);
			}

		});
		buttons.add(payButton);

		backButton = new JButton("Back");
		backButton.addActionListener((ActionEvent e) -> {
			changeView("main");
		});
		buttons.add(backButton);

		add(buttons, BorderLayout.SOUTH);
	}
}
