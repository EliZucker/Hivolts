import java.awt.Color;
import java.awt.Graphics;


public class Mho extends Character{
	
	/**
	 * 
	 * @param x the x coordinate of the placement on the board (0-11)
	 * @param y the y coordinate of the placement on the board (0-11)
	 * @param board the GameBoard object that the Mho is being drawn on
	 */
	
	public Mho(int x, int y, Gameboard board) {
		super(x, y, board);
	}

	/**
	 * Override the abstract method paint, in order to implement a way to draw the object
	 */
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillOval(getPaintInfo()[0]+getPadding(), getPaintInfo()[1]+getPadding(), getPaintInfo()[2]-getPadding()*2, getPaintInfo()[3]-getPadding()*2);
		
		g.setColor(Color.BLACK);
		g.drawOval(getPaintInfo()[0]+getPadding(), getPaintInfo()[1]+getPadding(), getPaintInfo()[2]-getPadding()*2, getPaintInfo()[3]-getPadding()*2);
		
	}

}
