import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;





public class Gameboard extends JPanel {

	private final int ANIMATION_SPEED = 10;
	private final int ANIMATION_INTERVALS = 10;
	private int animationInterval = 0;

	private boolean animating = false;

	GameProcessor gameProcessor;
	public Gameboard() {
		//Set the default size
		setPreferredSize(new Dimension(700,700));

		//instantiates the GameProcessor object
		gameProcessor = new GameProcessor(this);

	}

	public void toggleAnimating() {
		animating = !animating;
		animationInterval = 0;
		return;

	}

	public int getAnimationInterval() {
		return animationInterval;
	}

	public boolean isAnimating() {
		return animating;
	}
	
	public char[][] getMoveList() {
		return gameProcessor.getMoveList();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); 
		
		if(animationInterval == ANIMATION_INTERVALS) {
			toggleAnimating();
			gameProcessor.animatingDone();
		}
			
		paintGameElements(g);
		
		if (animating) {
			try {
				Thread.sleep(ANIMATION_SPEED);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		

	}

	public void paintGameElements(Graphics g) {
		
		animationInterval+=1;
		
		for (int i = 0; i < 12; i++)
			for (int j = 0; j < 12; j++) 
					gameProcessor.getMap()[i][j].paint(g);
		
	}

}





