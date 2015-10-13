import java.util.ArrayList;



public class GameProcessor {
	private Unit[][] map = new Unit[12][12];
	private Unit[][] duplicateMap = new Unit[12][12];
	private char[][] moveList = new char[12][12];
	Gameboard board;
	
	private int[] playerLocation = new int[2];
	private ArrayList<Integer> mhoLocations = new ArrayList<Integer>();

	public GameProcessor(Gameboard board) {
		this.board=board;
		generateMap();
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


	//called by gameboard when animating is done
	public void animatingDone() {

	}
	
	public int[] getPlayerLocation() {
		return playerLocation;
	}
	
	public void setPlayerLocation(int[] playerLocation) {
		this.playerLocation = playerLocation;
	}

	
	private void gameOver() {
		//still needs to be implemeneted
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

		if (map[x][y] instanceof Player)
			if (map[x+xOffset][y+yOffset] instanceof BlankSpace) {
				
				duplicateMap[x][y] = new BlankSpace(x, y, board);
				duplicateMap[x+xOffset][y+yOffset] = new Player(x+xOffset, y+yOffset, board);
				return true;
			}
		return false;

	}

	public void playerMove(char move) {
		if(validPlayerMove(move)) {
			
			duplicateMap = map.clone();
			
			for (int i = 0; i < 12; i++)
				for (int j = 0; j < 12; j++)
					moveList[i][j] = Legend.NO_MOVEMENT;
			
			moveList[getPlayerLocation()[0]][getPlayerLocation()[1]] = move;
			
			moveMhos();
			
			board.toggleAnimating();
			
		}
		else {
			gameOver();
		}
	}
	
	public void jump() {

	}
	
	public void moveMhos() {
		//still to be implemented
		for (int i = 0; i < (mhoLocations.size()/2); i++) {
			int playerX = getPlayerLocation()[0];
			int playerY = getPlayerLocation()[1];
			
			int mhoX = (int) mhoLocations.get(i*2);
			int mhoY = (int) mhoLocations.get(i*2+1);
			
		}
	}
}
