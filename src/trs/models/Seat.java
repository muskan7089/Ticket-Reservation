package trs.models;

/**
 * Seat entity, representing a seat within a theatre
 */
public class Seat {
	private int seatNumber;
	private boolean occupied, priority;
	private Showtime theShowtime;

	/**
	 * Seat entity constructor
	 */
	public Seat() {
		seatNumber = 0;
		occupied = true;
		theShowtime = new Showtime();
	}

	/**
	 * Seat entity constructor
	 * 
	 * @param seatNumber the seat number
	 * @param showtime   the Showtime
	 */

	public Seat(int seatNumber, int aisleNumber, Showtime theShowtime) {
		this.seatNumber = seatNumber;
		occupied = false;
		this.theShowtime = theShowtime;
	}

	/**
	 * the following are the getters and setters
	 * 
	 */

	public int getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	public Showtime getTheShowtime() {
		return theShowtime;
	}

	public String ShowtimetoString() {
		return "" + theShowtime.getTime();
	}

	public void setTheShowtime(Showtime theShowtime) {
		this.theShowtime = theShowtime;
	}

	public boolean isPriority() {
		return priority;
	}

	public void setPriority(boolean priority) {
		this.priority = priority;
	}
}
