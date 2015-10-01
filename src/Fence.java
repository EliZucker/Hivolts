import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class Fence extends Unit {



	public Fence(int x, int y, Gameboard board) {
		super(x, y, board);
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(getPaintInfo()[0], getPaintInfo()[1], getPaintInfo()[2], getPaintInfo()[3]);
		
	}

}
