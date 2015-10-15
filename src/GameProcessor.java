import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;



public class GameProcessor {
	private Unit[][] map = new Unit[12][12];
	private Unit[][] newMap = new Unit[12][12];
	private char[][] moveList = new char[12][12];
	Gameboard board;

	private int[] playerLocation = new int[2];
	private int[] newPlayerLocation = new int[2];
	private ArrayList<Integer> mhoLocations = new ArrayList<Integer>();

	public boolean gameOver = false;

	public GameProcessor(Gameboard board) {
		this.board=board;
		generateMap();
	}

	private void generateMap() {

		boolean[][] spaceUsed = new boolean[12][12];
		int fencesGenerated = 0;
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
				spaceUsed[x][y] = true;
				return;
			}
		}
	}

	public Unit[][] getMap() {
		return map;
	}

	public char[][] getMoveList() {
		return moveList;
	}

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

	//called by gameboard when animating is done
	public void animatingDone() {
		updateMap();
		setPlayerLocation(getNewPlayerLocation());
		if(gameOver) {
			System.out.println("GAME OVER!");
			//Restart Screen
		}
	}

	private void updateMhoLocationList() {
		mhoLocations.clear();
		for (int i = 1; i < 11; i++)
			for (int j = 1; j < 11; j++) 
				if(map[i][j] instanceof Mho) {
					mhoLocations.add(i);
					mhoLocations.add(j);
				}
	}

	public int[] getPlayerLocation() {
		return playerLocation;
	}

	public int[] getNewPlayerLocation() {
		return newPlayerLocation;
	}

	public void setPlayerLocation(int[] playerLocation) {
		this.playerLocation = playerLocation;
	}

	public void setNewPlayerLocation(int[] playerLocation) {
		this.newPlayerLocation = playerLocation;
	}

	private void gameOver() {
		gameOver=true;
	}

	private void validPlayerMove(char moveType) {
		int xOffset = 0;
		int yOffset = 0;

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
		}

		int x = getPlayerLocation()[0];
		int y = getPlayerLocation()[1];


		if (map[x+xOffset][y+yOffset] instanceof BlankSpace || map[x+xOffset][y+yOffset] instanceof Player) {
			newMap[x][y] = new BlankSpace(x, y, board);
			newMap[x+xOffset][y+yOffset] = new Player(x+xOffset, y+yOffset, board);
			moveList[x][y] = moveType;
			setNewPlayerLocation(new int[] {x+xOffset, y+yOffset});
			return;
		} else {
			newMap[x][y] = new BlankSpace(x, y, board);
			moveList[x][y] = Legend.SHRINK;
			setNewPlayerLocation(new int[] {x, y});
			gameOver();
			return;
		}

	}

	public void playerMove(char move) {

		for (int i = 0; i < 12; i++)
			for (int j = 0; j < 12; j++)
				moveList[i][j] = Legend.NO_MOVEMENT;

		copyMap();
		updateMhoLocationList();
		validPlayerMove(move);
		moveMhos();
		board.toggleAnimating();
	}

	private void jump() {

	}

	private void moveMhos() {
		moveAllignedMhos();
		moveDiagonalMhos();
		moveHorizontalMhos();
		moveVerticalMhos();
		moveRemainingMhos();
	}

	private void moveAllignedMhos() {

		int playerX = getNewPlayerLocation()[0];
		int playerY = getNewPlayerLocation()[1];

		for(int i = 0; i < mhoLocations.size()/2; i++) {
			int mhoX = mhoLocations.get(i*2);
			int mhoY = mhoLocations.get(i*2+1);

			int xOffset = 0;
			int yOffset = 0;
			char move = Legend.NO_MOVEMENT;

			if(playerX == mhoX) {
				if(playerY > mhoY) {
					yOffset = 1;
					move = Legend.DOWN;
				} else {
					yOffset = -1;
					move = Legend.UP;
				}
			} else if(playerY == mhoY) {
				if(playerX > mhoX) {
					xOffset = 1;
					move = Legend.RIGHT;
				} else {
					xOffset = -1;
					move = Legend.LEFT;
				}
			}

			if(!(newMap[mhoX+xOffset][mhoY+yOffset] instanceof Mho)) {
				newMap[mhoX][mhoY] = new BlankSpace(mhoX, mhoY, board);

				if(newMap[mhoX+xOffset][mhoY+yOffset] instanceof Player) {
					if(newMap[mhoX+xOffset][mhoY+yOffset] instanceof Player)
						moveList[mhoX+xOffset][mhoY+yOffset] = Legend.SHRINK;
					gameOver();
				}
				if(!(newMap[mhoX+xOffset][mhoY+yOffset] instanceof Fence)) {
					newMap[mhoX+xOffset][mhoY+yOffset] = new Mho(mhoX+xOffset, mhoY+yOffset, board);
				} else {
					move = Legend.SHRINK;
				}
				moveList[mhoX][mhoY] = move;
				mhoLocations.remove(i*2+1);
				mhoLocations.remove(i*2);
				moveAllignedMhos();
				break;
			}
		}
		return;
	}

	private void moveDiagonalMhos() {
		int playerX = getNewPlayerLocation()[0];
		int playerY = getNewPlayerLocation()[1];

		for(int i = 0; i < mhoLocations.size()/2; i++) {
			int mhoX = mhoLocations.get(i*2);
			int mhoY = mhoLocations.get(i*2+1);

			int xOffset = 0;
			int yOffset = 0;
			char move;

			if(playerX > mhoX) {
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

			if(newMap[mhoX+xOffset][mhoY+yOffset] instanceof BlankSpace || newMap[mhoX+xOffset][mhoY+yOffset] instanceof Player) {
				newMap[mhoX][mhoY] = new BlankSpace(mhoX, mhoY, board);

				if(newMap[mhoX+xOffset][mhoY+yOffset] instanceof Player) {
					if(newMap[mhoX+xOffset][mhoY+yOffset] instanceof Player)
						moveList[mhoX+xOffset][mhoY+yOffset] = Legend.SHRINK;
					gameOver();
				}

				newMap[mhoX+xOffset][mhoY+yOffset] = new Mho(mhoX+xOffset, mhoY+yOffset, board);
				moveList[mhoX][mhoY] = move;
				mhoLocations.remove(i*2+1);
				mhoLocations.remove(i*2);
				moveDiagonalMhos();
				break;
			}
		}
		return;
	}

	private void moveHorizontalMhos() {
		int playerX = getNewPlayerLocation()[0];
		int playerY = getNewPlayerLocation()[1];

		for(int i = 0; i < mhoLocations.size()/2; i++) {
			int mhoX = mhoLocations.get(i*2);
			int mhoY = mhoLocations.get(i*2+1);

			int xOffset = 0;
			int yOffset = 0;
			char move = Legend.NO_MOVEMENT;

			if((Math.abs(playerX-mhoX))>=(Math.abs(playerY-mhoY))) {
				if(playerX > mhoX) {
					xOffset = 1;
					move = Legend.RIGHT;
				} else {
					xOffset = -1;
					move = Legend.LEFT;
				}
			}

			if(newMap[mhoX+xOffset][mhoY+yOffset] instanceof BlankSpace || newMap[mhoX+xOffset][mhoY+yOffset] instanceof BlankSpace) {

				if(newMap[mhoX+xOffset][mhoY+yOffset] instanceof Player) {
					if(newMap[mhoX+xOffset][mhoY+yOffset] instanceof Player)
						moveList[mhoX+xOffset][mhoY+yOffset] = Legend.SHRINK;
					gameOver();
				}

				newMap[mhoX+xOffset][mhoY+yOffset] = new Mho(mhoX+xOffset, mhoY+yOffset, board);
				moveList[mhoX][mhoY] = move;
				newMap[mhoX][mhoY] = new BlankSpace(mhoX, mhoY, board);
				mhoLocations.remove(i*2+1);
				mhoLocations.remove(i*2);
				moveHorizontalMhos();
				break;
			} 
		}
		return;
	}

	private void moveVerticalMhos() {
		int playerX = getNewPlayerLocation()[0];
		int playerY = getNewPlayerLocation()[1];

		for(int i = 0; i < mhoLocations.size()/2; i++) {
			int mhoX = mhoLocations.get(i*2);
			int mhoY = mhoLocations.get(i*2+1);

			int xOffset = 0;
			int yOffset = 0;
			char move = Legend.NO_MOVEMENT;


			if((Math.abs(playerX-mhoX))<=(Math.abs(playerY-mhoY))) {
				if(playerY > mhoY) {
					yOffset = 1;
					move = Legend.DOWN;
				} else {
					yOffset = -1;
					move = Legend.UP;
				}
			}

			if(newMap[mhoX+xOffset][mhoY+yOffset] instanceof BlankSpace || newMap[mhoX+xOffset][mhoY+yOffset] instanceof BlankSpace) {
				newMap[mhoX][mhoY] = new BlankSpace(mhoX, mhoY, board);

				if(newMap[mhoX+xOffset][mhoY+yOffset] instanceof Player) {
					if(newMap[mhoX+xOffset][mhoY+yOffset] instanceof Player)
						moveList[mhoX+xOffset][mhoY+yOffset] = Legend.SHRINK;
					gameOver();
				}

				moveList[mhoX][mhoY] = move;
				newMap[mhoX+xOffset][mhoY+yOffset] = new Mho(mhoX+xOffset, mhoY+yOffset, board);
				mhoLocations.remove(i*2+1);
				mhoLocations.remove(i*2);
				moveVerticalMhos();
				break;
			} 
		}
		return;
	}

	private void moveRemainingMhos() {
		for(int i = 0; i < mhoLocations.size()/2; i++) {
			int mhoX = mhoLocations.get(i*2);
			int mhoY = mhoLocations.get(i*2+1);

			boolean fenceNear = false;

			if(newMap[mhoX][mhoY+1] instanceof Fence || newMap[mhoX][mhoY-1] instanceof Fence || newMap[mhoX-1][mhoY] instanceof Fence || newMap[mhoX-1][mhoY+1] instanceof Fence || newMap[mhoX-1][mhoY-1] instanceof Fence || newMap[mhoX+1][mhoY] instanceof Fence || newMap[mhoX+1][mhoY+1] instanceof Fence || newMap[mhoX+1][mhoY-1] instanceof Fence)
				fenceNear = true;
			if(fenceNear) {
				newMap[mhoX][mhoY] = new BlankSpace(mhoX, mhoY, board);
				moveList[mhoX][mhoY] = Legend.SHRINK;
				mhoLocations.remove(i*2+1);
				mhoLocations.remove(i*2);
				moveRemainingMhos();
				break;
			}
		}
		return;
	}
}
