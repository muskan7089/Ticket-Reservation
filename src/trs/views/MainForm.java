package trs.views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import trs.controllers.PanelController;
import trs.controllers.UserController;
import trs.models.RegisteredUser;

@SuppressWarnings("serial")
public class MainForm extends Panel {
	private UserController userController;
	
	public MainForm(PanelController panelController, UserController userController) {
		super(panelController);
		this.userController = userController;
		
		setBorder(new EmptyBorder(10, 20, 20, 20));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	/**
	 * When the view is changed, the frame size is set to 350 x 200
	 */
	@Override
	public void onViewChanged(JFrame frame) {		
		// Refresh the panel every time it's loaded
		removeAll();
		setupButtons();
		
		this.setPreferredSize(new Dimension(300, 250));
		frame.pack();
	}
	
	/**
	 * Sets up buttons for main form
	 */
	private void setupButtons() {
		add(new JLabel("Theatre Registration System"));
		
		if (userController.isLoggedIn()) {
			RegisteredUser user = userController.getCurrentUser();
			add(new JLabel("Hello " + user.getName()));
		}
		
		JButton theatres = new JButton("View Theatres");
		theatres.addActionListener((ActionEvent e) -> {
			changeView("selectTheatre");
		});
		add(theatres);
		
		JButton cancel = new JButton("Cancel Ticket");
		cancel.addActionListener((ActionEvent e) -> {
			changeView("cancelTicket");
		});
		add(cancel);
		
		if (!userController.isLoggedIn()) {
			JButton login = new JButton("Login");
			login.addActionListener((ActionEvent e) -> {
				changeView("login");
			});
			add(login);
			
			JButton register = new JButton("Register");
			register.addActionListener((ActionEvent e) -> {
				changeView("register");
			});
			add(register);
		} else {
			JButton payFee = new JButton("Pay Account Fee");
			payFee.addActionListener((ActionEvent e) -> {
				changeView("payFee");
			});
			add(payFee);
			
			JButton manage = new JButton("Manage Customer");
			manage.addActionListener((ActionEvent e) -> {
				changeView("manage");
			});
			add(manage);
			
			JButton news = new JButton("View News");
			news.addActionListener((ActionEvent e) -> {
				changeView("news");
			});
			add(news);
			
			JButton logout = new JButton("Logout");
			logout.addActionListener((ActionEvent e) -> {
				userController.logout();
				changeView("main");
			});
			add(logout);
		}
		
		JButton quit = new JButton("Quit");
		quit.addActionListener((ActionEvent e) -> {
			System.exit(0);
		});
		add(quit);
	}
}
