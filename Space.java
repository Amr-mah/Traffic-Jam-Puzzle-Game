
public class Space {
	int row;
	int col;
	
	
	 // The constructor that will set up the object to store a row and column
	public Space(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	
	public String toString() {
		return "r" + row + "c" + col;
	}
	
	public static void main(String[] args) {
		Space one = new Space(3, 4);
		Space two = new Space(1, 6);
		two.setRow(two.getRow()+1);
		two.setCol(two.getCol()-1);
		System.out.println("one r: " + one.getRow() + ", C: " + one.getCol());
		System.out.println("two r: " + two.getRow() + ", c: " + two.getCol());
	}

	
}

