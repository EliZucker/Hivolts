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


public class Spike extends Unit {
	
	/**
	 * 
	 * @param x the x coordinate of the placement on the board (0-11)
	 * @param y the y coordinate of the placement on the board (0-11)
	 * @param board the GameBoard object that the fence is being drawn on
	 */
	public Spike(int x, int y, Gameboard board) {
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
		
		g.setColor(Color.RED);
		
		g.fillPolygon(new int[]{(x+getPadding()), (int) (x+width/2.0+width/8.0),(int) (x+width/2.0-width/8.0)}, new int[]{(y+getPadding()),(int)(y+height/2.0-height/8.0), (int)(y+height/2.0+height/8.0)}, 3 );
		g.fillPolygon(new int[]{(x+width-getPadding()), (int) (x+width/2.0-width/8.0),(int) (x+width/2.0+width/8.0)}, new int[]{(y+getPadding()),(int)(y+height/2.0-height/8.0), (int)(y+height/2.0+height/8.0)}, 3 );
		g.fillPolygon(new int[]{(x+getPadding()), (int) (x+width/2.0-width/8.0),(int) (x+width/2.0+width/8.0)}, new int[]{(y+height-getPadding()),(int)(y+height/2.0-height/8.0), (int)(y+height/2.0+height/8.0)}, 3 );
		g.fillPolygon(new int[]{(x+width-getPadding()), (int) (x+width/2.0+width/8.0),(int) (x+width/2.0-width/8.0)}, new int[]{(y+height-getPadding()),(int)(y+height/2.0-height/8.0), (int)(y+height/2.0+height/8.0)}, 3 );
		
		g.setColor(Color.BLACK);
		g.drawPolygon(new int[]{(x+getPadding()), (int) (x+width/2.0+width/8.0),(int) (x+width/2.0-width/8.0)}, new int[]{(y+getPadding()),(int)(y+height/2.0-height/8.0), (int)(y+height/2.0+height/8.0)}, 3 );
		g.drawPolygon(new int[]{(x+width-getPadding()), (int) (x+width/2.0-width/8.0),(int) (x+width/2.0+width/8.0)}, new int[]{(y+getPadding()),(int)(y+height/2.0-height/8.0), (int)(y+height/2.0+height/8.0)}, 3 );
		g.drawPolygon(new int[]{(x+getPadding()), (int) (x+width/2.0-width/8.0),(int) (x+width/2.0+width/8.0)}, new int[]{(y+height-getPadding()),(int)(y+height/2.0-height/8.0), (int)(y+height/2.0+height/8.0)}, 3 );
		g.drawPolygon(new int[]{(x+width-getPadding()), (int) (x+width/2.0+width/8.0),(int) (x+width/2.0-width/8.0)}, new int[]{(y+height-getPadding()),(int)(y+height/2.0-height/8.0), (int)(y+height/2.0+height/8.0)}, 3 );
		//g.fillOval(x+width/4, y+height/4, width/2, height/2);
	}

}
