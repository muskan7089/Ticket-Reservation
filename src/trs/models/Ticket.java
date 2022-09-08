package trs.models;

/**
 * Ticket entity, representing the db entity for a ticket for a seat to a movie showtime
 */
public class Ticket {
	private int TicketID;
	private Movie movie;
	private Theatre theatre;
	private Seat seat;
	private Showtime st;
	private boolean priorityStatus;

	public Ticket(Movie m, Theatre t, Seat s, Showtime time, boolean status) {
		seat = s;
		movie = m;
		st = time;
		priorityStatus = status;
	}

	/**
	 * 
	 * @return Ticket ID
	 */
	public int getID() {
		return TicketID;
	}

	/**
	 * Sets the movie to a movie provided as a parameter
	 * 
	 * @param movie to be assigned
	 */
	public void setMovie(Movie m) {
		movie = m;
	}

	/**
	 * 
	 * @return movie object
	 */
	public Movie getMovie() {
		return movie;
	}

	/**
	 * Sets the theatre to a theatre provided as a parameter
	 * 
	 * @param theatre to be assigned
	 */
	public void setTheatre(Theatre t) {
		theatre = t;
	}

	/**
	 * 
	 * @return theatre object
	 */
	public Theatre getTheatre() {
		return theatre;
	}

	/**
	 * Sets the seat to a seat provided as a parameter
	 * 
	 * @param seat to be assigned
	 */
	public void setSeat(Seat s) {
		seat = s;
	}

	/**
	 * 
	 * @return seat object
	 */
	public Seat getSeat() {
		return seat;
	}

	/**
	 * Sets the showtime to a showtime provided as a parameter
	 * 
	 * @param showtime to be assigned
	 */
	public void setShowtime(Showtime time) {
		st = time;
	}

	/**
	 * 
	 * @return showtime object
	 */
	public Showtime getShowtime() {
		return st;
	}

	/**
	 * Sets the priority status to a status provided as a paramter
	 * 
	 * @param status to be assigned
	 */
	public void setStatus(boolean status) {
		priorityStatus = status;
	}

	/**
	 * 
	 * @return status of the ticket
	 */
	public boolean getStatus() {
		return priorityStatus;
	}

}
