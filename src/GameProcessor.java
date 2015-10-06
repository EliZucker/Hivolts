

public class GameProcessor {
	private Unit[][] map = new Unit[12][12];
	private char[][] moveList = new char[12][12];
	Gameboard board;

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
					map[i][j] = new Spike(i, j, board);
					spaceUsed[i][j] = true;
				}
			}
		}

		//Generate 20 spikes in unique locations
		while (fencesGenerated < 20) {
			int x = 1+(int)(Math.random()*10);
			int y = 1+(int)(Math.random()*10);

			if(spaceUsed[x][y]==false) {
				map[x][y] = new Spike(x, y, board);
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

	private int[] getPlayerLocation() {
		for (int i = 0; i < 12; i++)
			for (int j = 0; j < 12; j++) 
				if (map[i][j] instanceof Player)
					return new int[]{i,j};
		return null;
	}
	
	private void gameOver() {
		//still needs to be implemeneted
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

		int i = getPlayerLocation()[0];
		int j = getPlayerLocation()[1];

		if (map[i][j] instanceof Player)
			if (map[i+xOffset][j+yOffset] instanceof Spike || map[i+xOffset][j+yOffset] instanceof Mho) 
				return false;

		return true;

	}

	public void playerMove(char move) {
		if(validPlayerMove(move)) {
			moveList[getPlayerLocation()[0]][getPlayerLocation()[1]] = move;
			for (int i = 0; i < 12; i++)
				for (int j = 0; j < 12; j++)
					if (map[i][j] instanceof Mho)
						moveMho(i, j);
		}
		else {
			gameOver();
		}
	}
	
	public void jump() {

	}
	
	public void moveMho(int x, int y) {
		//still to be implemented
	}
}
