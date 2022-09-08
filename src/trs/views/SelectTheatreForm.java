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

import trs.controllers.PanelController;
import trs.controllers.TheatreController;
import trs.models.Theatre;

@SuppressWarnings("serial")
public class SelectTheatreForm extends Panel {
	private TheatreController theatreController;	
	private JButton selectButton, backButton;
	private JPanel title, buttons;
	private JTable table;
	private DefaultTableModel tableModel;

	/**
	 * Create the SelectTheatreForm and assign controllers
	 * 
	 * @param panelController The PanelController
	 * @param theatreController The ThreatreController
	 */
	public SelectTheatreForm(PanelController panelController, TheatreController theatreController) {
		super(panelController);
		this.theatreController = theatreController;

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
		theatreController.updateTheatres();
		updateTheaters();
	}
	
	/**
	 * Load theaters into table
	 */
	private void updateTheaters() {
		tableModel.setRowCount(0);
		
		for (Theatre t : theatreController.getTheatres()) {
			addTheatre(t);
		}
	}
	
	/**
	 * Sets up title text for panel
	 */
	private void setupTitle() {
		title = new JPanel();
		title.setBorder(new EmptyBorder(5, 5, 5, 5));
		title.add(new JLabel("Please select a theatre"));
		add(title, BorderLayout.NORTH);
	}
	
	/**
	 * Setup the theatre table
	 */
	private void setupTable() {
		String[] columns = { "Name", "Location" };

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
		
		selectButton = new JButton("Select");
		selectButton.addActionListener((ActionEvent e) -> {
			int row = table.getSelectedRow();
			
			if (row < 0 || row >= theatreController.getTheatres().size()) {
				JOptionPane.showMessageDialog(getRootPane(), "Please select a valid theatre", "Theare", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			theatreController.selectTheatre(theatreController.getTheatres().get(row));
			changeView("movie");
		});
		buttons.add(selectButton);
		
		backButton = new JButton("Back");
		backButton.addActionListener((ActionEvent e) -> {
			changeView("main");
		});
		buttons.add(backButton);
		
		add(buttons, BorderLayout.SOUTH);
	}
	
	/**
	 * Adds data to the theater table
	 * 
	 * @param t the theater
	 */
	private void addTheatre(Theatre t) {
		tableModel.addRow(new Object[] { t.getTheatreName(), t.getTheatreLocation() });
	}
}
