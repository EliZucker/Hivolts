import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		//create a JFrame window to house our FlagPanel object
		JFrame win = new JFrame();
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setLayout(new BorderLayout());
		win.setMinimumSize(new Dimension(100,150));
	
		//add the GameBoard
		Gameboard board = new Gameboard();
		win.add(board, BorderLayout.CENTER);

		//pack everything inside the frame and make it visible
		win.pack();
		win.setVisible(true);
		
		//Keep redrawing the GameBoard infinitely, but only do at at an interval of ANIMATION_SPEED
		while(true)
		{
			//Adds one more frame to animationFrame if animating
			if(board.isAnimating()) {
				board.increaseAnimationFrame();
			}
			board.repaint();
			
			try {
				Thread.sleep(board.ANIMATION_SPEED);
			} catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}