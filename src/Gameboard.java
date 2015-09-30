import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;





public class Gameboard extends JPanel {
	
	GameProcessor gameProcessor;
	public Gameboard() {
		//Set the default size
		setPreferredSize(new Dimension(700,700));
		
		//instantiates the GameProcessor object
		gameProcessor = new GameProcessor();
		
	}

	@Override
	public void paintComponent(Graphics g) {
		
		//i = x values, j = y values
		for (int i = 0 ; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				paintGameElement(i, j, g);
			}
		}
	}
	
	public void paintGameElement(int x, int y, Graphics g) {
		switch (gameProcessor.getGraphicsMap()[x][y]) {
		
		case Legend.YOU:
			Player p = new Player(x, y, this);
			p.paint(g);
			break;
			
		case Legend.MHO:
			Mho m = new Mho(x, y, this);
			m.paint(g);
			break;
			
		case Legend.FENCE:
			Fence f = new Fence(x, y, this);
			f.paint(g);
			break;
			
		default:
			break;
			
		}
	}

	


}
