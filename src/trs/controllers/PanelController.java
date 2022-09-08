package trs.controllers;

import java.util.HashMap;

import trs.views.*;

/**
 * Controls the panels and switches between them.
 *
 */
public class PanelController {
	private Frame frame;
	private HashMap<String, Panel> panels;

	/**
	 * Constructor for panel controller
	 */
	public PanelController() {
		frame = new Frame("Test");
		panels = new HashMap<String, Panel>();

		// Create controllers here
		UserController userController = new UserController();
		TheatreController theatreController = new TheatreController();
		MovieController movieController = new MovieController(theatreController);
		SeatController seatController = new SeatController(movieController,userController);
		PaymentController paymentController = new PaymentController();
		PurchaseController purchaseController = new PurchaseController(paymentController, userController,
				seatController, movieController);
		CancellationController cancellationController = new CancellationController(purchaseController,
				paymentController);
		NewsController newsController = new NewsController();
        ConfirmationController confirmationController = new ConfirmationController(purchaseController); 
		// Add new panels in here. First parameter is a unique string name, second is
		// panel instance
		panels.put("main", new MainForm(this, userController));
		panels.put("login", new LoginForm(this, userController));
		panels.put("register", new RegisterCustomerForm(this, userController));
		panels.put("movie", new SelectMovieForm(this, movieController));
		panels.put("seat", new SelectSeatForm(this, seatController, purchaseController));
		panels.put("manage", new ManageCustomerForm(this, userController));
		panels.put("selectTheatre", new SelectTheatreForm(this, theatreController));
		panels.put("payFee", new PayAccountFeeForm(this, userController, paymentController));
		panels.put("buyTickets", new BuyTicketsForm(this, purchaseController, confirmationController));
		panels.put("showtime", new SelectShowtimeForm(this, movieController));
		panels.put("cancelTicket", new CancelTicketForm(this, cancellationController, confirmationController));
		panels.put("news", new ViewMovieNewsForm(this, newsController, movieController));

		switchTo("main");
	}

	/**
	 * Switches to a different panel based on the panel's name.
	 * 
	 * @param key the desired panel to switch to.
	 */
	public void switchTo(String key) {
		if (!panels.containsKey(key)) {
			System.err.println("Error, panel does not exist: " + key);
			return;
		}

		Panel panel = panels.get(key);
		panel.onBeforeViewChanged(frame);
		frame.setPanel(panel);
		frame.pack();
		panel.onViewChanged(frame);
		frame.center();
	}
}
