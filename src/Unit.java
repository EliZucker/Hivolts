import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;


public abstract class Unit{
	private int x;
	private int y;
	private JPanel p;
	
	public Unit(int x, int y, JPanel p) {
		this.x = x;
		this.y = y;
		this.p = p;
	}
	
	public int getX() {
		return x;
	}
	
	public JPanel getP() {
		return p;
	}
	
	public int getY() {
		return y;
	}
	
	protected int convertGridPointX(int x) {
		return (int)((getP().getWidth()/12.0)*x);
	}

	protected int convertGridPointY(int y) {
		return (int)((getP().getHeight()/12.0)*y);
	}

	
	public abstract void paint(Graphics g);
}
