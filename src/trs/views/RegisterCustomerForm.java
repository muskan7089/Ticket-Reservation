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
public class RegisterCustomerForm extends Panel {
	private UserController userController;
	private JButton registerButton, backButton;
	private JTextField username, name, email, cardNumber, pin;
	private JPasswordField password;
	private JPanel title, inputs, buttons;

	/**
	 * Creates the RegisterCustomerForm
	 * 
	 * @param panelController The PanelController
	 * @param userController The UserController
	 */
	public RegisterCustomerForm(PanelController panelController, UserController userController) {
		super(panelController);
		this.userController = userController;

		setLayout(new BorderLayout());
		setupTitle();
		setupInputs();
		setupButtons();
	}

	/**
	 * When the view is changed, the frame size is set to 350 x 200
	 */
	@Override
	public void onViewChanged(JFrame frame) {
		//frame.setSize(350, 200);
	}

	/**
	 * Sets up title text for panel
	 */
	private void setupTitle() {
		title = new JPanel();
		title.setBorder(new EmptyBorder(5, 5, 5, 5));
		title.add(new JLabel("Register new customer"));
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

		add(inputs, BorderLayout.CENTER);
	}

	/**
	 * Sets up parts of the panel
	 */
	private void setupButtons() {
		buttons = new JPanel();

		registerButton = new JButton("Register");
		registerButton.addActionListener((ActionEvent e) -> {
			int res = JOptionPane.showConfirmDialog(null, "Confirm registration?", "Register", JOptionPane.YES_NO_OPTION);
			
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
				JOptionPane.showMessageDialog(null, "PIN must be a valid number", "Register", JOptionPane.ERROR_MESSAGE);
				return;
			}
				
			RegisteredUser user = userController.register(newUser);

			if (user == null) {
				JOptionPane.showMessageDialog(null, "Failed to register", "Register", JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "New customer successfuly registered", "Register",
						JOptionPane.INFORMATION_MESSAGE);
				changeView("payFee");
			}
		});
		buttons.add(registerButton);

		backButton = new JButton("Back");
		backButton.addActionListener((ActionEvent e) -> {
			changeView("main");
		});
		buttons.add(backButton);

		add(buttons, BorderLayout.SOUTH);
	}
}
