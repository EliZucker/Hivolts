import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class Player extends Character{
	
	public Player(int x, int y, Gameboard board) {
		super(x, y, board);
		
	}

	public void paint(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillOval(getPaintInfo()[0], getPaintInfo()[1], getPaintInfo()[2], getPaintInfo()[3]);
		
	}
	

}
