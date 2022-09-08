package trs;

import trs.controllers.DbController;
import trs.controllers.PanelController;
/**
 * the central system class
 *
 */
public class TicketRegistrationSystem {
	public static void main(String[] args) {
		// Create a new instance so it's ready for whenever you need it
		DbController.getInstance();
		
		// Create the panels
		new PanelController();
	}
}
