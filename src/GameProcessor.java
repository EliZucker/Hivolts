import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;



public class GameProcessor {

	//A map of the locations of each object
	private Unit[][] map = new Unit[12][12];

	//The next map of the locations each object will be
	private Unit[][] newMap = new Unit[12][12];

	//A list of corresponding moves each Object in the map will make
	private char[][] moveList = new char[12][12];

	//The Gameboard utilizing the current instance of GameProcessor
	Gameboard board;

	//The X and Y coordinate of the location of the player
	private int[] playerLocation = new int[2];

	//The X and Y coordinate of the NEW location of the player
	private int[] newPlayerLocation = new int[2];

	//A list of locations for each mho on the map (x and y of each)
	private ArrayList<Integer> mhoLocations = new ArrayList<Integer>();

	//set to false unless the player dies or there are no mhos on the board
	public boolean gameOver = false;

	//set to false unless there are no mhos on the board
	public boolean win = false;

	/**
	 * 
	 * @param board the Gameboard that will be passed to board
	 */
	public GameProcessor(Gameboard board) {
		this.board=board;

		//generate the intial locations of the Mhos, Fence, and Player
		generateMap();	
	}

	/**
	 * Generate random locations of the Player, Fences, and Mhos
	 */
	private void generateMap() {

		//Used to ensure unique locations of each Gameboard element
		boolean[][] spaceUsed = new boolean[12][12];

		//A running total of fences generated
		int fencesGenerated = 0;

		//A running total of Mhos generated
		int mhosGenerated = 0;

		//Fill the board with spaces and spikes on the edge
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				map[i][j] = new BlankSpace(i, j, board);
				if (i == 0 || i == 11 || j ==0 || j == 11) {
					map[i][j] = new Fence(i, j, board);
					spaceUsed[i][j] = true;
				}
			}
		}

		//Generate 20 spikes in unique locations
		while (fencesGenerated < 20) {
			int x = 1+(int)(Math.random()*10);
			int y = 1+(int)(Math.random()*10);

			if(spaceUsed[x][y]==false) {
				map[x][y] = new Fence(x, y, board);
				spaceUsed[x][y] = true;
				fencesGenerated++;
			}
		}

		//Generate 12 mhos in unique locations
		while (mhosGenerated < 12) {
			int x = 1+(int)(Math.random()*10);
			int y = 1+(int)(Math.random()*10);

			if(spaceUsed[x][y]==false) {
				map[x][y] = new Mho(x, y, board);

				mhoLocations.add(x);
				mhoLocations.add(y);

				spaceUsed[x][y] = true;

				mhosGenerated++;
			}
		}

		//Generate a player in a unique location
		while (true) {
			int x = 1+(int)(Math.random()*10);
			int y = 1+(int)(Math.random()*10);

			if(spaceUsed[x][y]==false) {
				map[x][y] = new Player(x, y, board);
				playerLocation[0] = x;
				playerLocation[1] = y;
				newPlayerLocation[0] = x;
				newPlayerLocation[1] = y;
				spaceUsed[x][y] = true;
				return;
			}
		}
	}

	/**
	 * Called to reset all game elements
	 */
	public void restart() {

		//Reset the list of Mho locations
		mhoLocations.clear();

		//Generate a unique map of game elements again
		generateMap();

		//reset gameOver and win variables
		gameOver = false;
		win = false;

		//repaint the board
		board.repaint();
	}

	/**
	 * 
	 * @return map, the locations of every game element
	 */
	public Unit[][] getMap() {
		return map;
	}

	/**
	 * 
	 * @return moveList, the list of moves that will be made by each game element
	 */
	public char[][] getMoveList() {
		return moveList;
	}

	/**
	 * copy map to newMap, without creating a shallow copy
	 */
	private void copyMap() {
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				if (map[i][j] instanceof Player) {
					newMap[i][j] = new Player(i, j, board);
				} else if (map[i][j] instanceof BlankSpace) {
					newMap[i][j] = new BlankSpace(i, j, board);
				} else if (map[i][j] instanceof Mho) {
					newMap[i][j] = new Mho(i, j, board);
				} else if (map[i][j] instanceof Fence) {
					newMap[i][j] = new Fence(i, j, board);
				}	
			}
		}
	}

	/**
	 * copy newMap to map, without creating a shallow copy
	 */
	private void updateMap() {
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				if (newMap[i][j] instanceof Player) {
					map[i][j] = new Player(i, j, board);
				} else if (newMap[i][j] instanceof BlankSpace) {
					map[i][j] = new BlankSpace(i, j, board);
				} else if (newMap[i][j] instanceof Mho) {
					map[i][j] = new Mho(i, j, board);
				} else if (newMap[i][j] instanceof Fence) {
					map[i][j] = new Fence(i, j, board);
				}	
			}
		}
	}

	/**
	 * called by the Gameboard when animating is done
	 */
	public void animatingDone() {

		//assign newMap to map
		updateMap();

		//assign newPlayerLocation to playerLocation
		setPlayerLocation(getNewPlayerLocation());

		//Check if the game has reached game over requirements
		if(gameOver) {
			if(win) {

				//show the winning message
				Main.showEndMessage(1, 0);
			} else {

				//show losing message with Mho in the context
				if (newMap[getNewPlayerLocation()[0]][getPlayerLocation()[1]] instanceof Mho) {
					Main.showEndMessage(-1, -1);
				} 

				//show losing message with Fence in the context
				else {
					Main.showEndMessage(-1, 1);
				}
			}
		}
	}

	/**
	 * clear mhoLocations and add all the current mhos that haven't died
	 */
	private void updateMhoLocationList() {

		//clear all the mhos in mhoLocations
		mhoLocations.clear();

		//add the locations of all the mhos in mhoLocations
		for (int i = 1; i < 11; i++)
			for (int j = 1; j < 11; j++) 
				if(newMap[i][j] instanceof Mho) {
					mhoLocations.add(i);
					mhoLocations.add(j);
				}
	}

	/**
	 * 
	 * @return array with x and y values of player location
	 */
	public int[] getPlayerLocation() {
		return playerLocation;
	}

	/**
	 * 
	 * @return newPlayerLocation, the location that results after animating
	 */
	public int[] getNewPlayerLocation() {
		return newPlayerLocation;
	}

	/**
	 * 
	 * @param playerLocation the values to be assigned to playerLocation
	 */
	public void setPlayerLocation(int[] playerLocation) {
		this.playerLocation = playerLocation;
	}

	/**
	 * 
	 * @param playerLocation the values to be assigned to newPlayerLocation
	 */
	public void setNewPlayerLocation(int[] playerLocation) {
		this.newPlayerLocation = playerLocation;
	}

	/**
	 * Simply change gameOver to true
	 */
	private void gameOver() {
		gameOver=true;
	}

	/**
	 * validate a certain move being applied to player.
	 * If the move is valid, change the necessary values.
	 * Else, end the game in the necessary fashion.
	 * @param moveType the char of the move assigned to the player
	 */
	private void validPlayerMove(char moveType) {

		//Obtain X and Y of playerLocation
		int x = getPlayerLocation()[0];
		int y = getPlayerLocation()[1];

		//By default, set xOffset and yOffset to 0
		int xOffset = 0;
		int yOffset = 0;

		//Change xOffset and yOffset corresponding to each moveType
		//For example, RIGHT would set xOffset to +1
		switch(moveType) {
		case Legend.UP: 
			yOffset = -1;
			break;
		case Legend.UP_LEFT:
			yOffset = -1;
			xOffset = -1;
			break;
		case Legend.UP_RIGHT:
			yOffset = -1;
			xOffset = 1;
			break;
		case Legend.DOWN:
			yOffset = 1;
			break;
		case Legend.DOWN_RIGHT:
			yOffset = 1;
			xOffset = 1;
			break;
		case Legend.DOWN_LEFT:
			yOffset = 1;
			xOffset = -1;
			break;
		case Legend.LEFT:
			xOffset = -1;
			break;
		case Legend.RIGHT:
			xOffset = 1;
			break;
		case Legend.JUMP:

			//Generate a random location of the jump
			while (true) {

				//random x and y values (between 0-10)
				int randomX = 1+(int)(Math.random()*10);
				int randomY = 1+(int)(Math.random()*10);

				//If the new location is a BlankSpace, assign that as a Player, and assign a new move in moveList
				if(map[randomX][randomY] instanceof BlankSpace) {
					newMap[randomX][randomY] = new Player(x, y, board);
					newMap[x][y] = new BlankSpace(x, y, board);
					moveList[x][y] = moveType;
					map[x][y].setJumpLocation(randomX, randomY);
					setNewPlayerLocation(new int[] {randomX, randomY});
					break;
				} 

				//If the new location is a Mho, completely wipe the board of the player, and initiate a gameOver procedure
				else if(map[randomX][randomY] instanceof Mho) {
					newMap[randomX][randomY] = new Mho(x, y, board);
					newMap[x][y] = new BlankSpace(x, y, board);
					moveList[x][y] = moveType;
					map[x][y].setJumpLocation(randomX, randomY);
					setNewPlayerLocation(new int[] {randomX, randomY});
					gameOver();
					break;
				}
			}
			return;
		}

		//If the new location that the Player will move to will be a Player (in the case of Legend.SHRINK), or BlankSpace, change map and newMap, as well as moveList and the playerLocation 
		if (map[x+xOffset][y+yOffset] instanceof BlankSpace || map[x+xOffset][y+yOffset] instanceof Player) {
			newMap[x][y] = new BlankSpace(x, y, board);
			newMap[x+xOffset][y+yOffset] = new Player(x+xOffset, y+yOffset, board);
			moveList[x][y] = moveType;
			setNewPlayerLocation(new int[] {x+xOffset, y+yOffset});
			return;
		} 

		//If the new location that the Player will move to will be a Fence, remove the player from the map and initiate the gameOver() process
		else {
			newMap[x][y] = new BlankSpace(x, y, board);
			moveList[x][y] = Legend.SHRINK;
			setNewPlayerLocation(new int[] {x, y});
			gameOver();
			return;
		}

	}

	/**
	 * called when the player should be moved with the move char
	 * @param move the corresponding move the player should make
	 */
	public void playerMove(char move) {

		//Set every game element to have no movement
		for (int i = 0; i < 12; i++)
			for (int j = 0; j < 12; j++)
				moveList[i][j] = Legend.NO_MOVEMENT;

		//Update map
		copyMap();

		//Validate the move
		validPlayerMove(move);

		//Only move the mhos if the move wasn't a jump (in which case the player gets to move again)
		if(move != Legend.JUMP)
			moveMhos();

		//If all mhos are removed, the player must have won
		if(mhoLocations.size() == 0) {
			gameOver();
			win = true;
		}

		//Signal that the board can be animated
		board.toggleAnimating();
	}

	/**
	 * Call all of the methods necessary to move the mhos
	 */
	private void moveMhos() {
		moveAllignedMhos();
		moveDiagonalMhos();
		moveHorizontalMhos();
		moveVerticalMhos();
		moveRemainingMhos();
		updateMhoLocationList();
	}

	/**
	 * Move all of the mhos that are perfectly alligned with the player
	 */
	private void moveAllignedMhos() {

		//Assign playerX and playerY to the X and Y of the player
		int playerX = getNewPlayerLocation()[0];
		int playerY = getNewPlayerLocation()[1];

		//Iterate through every mho's X and Y values
		for(int i = 0; i < mhoLocations.size()/2; i++) {

			//Assign mhoX and mhoY to the X and Y values of the mho that is currently being tested
			int mhoX = mhoLocations.get(i*2);
			int mhoY = mhoLocations.get(i*2+1);

			//set the default X and Y offsets to 0, and the move to NO_MOVEMENT
			int xOffset = 0;
			int yOffset = 0;
			char move = Legend.NO_MOVEMENT;

			//Check if the playerX is equal to mhoX (aligned on x-axis)
			if(playerX == mhoX) {

				//Check which direction the mho would have to move, and assign the corresponding move and yOffset
				if(playerY > mhoY) {
					yOffset = 1;
					move = Legend.DOWN;
				} else {
					yOffset = -1;
					move = Legend.UP;
				}
			} else if(playerY == mhoY) {

				//Check which direction the mho would have to move, and assign the corresponding move and XOffset
				if(playerX > mhoX) {
					xOffset = 1;
					move = Legend.RIGHT;
				} else {
					xOffset = -1;
					move = Legend.LEFT;
				}
			}

			//Only move if the new location is not an instance of mho (in order to make sure they are moved in the right order)
			if(!(newMap[mhoX+xOffset][mhoY+yOffset] instanceof Mho)) {

				//Set the previous location to a BlankSpace
				newMap[mhoX][mhoY] = new BlankSpace(mhoX, mhoY, board);

				//If the new square would be a player, end the game
				if(newMap[mhoX+xOffset][mhoY+yOffset] instanceof Player) {
					moveList[mhoX+xOffset][mhoY+yOffset] = Legend.SHRINK;
					gameOver();
				}

				//If the new square would be a fence, remove the mho from the map
				if(!(newMap[mhoX+xOffset][mhoY+yOffset] instanceof Fence)) {
					newMap[mhoX+xOffset][mhoY+yOffset] = new Mho(mhoX+xOffset, mhoY+yOffset, board);
				} else {
					move = Legend.SHRINK;
				}

				//Set the move of the mho on moveList
				moveList[mhoX][mhoY] = move;

				//remove the mhoX and mhoY in mhoLocations
				mhoLocations.remove(i*2+1);
				mhoLocations.remove(i*2);

				//Call moveAllignedMhos() again, because the list failed to be checked through completely
				moveAllignedMhos();
				break;
			}
		}
		return;
	}

	/**
	 * Move all of the mhos that move diagonally with the mho
	 */
	private void moveDiagonalMhos() {

		//Assign playerX and playerY to the X and Y of the player
		int playerX = getNewPlayerLocation()[0];
		int playerY = getNewPlayerLocation()[1];

		//Iterate through every mho's X and Y values
		for(int i = 0; i < mhoLocations.size()/2; i++) {

			//Assign mhoX and mhoY to the X and Y values of the mho that is currently being tested
			int mhoX = mhoLocations.get(i*2);
			int mhoY = mhoLocations.get(i*2+1);

			//set the default X and Y offsets to 0, and the move to NO_MOVEMENT
			int xOffset = 0;
			int yOffset = 0;
			char move;

			//Check if the playerX is greater to mhoX (aligned on x-axis) (to find direction of diagonal move)
			if(playerX > mhoX) {

				//check if playerY is greater to mhoY (to find direction of diagonal move)
				if(playerY > mhoY) {
					xOffset = 1;
					yOffset = 1;
					move = Legend.DOWN_RIGHT;
				} else {
					xOffset = 1;
					yOffset = -1;
					move = Legend.UP_RIGHT;
				}
			} else {

				//check if playerY is greater to mhoY (to find direction of diagonal move)
				if(playerY > mhoY) {
					xOffset = -1;
					yOffset = 1;
					move = Legend.DOWN_LEFT;
				} else {
					xOffset = -1;
					yOffset = -1;
					move = Legend.UP_LEFT;
				}
			}

			//Only move the mho if its location is an instance of BlankSpace or Player
			if(newMap[mhoX+xOffset][mhoY+yOffset] instanceof BlankSpace || newMap[mhoX+xOffset][mhoY+yOffset] instanceof Player) {

				//Assign the previous location as a BlankSpace
				newMap[mhoX][mhoY] = new BlankSpace(mhoX, mhoY, board);

				//If the new location is a player, shrink the player and call gameOver()
				if(newMap[mhoX+xOffset][mhoY+yOffset] instanceof Player) {
					moveList[mhoX+xOffset][mhoY+yOffset] = Legend.SHRINK;
					gameOver();
				}

				//Assign the new location as a mho
				newMap[mhoX+xOffset][mhoY+yOffset] = new Mho(mhoX+xOffset, mhoY+yOffset, board);

				//Assign move to the mho's old location
				moveList[mhoX][mhoY] = move;

				//remove each X and Y from mhoLocations
				mhoLocations.remove(i*2+1);
				mhoLocations.remove(i*2);

				//Call moveDiagonalMhos again, because the list failed to be checked through completely
				moveDiagonalMhos();
				break;
			}
		}
		return;
	}

	/**
	 * Move mhos horizontally if possible
	 */
	private void moveHorizontalMhos() {

		//Assign playerX and playerY to the X and Y of the player
		int playerX = getNewPlayerLocation()[0];
		int playerY = getNewPlayerLocation()[1];

		//Iterate through every mho's X and Y values
		for(int i = 0; i < mhoLocations.size()/2; i++) {

			//Assign mhoX and mhoY to the X and Y values of the mho that is currently being tested
			int mhoX = mhoLocations.get(i*2);
			int mhoY = mhoLocations.get(i*2+1);

			//set the default X and Y offsets to 0, and the move to NO_MOVEMENT
			int xOffset = 0;
			int yOffset = 0;
			char move = Legend.NO_MOVEMENT;

			//If the horizontal distance is greater than or equal to the vertical distance, change the xOffset and yOffset
			if((Math.abs(playerX-mhoX))>=(Math.abs(playerY-mhoY))) {
				if(playerX > mhoX) {
					xOffset = 1;
					move = Legend.RIGHT;
				} else {
					xOffset = -1;
					move = Legend.LEFT;
				}
			}

			//If the new location is an instance of BlankSpace, move the mho
			if(newMap[mhoX+xOffset][mhoY+yOffset] instanceof BlankSpace) {

				//If the mho were to move onto a Player, call gameOver() and shrink the Player
				if(newMap[mhoX+xOffset][mhoY+yOffset] instanceof Player) {
					moveList[mhoX+xOffset][mhoY+yOffset] = Legend.SHRINK;
					gameOver();
				}

				//Assign the new map location as a Mho
				newMap[mhoX+xOffset][mhoY+yOffset] = new Mho(mhoX+xOffset, mhoY+yOffset, board);

				//Set the mho's move in the moveList
				moveList[mhoX][mhoY] = move;

				//Assign the mho's original location as a BlankSpace in the new map
				newMap[mhoX][mhoY] = new BlankSpace(mhoX, mhoY, board);

				//remove each X and Y from mhoLocations
				mhoLocations.remove(i*2+1);
				mhoLocations.remove(i*2);
				
				//Call moveHorizontalMhos again, because the list failed to be checked through completely
				moveHorizontalMhos();
				break;
			} 
		}
		return;
	}

	/**
	 * Move mhos vertically if possible
	 */
	private void moveVerticalMhos() {

		//Assign playerX and playerY to the X and Y of the player
		int playerX = getNewPlayerLocation()[0];
		int playerY = getNewPlayerLocation()[1];

		//Iterate through every mho's X and Y values
		for(int i = 0; i < mhoLocations.size()/2; i++) {

			//Assign mhoX and mhoY to the X and Y values of the mho that is currently being tested
			int mhoX = mhoLocations.get(i*2);
			int mhoY = mhoLocations.get(i*2+1);

			//set the default X and Y offsets to 0, and the move to NO_MOVEMENT
			int xOffset = 0;
			int yOffset = 0;
			char move = Legend.NO_MOVEMENT;

			//If the horizontal distance is less than or equal to the vertical distance, change the xOffset and yOffset
			if((Math.abs(playerX-mhoX))<=(Math.abs(playerY-mhoY))) {
				if(playerY > mhoY) {
					yOffset = 1;
					move = Legend.DOWN;
				} else {
					yOffset = -1;
					move = Legend.UP;
				}
			}
			
			//If the new location is an instance of BlankSpace, move the mho
			if(newMap[mhoX+xOffset][mhoY+yOffset] instanceof BlankSpace) {
				newMap[mhoX][mhoY] = new BlankSpace(mhoX, mhoY, board);
				
				//If the new location is a player, shrink the player and call gameOver()
				if(newMap[mhoX+xOffset][mhoY+yOffset] instanceof Player) {
					moveList[mhoX+xOffset][mhoY+yOffset] = Legend.SHRINK;
					gameOver();
				}
				
				//Set the mho's move in the moveList
				moveList[mhoX][mhoY] = move;
				
				//Assign the new map location as a Mho
				newMap[mhoX+xOffset][mhoY+yOffset] = new Mho(mhoX+xOffset, mhoY+yOffset, board);
				
				//remove each X and Y from mhoLocations
				mhoLocations.remove(i*2+1);
				mhoLocations.remove(i*2);
				
				//Call moveVerticalMhos again, because the list failed to be checked through completely
				moveVerticalMhos();
				break;
			} 
		}
		return;
	}
	
	/**
	 * Move all of the remaining mhos
	 */
	private void moveRemainingMhos() {
		
		//Iterate through every mho's X and Y values
		for(int i = 0; i < mhoLocations.size()/2; i++) {
			
			//Assign mhoX and mhoY to the X and Y values of the mho that is currently being tested
			int mhoX = mhoLocations.get(i*2);
			int mhoY = mhoLocations.get(i*2+1);
			
			//Check if there is a fence 1 block away from the mho
			if(newMap[mhoX][mhoY+1] instanceof Fence || newMap[mhoX][mhoY-1] instanceof Fence || newMap[mhoX-1][mhoY] instanceof Fence || newMap[mhoX-1][mhoY+1] instanceof Fence || newMap[mhoX-1][mhoY-1] instanceof Fence || newMap[mhoX+1][mhoY] instanceof Fence || newMap[mhoX+1][mhoY+1] instanceof Fence || newMap[mhoX+1][mhoY-1] instanceof Fence) {
				
				//Assign the new map location as a Mho
				newMap[mhoX][mhoY] = new BlankSpace(mhoX, mhoY, board);
				
				//Set the mho's move in the moveList
				moveList[mhoX][mhoY] = Legend.SHRINK;
				
				//remove each X and Y from mhoLocations
				mhoLocations.remove(i*2+1);
				mhoLocations.remove(i*2);
				
				//Call moveRemainingMhos again, because the list failed to be checked through completely
				moveRemainingMhos();
				break;
			}
		}
		return;
	}
}
