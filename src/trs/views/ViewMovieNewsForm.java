package trs.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.List;

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

import trs.controllers.NewsController;
import trs.controllers.MovieController;
import trs.controllers.PanelController;
import trs.models.Movie;

@SuppressWarnings("serial")
public class ViewMovieNewsForm extends Panel {
	private NewsController newsController;
	private JButton viewButton, backButton;
	private JPanel title, buttons;
	private JTable table;
	private DefaultTableModel tableModel;

	public ViewMovieNewsForm(PanelController panelController, NewsController newsController,
			MovieController movieController) {
		super(panelController);
		this.newsController = newsController;

		setLayout(new BorderLayout());
		setupTitle();
		setupTable();
		setupButtons();
	}

	/**
	 * This method sets the title of the Panel to "List of New Movies"
	 */
	private void setupTitle() {
		title = new JPanel();
		title.setBorder(new EmptyBorder(5, 5, 5, 5));
		title.add(new JLabel("List of New Movies"));
		add(title, BorderLayout.NORTH);
	}

	/**
	 * Sets up the table for displaying the names and the release dates of new
	 * movies.
	 */
	private void setupTable() {
		String[] columns = { "New Movies", "Release Dates" };

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
	 * Sets up the buttons and dialog box needed for the user to select a new Movie.
	 */
	private void setupButtons() {
		buttons = new JPanel();

		viewButton = new JButton("Display");
		viewButton.addActionListener((ActionEvent e) -> {
			int row = table.getSelectedRow();
			List<Movie> newMovies = newsController.getNewsMovies();

			if (row < 0 || row >= newMovies.size()) {
				JOptionPane.showMessageDialog(getRootPane(), "Please select a valid Movie", "Movie",
						JOptionPane.OK_OPTION);
				return;
			}

			Movie movie = newMovies.get(row);
			JOptionPane.showMessageDialog(getRootPane(), movie.getMovieName() + "\n" + movie.getNews().getNewsMessage(),
					"Movie news", JOptionPane.INFORMATION_MESSAGE);
		});
		buttons.add(viewButton);

		backButton = new JButton("Back");
		backButton.addActionListener((ActionEvent e) -> {
			changeView("main");
		});
		buttons.add(backButton);

		add(buttons, BorderLayout.SOUTH);
	}

	/**
	 * When the view is changed, the frame size is set to 350 x 200
	 */
	@Override
	public void onBeforeViewChanged(JFrame frame) {
		updateMovieNews();
	}

	/**
	 * Load new Movies into table
	 */
	private void updateMovieNews() {
		tableModel.setRowCount(0);
		newsController.updateMovies();

		for (Movie movie : newsController.getNewsMovies()) {
			addNewMovie(movie);
		}
	}

	/**
	 * Adds data to the seat table
	 * 
	 * @param s the Seat
	 */
	private void addNewMovie(Movie m) {

		tableModel.addRow(new Object[] { m.getMovieName(), m.getReleaseDate() });

	}

}
