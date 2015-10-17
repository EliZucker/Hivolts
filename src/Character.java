import java.awt.Graphics;




public abstract class Character extends Unit{

	/** 
	 * A basic constructor
	 * @param x the x coordinate of the placement on the board (0-11)
	 * @param y the y coordinate of the placement on the board (0-11)
	 * @param board the GameBoard object that the Character is being drawn on
	 */
	public Character(int x, int y, Gameboard board) {
		super(x, y, board);
	}
}
