

public class GameProcessor {
	private Unit[][] map = new Unit[12][12];
	private char[][] moveList = new char[12][12];
	Gameboard board;
	
	public GameProcessor(Gameboard board) {
		this.board=board;
		
		//COMPLETELY TEMPORARY PLACEHOLDER CODE
				for (int i = 0; i < 12; i++) {
					for (int j = 0; j < 12; j++) {
						map[i][j] = new BlankSpace(i, j, board);
						if (i == 0 || i == 11 || j ==0 || j == 11)
							map[i][j] = new Spike(i, j, board);
					}
				}
				map[2][2] = new Mho(2, 2, board);
				board.toggleAnimating();
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
