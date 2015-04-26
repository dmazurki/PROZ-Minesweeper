package Model;

/**
 * This class provides convenient interface for game board. 
 * It sets the board, allows making game moves and checking board state.
 * @author Damian Mazurkiewicz
 *
 */
public class Board {
	

	private int mines_;
	private boolean beginning_;
	boolean endGame_;
	
	FieldMatrix fields_;
	
	
	public Board(int width, int height, int mines)
	{
		fields_ = new FieldMatrix(width,height);
		mines_ = mines;
		beginning_ = true;
		endGame_ = false;
	}
	
	public void resetBoard()
	{
		
	}
	
	public boolean endGame(){ return endGame_;}
	
	public Field getField(int x, int y)
	{
		return fields_.getField(x, y);
	}
	
	public void revealField(int x, int y)
	{
		if(fields_.correctCoordinates(x, y)==false)
			return;
		
		Field field = fields_.getField(x, y);
		if(field.getState()==Field.State.COVERED)
		{
			field.reveal();
			
			if(field.isMine())
				endGame_ = true;
			
			if (beginning_ == true)
			{
				fields_.throwMines(mines_);
				beginning_ = false;
			}
			
			int adjacent = fields_.adjacentMines(x, y);
			if(adjacent == 0)
			{
				for(int xx = x-1; xx <= x+1; ++xx)
					for(int yy = y-1; yy<=y+1; ++yy)
					{
						revealField(xx,yy);
					}
			}
		}
	}
	
	
	void revealAll()
	{
		for(int x= 0; x<fields_.getWidth();++x)
			for(int y= 0; y<fields_.getHeight();++y)
				revealField(x,y);
	}
	public void drawInConsole()
	{
		fields_.drawInConsole();
	}
}
