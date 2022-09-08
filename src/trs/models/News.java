package trs.models;

/**
 * News entity, representing news about a movie
 */
public class News {
	private Movie movie;
	private String newsMessage;

	public News(String newsMessage) {
		this.newsMessage = newsMessage;
	}

	public String getNewsMessage() {
		return newsMessage;
	}

	public void setNewsMessage(String newsMessage) {
		this.newsMessage = newsMessage;
	}

	public String toString() {
		return newsMessage;
	}

	public void setMovie(Movie m) {
		movie = m;
	}

	public Movie getMovie() {
		return movie;
	}
}
