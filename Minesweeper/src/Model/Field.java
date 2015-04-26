package Model;

/**
 * This class represents field on the game board. It allows diffrent operations and initialisation of field.
 * @author Damian
 *
 */

public class Field
{
	private boolean isMine_;
	private boolean isRevealed_;
	
	public Field(){isRevealed_=isMine_=false;}
	public void reveal(){isRevealed_ = true;}
	public boolean isRevealed(){return isRevealed_;}
	public boolean isMine(){return isMine_;}
	void putMine(){isMine_ = true;}
	void disarm(){isMine_ = false;}
}
