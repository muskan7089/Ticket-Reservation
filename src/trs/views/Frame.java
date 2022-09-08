package trs.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Holds the various views
 *
 */
public class Frame extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new Frame
	 * 
	 * @param s Title
	 */
	public Frame(String s) {
		super(s);
		setTitle("Ticket Registration System");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		center();
	}

	/**
	 * Sets the panel and
	 * 
	 * @param p the JPanel
	 */
	public void setPanel(JPanel p) {
		setContentPane(p);
		pack();
	}

	/**
	 * Centers the GUI
	 */
	public void center() {
		Dimension dimensions = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dimensions.width - getSize().width) / 2, (dimensions.height - getSize().height) / 2);
	}
}
