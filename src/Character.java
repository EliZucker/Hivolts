import java.awt.Graphics;

import javax.swing.JPanel;




public abstract class Character extends Unit{

	public Character(int x, int y, JPanel p) {
		super(x, y, p);
		// TODO Auto-generated constructor stub
	}
	
	public abstract void paint(Graphics g);



}
