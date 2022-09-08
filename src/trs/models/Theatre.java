package trs.models;

import java.util.ArrayList;

/**
 * Theatre entity, representing a movie theatre
 */
public class Theatre {
	private String theatreName;
	private String theatreLocation;
	private int theatreCapacity;
	private ArrayList<Movie> movieList;

	/**
	 * Theatre entity constructor
	 * 
	 * @param theatreName     name of the theatre
	 * @param theatreLocation location of the theatre
	 * @param theatreCapacity capacity of the theatre
	 */
	public Theatre(String theatreName, String theatreLocation, int theatreCapacity) {
		this.theatreName = theatreName;
		this.theatreLocation = theatreLocation;
		this.theatreCapacity = theatreCapacity;
	}

	/**
	 * the following are the getters and setters
	 * 
	 */
	public String getTheatreName() {
		return theatreName;
	}

	public void setTheatreName(String theatreName) {
		this.theatreName = theatreName;
	}

	public String getTheatreLocation() {
		return theatreLocation;
	}

	public void setTheatreLocation(String theatreLocation) {
		this.theatreLocation = theatreLocation;
	}

	public int getTheatreCapacity() {
		return theatreCapacity;
	}

	public void setTheatreCapacity(int theatreCapacity) {
		this.theatreCapacity = theatreCapacity;
	}

	public ArrayList<Movie> getMovieList() {
		return movieList;
	}

	public void setMovieList(ArrayList<Movie> movieList) {
		this.movieList = movieList;
	}

}
