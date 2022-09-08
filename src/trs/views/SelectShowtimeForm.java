package trs.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

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
import trs.models.Showtime;

@SuppressWarnings("serial")
public class SelectShowtimeForm extends Panel {
	private MovieController movieController;
	private JButton selectShowButton, backButton;
	private JPanel title, buttons;
	private JTable table;
	private DefaultTableModel tableModel;

	/**
	 * the constructor of the class
	 * @param panelController The PanelController Object
	 * @param movieController The MovieController object
	 */
	public SelectShowtimeForm(PanelController panelController, MovieController movieController) {
		super(panelController);
		this.movieController = movieController;

		setLayout(new BorderLayout());
		setupTitle();
		// setupInputs();
		setupTable();
		setupButtons();

	}

	/**
	 * When the view is changed, the frame size is set to 350 x 200
	 */
	@Override
	public void onBeforeViewChanged(JFrame frame) {
		updateShowtimes();
	}

	/**
	 * Load showtime into table
	 */
	
	private void updateShowtimes() {
		tableModel.setRowCount(0);
		movieController.updateShowtimes();
		addMovie(movieController.getSelectedMovie());
	}


	/**
	 * Sets up title text for panel
	 */
	
	private void setupTitle() {
		title = new JPanel();
		title.setBorder(new EmptyBorder(5, 5, 5, 5));
		title.add(new JLabel("Select showtime for movie"));
		add(title, BorderLayout.NORTH);
	}

	/**
	 * Setup the showtime table
	 */
	private void setupTable() {
		String[] columns = { "Showtime", "Show date" };

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

		selectShowButton = new JButton("Select Showtime");
		selectShowButton.addActionListener((ActionEvent e) -> {
			int row = table.getSelectedRow();
			ArrayList<Showtime> showtimes = movieController.getSelectedMovie().getShowtimes();
			
			if (row < 0 || row >= showtimes.size()) {
				JOptionPane.showMessageDialog(getRootPane(), "Please select a valid Showtime", "Showtime",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			movieController.selectShowtime(showtimes.get(row));
			changeView("seat");

		});

		buttons.add(selectShowButton);

		// ToDo: maybe do some remaining stuff

		backButton = new JButton("Back");
		backButton.addActionListener((ActionEvent e) -> {
			changeView("main");
		});
		buttons.add(backButton);

		add(buttons, BorderLayout.SOUTH);

	}

	/**
	 * Adds data to the showtime table
	 * 
	 * @param m the Movie
	 */
	private void addMovie(Movie m) {

		for (Showtime s : m.getShowtimes()) {
			tableModel.addRow(new Object[] { s.getTime(), s.getDate() });
		}

	}
}
