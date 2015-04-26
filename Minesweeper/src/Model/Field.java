package Model;

public class Field {
	
	public enum State
	{
		COVERED, REVEALED;
	}
	
	private State state_; 
	private boolean isMine_;

	
	public Field()
	{
		state_ = State.COVERED;
		isMine_ = false;
		
	}
	
	public void reveal(){state_ = State.REVEALED;}
	
	public State getState(){return state_;}
	
	public boolean isMine(){return isMine_;}
	
	void putMine(){isMine_ = true;}
	
	void disarm(){isMine_ = false;}
	

}
