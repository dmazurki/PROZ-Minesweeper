package Event;

public class NewHighScoreEvent extends Event{
	final public String playerName_;
	public  NewHighScoreEvent(String s)
	{
		playerName_ = s;
	}
	

}
