import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;





public class Gameboard extends JPanel {

	public final int ANIMATION_SPEED = 10;
	public final int ANIMATION_INTERVALS = 10;
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

	public void animate() {

		while(true)
		{
			
			if(animationInterval == ANIMATION_INTERVALS) {
				toggleAnimating();
				gameProcessor.animatingDone();
				break;
				
			}
			
			repaint();

			try{
				Thread.sleep(ANIMATION_SPEED);
			} catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); 

		paintBackground(g);

		paintGameElements(g);

	}

	public void paintBackground(Graphics g) {
		g.setColor(Color.BLACK);
		for (int i = 1; i<12; i++) {
			g.drawLine((int)(i*(getWidth()/12.0)), 0, (int)(i*(getWidth()/12.0)), getHeight());
			g.drawLine(0, (int)(i*(getHeight()/12.0)), getWidth(), (int)(i*(getHeight()/12.0)));
		}




	}

	public void paintGameElements(Graphics g) {

		animationInterval+=1;

		for (int i = 0; i < 12; i++)
			for (int j = 0; j < 12; j++) 
				gameProcessor.getMap()[i][j].paint(g);

	}

}





