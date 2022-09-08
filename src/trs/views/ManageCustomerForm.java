package trs.views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import trs.controllers.PanelController;
import trs.controllers.UserController;
import trs.models.RegisteredUser;

@SuppressWarnings("serial")
public class ManageCustomerForm extends Panel {
	private UserController userController;
	private JButton updateButton, backButton;
	private JTextField username, name, email, cardNumber, pin;
	private JPasswordField password;
	private JPanel title, inputs, buttons;

	/**
	 * Creates the ManageCustomerForm
	 * 
	 * @param panelController The PanelController
	 * @param userController The UserController
	 */
	public ManageCustomerForm(PanelController panelController, UserController userController) {
		super(panelController);
		this.userController = userController;

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
		title.add(new JLabel("Update customer information"));
		add(title, BorderLayout.NORTH);
	}

	/**
	 * Sets up inputs for creating user
	 */
	private void setupInputs() {
		inputs = new JPanel();
		inputs.setBorder(new EmptyBorder(10, 20, 20, 20));
		inputs.setLayout(new BoxLayout(inputs, BoxLayout.Y_AXIS));

		username = new JTextField(50);
		inputs.add(new JLabel("Username"));
		inputs.add(username);

		password = new JPasswordField(50);
		inputs.add(new JLabel("Password"));
		inputs.add(password);
		
		name = new JTextField(50);
		inputs.add(new JLabel("Name"));
		inputs.add(name);
		
		email = new JTextField(50);
		inputs.add(new JLabel("Email"));
		inputs.add(email);
		
		cardNumber = new JTextField(50);
		inputs.add(new JLabel("Card number"));
		inputs.add(cardNumber);
		
		pin = new JTextField(50);
		inputs.add(new JLabel("PIN"));
		inputs.add(pin);
		
		RegisteredUser user = userController.getCurrentUser();
		
		if (user != null) {
			username.setText(user.getUsername());
			password.setText(user.getPassword());
			name.setText(user.getName());
			email.setText(user.getEmail());
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

		updateButton = new JButton("Update");
		updateButton.addActionListener((ActionEvent e) -> {
			int res = JOptionPane.showConfirmDialog(null, "Update customer details?", "Manage Customer", JOptionPane.YES_NO_OPTION);
			
			if (res != JOptionPane.YES_OPTION) {
				return;
			}
			
			RegisteredUser newUser = new RegisteredUser(username.getText(), new String(password.getPassword()));
			newUser.setEmail(email.getText());
			newUser.setName(name.getText());
			newUser.setCardNumber(cardNumber.getText());
			
			try {
				newUser.setPin(Integer.parseInt(pin.getText()));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "PIN must be a valid number", "Manage Customer", JOptionPane.ERROR_MESSAGE);
				return;
			}
				
			RegisteredUser user = userController.update(newUser);

			if (user == null) {
				JOptionPane.showMessageDialog(null, "Failed to update", "Manage Customer", JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Successfully updated customer information", "Manage Customer",
						JOptionPane.INFORMATION_MESSAGE);
				changeView("main");
			}
		});
		buttons.add(updateButton);

		backButton = new JButton("Back");
		backButton.addActionListener((ActionEvent e) -> {
			changeView("main");
		});
		buttons.add(backButton);

		add(buttons, BorderLayout.SOUTH);
	}
}
