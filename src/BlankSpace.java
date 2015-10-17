import java.awt.Graphics;

import javax.swing.JPanel;


public class BlankSpace extends Unit{

	/** 
	 * A basic constructor
	 * @param x the x coordinate of the placement on the board (0-11)
	 * @param y the y coordinate of the placement on the board (0-11)
	 * @param board the GameBoard object that the BlankSpace is being drawn on
	 */
	public BlankSpace(int x, int y, Gameboard p) {
		super(x, y, p);
	}

	/**
	 * @param g the Graphics object in which the shapes will be drawn on
	 * Override the abstract method paint, in order to draw the shapes needed
	 */
	@Override
	public void paint(Graphics g) {
		//Does literally nothing because this is a BlankSpace object
	}

}
