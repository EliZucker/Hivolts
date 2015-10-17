import java.awt.Color;
import java.awt.Graphics;


public class Mho extends Character{

	/**
	 * A basic constructor
	 * @param x the x coordinate of the placement on the board (0-11)
	 * @param y the y coordinate of the placement on the board (0-11)
	 * @param board the GameBoard object that the Mho is being drawn on
	 */
	public Mho(int x, int y, Gameboard board) {
		super(x, y, board);
	}

	/**
	 * @param g the Graphics object in which the shapes will be drawn on
	 * Override the abstract method paint, in order to draw the shapes needed
	 */
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillOval(getPaintInfo()[0]+getPadding(), getPaintInfo()[1]+getPadding(), getPaintInfo()[2]-getPadding()*2, getPaintInfo()[3]-getPadding()*2);

		g.setColor(Color.BLACK);
		g.drawOval(getPaintInfo()[0]+getPadding(), getPaintInfo()[1]+getPadding(), getPaintInfo()[2]-getPadding()*2, getPaintInfo()[3]-getPadding()*2);

	}

}
