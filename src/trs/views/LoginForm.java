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
public class LoginForm extends Panel {
	private UserController userController;
	private JButton loginButton, backButton;
	private JTextField username;
	private JPasswordField password;
	private JPanel title, inputs, buttons;

	public LoginForm(PanelController panelController, UserController userController) {
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
		//frame.setSize(350, 225);
	}

	/**
	 * Sets up title text for panel
	 */
	private void setupTitle() {
		title = new JPanel();
		title.setBorder(new EmptyBorder(5, 5, 5, 5));
		title.add(new JLabel("Please enter username and password"));
		add(title, BorderLayout.NORTH);
	}

	/**
	 * Sets up username and password inputs
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

		add(inputs, BorderLayout.CENTER);
	}

	/**
	 * Sets up parts of the panel
	 */
	private void setupButtons() {
		buttons = new JPanel();

		loginButton = new JButton("Login");
		loginButton.addActionListener((ActionEvent e) -> {
			String passwordStr = new String(password.getPassword());
			RegisteredUser user = userController.login(username.getText(), passwordStr);
			
			if (user == null) {
				JOptionPane.showMessageDialog(getRootPane(), "User does not exist", "Login", JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(getRootPane(), "Logged in succesfully", "Login", JOptionPane.INFORMATION_MESSAGE);
				changeView("main");
			}
		});
		buttons.add(loginButton);

		backButton = new JButton("Back");
		backButton.addActionListener((ActionEvent e) -> {
			changeView("main");
		});
		buttons.add(backButton);

		add(buttons, BorderLayout.SOUTH);
	}
}
