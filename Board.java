
import java.util.*;

public class Board {

	private int numRows;
	private int numCols;
	private ArrayList<Vehicle> addV = new ArrayList<Vehicle>();
	private Vehicle[][] Board;
	private Vehicle v;
	
	public Board(int rows, int cols) 
	{
		this.numRows = rows;
		this.numCols = cols;
		Board = new Vehicle[rows][cols];
	}
	
	
	//@return number of columns the board has
	public int getNumCols() 
	{
		return numCols; 
	}
	//column setter
	public void setNumCols(int cols)
	{
		this.numCols = cols;
	}

	//@return number of rows the board has
	public int getNumRows() 
	{
		return numRows; 
	}
	//row setter
	public void setNumRows(int rows)
	{
		this.numCols = rows;
	}
	
	boolean isVehicleOnSpace(Space space) 
	{
		if (Board[space.getRow()][space.getCol()] == null) {
			return false;
		}
		return true;
	}
	
	 // Grabs the vehicle present on a particular space if any is there
	 // If a Vehicle occupies three spaces, the same Vehicle pointer should be returned for all three spaces

	public Vehicle getVehicle(Space s) 
	{
		if (Board[s.getRow()][s.getCol()] == null)
		{
			return null;
		}
		return Board[s.getRow()][s.getCol()];		
	}

	 // adds a vehicle to the board. It would be good to do some checks for a legal placement here.

	public void addVehicle(VehicleType type, int startRow, int startCol, int length, boolean vert) 
	{
		Vehicle tempVehicle = new Vehicle(type, startRow, startCol, length,vert);
		Space[] tempArray = new Space[tempVehicle.getLength()];
		tempArray =  tempVehicle.spacesOccupied();
		//check for out of bound
		for (int i = 0; i < tempVehicle.getLength();i++ )
		{
			if (tempArray[i].getRow() < 0 || tempArray[i].getRow() >= numRows || tempArray[i].getCol() < 0 || tempArray[i].getCol() >= numCols)
			{
				System.out.println("Invalid. Out of Bound.");
					return;
			}
		//check for overlap with other vehicle	
			tempArray[i] = new Space( tempVehicle.spacesOccupied()[i].getRow(),tempVehicle.spacesOccupied()[i].getCol());
			if (isVehicleOnSpace(tempArray[i])) {
				System.out.println("Can not add Vehicle. Space is occupied");
				return;
			}
		}
		for (int i = 0; i < tempVehicle.getLength(); i++) {
			Board[tempVehicle.spacesOccupied()[i].getRow()][tempVehicle.spacesOccupied()[i].getCol()] = tempVehicle;
		}	
		addV.add(tempVehicle); //add vehicle to array incrementally
		//System.out.println(addV);
	}
	
	public ArrayList<Vehicle> getVehicleOnBoard()
	{
		return addV;
	}
	
	 // This method moves the vehicle on a certain row/column a specific number of spaces

	public boolean moveNumSpaces(Space start, int numSpaces) {
		if (canMoveNumSpaces(start, numSpaces) == true)
		{
			Vehicle moveVehicle = getVehicle(start);
			//delete vehicle
			Space[] spaceVehicle = moveVehicle.spacesOccupied();
			for (int i = 0; i < moveVehicle.spacesOccupied().length; i++)
			{
				Board[spaceVehicle[i].getRow()][spaceVehicle[i].getCol()] = null;
			}
			//add new location
			moveVehicle.move(numSpaces);
			addVehicle(moveVehicle.getVehicleType(), moveVehicle.getStartRow(), moveVehicle.getStartCol(), moveVehicle.getLength(), moveVehicle.isVertical());
			return true;
		}
		return false;
	}
	

	 // This method just checks to see if the vehicle on a certain row/column can move a specific number of spaces, though
	 // it will not move the vehicle.  You should use this when you wish to move or want to see if you can
	 // move a vehicle numSpaces without going out of bounds or hitting another vehicle
	
	public boolean canMoveNumSpaces(Space start, int numSpaces) {
		//Check for out of Bound

		Vehicle canMoveCar = getVehicle(start);
		if (canMoveCar == null) 
		{
			return false;
		}
		Space[] spaceOnTrail = canMoveCar.spacesOccupiedOnTrail(numSpaces);
		for (int i = 0; i < Math.abs(numSpaces); i++)
		{
			if (spaceOnTrail[i].getRow() < 0 || spaceOnTrail[i].getRow() >= numRows || spaceOnTrail[i].getCol() < 0 || spaceOnTrail[i].getCol() >= numCols) 
			{
				return false;
			}
			if (isVehicleOnSpace(spaceOnTrail[i]))
			{
				return false;
			}
		}
		return true;
	}
	public VehicleType getVehicleType() 
	{
		return v.getVehicleType();
	}

	// This method helps create a string version of the board
	public String toString() {
		return BoardConverter.createString(this);
	}
	
	// Testing methods down here for testing the board 
	
	
	public static void main(String[] args) {
		Board b = new Board(5, 5);
		b.addVehicle(VehicleType.MYCAR, 1, 0, 2, false);
		b.addVehicle(VehicleType.TRUCK, 0, 2, 3, true);
		b.addVehicle(VehicleType.AUTO, 3, 3, 2, true);
		b.addVehicle(VehicleType.AUTO, 0, 3, 2, true);
		b.addVehicle(VehicleType.AUTO, 0, 4, 4, false); // shouldn't not able to add
		System.out.println(b);
		testCanMove(b);
		testMoving(b);
		System.out.println(b);
	}
	
	public static void testMoving(Board b) {
		System.out.println("just moving some stuff around");
		b.moveNumSpaces(new Space(1, 2), 1);
		b.moveNumSpaces(new Space(1, 2), 1);
		b.moveNumSpaces(new Space(1, 1), 1);
		b.moveNumSpaces(new Space(3, 3), -1);
		}
	
	public static void testCanMove(Board b) {
		System.out.println("Ok, now testing some moves...");
		System.out.println("These should all be true");
		System.out.println("Moving truck down " + b.canMoveNumSpaces(new Space(0, 2), 2));
		System.out.println("Moving truck down " + b.canMoveNumSpaces(new Space(1, 2), 2));
		System.out.println("Moving truck down " + b.canMoveNumSpaces(new Space(2, 2), 2));
		System.out.println("Moving lower auto up " + b.canMoveNumSpaces(new Space(3, 3), -1));
		System.out.println("Moving lower auto up " + b.canMoveNumSpaces(new Space(4, 3), -1));
		
		System.out.println("And these should all be false");
		System.out.println("Moving truck down " + b.canMoveNumSpaces(new Space(3, 2), 2));
		System.out.println("Moving the car into truck" + b.canMoveNumSpaces(new Space(1, 0), 1));
		System.out.println("Moving the car into truck" + b.canMoveNumSpaces(new Space(1, 0), 2));
		System.out.println("Moving nothing at all" + b.canMoveNumSpaces(new Space(4, 4), -1));
		System.out.println("Moving lower auto up " + b.canMoveNumSpaces(new Space(3, 3), -2));
		System.out.println("Moving lower auto up " + b.canMoveNumSpaces(new Space(4, 3), -2));
		System.out.println("Moving upper auto up " + b.canMoveNumSpaces(new Space(0, 3), -1));
		System.out.println("Moving upper auto up " + b.canMoveNumSpaces(new Space(1, 3), -1));
	}
}
