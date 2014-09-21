/**
*The Board Class is the game board of the Network. It includes a field board which is a  two-dimensional
*array of Cell objects.
*/
package board;
import list.*;
import player.*;

public class Board {
	/**
	*The field board is the board made of Cells.
	*/
	private Cell[][] board;
	private int blackNum;
	private int whiteNum;
	/**
	*This is the constructor for board. It creates a 8x8 Cell array.
	*/
	public Board() {
		board = new Cell[8][8];
        for (int j = 0; j<8; j++) {
            for (int i =0; i<8; i++) {
                board[i][j] = new Cell(i,j,-1);
            }
        }
        blackNum=0;
        whiteNum=0;
	}
	/**
	*isCorner checks if the coordinates x and y on the board are a corner.
	*@param x is the x coordinate.
	*@param y is the y coordinate.
	*@return boolean return true if its a corner returns false if its not a corner.
	*/
	private boolean isCorner(int x, int y) {
		if ((x == 0 && y == 0) || (x == 0 && y == 7) || (x == 7 && y == 0) || (x == 7 && y == 7)) {
			return true;
		}
		return false;
	}

	/**
        * getCell returns a Cell object that represents the location, specified by
        * the input parameters x and y, on the board. The Cell object contains information
        * about whether there is a chip at the coordinate (x,y) on the board and also contains
        * the coordinates themselves.
        * @param x its the x coordinate
        * @param y its the y coordinate
        * @return Cell it return the cell at x and y
    */
	public Cell getCell(int x, int y) {
		if (isCorner(x, y)) {
			return new Cell();
		}
		if (x > 7 || y > 7 || x < 0 || y < 0) {
			return new Cell();
		}
		return board[x][y];
	}
	/**
      * setCell either places a chip or removes a chip from the location (x,y)
      * on the board. It takes in moveKind in order to maintain the invariant that there
      * are no more than 10 black or 10 white chips on the board in the case a step move
      * is being made.
      * @param int x its the x coordinate
      * @param int y its the y coordinate
      * @param int color its the color we want to place at x and y coordinates.
      * @param int moveKind a stepmove or add move
    */
	public void setCell(int x, int y, int color, int moveKind){
		if (color == 0) {
            if (moveKind == 1) {
			    blackNum++;
            }
		} else if (color == 1) {
            if (moveKind==1) {
			    whiteNum++;
            }
		} else if (color == -1) {
            if (moveKind == 1) {
			    if (board[x][y].color == 0) {
				    blackNum=blackNum-1;
			    } else if (board[x][y].color == 1){
				    whiteNum=whiteNum-1;
			    }
            }
		}
		board[x][y].color = color;
	}

    /**
        * isEmpty checks if "this" board does not have a chip on it
        * @return return true if board is empty
    */
    public boolean isEmpty() {
        for (int x = 0; x<8; x++) {
            for (int y = 0; y<8; y++) {
                if (getCell(x,y).color != -1) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
        * toString() returns a string representatation of this board
        *@return String that displays the board as a string.
    **/
    public String toString() {
        String s = "|";
        for (int y = 0; y<8; y++) {
            for (int x = 0; x<8; x++) {
                if (getCell(x,y).color != -1) {
                    s = s+" "+getCell(x,y).color+" ";
                } else {
                    s = s + "   ";
                }
            }
            s = s+"|\n|";
        }
        return s;
    }
	/**
      *isValidMove checks if a move on "this" board done by Player p is a legal move.
      *Legal moves include not placing chips in four corners of "this" board,
      *No chips in opponent goal, no chip can be placed in a square already occupied,
      *Nor can there be three chips placed next to each other.
      *@param m is the Move being validated
      *@param color is the player making the move color
      *@return true if the move is legal and false if move is illegal
    */
	public boolean isValidMove(Move m, int color) {
        if ((m.x1<0) || (m.x1>7) || (m.y1<0) || (m.y1>7)) {
            return false;
        } else if ((isCorner(m.x1, m.y1))) {
			return false;
		} else if (getCell(m.x1, m.y1).color != -1) {
			return false;
		} else if (this.numOfNeighbors(m.x1,m.y1,color, new DList()) > 1) {
            return false;
        } else if (color == 0) {
			if ((m.moveKind == 1 && blackNum >= 10) || (m.moveKind == 2 && blackNum < 10) || ((m.x1 == 0) || (m.x1 == 7))) {
				return false;
			}
		} else if (color == 1) {
			if ((m.moveKind == 1 && whiteNum >= 10) || (m.moveKind == 2 && whiteNum < 10) || ((m.y1 == 0) || (m.y1 == 7))) {
				return false;
			}
		}
		return true;
	}

    /**
        * numOfNeighbors is the number of chips that are neighbors of the same color as the chip
        * at (x,y) on the board.
        * @param int x is the x coordinate of the chip were looking at.
        * @param int y is the y coordinate of the chip were looking at.
        * @param int color is the color of the chip were looking at.
        * @param DList visited is a list of the chips that we've looked at that are neighbors.
        * @return int is the number of chips that are neighbors with the chip at (x,y).
    */
    private int numOfNeighbors(int x, int y, int color, DList visited) {
		int count = 0;
        if (color != -1) {
		  for(int i = -1; i <= 1; i++) {
            for(int j = -1; j <= 1; j++) {
				if ((!outOfBounds(i+x,j+y)) && (!isInList(getCell(i+x,j+y), visited)) && (getCell(i + x, j + y).color == color)) {
                    visited.insertBack(getCell(i+x,j+y));
                    count++;
				}
			}
		  }
          if ((visited.length() == 1) && ((((Cell) visited.head.next.item).x != x) || (((Cell) visited.head.next.item).y != y))) {
            count = count + numOfNeighbors(((Cell) visited.head.next.item).x, ((Cell) visited.head.next.item).y, color, visited);
          }
        }
		return count;
	}

    /**
        * outOfBounds checks if coordinates are not on "this" board.
        * @param x the x coor
        * @param y the y coor
        * @return true if out of bounds false if not out of bounds
    */
    private boolean outOfBounds(int x, int y) {
        if (isCorner(x,y)) {
            return true;
        } else if (x<0 || x>7 || y<0 || y>7) {
            return true;
        }
        return false;
    }

    /**
        * allMoves creates a list of all possible moves on "this" board that Player p can choose from.
        * @param color is the turn of the player
        * @return DList is the list of all valid moves that player can do on his turn on "this" board.
    */
    public DList allMoves(int color) {
    	DList moveList = new DList();
    	if ((blackNum >= 10 && color == 0) || (whiteNum >= 10 && color == 1)) {
    		DList allchips = allChips(color);
            DListNode elem = allchips.head.next;
    		while (elem != allchips.head) {
    			for(int i = 0; i <= 7; i++) {
    				for(int j = 0; j <= 7; j++) {
    					Move step = new Move(i, j, ((Cell) elem.item).x, ((Cell) elem.item).y);
    					if (isValidMove(step, color)){
    						moveList.insertBack(step);
    					}
    				}
    			}
                elem = elem.next;
    		}
    	} else {
    		for(int i = 0; i <= 7; i++) {
    			for(int j = 0; j <= 7; j++) {
    				Move add = new Move(i, j);
    				if (isValidMove(add, color)) {
    					moveList.insertBack(add);
    				}
    			}
    		}
    	}
    	return moveList;
    }
    /**
     * allChips returns a Dlist of all the coordinates of the white or black chips on this board.
     * @param color the color of the player
	 * @return a Dlist of the coordinates of all chips of a color
	*/
	public DList allChips(int color) {
		DList allchips = new DList();
		for(int i = 0; i <= 7; i++) {
    		for(int j = 0; j <= 7; j++) {
    			if (getCell(i, j).color == color) {
    				allchips.insertBack(getCell(i,j));
    			}
    		}
    	}
    	return allchips;
	}
    /**
      * isConnection determines whether two cells on "this" board are connected. They are connected orthogonally along
      * the board and if there is no chip of any color between them.
      *@param c the cell that we want to find its unblocked neigbors for.
      *@return DList is a list of all neigbors that are not blocked.
    */
    public boolean isConnection(Cell c, Cell b) {
    	int cool = c.x;
    	int cats = c.y;
        if (isOnDiagonal(c,b)) {
    	   if (c.x < b.x && c.y < b.y) {
                cool++;
                cats++;
                while (cool < b.x && cats < b.y) {
                    if (getCell(cool, cats).color != -1) {
    				    return false;
                    }
                    cool++;
                    cats++;
                }
                return true;
    	    } else if (c.x > b.x && c.y < b.y) {
                cool--;
                cats++;
    		    while (cool > b.x && cats < b.y) {
    			    if (getCell(cool, cats).color != -1) {
    				    return false;
    			    }
    			    cool--;
    			    cats++;
    		    }
                return true;
    	    } else if (c.x < b.x && c.y > b.y) {
                cool++;
                cats--;
    		    while (cool < b.x && cats > b.y) {
                    if (getCell(cool, cats).color != -1) {
    				    return false;
    			    }
    			    cool++;
    			    cats--;
    		    }
                return true;
    	    } else if (c.x > b.x && c.y > b.y) {
                cool--;
                cats--;
                while (cool > b.x) {
    			    if (getCell(cool, cats).color != -1) {
    				    return false;
    			    }
    			    cool--;
    			    cats--;
    		    }
                return true;
            }
        } else if (isOnLine(c,b)) {
            if (c.x < b.x && c.y == b.y) {
                cool++;
    		    while (cool < b.x) {
    			    if (getCell(cool, c.y).color != -1){
    				    return false;
    			    }
    			    cool++;
    		    }
                return true;
    	    } else if (c.x > b.x) {
                cool--;
    		    while (cool > b.x) {
    			    if (getCell(cool, c.y).color != -1){
    				    return false;
    			    }
    			    cool--;
    		    }
                return true;
    	    } else if (c.y < b.y) {
                cats++;
    		    while (cats < b.y) {
    			    if (getCell(c.x, cats).color != -1){
    				    return false;
    			    }
    			    cats++;
    		    }
                return true;
    	    } else if (c.y > b.y) {
                cats--;
    		    while (cats > b.x) {
    			    if (getCell(c.x, cats).color != -1){
    				    return false;
    			    }
    			    cats--;
    		    }
                return true;
    	    }
        }
    	return false;
    }

    /**
        * isOnDiagonal determines if Cell c and Cell b are on the same diagonal.
        * @param Cell c is the first cell being checked to be on a diagonal with Cell b.
        * @param Cell b is the second cell being checked if on a diagonal with Cell c.
        * @return boolean is true if Cell c and Cell b are on a diagonal.
    */
    private boolean isOnDiagonal(Cell c, Cell b) {
        int dif_y = abs(b.y - c.y);
        int dif_x = abs(b.x - c.x);
        if (dif_y==dif_x) {
            return true;
        }
        return false;
    }

    /**
        * isOnLine determines if two cells are on the same line of the board horizontally or vertically.
        * @param Cell c is the first cell being looked at and the line its on.
        * @param Cell b is the second cell being looked at and the line its on.
        * @return boolean is true if the two cells are on the same line either verticall or horizontally, or false if not.
    */
    private boolean isOnLine(Cell c, Cell b) {
        if (c.x==b.x && c.y != b.y) {
            return true;
        } else if (c.y == b.y && c.x != b.x) {
            return true;
        } else {
            return false;
        }
    }

    /**
        * abs returns the absolute value of inputed integer
        * @param int n is the number we want the absolute value of.
        * @return int is the absolute value of n.
    */
    private int abs(int n) {
        if (n<0) {
            return -1*n;
        } else {
            return n;
        }
    }

    /**
        * hasNetwork determines whether there is a network on this board for a player specified by that player's color.
        * If there is a network than that player has won, and this method returns true. If there is no network on the board for
        * that player, than hasNetwork returns false.
        * @param int color is the color of the player that we are checking a network for. The color is represented as an
        * integer where 1 is for white and 0 is for black.
        * @return boolean is the boolean value that represents whether there is a network on this board (true) or not (false)/
    **/

    public boolean hasNetwork(int color) {
        DList chips = allChips(color);
        DList path = new DList();
        DList visited = new DList();
        if (chips.length()>5) {
            DListNode point = chips.head.next;
            while (point != chips.head) {
                if (color == 0) {
                    if (((Cell) point.item).y == 0) {
                        visited.insertBack((Cell) point.item);
                        path.insertBack((Cell) point.item);
                        path = search(((Cell) point.item), chips, visited, path);
                        if (networkConditions(path, color)) {
                            return true;
                        }
                    }
                } else if (color == 1) {
                    if (((Cell) point.item).x == 0) {
                        visited.insertBack((Cell) point.item);
                        path.insertBack((Cell) point.item);
                        path = search(((Cell) point.item), chips, visited, path);
                        if (networkConditions(path, color)) {
                            return true;
                        }
                    }
                }
                point = point.next;
                path = new DList();
                visited = new DList();
            }
        }
        return false;
    }

    /**
        * search does a depth first seach from a Cell c to all other chips on the gameboard to make
        * a connection of chips that forms a network.
        * @param Cell c is the root of the path were looking to make.
        * @param DList listOfChips are all the chips of the color of the chip at Cell c on the board.
        * @param DList visited is a list of chips that have been visited already that are on listOfChips.
        * @param DList path is the DList of chips that is being formed to make a network.
        * @return DList is a DList of chips that form a network.
    */
    private DList search(Cell c, DList listOfChips, DList visited, DList path) {
        if (networkConditions(path, c.color)) {
            return path;
        } else if (!canConnect(c, listOfChips, visited)) {
            return path;
        } else {
            DListNode point = listOfChips.head.next;
            while (point != listOfChips.head) {
                if (!isInList(((Cell) point.item), visited)) {
                    if (isConnection(c, ((Cell) point.item))) {
                        visited.insertBack((Cell) point.item);
                        path.insertBack(((Cell) point.item));
                        if (networkConditions(search(((Cell) point.item), listOfChips, visited, path), c.color)) {
                            return search(((Cell) point.item), listOfChips, visited, path);
                        } else {
                            try {
                                path.back().remove();
                                visited.back().remove();
                            } catch (InvalidNodeException e) {}
                        }
                    }
                }
            point = point.next;
            }
        }
        return path;
    }

    /**
        * canConnect determines whether a Cell c can connect with any cells on the board that haven't
        * been visited.
        * @param Cell c is the cell that holds the chip we want to connect with other chips.
        * @param DList listOfChips are all the chips that are located on the board that are the same color as the chip on Cell c.
        * @param DList visited is a list of chips that have already been visited and tried to be connected.
        * @return boolean is true if Cell can connect with chips in listOfChips if those chips have not been visited.
    */
    private boolean canConnect(Cell c, DList listOfChips, DList visited) {
        DListNode temp = listOfChips.head.next;
        while (temp != listOfChips.head) {
            if (isConnection(c, (Cell) temp.item) && !isInList((Cell) temp.item, visited)) {
                return true;
            }
            temp =temp.next;
        }
        return false;
    }

    /**
        * networkConditions determines if a path of a chips of a certain color form a network under the conditions
        * of forming a network.
        * @param DList path is a list of chips that potentially form a network.
        * @param int color is the color of the chips on the path.
        * @return boolean is true if the path satisfies the conditions for a network and false if not.
    */
    private boolean networkConditions(DList path, int color) {
        if (path.length()<6) {
            return false;
        }
        if (color == 0) {
            if (((Cell) path.head.next.item).y != 0 || ((Cell) path.head.prev.item).y != 7) {
                return false;
            }
        } else if (color == 1) {
            if (((Cell) path.head.next.item).x != 0 || ((Cell) path.head.prev.item).x != 7) {
                return false;
            }
        }
        DListNode prev = path.head.next;
        DListNode curr = path.head.next.next;
        double direction = computeSlope(prev, curr);
        curr = curr.next;
        prev = prev.next;
        while (curr != path.head) {
            if (inBorder((Cell) prev.item)) {
                return false;
            } else if (((Cell) prev.item).x == ((Cell) curr.item).x) {
                if (direction == Double.MIN_VALUE) {
                    return false;
                 } else {
                    direction = Double.MIN_VALUE;
                 }
            } else if (((Cell) prev.item).y == ((Cell) curr.item).y) {
                if (direction == 0.0) {
                    return false;
                } else {
                    direction = 0.0;
                }
            } else {
                if (direction == computeSlope(prev, curr)) {
                    return false;
                } else {
                    direction = computeSlope(prev, curr);
                }
            }
            curr = curr.next;
            prev = prev.next;
        }
        return true;
    }

    /**
        * computeSlope finds the direction from one cell to another by calculating the slope from the
        * coordinates of both cells.
        * @param DListNode c is the node that contains the coordinates of one chip.
        * @param DListNode b is the node that contains the coordinates of another chip.
        * @return Double value that represents the slope from the two coordinate points.
    */
    private double computeSlope(DListNode c, DListNode b) {
        double direction;
        try {
            direction = ((double) (((Cell) b.item).y - ((Cell) c.item).y)/((double) ((Cell) b.item).x-((Cell) c.item).x));
        } catch (Exception e) {
            direction = Float.MIN_VALUE;
        }
        return direction;
    }

    /**
        *inBorder checks whether a Cell is in its own goal area.
        * @param Cell c is the cell being checked if its in a border.
        * @return boolean is true if a cell is in its own goal area and false if not.
    */
    private boolean inBorder(Cell c) {
        if (c.color == 0) {
            return (c.y==0 || c.y==7);
        } else if (c.color == 1) {
            return (c.x == 0 || c.x == 7);
        }
        return false;
    }

    /**
        *isInList checks whether Cell c is already in a list of Cells.
        * @param Cell c is the cell that is being checked if it in a list.
        * @param DList list is a list that contains Cells.
        * @return boolean is true if Cell c is in the list and false if not.
    */
    private boolean isInList(Cell c, DList list) {
        DListNode point = list.head.next;
        while (point != list.head) {
            if (c.equals(point.item)) {
                return true;
            }
            point = point.next;
        }
        return false;
    }

    /**
    *evaluate() looks at "this" board and determines the favoribility of the Move m
    *that can be made by returing a score the current board with the Move m already made on the board.
    *higher positive scores as higher likelihoods of winning and negative scores as higher likelihoods of losing.
    *@param m is the move being evaluated that will determine if that move will help the player to win or will cause him to lose.
    *@param color the color of the player choosing a move.
    *@param numOfMove is the number of moves made to get to the current board setup.
    *@return integer that determines the favoribility of the move by the current player.
    */
    public int evaluate(Move m, int color, int numOfMoves) {
        if (hasNetwork(color)) {
            return Integer.MAX_VALUE - numOfMoves;
        } else if (hasNetwork(changeColor(color))) {
            return Integer.MIN_VALUE + numOfMoves;
        } else if (numInGoal(color, 0)>1 + numOfMoves){
            if (color == 0 && m.y1 == 0) {
                return -100;
            } else if (color == 1 && m.x1 == 0) {
                return -100;
            }
        } else if (numInGoal(color,7)>1 + numOfMoves) {
            if (color == 0 && m.y1 == 7) {
                return -100;
            } else if (color == 1 && m.x1 == 7) {
                return -100;
            }
        } else if (numInGoal(color,0) == 1 + numOfMoves) {
            if (color == 0 && m.x1==3 && m.y1==0) {
                return 100;
            } else if (color == 1 && m.x1 == 0 && m.y1 == 4) {
                return 100;
            }
        }
        return (totalConnections(color) - totalConnections(changeColor(color)));
        }

    /**
    * changes the player we are concerned about
    * @param color current player
    * @return the other player
    */
    private int changeColor(int color) {
        if (color == 0) {
            return 1;
        } else {
            return 0;
        }
    }
    /**
    * totalConnections tells you how many connection a player of color int color has on "this" board.
      * @param int color the color of the player
      * @return int the number of connections on this board a player has
    */
    public int totalConnections(int color) {
        DList allchips = allChips(color);
        int count = 0;
        if (allchips.length() > 1) {
            DList seen = new DList();
            DListNode point = allchips.head.next;
            while (point != allchips.head) {
                seen.insertBack(point);
                DListNode temp = allchips.head.next;
                while (temp != allchips.head) {
                    if (!isInList((Cell) temp.item, seen)) {
                        if (isConnection((Cell) point.item, (Cell) temp.item)) {
                            count++;
                        }
                    }
                    temp = temp.next;
                }
                point = point.next;
            }
        }
        return count;
    }
    /**
    * Find the number of chips in a goal area.
    * @param color the color of the player
    * @param side one of the side of players goal
    * @return return number of chips on a side
    */
    private int numInGoal(int color, int side) {
        int count = 0;
        if (color == 0) {
            for (int x =1; x < 7; x++) {
                if (getCell(x,side).color == color) {
                    count++;
                }
            }
        } else if (color == 1) {
            for (int y =1; y <7; y++) {
                if (getCell(side,y).color == color) {
                    count++;
                }
            }
        }
        return count;
    }


}

