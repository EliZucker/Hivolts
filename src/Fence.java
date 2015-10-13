import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.print.DocFlavor.URL;
import javax.swing.ImageIcon;


public class Fence extends Unit {
	
	/**
	 * 
	 * @param x the x coordinate of the placement on the board (0-11)
	 * @param y the y coordinate of the placement on the board (0-11)
	 * @param board the GameBoard object that the fence is being drawn on
	 */
	public Fence(int x, int y, Gameboard board) {
		super(x, y, board);

	}
	
	/**
	 * Override the abstract method paint, in order to implement a way to draw the object
	 */
	@Override
	public void paint(Graphics g) {
		
		int x = getPaintInfo()[0];
		int y = getPaintInfo()[1];
		int width = getPaintInfo()[2];
		int height = getPaintInfo()[3];
		
		g.setColor(Color.BLACK);
		g.drawRect((int) (x+width/6.0 +getPadding()), y + getPadding(), (int) (width/6.0), height-(getPadding()*2));
		g.drawRect(x+width-((int)(width/6.0 +getPadding()))-(int)(width/6.0), y + getPadding(), (int) (width/6.0), height-(getPadding()*2));
		
		g.setColor(new Color(92, 61, 16));
		g.fillRect((int) (x+width/6.0 +getPadding()), y + getPadding(), (int) (width/6.0), height-(getPadding()*2));
		g.fillRect(x+width-((int)(width/6.0 +getPadding()))-(int)(width/6.0), y + getPadding(), (int) (width/6.0), height-(getPadding()*2));
		
		g.setColor(new Color(128, 85, 22));
		g.fillRect(x + getPadding(),(int) (y + getPadding()+height/6.0), width-(getPadding()*2), (int) (height/6.0));
		g.fillRect(x + getPadding(),y+height-((int) (getPadding()+height/6.0))-(int) (height/6.0), width-(getPadding()*2), (int) (height/6.0));
		
		g.setColor(Color.BLACK);
		g.drawRect(x + getPadding(),(int) (y + getPadding()+height/6.0), width-(getPadding()*2), (int) (height/6.0));
		g.drawRect(x + getPadding(),y+height-((int) (getPadding()+height/6.0))-(int) (height/6.0), width-(getPadding()*2), (int) (height/6.0));
		
	}

}
