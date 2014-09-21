/* MachinePlayer.java */

package player;
import list.*;
import board.*;
/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {
  private int color;
  private int depth;
  private Board board;
  public static final int WHITE = 1;
  public static final int BLACK = 0;
  // Creates a machine player with the given color.  Color is either 0 (black)
  // or 1 (white).  (White has the first move.)
  public MachinePlayer(int color) {
    board = new Board();
    this.color = color;
    depth = 2;
    if (color == BLACK) {
      myName = "black";
    } else if (color == WHITE) {
      myName ="white";
    }
  }

  // Creates a machine player with the given color and search depth.  Color is
  // either 0 (black) or 1 (white).  (White has the first move.)
  public MachinePlayer(int color, int searchDepth) {
    board = new Board();
    this.color = color;
    if (searchDepth<1) {
      depth = 3;
    } else {
      depth = searchDepth;
    }
    if (color == BLACK) {
      myName = "black";
    } else if (color ==  WHITE) {
      myName = "white";
    }
  }

  // Returns a new move by "this" player.  Internally records the move (updates
  // the internal game board) as a move by "this" player.
  public Move chooseMove() {
    Best best = chooseBestMove(board, Integer.MIN_VALUE, Integer.MAX_VALUE, this.depth, this.color);
    return best.move;
  }

  // If the Move m is legal, records the move as a move by the opponent
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method allows your opponents to inform you of their moves.
  public boolean opponentMove(Move m) {
    if (board.isValidMove(m, changeColor(this.color))) {
      makeMove(m, changeColor(this.color));
      return true;
    }
    return false;
  }

  // If the Move m is legal, records the move as a move by "this" player
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method is used to help set up "Network problems" for your
  // player to solve.
  public boolean forceMove(Move m) {
    if (board.isValidMove(m, color)) {
      makeMove(m, color);
      return true;
    }
    return false;
  }

  /**
    * makeMove makes a move m on "this" player's board according to the color of the
    * player that is making the move
    * @param Move m is the move to be made on the board.
    * @param int color is the color of the chip to be placed or removed from the board.
    * @return void adjusts the internal game board.
  */
  private void makeMove(Move m, int color) {
    if (m.moveKind == 1) {
      board.setCell(m.x1, m.y1, color, m.moveKind);
    } else if (m.moveKind == 2) {
      board.setCell(m.x1, m.y1, color, m.moveKind);
      board.setCell(m.x2, m.y2, -1, m.moveKind);
    }
  }

  /**
    * undoMove undoes the Move m that was made on "this" player's board.
  */
  private void undoMove(Move m, int color) {
    if (m.moveKind == 1) {
      board.setCell(m.x1, m.y1, -1, m.moveKind);
    } else if (m.moveKind == 2) {
      board.setCell(m.x1, m.y1, -1, m.moveKind);
      board.setCell(m.x2, m.y2, color, m.moveKind);
    }
  }

  /**
    * Creates a machine player with the given color and search depth.  Color is
    * either 0 (black) or 1 (white).  White has the first move.
  **/
  public Board getBoard() {
    return board;
  }

  /**
    * chooseBestMove returns a Best object that contains the best possible move that can be made on "this" player's
    * board and also makes the move. It takes in a Board b to make a move on, an alpha value that
    * represents the best possible score that can be achieved on the board by "this" player's move,
    * a beta value that represents the best possible score for the opponent, a searchDepth, and the color of
    * the player we want to make a move for.
    * @param Board b is "this" player's board.
    * @param int alpha is the best possible score that can be made for a move on the board b.
    * @param int beta is the best possible score for the opponent for a move made on the board b.
    * @param int searchDepth is how far the player wants to look ahead due to the ramification of his move.
    * @param int color is the color of the player we want ot choose the best move for.
    * @return Best is a Best object that contains the best move to be chosen on the board
  */
  private Best chooseBestMove(Board b, int alpha, int beta, int searchDepth, int color) {
    DList movesList = b.allMoves(color);
    DListNode point = movesList.head.next;
    Best bestMove = new Best();
    Best reply;
    bestMove.move = (Move) point.item;
    if (color == this.color) {
      bestMove.score = alpha;
    } else {
      bestMove.score = beta;
    }
    while (point != movesList.head) {
      makeMove((Move) point.item, color);
      if (searchDepth < 2) {
        int moveScore = b.evaluate((Move) point.item, this.color, (this.depth-searchDepth)/2);
        undoMove((Move) point.item, color);
        if ((color == this.color) && (moveScore>alpha)) {
          bestMove.move = (Move) point.item;
          bestMove.score = moveScore;
          alpha = bestMove.score;
        } else if ((color != this.color) && (moveScore<beta)){
          bestMove.move = (Move) point.item;
          bestMove.score = moveScore;
          beta = bestMove.score;
        }
      } else {
        if (b.hasNetwork(color)) {
          bestMove.move = (Move) point.item;
          bestMove.score = b.evaluate((Move) point.item, this.color, this.depth-searchDepth+1);
          if (color == this.color) {
            alpha = bestMove.score;
          } else if (color != this.color) {
            beta = bestMove.score;
          }
        }
        reply = chooseBestMove(b, alpha, beta, searchDepth-1, changeColor(color));
        undoMove(reply.move, changeColor(color));
        undoMove((Move) point.item, color);
        if ((color == this.color) && (bestMove.score<reply.score)) {
          bestMove.move = (Move) point.item;
          bestMove.score = reply.score;
          alpha = reply.score;
        } else if ((color != this.color) && (reply.score < bestMove.score)) {
          bestMove.move = (Move) point.item;
          bestMove.score = reply.score;
          beta = reply.score;
        }
        if (alpha >= beta) {
          makeMove(bestMove.move, color);
          return bestMove;
        }
      }
      point = point.next;
    }
    makeMove(bestMove.move, color);
    return bestMove;
  }

  private int changeColor(int color) {
    if (color == WHITE) {
      return BLACK;
    } else if (color == BLACK) {
      return WHITE;
    } else {
      return -1;
    }
  }

}
