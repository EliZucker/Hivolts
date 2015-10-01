import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;


public abstract class Unit{
	private int x;
	private int y;
	private Gameboard board;
	
	public Unit(int x, int y, Gameboard board) {
		this.x = x;
		this.y = y;
		this.board = board;
	}
	
	public int getX() {
		return x;
	}
	
	public Gameboard getBoard() {
		return board;
	}
	
	public int getY() {
		return y;
	}
	
	protected int convertGridPointX(int x) {
		return (int)((getBoard().getWidth()/12.0)*x);
	}

	protected int convertGridPointY(int y) {
		return (int)((getBoard().getHeight()/12.0)*y);
	}

	public int[] getPaintInfo() {
		//x, y, width, height
		int paintInfo[] = new int[4];
		
		if (!board.isAnimating()) {
			paintInfo[0] = convertGridPointX(getX());
			paintInfo[1] = convertGridPointY(getY());
			paintInfo[2] = (int) (getBoard().getWidth()/12.0);
			paintInfo[3] = (int) (getBoard().getHeight()/12.0);
		}
		
		else {
			char moveType = getBoard().getMoveList()[getX()][getY()];
			int animationInterval = getBoard().getAnimationInterval();
			//NEED MATHS HERE
		}
		
		return paintInfo;
		
	}
	
	
	
	public abstract void paint(Graphics g);
}
