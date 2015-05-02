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
	
	/**
	 * 
	 * @param width - how many fields horizontally
	 * @param height - how many fields vertically
	 * @param mines - how many fields should be on the board
	 */
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
	
	public FieldOutlook[][] getFields()
	{
		
		FieldOutlook[][] fields = new FieldOutlook[fields_.getHeight()][];
		
		for(int i = 0; i< fields_.getHeight(); ++i)
		{
			fields[i] = new FieldOutlook[fields_.getWidth()];
		
			for(int j = 0; j<fields_.getWidth(); ++j)
			{
				Field field = fields_.getField(j,i);
				fields[i][j] = field.getOutlook();
			}
		}
		return fields;
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
		if(!field.isRevealed())
		{
			field.reveal();
			
			if (beginning_ == true)
			{
				fields_.throwMines(mines_);
				beginning_ = false;
			}
			
			if(field.isMine())
			{
				endGame_ = true;
				field.setOutlook(FieldOutlook.MINE);
			}
			
			int adjacent = fields_.adjacentMines(x, y);
			if(!field.isMine())
			{
			switch(adjacent)
			{
			case 0: field.setOutlook(FieldOutlook.ZERO_ADJACENT); break;
			case 1: field.setOutlook(FieldOutlook.ONE_ADJACENT); break;
			case 2: field.setOutlook(FieldOutlook.TWO_ADJACENT); break;
			case 3: field.setOutlook(FieldOutlook.THREE_ADJACENT); break;
			case 4: field.setOutlook(FieldOutlook.FOUR_ADJACENT); break;
			case 5: field.setOutlook(FieldOutlook.FIVE_ADJACENT); break;
			case 6: field.setOutlook(FieldOutlook.SIX_ADJACENT); break;
			case 7: field.setOutlook(FieldOutlook.SEVEN_ADJACENT); break;
			case 8: field.setOutlook(FieldOutlook.EIGHT_ADJACENT); break;
			}
			}
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
