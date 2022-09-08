package trs.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Showtime entity, representing a showtime for a movie at a theatre
 */
public class Showtime {
	private String time;
	private Date date;
	private Movie movie;
	private Theatre theatre;
	private ArrayList<Seat> seats;

	public Showtime() {
		time = "";
		seats = null;
		date = null;
	}

	public Showtime(String time) {
		this.time = time;
		seats = new ArrayList<Seat>();
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public ArrayList<Seat> getSeats() {
		return seats;
	}

	public void setSeats(ArrayList<Seat> seats) {
		this.seats = seats;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Theatre getTheatre() {
		return theatre;
	}

	public void setTheatre(Theatre theatre) {
		this.theatre = theatre;
	}
}
