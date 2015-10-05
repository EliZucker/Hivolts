

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
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				moveList[i][j] = Legend.NO_MOVEMENT;
			}
		}
		moveList[2][2] = Legend.DOWN_RIGHT;
		return moveList;
	}
	
	
	//called by gameboard when animating is done
	public void animatingDone() {
		map[2][2] = new BlankSpace(2, 2, board);
		map[3][3] = new Mho(3, 3, board);
	}
	
	
	
}
