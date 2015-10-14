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

	private boolean gameOver = false;
	
	public GameProcessor(Gameboard board) {
		this.board=board;
		generateMap();
		playerMove(Legend.RIGHT);
	}

	public void generateMap() {

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

	public void copyMap() {
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
	
	public void updateMap() {
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
		//still needs to be implemeneted
		//
		gameOver=true;
		System.out.println("GAME OVER!");
	}

	private boolean validPlayerMove(char moveType) {
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


		if (map[x+xOffset][y+yOffset] instanceof BlankSpace) {
			newMap[x][y] = new BlankSpace(x, y, board);
			newMap[x+xOffset][y+yOffset] = new Player(x+xOffset, y+yOffset, board);
			moveList[x][y] = moveType;
			setNewPlayerLocation(new int[] {x+xOffset, y+yOffset});
			return true;
		}

		return false;

	}

	public void playerMove(char move) {

		for (int i = 0; i < 12; i++)
			for (int j = 0; j < 12; j++)
				moveList[i][j] = Legend.NO_MOVEMENT;

		copyMap();

		if(validPlayerMove(move)) {

			moveMhos();

			board.toggleAnimating();

		}
		else {
			moveList[getPlayerLocation()[0]][getPlayerLocation()[1]] = Legend.SHRINK;
			newMap[getPlayerLocation()[0]][getPlayerLocation()[1]] = new BlankSpace(getPlayerLocation()[0],getPlayerLocation()[1], board);
			board.toggleAnimating();
			gameOver();
		}
	}

	public void jump() {

	}
	
	public void moveMhos() {
		moveAllignedMhos();
		moveDiagonalMhos();
	}
	
	public void moveAllignedMhos() {

		int playerX = getNewPlayerLocation()[0];
		int playerY = getNewPlayerLocation()[1];

		//Check if any mhos are perfectly vertically or horizontally alligned
		for (ListIterator<Integer> iterator = mhoLocations.listIterator(); iterator.hasNext();) {
			int mhoX = iterator.next();
			int mhoY = iterator.next();

			if (mhoX==playerX) {
				iterator.remove();
				iterator.previous();
				iterator.remove();

				if (playerY>mhoY) {
					moveList[mhoX][mhoY] = Legend.DOWN;
					newMap[mhoX][mhoY] = new BlankSpace(mhoX, mhoY, board);

					if(map[mhoX][mhoY+1] instanceof Fence) {
						moveList[mhoX][mhoY] = Legend.SHRINK;
					} else {
						if(newMap[mhoX][mhoY+1] instanceof Player) {
							gameOver();
						}
						newMap[mhoX][mhoY+1] = new Mho(mhoX, mhoY+1, board);
					}

				} else {
					moveList[mhoX][mhoY] = Legend.UP;
					newMap[mhoX][mhoY] = new BlankSpace(mhoX, mhoY, board);

					if(map[mhoX][mhoY-1] instanceof Fence) {
						moveList[mhoX][mhoY] = Legend.SHRINK;
					} else {
						if(newMap[mhoX][mhoY-1] instanceof Player) {
							gameOver();
						}
						newMap[mhoX][mhoY-1] = new Mho(mhoX, mhoY-1, board);
					}
				}
			} else if (mhoY==playerY) {
				iterator.remove();
				iterator.previous();
				iterator.remove();

				if (playerX>mhoX) {
					moveList[mhoX][mhoY] = Legend.RIGHT;
					newMap[mhoX][mhoY] = new BlankSpace(mhoX, mhoY, board);

					if(map[mhoX+1][mhoY] instanceof Fence) {
						moveList[mhoX][mhoY] = Legend.SHRINK;
					} else {
						if(newMap[mhoX+1][mhoY] instanceof Player) {
							gameOver();
						}
						newMap[mhoX+1][mhoY] = new Mho(mhoX+1, mhoY, board);
					}
				} else {
					moveList[mhoX][mhoY] = Legend.LEFT;
					newMap[mhoX][mhoY] = new BlankSpace(mhoX, mhoY, board);

					if(map[mhoX-1][mhoY] instanceof Fence) {
						moveList[mhoX][mhoY] = Legend.SHRINK;
					} else {
						if(newMap[mhoX-1][mhoY] instanceof Player) {
							gameOver();
						}
						newMap[mhoX-1][mhoY] = new Mho(mhoX-1, mhoY, board);
					}
				}
			}
		}
	}

	public void moveDiagonalMhos() {
		for(int i = 0; i < mhoLocations.size()/2-1; i++) {
			int mhoX = mhoLocations.get(i*2);
			int mhoY = mhoLocations.get(i*2+1);
			int playerX = getNewPlayerLocation()[0];
			int playerY = getNewPlayerLocation()[1];

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

			if(newMap[mhoX+xOffset][mhoY+yOffset] instanceof BlankSpace) {
				newMap[mhoX][mhoY] = new BlankSpace(mhoX, mhoY, board);
				
				if(newMap[mhoX+xOffset][mhoY+yOffset] instanceof Player) {
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
}
