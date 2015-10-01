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
		
	}

}
