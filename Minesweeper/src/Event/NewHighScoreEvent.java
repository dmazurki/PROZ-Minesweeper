package Event;
/**Player is inserting new registration to HighScores table.*/
public class NewHighScoreEvent extends Event{
	/**Name of the player.*/
	final public String playerName_;
	
	/**
	 * Create NewHighScoreEvent.
	 * @param s name of the player.
	 */
	public  NewHighScoreEvent(String s)
	{
		playerName_ = s;
	}
	

}
