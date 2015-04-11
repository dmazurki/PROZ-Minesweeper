package Model;

public class Field {
	
	public enum State
	{
		COVERED, REVEALED;
	}
	
	private State state; 
	private boolean isMine;

	
	public Field()
	{
		state = State.COVERED;
		isMine = false;
		
	}
	
	public void reveal()
	{
		state = State.REVEALED;
	}
	
	public State getState()
	{
		return state;
	}
	
	public boolean isMine()
	{
		return isMine;
	}
	

}
