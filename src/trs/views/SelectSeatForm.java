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
import trs.controllers.PurchaseController;
import trs.controllers.SeatController;
import trs.models.Seat;

@SuppressWarnings("serial")
public class SelectSeatForm extends Panel {
	private SeatController seatController;
	private PurchaseController purchaseController;
	private JButton selectButton, backButton;
	private JPanel title, buttons;
	private JTable table;
	private DefaultTableModel tableModel;
	private Seat selectedSeat;
	private int row;

	/**
	 * Create the SelectSeatForm and assign controllers
	 * 
	 * @param panelController The PanelController
	 * @param seatController  The SeatController
	 */
	public SelectSeatForm(PanelController panelController, SeatController seatController, PurchaseController purchaseController) {
		super(panelController);
		this.seatController = seatController;
		this.purchaseController = purchaseController;
        
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
		updateSeats();
	}

	/**
	 * Load seats into table
	 */
	private void updateSeats() {
		tableModel.setRowCount(0);
		seatController.updateArrayList();

		for (Seat s : seatController.getSeats()) {
			addSeat(s);
		}
	}

	/**
	 * Sets up title text for panel
	 */
	private void setupTitle() {
		title = new JPanel();
		title.setBorder(new EmptyBorder(5, 5, 5, 5));
		title.add(new JLabel("Please select a seat"));
		add(title, BorderLayout.NORTH);
	}

	/**
	 * Setup the seat table
	 */
	private void setupTable() {
		String[] columns = { "Seat Number", "Available", "Priority" };

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

			row = table.getSelectedRow();

			if (row < 0 || row >= seatController.getSeats().size()) {
				JOptionPane.showMessageDialog(getRootPane(), "Please select a valid seat", "Seat",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			selectedSeat = seatController.getSeats().get(row);
		    seatController.setSelectedSeat(seatController.getSeats().get(row));
			 
			if(selectedSeat.isOccupied()==true)
			{
				JOptionPane.showMessageDialog(getRootPane(), "Please select a seat with availability as true", "Seat",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if(selectedSeat.isPriority()==true && seatController.isLoggedin()!=true)
			{
				JOptionPane.showMessageDialog(getRootPane(), "Please login to select a priority seat", "Seat",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			purchaseController.resetPrice();
			changeView("buyTickets");

		});
		buttons.add(selectButton);

		backButton = new JButton("Back");
		backButton.addActionListener((ActionEvent e) -> {
			changeView("showtime");
		});
		buttons.add(backButton);

		add(buttons, BorderLayout.SOUTH);
	}

	/**
	 * Adds data to the seat table
	 * 
	 * @param s the Seat
	 */
	private void addSeat(Seat s) {
		tableModel.addRow(new Object[] { s.getSeatNumber(), !(s.isOccupied()), s.isPriority() });
	}

}
