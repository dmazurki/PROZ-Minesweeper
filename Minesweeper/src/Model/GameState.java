package Model;

/**
 * Enum that holds states in which the game can be.
 * BEGINNING - player didn't make a first move yet.
 * RUNNING - game is running, time is passing
 * PAUSED - game was paused, time is not passing
 * WON - player won the game
 * LOST - player lost the game
 * @author Damian Mazurkiewicz
 *
 */
public enum GameState {
	BEGINNING, RUNNING, PAUSED, WON, LOST, BLOCKED
}
