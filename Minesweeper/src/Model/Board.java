package Model;

/**
 * This class provides convenient interface for game board. 
 * It sets the board, allows making game moves and checking board state.
 * @author Damian Mazurkiewicz
 *
 */
public class Board {
	

	private int mines;
	
	FieldMatrix fields;
	
	
	public Board(int width, int height, int mines)
	{
		fields = new FieldMatrix(width,height);
		this.mines = mines;
	}
	
	public void resetBoard()
	{
		
	}
	
	public void revealField(int x, int y)
	{
		
	}

}
