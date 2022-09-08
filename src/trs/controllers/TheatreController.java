package trs.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import trs.models.Theatre;

public class TheatreController {
	private Theatre selectedTheatre;
	private List<Theatre> theatres;

	/**
	 * Initialize theatre controller
	 */
	public TheatreController() {
		theatres = new ArrayList<Theatre>();
	}
	
	/**
	 * Gets currently selected theatre
	 * 
	 * @return Selected theatre
	 */
	public Theatre getSelectedTheatre() {
		return selectedTheatre;
	}
	
	/**
	 * Sets selected theatre
	 * 
	 * @param selectedTheatre New theatre to select
	 */
	public void setSelectedTheatre(Theatre selectedTheatre) {
		this.selectedTheatre = selectedTheatre;
	}
	
	/**
	 * Gets all theatres
	 * 
	 * @return All theatres
	 */
	public List<Theatre> getTheatres() {
		return theatres;
	}
	
	/**
	 * Sets list of all theatres
	 * 
	 * @param theatres New list of all theatres
	 */
	public void setTheatres(List<Theatre> theatres) {
		this.theatres = theatres;
	}
	
	/**
	 * Searches for a theatre by name
	 * 
	 * @param theatreName Name to search for
	 * @return Theatre with given name, or null if none found
	 */
	public Theatre searchTheatre(String theatreName) {
		for (Theatre t : theatres) {
			if (t.getTheatreName().equals(theatreName)) {
				return t;
			}
		}

		return null;
	}
	
	/**
	 * Selects a new theatre
	 * 
	 * @param t Theatre to select
	 */
	public void selectTheatre(Theatre t) {
		this.selectedTheatre = t;
	}
	
	/**
	 * Updates theatres with results from database
	 */
	public void updateTheatres() {
		theatres.clear();
		ResultSet res = DbController.getInstance().query("SELECT * FROM theatre;");
		
		try {
			while (res.next()) {
				String name = res.getString("name");
				String location = res.getString("location");
				int capacity = res.getInt("capacity");
				
				theatres.add(new Theatre(name, location, capacity));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
