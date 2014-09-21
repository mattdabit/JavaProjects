package player;
import board.*;
import list.*;

public class NetworkTestClass {

	// private static void test(boolean one, boolean two, int test) {
	// 	if (one == two) {
	// 		System.out.println(test+" is good");
	// 	} else {
	// 		System.out.println("fail, inspect test number "+test);
	// 	}
	// }

	// private static void listPrinter(DList list) {
	// 	DListNode point = list.head.next;
	// 	while (point != list.head) {
	// 		System.out.println(((Cell) point.item).x + ", "+((Cell) point.item).y);
	// 		point = point.next;
	// 	}
	//}

	public static void main(String[] args) {
		// MachinePlayer white = new MachinePlayer(1);
		// MachinePlayer black = new MachinePlayer(0);
		// Board board = white.getBoard();
		// //valid moves test 1-13
		// //test 1 making a step move before making an add move.
		// test(white.forceMove(new Move(1,2,3,4)), false, 1);
		// //test 2 making an add move in a corner.
		// test(white.forceMove(new Move(0,0)), false, 2);
		// test(white.forceMove(new Move(0,7)), false, 2);
		// test(white.forceMove(new Move(7,7)), false, 2);
		// test(white.forceMove(new Move(7,0)), false, 2);
		// //test 3 making an add move outside of the board.
		// test(white.forceMove(new Move(0,8)), false, 3);
		// test(white.forceMove(new Move(8,3)), false, 3);
		// //test 4 making an add move in the opponent's boundary.
		// test(white.forceMove(new Move(1,0)), false, 4);
		// test(white.forceMove(new Move(2,7)), false, 4);
		// test(black.forceMove(new Move(0,5)), false, 4);
		// test(black.forceMove(new Move(7,4)), false, 4);
		// //test 5 making an add move onto another player's chip.
		// white.forceMove(new Move(0,4));
		// white.opponentMove(new Move(3,3));
		// test(white.forceMove(new Move(3,3)), false, 5);
		// white.forceMove(new Move(4,4));
		// test(white.opponentMove(new Move(4,4)), false, 5);
		// white.opponentMove(new Move(3,0));
		// test(white.forceMove(new Move(4,4)), false, 5);
		// test(white.opponentMove(new Move(3,3)), false, 5);
		// //test 6 making an add move on a cell next to two cells containing the same color chips.
		// white.forceMove(new Move(2,6));
		// white.opponentMove(new Move(1,2));
		// test(white.forceMove(new Move(3,5)), false, 6);
		// white.forceMove(new Move(4,1));
		// test(white.opponentMove(new Move(2,2)), false, 6);
		// white.opponentMove(new Move(4,2));
		// //test 7 making an add move when all chips are on the board.
		// white.forceMove(new Move(7,4));
		// white.opponentMove(new Move(4,7));
		// white.forceMove(new Move(6,2));
		// white.opponentMove(new Move(4,5));
		// white.forceMove(new Move(6,6));
		// System.out.println(white.opponentMove(new Move(5,4)));
		// white.forceMove(new Move(2,2));
		// white.opponentMove(new Move(1,5));
		// white.forceMove(new Move(4,6));
		// white.opponentMove(new Move(2,7));
		// white.forceMove(new Move(2,4));
		// white.opponentMove(new Move(2,5));
		// listPrinter(board.allChips(0));
		// System.out.println("--------");
		// //listPrinter(board.allChips(1));
		// test(white.forceMove(new Move(7,2)), false, 7);
		// test(white.opponentMove(new Move(6,0)), false, 7);
		// //test 8 making a step move onto a cell holding a chip.
		// test(white.forceMove(new Move(4,2,4,1)), false, 8);
		// test(white.forceMove(new Move(7,4,6,2)), false, 8);
		// test(white.opponentMove(new Move(1,5,1,2)), false, 8);
		// test(white.opponentMove(new Move(2,4,2,5)), false, 8);
		// //test 9 making a step move to a cell with 2 or more neighbors of the same color.
		// test(white.forceMove(new Move(6,5,6,2)), false, 9);
		// test(white.opponentMove(new Move(3,5,3,3)), false, 9);
		// //test 10 making sure step works.
		// test(white.forceMove(new Move(6,1,6,2)), true, 10);
		// test(white.opponentMove(new Move(6,2,5,4)), true, 10);
		// //test 11 making a step move into opponent's boundary.
		// test(white.forceMove(new Move(4,0,4,1)), false, 11);
		// test(white.opponentMove(new Move(0,3,3,3)), false, 11);
		// //test 12 making a step move into a corner.
		// test(white.forceMove(new Move(0,0,4,1)), false, 12);
		// test(white.opponentMove(new Move(7,0,4,2)), false, 12);
		// //test 13 making a step move out of bounds.
		// test(white.forceMove(new Move(8,0,4,1)), false, 13);
		// test(white.opponentMove(new Move(8,-1,1,5)), false, 13);
		// //isConnection tests 14
		// //test 14
		// test(board.isConnection(board.getCell(4,1), board.getCell(6,1)), true, 14);
		// test(board.isConnection(board.getCell(2,5), board.getCell(4,5)), true, 14);
		// test(board.isConnection(board.getCell(4,4), board.getCell(4,6)), false, 14);
		// test(board.isConnection(board.getCell(1,2), board.getCell(4,2)), false, 14);
		// test(board.isConnection(board.getCell(1,5), board.getCell(2,5)), true, 14);
		// test(board.isConnection(board.getCell(2,6), board.getCell(4,1)), false, 14);

		// //allChips tests 15
		// test(board.allChips(1).length()==10, true, 15);
		// test(board.allChips(0).length()==10, true, 15);
		// DList allchips = board.allChips(0);
		// //listPrinter(allchips);
		// //isInList tests 16
		// test(board.isInList(board.getCell(6,2), allchips), true, 16);
		// test(board.isInList(board.getCell(4,1), allchips), false, 16);
		// test(board.isInList(board.getCell(1,1), allchips), false, 16);
		// DList allchips2 = board.allChips(1);
		// test(board.isInList(board.getCell(2,6), allchips2), true, 16);
		// test(board.isInList(board.getCell(2,5), allchips2), false, 16);
		// test(board.isInList(board.getCell(3,5), allchips2), false, 16);
		// //networkConditions test 17
		// DList path = new DList();
		// path.insertBack(new Cell(0,4,1));
		// path.insertBack(new Cell(2,2,1));
		// path.insertBack(new Cell(2,4,1));
		// path.insertBack(new Cell(4,4,1));
		// path.insertBack(new Cell(2,6,1));
		// path.insertBack(new Cell(4,6,1));
		// path.insertBack(new Cell(6,6,1));
		// path.insertBack(new Cell(6,1,1));
		// path.insertBack(new Cell(4,1,1));
		// path.insertBack(new Cell(7,4,1));
		// //listPrinter(path);
		// test(board.networkConditions(path, 1), false, 17);
		// DList path2 = new DList();
		// path2.insertBack(new Cell(3,0,0));
		// path2.insertBack(new Cell(1,2,0));
		// path2.insertBack(new Cell(1,5,0));
		// path2.insertBack(new Cell(2,5,0));
		// path2.insertBack(new Cell(4,7,0));
		// //listPrinter(path2);
		// test(board.networkConditions(path2, 0), false, 17);
		// //test 18 hasNetwork
		// test(board.hasNetwork(1), true, 18);
		// test(board.hasNetwork(0), false, 18);
		// white.opponentMove(new Move(1,3,1,5));
		// test(board.hasNetwork(1), true, 18);
		// test(board.hasNetwork(0), false, 18);
		// white.forceMove(new Move(1,6,2,4));
		// white.opponentMove(new Move(1,5,2,5));
		// test(board.hasNetwork(1), true, 18);
		// test(board.hasNetwork(0), true, 18);
		// //test 19 evaluation
		// //test(board.evaluate(new Move(2,4,1,6), 1,0)==Integer.MAX_VALUE, true, 19);
		// //test 20 chooseMove()
		// listPrinter(board.allChips(0));
		// System.out.println("--------");
		// //listPrinter(board.allChips(1));
		// System.out.println(board.numOfNeighbors(1,1,0,new DList()));
		// test(white.opponentMove(new Move(1,1,4,5)),false,20);

		// MachinePlayer test = new MachinePlayer(1);
		// Board b = test.getBoard();
		// test.opponentMove(new Move(1,1));
		// test.opponentMove(new Move(2,1));
		// test.opponentMove(new Move(4,1));
		// test.opponentMove(new Move(5,1));
		// test.opponentMove(new Move(1,6));
		// test.opponentMove(new Move(2,6));
		// test.opponentMove(new Move(5,5));
		// test.forceMove(new Move(0,2));
		// test.forceMove(new Move(1,3));
		// test.forceMove(new Move(3,2));
		// test.forceMove(new Move(3,5));
		// test.forceMove(new Move(5,3));
		// test.forceMove(new Move(6,1));
		// test.forceMove(new Move(6,5));
		// //test.opponentMove(new Move(3,1));
		// //System.out.println(b.hasNetwork(0));
		// System.out.println("Test7?");
		// test.chooseMove();

		// MachinePlayer test2 = new MachinePlayer(1);
		// Board b1 = test2.getBoard();
		// test2.opponentMove(new Move(1,0));
		// test2.opponentMove(new Move(1,6));
		// test2.opponentMove(new Move(3,4));
		// test2.opponentMove(new Move(6,4));
		// test2.opponentMove(new Move(6,7));
		// test2.forceMove(new Move(0,1));
		// test2.forceMove(new Move(2,2));
		// test2.forceMove(new Move(2,3));
		// test2.forceMove(new Move(6,1));
		// test2.forceMove(new Move(7,4));
		// System.out.println("Test5");
		// test2.chooseMove();

		// MachinePlayer test3 = new MachinePlayer(1);
		// Board b2 = test3.getBoard();
		// test3.opponentMove(new Move(1,0));
		// test3.opponentMove(new Move(1,2));
		// test3.opponentMove(new Move(6,2));
		// test3.opponentMove(new Move(6,7));
		// test3.forceMove(new Move(0,2));
		// test3.forceMove(new Move(1,6));
		// test3.forceMove(new Move(4,3));
		// test3.forceMove(new Move(4,6));
		// //test3.forceMove(new Move(1,3));
		// //test3.opponentMove(new Move(1,4));
		// //test3.forceMove(new Move(7,3));
		// System.out.println(b2);
		// //System.out.println(b2.getCell(1,6).color);
		// //System.out.println(b.isConnection(b2.getCell(4,3),b2.getCell(1,6)));
		// //System.out.println(b2.hasNetwork(1));
		// //System.out.println("Test6");
		// //System.out.println(b2);
		// test3.chooseMove();

	}
}