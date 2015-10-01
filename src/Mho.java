import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class Mho extends Character{

	public Mho(int x, int y, Gameboard board) {
		super(x, y, board);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillOval(getPaintInfo()[0], getPaintInfo()[1], getPaintInfo()[2], getPaintInfo()[3]);
		
	}

}
