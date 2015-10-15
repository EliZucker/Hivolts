import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javafx.scene.input.InputEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

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
		board.requestDefaultFocus();
		board.requestFocus();
		board.requestFocusInWindow();
		board.setFocusable(true);
		
		board.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent e) {
				if(board.isAnimating())
					return;
				
				switch (e.getKeyChar()) {
				case 'q':
					board.getGameProcessor().playerMove(Legend.UP_LEFT);
					break;
				case 'w':
					board.getGameProcessor().playerMove(Legend.UP);
					break;
				case 'e':
					board.getGameProcessor().playerMove(Legend.UP_LEFT);
					break;
				case 'a':
					board.getGameProcessor().playerMove(Legend.LEFT);
					break;
				case 's':
					board.getGameProcessor().playerMove(Legend.NO_MOVEMENT);
					break;
				case 'd':
					board.getGameProcessor().playerMove(Legend.RIGHT);
					break;
				case 'z':
					board.getGameProcessor().playerMove(Legend.DOWN_LEFT);
					break;
				case 'x':
					board.getGameProcessor().playerMove(Legend.DOWN);
					break;
				case 'c':
					board.getGameProcessor().playerMove(Legend.DOWN_RIGHT);
					break;
				case 'j':
					//still needs to be implemented
					break;
				}
			}
					
			});

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