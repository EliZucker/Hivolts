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
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public class Main {
	private final static String TITLE = "Hivolts Recreation - Eli Zucker";
	public static boolean messageDisplaying = false;
	private static Gameboard board = new Gameboard();
	
	public static void main(String[] args) {
		//create a JFrame window to house our FlagPanel object
		JFrame win = new JFrame();
		win.setTitle(TITLE);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setLayout(new BorderLayout());
		win.setMinimumSize(new Dimension(100,150));

		//add the GameBoard
		win.add(board, BorderLayout.CENTER);
		
		board.requestFocus();
		board.requestFocusInWindow();
		board.setFocusable(true);
		
		addKeyListener(board);

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
			
			if(!messageDisplaying)
				board.repaint();

			try {
				Thread.sleep(board.ANIMATION_SPEED);
			} catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}

	public static void showEndMessage(int status, int reason) {
		messageDisplaying = true;
		String gameMessage ;
		if(status == 1) {
			gameMessage = "Congratulations! You Won!";
		} else {

			if (reason == -1) {
				gameMessage = "Game Over! You were eaten by a Mho";
			} else {
				gameMessage = "Game Over! You landed on a Fence";
			}
		} 

		Object[] buttons = {"Restart",
		"Quit"};

		int value = JOptionPane.showOptionDialog(
				null, 
				gameMessage,
				TITLE, 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE, 
				null, 
				buttons, 
				buttons[0]);

		if (value == 0) {
			messageDisplaying = false;
			board.getGameProcessor().restart();
			
		} else {
			System.exit(0);
		}
	}

	private static void addKeyListener(Gameboard board) {
		board.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {

				if(board.isAnimating() || board.getGameProcessor().gameOver)
					return;

				switch (e.getKeyCode()) {
				case KeyEvent.VK_DOWN:
					board.getGameProcessor().playerMove(Legend.DOWN);
					break;
				case KeyEvent.VK_UP:
					board.getGameProcessor().playerMove(Legend.UP);
					break;
				case KeyEvent.VK_LEFT:
					board.getGameProcessor().playerMove(Legend.LEFT);
					break;
				case KeyEvent.VK_RIGHT:
					board.getGameProcessor().playerMove(Legend.RIGHT);
					break;
				case KeyEvent.VK_Q:
					board.getGameProcessor().playerMove(Legend.UP_LEFT);
					break;
				case KeyEvent.VK_W:
					board.getGameProcessor().playerMove(Legend.UP);
					break;
				case KeyEvent.VK_E:
					board.getGameProcessor().playerMove(Legend.UP_RIGHT);
					break;
				case KeyEvent.VK_A:
					board.getGameProcessor().playerMove(Legend.LEFT);
					break;
				case KeyEvent.VK_S:
					board.getGameProcessor().playerMove(Legend.NO_MOVEMENT);
					break;
				case KeyEvent.VK_D:
					board.getGameProcessor().playerMove(Legend.RIGHT);
					break;
				case KeyEvent.VK_Z:
					board.getGameProcessor().playerMove(Legend.DOWN_LEFT);
					break;
				case KeyEvent.VK_X:
					board.getGameProcessor().playerMove(Legend.DOWN);
					break;
				case KeyEvent.VK_C:
					board.getGameProcessor().playerMove(Legend.DOWN_RIGHT);
					break;
				case KeyEvent.VK_J:
					board.getGameProcessor().playerMove(Legend.JUMP);
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

		});
	}
}