import java.util.*;


public class Level {	
	private Board board;
	private Space winningSpace;
	private int numMoves;
	
	public Level(int nRows, int nCols) {
		board = new Board(nRows, nCols);
		setupLevel1(nRows,nCols);
	}
	
	public void setupLevel1(int maxRows, int maxCols) {
		board.addVehicle(VehicleType.MYCAR, 2, 0, 2, false);
		board.addVehicle(VehicleType.TRUCK, 0, 2, 3, true);
		board.addVehicle(VehicleType.AUTO, 4, 0, 2, true);
		board.addVehicle(VehicleType.AUTO, 5, 1, 2, false);
		board.addVehicle(VehicleType.AUTO, 0, 3, 2, false);
		board.addVehicle(VehicleType.AUTO, 4, 3, 2, true);
		board.addVehicle(VehicleType.TRUCK, 1, 4, 3, true);
		board.addVehicle(VehicleType.AUTO, 0, 5, 2, true);
		board.addVehicle(VehicleType.TRUCK, 2, 5, 3, true);
		
		winningSpace = new Space(2,5);
		numMoves = 0;
	}
	
	public int getColumns() {
		return board.getNumCols();
	}
	
	public int getRows() {
		return board.getNumRows();
	}
	
	public int getNumMoves() {
		return numMoves;
	}
	
	public void incrementMoves() {
		numMoves++;
	}
	
	public Vehicle getVehicle(Space space) {
		return board.getVehicle(space);
	}
	
	public Space getWinningSpace() {
		return winningSpace;
	}
	
	// Moves the vehicle on the space if it can
	// If it cant then returns false
	public boolean moveNumSpaces(Space space, int numSpaces) {
		if(board.canMoveNumSpaces(space,numSpaces) == true) {
			board.moveNumSpaces(space, numSpaces);
			numMoves++;
			if (numSpaces == 0)
				numMoves--;
			return true;
		}
		return false;
	}
	
	public String toString() {
		String result = generateColHeader(getColumns());
		result+=addRowHeader(board.toString());
		return result;
	}

	// Returns true if MYCAR reaches goal space
	public boolean passedLevel() {
		Vehicle v = board.getVehicle(winningSpace);
		if (v == null) {
			return false;
		}
		if (v.getVehicleType() == VehicleType.MYCAR) {
			return true;
		}
		return false;
	}
	
	// This method will add the row information
	// needed to the board and is used by the toString method
	private String addRowHeader(String origBoard) {
		String result = "";
		String[] elems = origBoard.split("\n");
		for(int i = 0; i < elems.length; i++) {
			result += (char)('A' + i) + "|" + elems[i] + "\n"; 
		}
		return result;
	}
	
	// This one is responsible for making the row of column numbers at the top and is used by the toString method
	private String generateColHeader(int cols) {
		String result = "  ";
		for(int i = 1; i <= cols; i++) {
			result+=i;
		}
		result+="\n  ";
		for(int i = 0; i < cols; i++) {
			result+="-";
		}
		result+="\n";
		return result;
	}
	
	// returns Vehicle ArrayList
	public ArrayList<Vehicle> getVehiclesOnBoard() {
		return board.getVehicleOnBoard();
	}
}
