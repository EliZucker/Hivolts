import java.awt.Graphics;


public abstract class Unit {
	//X and Y locations on the board (0-11)
	private int x;
	private int y;

	//The new X and Y values for the jump location
	private int jumpX;
	private int jumpY;

	//Number of pixels in the padding
	private final int PADDING = 4;

	//Gameboard object (will later be assigned in the constructor)
	private Gameboard board;

	/**
	 * A very simple constructor
	 * @param x the x coordinate of the placement on the board (0-11)
	 * @param y the y coordinate of the placement on the board (0-11)
	 * @param board the GameBoard object that the Unit is being drawn on
	 */
	public Unit(int x, int y, Gameboard board) {
		this.x = x;
		this.y = y;
		this.board = board;
	}

	/**
	 * A setter that changes the jump location	
	 * @param x the new JumpX variable
	 * @param y the new JumpY variable
	 */
	public void setJumpLocation(int x, int y) {
		jumpX = x;
		jumpY = y;
	}

	/**
	 * 
	 * @return the GameBoard that the Unit is drawn on
	 */
	public Gameboard getBoard() {
		return board;
	}

	/**
	 * 
	 * @return the x coordinate of the Unit
	 */
	public int getX() {
		return x;
	}


	/**
	 * 
	 * @return the y coordinate of the Unit
	 */
	public int getY() {
		return y;
	}

	/**
	 * 
	 * @return the padding value
	 */
	public int getPadding() {
		return PADDING;
	}

	/**
	 * This is an abstract method meant to utilize the getPaintInfo() method, but still be unique to each game element
	 * @param g the Graphics object from the GameBoard
	 */
	public abstract void paint(Graphics g);

	/**
	 * A standard method that can be used for all Unit objects. 
	 * This method will return the x coordinate, y coordinate, width, and height
	 * for the object, whether it is being animated or is just staying still. 
	 * The Unit will then implement it's own method of paint(), but still use the data from getPaintInfo()
	 * 
	 * @return an array of ints with x, y, width, and height of the object
	 */
	protected int[] getPaintInfo() {
		//x, y, width, height
		int paintInfo[] = new int[4];

		//Height and width units are 1/12 of the board (keep as double for precision)
		double widthUnit = getBoard().getWidth()/12.0;
		double heightUnit = getBoard().getHeight()/12.0;

		//Change the X and Y values in order to scale the board with a correct aspect ratio
		if(getBoard().getWidth() > getBoard().getHeight()) {
			heightUnit = getBoard().getHeight()/12.0;
			widthUnit = getBoard().getHeight()/12.0;
		} else if(getBoard().getWidth() < getBoard().getHeight()) {
			heightUnit = getBoard().getWidth()/12.0;
			widthUnit = getBoard().getWidth()/12.0;
		}


		//If the board is not animating, simply return the standard x, y, and width
		paintInfo[0] = (int)(widthUnit*getX()+(getBoard().getWidth()-widthUnit*12.0)/2.0);
		paintInfo[1] = (int)(heightUnit*getY()+(getBoard().getHeight()-heightUnit*12.0)/2.0);
		paintInfo[2] = (int) (widthUnit);
		paintInfo[3] = (int) (heightUnit);

		//If the board IS animating, a special algorithm will be determined for the type of movement
		if (board.isAnimating()) {
			
			//Assign default x, y, width, and height values
			paintInfo[0] = (int)(widthUnit*getX()+(getBoard().getWidth()-widthUnit*12.0)/2.0);
			paintInfo[1] = (int)(heightUnit*getY()+(getBoard().getHeight()-heightUnit*12.0)/2.0);
			paintInfo[2] = (int) (widthUnit);
			paintInfo[3] = (int) (heightUnit);

			//Obtain the moveType from the getMoveList() method of the GameBoard
			char moveType = getBoard().getMoveList()[getX()][getY()];
			
			//Switch statement for each type of movement. There are slight nuances in the x, y, width, and height, corresponding to the type of movement
			switch (moveType) {
			case Legend.DOWN:
				paintInfo[1] += (int)(((heightUnit/board.ANIMATION_INTERVALS)*board.getAnimationFrame()));
				break;

			case Legend.RIGHT:
				paintInfo[0] += (int)(((widthUnit/board.ANIMATION_INTERVALS)*board.getAnimationFrame()));
				break;

			case Legend.UP:
				paintInfo[1] -= (int)(((heightUnit/board.ANIMATION_INTERVALS)*board.getAnimationFrame()));
				break;

			case Legend.LEFT:
				paintInfo[0] -= (int)(((widthUnit/board.ANIMATION_INTERVALS)*board.getAnimationFrame()));
				break;

			case Legend.DOWN_RIGHT:
				paintInfo[0] += (int)(((widthUnit/board.ANIMATION_INTERVALS)*board.getAnimationFrame()));
				paintInfo[1] += (int)(((heightUnit/board.ANIMATION_INTERVALS)*board.getAnimationFrame()));
				break;

			case Legend.DOWN_LEFT:
				paintInfo[0] -= (int)(((widthUnit/board.ANIMATION_INTERVALS)*board.getAnimationFrame()));
				paintInfo[1] += (int)(((heightUnit/board.ANIMATION_INTERVALS)*board.getAnimationFrame()));
				break;

			case Legend.UP_RIGHT:
				paintInfo[0] += (int)(((widthUnit/board.ANIMATION_INTERVALS)*board.getAnimationFrame()));
				paintInfo[1] -= (int)(((heightUnit/board.ANIMATION_INTERVALS)*board.getAnimationFrame()));
				break;

			case Legend.UP_LEFT:
				paintInfo[0] -= (int)(((widthUnit/board.ANIMATION_INTERVALS)*board.getAnimationFrame()));
				paintInfo[1] -= (int)(((heightUnit/board.ANIMATION_INTERVALS)*board.getAnimationFrame()));
				break;	

			case Legend.SHRINK:
				paintInfo[0] += (int)(((widthUnit/(board.ANIMATION_INTERVALS*2))*board.getAnimationFrame()));
				paintInfo[1] += (int)(((heightUnit/(board.ANIMATION_INTERVALS*2))*board.getAnimationFrame()));
				paintInfo[2] = (int) ((widthUnit/board.ANIMATION_INTERVALS)*(board.ANIMATION_INTERVALS-board.getAnimationFrame()));
				paintInfo[3] = (int) ((heightUnit/board.ANIMATION_INTERVALS)*(board.ANIMATION_INTERVALS-board.getAnimationFrame()));
				break;

			case Legend.GROW:
				paintInfo[0] += (int)(((widthUnit/(board.ANIMATION_INTERVALS*2))*(board.ANIMATION_INTERVALS-board.getAnimationFrame())));
				paintInfo[1] += (int)(((heightUnit/(board.ANIMATION_INTERVALS*2))*(board.ANIMATION_INTERVALS-board.getAnimationFrame())));
				paintInfo[2] = (int) ((widthUnit/board.ANIMATION_INTERVALS)*(board.getAnimationFrame()));
				paintInfo[3] = (int) ((heightUnit/board.ANIMATION_INTERVALS)*(board.getAnimationFrame()));
				break;

			case Legend.JUMP:
				double xDifference = jumpX-x;
				double yDifference = jumpY-y;

				paintInfo[0] += (int)((((xDifference*widthUnit)/board.ANIMATION_INTERVALS)*board.getAnimationFrame()));
				paintInfo[1] += (int)((((yDifference*heightUnit)/board.ANIMATION_INTERVALS)*board.getAnimationFrame()));
				break;

			case Legend.NO_MOVEMENT:
				break;

			default:
				break;
			}
		}
		return paintInfo;
	}

}
