package trs.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import trs.models.Movie;
import trs.models.Seat;
import trs.models.Showtime;
/**
 * seat controller class
 * 
 *
 */
public class SeatController {
	/**
	 * member variables declaration and initialization
	 */
	private Seat selectedSeat;
	private MovieController movieController;
	private UserController userController;
	private ArrayList<Seat> seats = new ArrayList<>();

	/**
	 * Constructor of the class
	 * @param movieController MovieController object 
	 * @param userController UserController object
	 */
	public SeatController(MovieController movieController, UserController userController) {
		this.movieController = movieController;
		this.userController = userController;
	}

	public ArrayList<Seat> getSeats() {
		return seats;
	}

	public void setSeats(ArrayList<Seat> seats) {
		this.seats = seats;
	}

	public Seat getSelectedSeats() {
		return selectedSeat;
	}

	public void setSelectedSeat(Seat selectedSeats) {
		this.selectedSeat = selectedSeats;
	}

	
	/**
	 * method that calculated the availability of the seats for a particular showtime
	 * @param s showtime to find the available seats
	 * @return list of available seats
	 */
	public ArrayList<Seat> findAvailableSeats(Showtime s) {
		seats = s.getSeats();
		ArrayList<Seat> availableSeats = new ArrayList<>();

		for (Seat i : seats) {
			if (!(i.isOccupied())) {
				availableSeats.add(i);
			}
		}

		return availableSeats;
	}

	/**
	 * checking for the availability of a particular seat
	 * @param s seat object
	 * @return true if the seat if available else return false
	 */
	public boolean isSeatAvailable(Seat s) {
		if (!s.isOccupied()) {
			return true;
		}

		return false;
	}

	/**
	 * this function updates the seats arrayList with the data from the database. 
	 */
	public void updateArrayList() {
		seats.clear();

		try {
			Showtime showtime = movieController.getSelectedShowtime();
			Movie movie = movieController.getSelectedMovie();

			ResultSet res = DbController.getInstance().query("SELECT * FROM seat, showtime "
					+ "WHERE seat.showtime = showtime.showtime AND seat.showtime = ? AND movieName = ? AND theatre = ? AND showDate = ?;",
					showtime.getTime(), movie.getMovieName(), showtime.getTheatre().getTheatreName(),
					showtime.getDate());

			while (res.next()) {
				Seat seat = new Seat();
				seat.setSeatNumber(res.getInt("seatNumber"));
				seat.setOccupied(res.getBoolean("occupied"));
				seat.setPriority(res.getBoolean("priority"));
				seat.setTheShowtime(showtime);

				seats.add(seat);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This function updates the seats status as occupied after the seat is booked
	 */
	public void updateSeatStatus() {
		if (selectedSeat == null) {
			return;
		}
		
		DbController.getInstance().execute("UPDATE seat SET occupied = true WHERE seatNumber = ? AND showtime = ?;",
				selectedSeat.getSeatNumber(), selectedSeat.getTheShowtime().getTime());
	}
	
	/**
	 * this function checks whether the user is logged in(Registered user) or not
	 * @return true if the useer is logged in else false
	 */
	public boolean isLoggedin()
	{
		if(userController.isLoggedIn() == true)
			return true;
		else return false;
	}
}
