package board;

public class Cell {
	protected int color;
	protected int x;
	protected int y;

	/**
		* Creates a cell with no x and y coordinate specifications and sets the color
		* field to -1 to signify there is no chip.
	*/
	public Cell() {
		color = -1;
	}
	/**
		* Creates a cell object with a specific location (x,y) and int color.
		* @param int x is the x coordinate of the cell being created.
		* @param int y is the y coordinate of the cell being created.
		* @param int color is the color of the chip being placed on that cell.
	*/
	public Cell(int x, int y, int color) {
		this.color = color;
		this.x = x;
		this.y = y;
	}

	/**
		* areNeighbor determines whether "this" Cell is neighbors with a Cell b. Being neighbors
		* means that these two cells are next to each other whethe diagonally, horizontally
		* or vertically.
		* @param Cell b is the cell that is the potential neighbor of this cell.
		* @return boolean true if b is a neighbor of this cell or false if not.
	*/
	protected boolean areNeigbors(Cell b) {
		if ((this.x == b.x) && (this.y == b.y)) {
			return false;
		}
		if ((this.x - 1 == b.x) || (this.x + 1 == b.x) || (this.y -1 == b.y) || (this.y + 1 == b.y)) {
			return true;
		}
		return false;
	}

	/**
		* equals determines whether two cells are the same. Two cells being the same means they
		* contain the same information, which means their x and y coordinates are the same and
		* their color fields are the same value.
		* @param Cell c is the cell being checked for equality
		* @return boolean is true if Cell c's fields are the same as "this" cell and false if not.
	*/
	protected boolean equals(Cell c) {
		if ((this.color == c.color) && (this.x==c.x) && (this.y==c.y)) {
			return true;
		}
		return false;
	}
}