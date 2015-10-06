import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;





public class Gameboard extends JPanel {

	//The number of milliseconds per change in frame - lower means faster
	public final int ANIMATION_SPEED = 100;

	//Number of frames in each animation - higher means a smoother animation, but will take longer
	public final int ANIMATION_INTERVALS = 10;

	//The current frame that the animation is on
	private int animationFrame = -1;

	//Whether or not the board is currently in an animation phase
	private boolean animating = false;

	//The GameProcessor object. This object will do all of the move processing behind the scenes
	GameProcessor gameProcessor;

	public Gameboard() {
		//Set the default size
		setPreferredSize(new Dimension(700,700));

		//instantiates the GameProcessor object
		gameProcessor = new GameProcessor(this);


	}

	/**
	 * Called whenever the state of animating has changed
	 */
	public void toggleAnimating() {

		//Signify to the GameProcessor that animating has finished
		if(animating)
			gameProcessor.animatingDone();

		animating = !animating;

		//Set the animation frame back to -1
		animationFrame = -1;

		return;

	}

	/**
	 * 
	 * @return the animation frame that is currently animating
	 */
	public int getAnimationFrame() {
		return animationFrame;
	}

	/**
	 * 
	 * @return true if currently animating, false if otherwise
	 */
	public boolean isAnimating() {
		return animating;
	}

	/**
	 * 
	 * @return the list of moves (animations) from the gameProcessor
	 */
	public char[][] getMoveList() {
		return gameProcessor.getMoveList();
	}

	/**
	 * Override the paintComponent method in order to draw each object in the window
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		//Checks if animation is finished, then calls toggleAnimating() if true
		if(animationFrame == ANIMATION_INTERVALS) 
			toggleAnimating();

		//Paint the background and game elements
		paintBackground(g);
		paintGameElements(g);


	}

	/**
	 * paints the grid in the background
	 * @param g the Graphics object to paint with
	 */
	public void paintBackground(Graphics g) {

		g.setColor(Color.BLACK);

		//Loop in order to draw each line
		for (int i = 1; i<12; i++) {
			g.drawLine((int)(i*(getWidth()/12.0)), 0, (int)(i*(getWidth()/12.0)), getHeight());
			g.drawLine(0, (int)(i*(getHeight()/12.0)), getWidth(), (int)(i*(getHeight()/12.0)));
		}

	}

	public void paintGameElements(Graphics g) {

		//Adds one more frame to animationFrame if animating
		if(isAnimating()) {
			animationFrame+=1;
		}

		//Calls the paint method for each game element
		for (int i = 0; i < 12; i++)
			for (int j = 0; j < 12; j++) 
				gameProcessor.getMap()[i][j].paint(g);
	}
}





