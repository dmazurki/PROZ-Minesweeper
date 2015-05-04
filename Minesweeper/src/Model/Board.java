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
	private int revealedFields_;
	private int revealedMines_;
	private int flaggedFields_;
	private int flaggedMines_;
	
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
		revealedFields_ = revealedMines_ = flaggedFields_ = flaggedMines_ = 0;
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
	
	public boolean endGame(){ 
		if ( revealedMines_!=0)
			return true;
		return false;
	}
	
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
			revealedFields_++;
			if (beginning_ == true)
			{
				field.setOutlook(FieldOutlook.ZERO_ADJACENT);
				fields_.throwMines(mines_);
				beginning_ = false;
			}
			
			if(field.isMine())
			{
				revealedMines_++;
				field.setOutlook(FieldOutlook.MINE);
			}
			
			if(field.getOutlook() == FieldOutlook.FLAGGED)
			{
				flaggedFields_--;
				if(field.isMine()) { flaggedMines_--;}
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
	
	public void flagField(int x, int y)
	{
		Field field = fields_.getField(x, y);
		if(field.getOutlook() == FieldOutlook.COVERED)
		{
			flaggedFields_++;
			field.setOutlook(FieldOutlook.FLAGGED);
			if(field.isMine()) flaggedMines_++;
		}
	}
	
	public void unflagField(int x, int y)
	{
		Field field = fields_.getField(x, y);
		if(field.getOutlook() == FieldOutlook.FLAGGED)
		{
			flaggedFields_--;
			field.setOutlook(FieldOutlook.COVERED);
			if(field.isMine()) flaggedMines_--;
		}
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public FieldOutlook getOutlook(int x, int y)
	{
		Field field = fields_.getField(x, y);
		if(field.isMine()) return FieldOutlook.MINE;
		int adjacent = fields_.adjacentMines(x, y);
		switch(adjacent)
		{
			case 0: return (FieldOutlook.ZERO_ADJACENT); 
			case 1: return (FieldOutlook.ONE_ADJACENT); 
			case 2: return (FieldOutlook.TWO_ADJACENT); 
			case 3: return (FieldOutlook.THREE_ADJACENT); 
			case 4: return (FieldOutlook.FOUR_ADJACENT); 
			case 5: return (FieldOutlook.FIVE_ADJACENT); 
			case 6: return (FieldOutlook.SIX_ADJACENT); 
			case 7: return (FieldOutlook.SEVEN_ADJACENT);
			default : return (FieldOutlook.EIGHT_ADJACENT);
		}
	}
	
	/**
	 * 
	 */
	void revealAll()
	{
		for(int x= 0; x<fields_.getWidth();++x)
			for(int y= 0; y<fields_.getHeight();++y)
				revealField(x,y);
	}
	
	public int getRevealedFields_() {return revealedFields_; }
	public int getRevealedMines_() {return revealedMines_; }
	public int getFlaggedFields_() {return flaggedFields_;}
	public int getFlaggedMines_() {return flaggedMines_;}
}
