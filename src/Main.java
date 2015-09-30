import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		//create a JFrame window to house our FlagPanel object
		JFrame win = new JFrame();
		win.setDefaultCloseOperation(win.EXIT_ON_CLOSE);
		win.setLayout(new BorderLayout());
		win.setMinimumSize(new Dimension(50,75));
	
		//add the GameBoard
		Gameboard board = new Gameboard();
		win.add(board, BorderLayout.CENTER);

		//pack everything inside the frame and make it visible
		win.pack();
		win.setVisible(true);
	}
}