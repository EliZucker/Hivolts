import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class Mho extends Character{

	public Mho(int x, int y, JPanel p) {
		super(x, y, p);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(convertGridPointX(getX()), convertGridPointY(getY()), (int) (getP().getWidth()/12.0), (int) (getP().getHeight()/12.0));
		
	}

}
