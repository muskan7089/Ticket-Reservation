package trs.models;

import java.util.ArrayList;

/**
 * Cancellation entity, representing when a user cancelled a ticket
 */
public class Cancellation {
	private ArrayList<Ticket> cancelledTickets;
	private int credits;

	public Cancellation() {
		cancelledTickets = new ArrayList<Ticket>();
		credits = 5;
	}

	public ArrayList<Ticket> getTicket() {
		return cancelledTickets;
	}

	public void setTicket(ArrayList<Ticket> ticket) {
		this.cancelledTickets = ticket;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}
}
