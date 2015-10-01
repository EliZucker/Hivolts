import java.awt.Graphics;

import javax.swing.JPanel;


public class BlankSpace extends Unit{

	public BlankSpace(int x, int y, Gameboard p) {
		super(x, y, p);
	}

	@Override
	public void paint(Graphics g) {
		//Does literally nothing because this is a BlankSpace object
		
	}

}
