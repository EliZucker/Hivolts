import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;





public class Gameboard extends JPanel {

	//The number of milliseconds per change in frame - lower means faster
	public final int ANIMATION_SPEED = 8;

	//Number of frames in each animation - higher means a smoother animation, but will take longer
	public final int ANIMATION_INTERVALS = 60;

	//The current frame that the animation is on
	private int animationFrame = -1;

	//Whether or not the board is currently in an animation phase
	private boolean animating = false;

	//The GameProcessor object. This object will do all of the move processing behind the scenes
	private GameProcessor gameProcessor;

	/**
	 * Gameboard constructor
	 */
	public Gameboard() {
		//Set the default size
		setPreferredSize(new Dimension(700,700));

		//instantiates the GameProcessor object
		gameProcessor = new GameProcessor(this);


	}

	/**
	 * 
	 * @return the GameProcessor being used by the board
	 */
	public GameProcessor getGameProcessor() {
		return gameProcessor;
	}

	/**
	 * Called when the frame of animation has changed
	 */
	public void increaseAnimationFrame() {
		animationFrame+=1;
	}


	/**
	 * Called whenever the state of animating has changed
	 */
	public void toggleAnimating() {

		//Signify to the GameProcessor that animating has finished
		if(animating)
			gameProcessor.animatingDone();

		//Change animating to its opposite value
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

		//Stop the painting process if a message is being displayed
		if(Main.messageDisplaying)
			return;
		//A necessary line of code when you override paintComponent
		super.paintComponent(g);

		//Checks if animation is finished, then calls toggleAnimating() if true
		if(animationFrame >= ANIMATION_INTERVALS) 
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

		//set widthUnit and heightUnit to width/12 and height/12 respectively (because there are 12 tiles)
		double widthUnit = getWidth()/12.0;
		double heightUnit = getHeight()/12.0;

		//Change the X and Y values in order to scale the board with a correct aspect ratio
		if(getWidth() > getHeight()) {
			heightUnit = getHeight()/12.0;
			widthUnit = getHeight()/12.0;
		} else if(getWidth() < getHeight()) {
			heightUnit = getWidth()/12.0;
			widthUnit = getWidth()/12.0;
		}

		//Change the color that is being used by Graphics
		g.setColor(Color.BLACK);

		//Loop in order to draw each line
		for (int i = 0; i<13; i++) {
			g.drawLine((int)(i*(widthUnit)+(getWidth()-widthUnit*12.0)/2.0), (int)((getHeight()-heightUnit*12.0)/2.0), (int)(i*(widthUnit)+(getWidth()-widthUnit*12.0)/2.0), (int)(heightUnit*12+(getHeight()-heightUnit*12.0)/2.0));
			g.drawLine((int)((getWidth()-widthUnit*12.0)/2.0), (int)(i*(heightUnit)+(getHeight()-heightUnit*12.0)/2.0), (int)(widthUnit*12+(getWidth()-widthUnit*12.0)/2.0), (int)(i*(heightUnit)+(getHeight()-heightUnit*12.0)/2.0));
		}
	}

	public void paintGameElements(Graphics g) {

		//paint all of the fences first (lowest layer)
		for (int i = 0; i < 12; i++)
			for (int j = 0; j < 12; j++) 
				if (gameProcessor.getMap()[i][j] instanceof Fence)
					gameProcessor.getMap()[i][j].paint(g);

		//paint the Player object next (so that it looks like the player is being eaten)
		for (int i = 0; i < 12; i++)
			for (int j = 0; j < 12; j++) 
				if (gameProcessor.getMap()[i][j] instanceof Player)
					gameProcessor.getMap()[i][j].paint(g);

		//paint the Mhos last (highest layer)
		for (int i = 0; i < 12; i++)
			for (int j = 0; j < 12; j++) 
				if (gameProcessor.getMap()[i][j] instanceof Mho)
					gameProcessor.getMap()[i][j].paint(g);
	}
}





