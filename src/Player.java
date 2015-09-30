import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class Player extends Character{
	
	public Player(int x, int y, JPanel p) {
		super(x, y, p);
		
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(convertGridPointX(getX()), convertGridPointY(getY()), (int) (getP().getWidth()/12.0), (int) (getP().getHeight()/12.0));
		
	}
	

}
