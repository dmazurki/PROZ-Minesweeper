package Model;

/**
 * Enum that holds states in which the game can be. 
 * @author Damian Mazurkiewicz
 */
public enum GameState {
	/**Player didn't make a first move yet.*/
	BEGINNING,
	/**Game is running, time is passing.*/
	RUNNING,
	/**Game was paused, time is not passing.*/
	PAUSED,
	/**Player won the game.*/
	WON, 
	/**Player lost the game.*/
	LOST, 
	/**Game is blocked.*/
	BLOCKED
}
