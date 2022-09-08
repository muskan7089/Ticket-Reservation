package trs.models;

/**
 * Priority Ticket entity, representing a ticket bought for a priority seat
 */
public class PriorityTicket extends Ticket{
	public PriorityTicket(Movie m, Theatre t,  Seat s, Showtime time) {
		super(m, t, s, time, true);
	}
}
