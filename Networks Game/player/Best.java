package player;
import board.*;

public class Best {
	protected int score;
	protected Move move;

	/**
		* Creates a best with score set to zero and move set to null.
	*/
	public Best() {
		score = 0;
		move = null;
	}
}