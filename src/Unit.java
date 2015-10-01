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
	
	protected int[] getPaintInfo() {
		//x, y, width, height
		int paintInfo[] = new int[4];
		
		double widthUnit = getBoard().getWidth()/12.0;
		double heightUnit = getBoard().getHeight()/12.0;
		
		if (!board.isAnimating()) {
			paintInfo[0] = (int)(widthUnit*getX());
			paintInfo[1] = (int)(heightUnit*getY());
			paintInfo[2] = (int) (widthUnit);
			paintInfo[3] = (int) (heightUnit);
		}
		
		else {
			
			char moveType = getBoard().getMoveList()[getX()][getY()];
			int animationInterval = getBoard().getAnimationInterval();
			
			switch (moveType) {
			
			case Legend.DOWN:
				
				paintInfo[0] = (int)(widthUnit*getX());
				paintInfo[1] = (int)(heightUnit*getY()+((heightUnit/board.ANIMATION_INTERVALS)*board.getAnimationInterval()));
				paintInfo[2] = (int) (widthUnit);
				paintInfo[3] = (int) (heightUnit);
				
				break;
				
			case Legend.RIGHT:
				paintInfo[0] = (int)(widthUnit*getX()+((widthUnit/board.ANIMATION_INTERVALS)*board.getAnimationInterval()));
				paintInfo[1] = (int)(heightUnit*getX());
				paintInfo[2] = (int) (widthUnit);
				paintInfo[3] = (int) (heightUnit);
				break;
				
			case Legend.UP:
				paintInfo[0] = (int)(widthUnit*getX());
				paintInfo[1] = (int)(heightUnit*getY()-((heightUnit/board.ANIMATION_INTERVALS)*board.getAnimationInterval()));
				paintInfo[2] = (int) (widthUnit);
				paintInfo[3] = (int) (heightUnit);
				break;
				
			case Legend.LEFT:
				paintInfo[0] = (int)(widthUnit*getX()-((widthUnit/board.ANIMATION_INTERVALS)*board.getAnimationInterval()));
				paintInfo[1] = (int)(heightUnit*getX());
				paintInfo[2] = (int) (widthUnit);
				paintInfo[3] = (int) (heightUnit);
				break;
				
			case Legend.DOWN_RIGHT:
				paintInfo[0] = (int)(widthUnit*getX()+((widthUnit/board.ANIMATION_INTERVALS)*board.getAnimationInterval()));
				paintInfo[1] = (int)(heightUnit*getY()+((heightUnit/board.ANIMATION_INTERVALS)*board.getAnimationInterval()));
				paintInfo[2] = (int) (widthUnit);
				paintInfo[3] = (int) (heightUnit);
				break;
				
			case Legend.DOWN_LEFT:
				paintInfo[0] = (int)(widthUnit*getX()-((widthUnit/board.ANIMATION_INTERVALS)*board.getAnimationInterval()));
				paintInfo[1] = (int)(heightUnit*getY()+((heightUnit/board.ANIMATION_INTERVALS)*board.getAnimationInterval()));
				paintInfo[2] = (int) (widthUnit);
				paintInfo[3] = (int) (heightUnit);
				break;
				
			case Legend.UP_RIGHT:
				paintInfo[0] = (int)(widthUnit*getX()+((widthUnit/board.ANIMATION_INTERVALS)*board.getAnimationInterval()));
				paintInfo[1] = (int)(heightUnit*getY()-((heightUnit/board.ANIMATION_INTERVALS)*board.getAnimationInterval()));
				paintInfo[2] = (int) (widthUnit);
				paintInfo[3] = (int) (heightUnit);
				break;
				
			case Legend.UP_LEFT:
				paintInfo[0] = (int)(widthUnit*getX()-((widthUnit/board.ANIMATION_INTERVALS)*board.getAnimationInterval()));
				paintInfo[1] = (int)(heightUnit*getY()-((heightUnit/board.ANIMATION_INTERVALS)*board.getAnimationInterval()));
				paintInfo[2] = (int) (widthUnit);
				paintInfo[3] = (int) (heightUnit);
				break;	
			
			case Legend.SHRINK:
				paintInfo[0] = (int)(widthUnit*getX()+((widthUnit/(board.ANIMATION_INTERVALS*2))*board.getAnimationInterval()));
				paintInfo[1] = (int)(heightUnit*getY()+((heightUnit/(board.ANIMATION_INTERVALS*2))*board.getAnimationInterval()));
				paintInfo[2] = (int) (widthUnit/(board.ANIMATION_INTERVALS*2));
				paintInfo[3] = (int) (heightUnit/(board.ANIMATION_INTERVALS*2));
				
			default:
				break;
				
			}
		}
		
		return paintInfo;
		
	}
	
	
	
	public abstract void paint(Graphics g);
}
