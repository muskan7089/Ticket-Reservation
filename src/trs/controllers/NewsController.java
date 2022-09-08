package trs.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import trs.models.Movie;
import trs.models.News;

public class NewsController {
	private List<Movie> newMovies;

	public NewsController() {
		newMovies = new ArrayList<Movie>();
	}

	/**
	 * This method sends an SQL query to the database such that it retrieves a table
	 * with movies that have their release dates 10 days in the future of the
	 * current timestamp. It then updates the List of newMovies to those movies
	 * retrieved from the table. It also checks for SQL Exceptions.
	 */
	public void updateMovies() {
		newMovies.clear();

		try {
			ResultSet res = DbController.getInstance()
					.query("SELECT * FROM movie, news WHERE movie.movieName = news.movieName "
							+ "AND releaseDate >= (SELECT DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 10 DAY));");

			while (res.next()) {
				Movie movie = new Movie();
				movie.setMovieName(res.getString("movieName"));

				Date release = res.getDate("releaseDate");
				movie.setReleaseDate(release);

				movie.setNews(new News(res.getString("message")));

				newMovies.add(movie);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return the list of movies which have release dates 10 days in the future of
	 *         the current time stamp.
	 */
	public List<Movie> getNewsMovies() {
		return newMovies;
	}
}