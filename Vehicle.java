public class Vehicle {
	
	VehicleType type;
	Space start;
	int length;
	boolean isVertical;
	
	
	// Constructor
	public Vehicle(VehicleType type, int startRow, int startCol, int length, boolean isVertical) {
		this.type = type;
		start = new Space(startRow, startCol);
		this.length = length;
		this.isVertical = isVertical;
	}
	
	// returns vehicle start space
	public Space getStart() {
		return this.start;
	}
	
	// returns start row
	public int getStartRow() {
		return this.start.getRow();
	}
	
	// returns start col
	public int getStartCol() {
		return this.start.getCol();
	}
	
	// returns length of the vehicle
	public int getLength() {
		return this.length;
	}
	
	// returns true if vehicle is vertical
	public boolean isVertical() {
		return this.isVertical;
	}
	
	// sets the start row for the vehicle
	public void setStartRow(int newRow) {
		this.start.setRow(newRow);
	}
	
	// sets the start col for the vehicle
	public void setStartCol(int newCol) {
		this.start.setCol(newCol);
	}

	// returns the type associated with the particular vehicle
	public VehicleType getVehicleType() {
		return this.type;
	}

	// Provides an array of Spaces that indicate where a particular Vehicle
	// would be located, based on its current starting space
	public Space[] spacesOccupied() {
		
		Space[] spaceArray;
		if (this.length == 2) {
			spaceArray = new Space[2];
		}
		else {
			spaceArray = new Space[3];
		}
		spaceArray[0] = start;
		if (this.isVertical) {
			spaceArray[1] = new Space(spaceArray[0].getRow() + 1, spaceArray[0].getCol());
			if (this.length == 3) {
				spaceArray[2] = new Space(spaceArray[1].getRow() + 1, spaceArray[1].getCol());
			}
		}
		else {
			spaceArray[1] = new Space(spaceArray[0].getRow(), spaceArray[0].getCol() + 1);
			if (this.length == 3) {
				spaceArray[2] = new Space(spaceArray[1].getRow(), spaceArray[1].getCol() + 1);
			}
		}
		
		return spaceArray;
	}

	// Calculates an array of the spaces that would be traveled if a vehicle
	// were to move numSpaces
	public Space[] spacesOccupiedOnTrail(int numSpaces) {
		Space[] spacesOccupied;
		spacesOccupied = new Space[Math.abs(numSpaces)];
		
		if (numSpaces < 0) {
			numSpaces = Math.abs(numSpaces);
			if (this.isVertical) {
				for (int i = 0; i < numSpaces; i++) {
					spacesOccupied[i] = new Space(start.getRow() - i - 1, start.getCol());
				}
			}
			else {
				for (int i = 0; i < numSpaces; i++) {
					spacesOccupied[i] = new Space(start.getRow(), start.getCol() - i - 1);
				}
			}

		}
		else {
			if (this.isVertical) {
				for (int i = 0; i < numSpaces; i++) {
					if (this.length == 3) {
						spacesOccupied[i] = new Space(start.getRow() + i + 3, start.getCol());
					}
					else {
						spacesOccupied[i] = new Space(start.getRow() + i + 2, start.getCol());
					}
				}
			}
			else {
				for (int i = 0; i < numSpaces; i++) {
					if (this.length == 3) {
						spacesOccupied[i] = new Space(start.getRow(), start.getCol() + i + 3);
					}
					else {
						spacesOccupied[i] = new Space(start.getRow(), start.getCol() + i + 2);
					}
				}
			}
		}
				
		return spacesOccupied;
	}
	
	
	// moves the vehicle to a new location by changing its start space
	public void move(int numSpaces) {
		if (this.isVertical) {
			setStartRow(this.start.getRow() + numSpaces);
		}
		else {
			setStartCol(this.start.getCol() + numSpaces);
		}
	}
	
	// Returns the start space a vehicle would occupy if it would move
	public Space ifIWereToMove(int numSpaces) {
		Space newSpace;
		if (this.isVertical) {
			newSpace = new Space(this.start.getRow() + numSpaces, this.start.getCol());
		}
		else {
			newSpace = new Space(this.start.getRow(), this.start.getCol() + numSpaces);
		}
		return newSpace;
	}
	
	public static void printSpaces(Space[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
	
	public String toString() {
		return "Type: " + this.type + ", Start: " + this.start + ", Length: " + this.length + ", Vertical? " + this.isVertical;
	}
	
	public static void main(String[] args) {
		Vehicle someTruck = new Vehicle(VehicleType.TRUCK, 1, 1, 3, true);
		Vehicle someAuto = new Vehicle(VehicleType.AUTO, 2, 2, 2, false);
		
		System.out.println("This next test is for spacesOccupied: ");
		
		System.out.println("vertical truck at r1c1 should give you r1c1; r2c1; r3c1 as the spaces occupied:does it?");
		printSpaces(someTruck.spacesOccupied());
		
		System.out.println("horizontal auto at r2c2 should give you r2c2; r2c3 as the spaces occupied:does it?");
		printSpaces(someAuto.spacesOccupied());
		
		System.out.println("if we were to move horizontal auto -2 it should give you at least r2c0; r2c1; it may also add r2c2; r2c3 to its answer:does it?");
		printSpaces(someAuto.spacesOccupiedOnTrail(-2));
		
		System.out.println(someTruck.ifIWereToMove(-3));
		System.out.println(someTruck);
		someTruck.move(-3);
		System.out.println(someTruck);
		
		printSpaces(someTruck.spacesOccupied());
	}
}
