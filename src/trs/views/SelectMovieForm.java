package trs.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import trs.controllers.MovieController;
import trs.controllers.PanelController;
import trs.models.Movie;

@SuppressWarnings("serial")
public class SelectMovieForm extends Panel {
	private MovieController movieController;
	private JButton searchButton, selectMovieButton, backButton;
	private JPanel title, buttons;
	private JTable table;
	private DefaultTableModel tableModel;

	/**
	 * Create the SelectMovieForm and assign controllers
	 * 
	 * @param panelController The PanelController
	 * @param movieController The MovieController
	 */
	public SelectMovieForm(PanelController panelController, MovieController movieController) {
		super(panelController);
		this.movieController = movieController;

		setLayout(new BorderLayout());
		setupTitle();
		setupTable();
		setupButtons();

	}

	/**
	 * When the view is changed, the frame size is set to 350 x 200
	 */
	@Override
	public void onBeforeViewChanged(JFrame frame) {
		updateMovies();
	}

	/**
	 * Load movies into table
	 */
	private void updateMovies() {
		tableModel.setRowCount(0);
		movieController.updateMovies();

		for (Movie m : movieController.getMovies()) {
			addMovie(m);
		}
	}

	/**
	 * Sets up title text for panel
	 */
	private void setupTitle() {
		title = new JPanel();
		title.setBorder(new EmptyBorder(5, 5, 5, 5));
		title.add(new JLabel("Selecting the Movie"));
		add(title, BorderLayout.NORTH);
	}

	/**
	 * Setup the seat table
	 */
	private void setupTable() {
		String[] columns = { "Name", "Release Date" };

		tableModel = new DefaultTableModel(null, columns) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable(tableModel);
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(350, 175));
		add(scrollPane, BorderLayout.EAST);
	}

	/**
	 * Sets up parts of the panel
	 */
	private void setupButtons() {
		buttons = new JPanel();

		selectMovieButton = new JButton("Select Movie");
		selectMovieButton.addActionListener((ActionEvent e) -> {
			int row = table.getSelectedRow();

			if (row < 0 || row >= movieController.getMovies().size()) {
				JOptionPane.showMessageDialog(getRootPane(), "Please select a valid movie", "Movie",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			movieController.selectMovie(movieController.getMovies().get(row));

			changeView("showtime");

		});
		buttons.add(selectMovieButton);

		searchButton = new JButton("Search");
		searchButton.addActionListener((ActionEvent e) -> {
			String search = JOptionPane.showInputDialog(getRootPane(), "Please enter movie to search for");
			Movie movie = movieController.searchMovies(search);

			if (movie != null) {
				JOptionPane.showMessageDialog(getRootPane(), "Found movie: " + movie.getMovieName(), "Movie",
					JOptionPane.INFORMATION_MESSAGE);

				movieController.selectMovie(movie);
				changeView("showtime");
			} else {
				JOptionPane.showMessageDialog(getRootPane(), "Sorry, that movie does not exist", "Movie",
					JOptionPane.ERROR_MESSAGE);
			}
		});
		buttons.add(searchButton);

		backButton = new JButton("Back");
		backButton.addActionListener((ActionEvent e) -> {
			changeView("selectTheatre");
		});
		buttons.add(backButton);

		add(buttons, BorderLayout.SOUTH);
	}

	/**
	 * Adds data to the movie table
	 * 
	 * @param m the Movie
	 */
	private void addMovie(Movie m) {
		tableModel.addRow(new Object[] { m.getMovieName(), m.getReleaseDate().toString() });
	}
}
