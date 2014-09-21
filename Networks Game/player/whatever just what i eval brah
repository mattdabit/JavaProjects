 /**
    *evaluate() looks at "this" board and determines the favoribility of the Move m that can be made by assigning each legal move an integer score based on how favorable it is with higher positive scores as higher likelihoods of winning and negative scores as higher likelihoods of losing.
    *@param m is the move being evaluated that will determine if that move will help the player to win or will cause him to lose.
    *@param color the color of the player choosing a move.
    *@return integer that determines the favoribility of the move by the current player. -9 - 9
    */
    public int evaluate(Move m, int color) {
        makeMove(m, color);
        int count = 0;
        if(hasNetwork(color)) {
            return 9;
        } else if(hasNetwork(changeColor(color))) {
            return -9;
        }
        Cell move = new Cell(m.x1, m.y1) 
        if (moveKind == ADD) {
            count += numOfConnections(move, color) + (numOfBlocked(c, color);
        } else if (moveKind == STEP) {
            Cell stepmove = new Cell(m.x2, m.y2);
            int nobm = numOfBlocked(move, color);
            int nobs = numOfBlocked(stepmove, color);     
            if (nobm > nobs) {
                count -= nobm;
            } else {
                count += nobs;
            }
        }
        undoMove(m);
        return count;
    }
    /**
    * numOfBlocked counts the number of blocks you created for your opponent.
    * @param m the move you may make.
    * @param color your color.
    * @return the number of blocks you made.
    */
    public int numOfBlocked(Cell c, int color) {
        DList oppchips = new DList();
        oppchips = allChips(changeColor(color));
        for(Cell oppchip : oppchips) {
            if (!isConnection(c, oppchip)) {
                oppchip.remove();
            }
        }
        int blocks = oppchips.size;
        return blocks - 1;
    }
    /**
    * numOfConnections counts the number of connections you created for yourself.
    * @param m the move you may make.
    * @param color your color.
    * @return the number of connections you made.
    */
    public int numOfConnections(Cell c, int color) {
        int count = 0;
        DList allchips = new DList();
        allchips = allChips(color);
        for(Cell chip : allchips) {
                if(isConnection(move, chip)) {
                    count++;
                }
            }
    }
}

