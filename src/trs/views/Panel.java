package trs.views;

import javax.swing.JFrame;
import javax.swing.JPanel;

import trs.controllers.PanelController;

/**
 * Contains functions that all main panels will use.
 *
 */
public abstract class Panel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected PanelController panelController;

	/**
	 * Creates a new Panel
	 * 
	 * @param panMan Panel controller
	 */
	public Panel(PanelController panelController) {
		this.panelController = panelController;
	}

	/**
	 * changes the view of the panel
	 * 
	 * @param s the desired panel
	 */
	public void changeView(String s) {
		panelController.switchTo(s);
	}
	
	/**
	 * Declaration to override in the subclasses
	 * 
	 * @param frame Frame to change
	 */
	public void onBeforeViewChanged(JFrame frame) {
		// To be overridden if needed
	}

	/**
	 * Declaration to override in the subclasses
	 * 
	 * @param frame Frame to change
	 */
	public void onViewChanged(JFrame frame) {
		// To be overridden if needed
	}
}
