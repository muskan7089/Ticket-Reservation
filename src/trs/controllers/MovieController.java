package trs.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import trs.models.Movie;
import trs.models.Showtime;
import trs.models.Theatre;

/**
 * movie controller class
 *
 */
public class MovieController {
	/**
	 * member variables declaration and initialization
	 */
	private TheatreController theatreController;
	private Movie selectedMovie;
	private Showtime selectedShowtime;
	private List<Movie> movies;

	/**
	 * Constructor of the class
	 * @param theatreController the TheatreController object
	 */
	public MovieController(TheatreController theatreController) {
		this.theatreController = theatreController;
		movies = new ArrayList<Movie>();
	}

	public Movie getSelectedMovie() {
		return selectedMovie;
	}

	public void setSelectedMovie(Movie selectedMovie) {
		this.selectedMovie = selectedMovie;
	}

	public List<Movie> getMovies() {
		return movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}

	public Showtime getSelectedShowtime() {
		return selectedShowtime;
	}

	public void setSelectedShowtime(Showtime selectedShowtime) {
		this.selectedShowtime = selectedShowtime;
	}

	/**
	 * function to egt the showtimes for a particular movie
	 * @param m the Movie
	 * @param t the Theatre
	 * @return list of showtimes
	 */
	public List<Showtime> getShowtimes(Movie m, Theatre t) {

		for (Movie d : t.getMovieList()) {
			if (d == m) {
				return m.getShowtimes();
			}
		}

		return null;
	}

	/**
	 * method which selects the movie
	 * @param m The movie to be selected
	 */

	public void selectMovie(Movie m) {
		this.selectedMovie = m;
	}
	
	/**
	 * method which selects the showtime
	 * @param s The showtime to be selected
	 */

	public void selectShowtime(Showtime s) {
		for (Showtime r : selectedMovie.getShowtimes()) {
			if (r == s) {
				this.setSelectedShowtime(r);
			}
		}
	}
	/**
	 * this function updates the movies arrayList with the data from the database. 
	 */

	public void updateMovies() {
		movies.clear();

		try {
			ResultSet res = DbController.getInstance()
					.query("SELECT * FROM movie, theatre_movie WHERE theatre = ? "
							+ "AND movie.movieName = theatre_movie.movieName AND releaseDate <= CURRENT_TIMESTAMP;",
							theatreController.getSelectedTheatre().getTheatreName());

			while (res.next()) {
				Movie movie = new Movie();
				movie.setMovieName(res.getString("movieName"));

				Date release = res.getDate("releaseDate");
				movie.setReleaseDate(release);

				movies.add(movie);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Movie searchMovies(String name) {
		for (Movie m : movies) {
			if (m.getMovieName().toLowerCase().contains(name.toLowerCase())) {
				return m;
			}
		}

		return null;
	}

	/**
	 * this function updates the showtimes arrayList with the data from the database. 
	 */
	public void updateShowtimes() {
		if (selectedMovie == null) {
			return;
		}

		ArrayList<Showtime> showtimes = new ArrayList<Showtime>();

		try {
			ResultSet res = DbController.getInstance().query(
					"SELECT * FROM showtime WHERE theatre = ? AND movieName = ? AND showDate >= CURRENT_TIMESTAMP;",
					theatreController.getSelectedTheatre().getTheatreName(), selectedMovie.getMovieName());

			while (res.next()) {
				Showtime showtime = new Showtime();
				showtime.setTheatre(theatreController.getSelectedTheatre());
				showtime.setMovie(selectedMovie);
				showtime.setTime(res.getString("showtime"));
				showtime.setDate(res.getDate("showDate"));
				showtimes.add(showtime);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		selectedMovie.setShowtimes(showtimes);
	}
}
