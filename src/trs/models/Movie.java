package trs.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Movie entity, representing a movie in the database
 */
public class Movie {

	private String movieName;
	private Date releaseDate;
	private ArrayList<Showtime> showtimes;
	private News news;

	/**
	 * Movie entity constructor
	 * 
	 * @param movieName   the name of the movie
	 * @param releaseDate the release Date of the move
	 * @param showDate    the day that the customer selects to watch the movie
	 * @param news        the movie news
	 */
	public Movie(String movieName, Date releaseDate, Date showDate, News news) {
		this.movieName = movieName;
		this.news = news;
		this.releaseDate = releaseDate;
		showtimes = new ArrayList<Showtime>();
	}

	/**
	 * Movie entity constructor
	 */
	public Movie() {
		this.movieName = null;
		this.news = null;
		this.releaseDate = null;
	}

	/**
	 * the following are the getters and setters
	 * 
	 */
	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public ArrayList<Showtime> getShowtimes() {
		return showtimes;
	}

	public void setShowtimes(ArrayList<Showtime> showtimes) {
		this.showtimes = showtimes;
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getMovieName() {
		return movieName;
	}

	/**
	 * function that changes the showtime to string
	 * 
	 * @return the showtime as string
	 */
	public String ShowTimetoString() {
		String s = new String();
		for (Showtime st : showtimes) {
			s = s + " " + st.getTime();
		}
		return s;
	}

}
