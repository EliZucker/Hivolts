import javax.swing.JPanel;


public class GameProcessor {
	private Unit[][] map = new Unit[12][12];
	private char[][] moveList = new char[12][12];
	
	public GameProcessor(Gameboard board) {
		//COMPLETELY TEMPORARY PLACEHOLDER CODE
				for (int i = 0; i < 12; i++) {
					for (int j = 0; j < 12; j++) {
						map[i][j] = new BlankSpace(i, j, board);
					}
				}
				map[1][2] = new Player(1, 2, board);
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
	
	
	
}
