package Model;

/**
 * This class provides convenient interface for game board. 
 * It sets the board, allows making game moves and checking board state.
 * @author Damian Mazurkiewicz
 *
 */
public class Board {
	
	/**How many mines there are on the game board.*/
	private int mines_;
	/**True if first field is still not uncovered, false otherwise.*/
	private boolean beginning_;
	/**How many fields did the player reveal.*/
	private int revealedFields_;
	/**How many mines did the player reveal. (Obviously maximum value is 1).*/
	private int revealedMines_;
	/**How many fields did the player flag.*/ 
	private int flaggedFields_;
	/**How many mines did the player flag.*/
	private int flaggedMines_;
	/**Matrix of the fields on the game board.*/
	private FieldMatrix fields_;
	
	/**
	 * Creates new game board.
	 * @param width 	 how many fields horizontally.
	 * @param height 	 how many fields vertically.
	 * @param mines  	 how many fields should be on the board.
	 */
	public Board(int width, int height, int mines)
	{
		fields_ = new FieldMatrix(width,height);
		mines_ = mines;
		beginning_ = true;
		revealedFields_ = revealedMines_ = flaggedFields_ = flaggedMines_ = 0;
	}
	
	/** @return Two dimensional table of states of all fields on the board.*/
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
	
	/**Get field from game board.
	 * @param x  column of the field.
	 * @param y  row of the field.
	 * @return Field that is on given coordinates.
	 */
	public Field getField(int x, int y)
	{
		return fields_.getField(x, y);
	}
	
	/**
	 * Reveal field on given coordinates. If it is first field being revealed the mines are put on the
	 * game board randomly after revealig that fields. It provides certainty that player will not loose
	 * with his first move.
	 * @param x culumn with field we want to reveal.
	 * @param y row with field we want to reveal.
	 */
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
	
	/**
	 * Put a flag on field on given coordinates. It affects only visual representation of the field.
	 * @param x culumn with field we want to flag.
	 * @param y row with field we want to flag.
	 */
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
	
	/**
	 * Remove flag from field on given coordinates. It affects only visual representation of the field.
	 * @param x culumn with field we want to unflag.
	 * @param y row with field we want to unflag.
	 */
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
	 * Find out how to represent field visually.
	 * @param x culumn with field.
	 * @param y row with field.
	 * @return Outlook of the fields at given coordinates.
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
	
	/**Reveal all fields on game board.*/
	void revealAll()
	{
		for(int x= 0; x<fields_.getWidth();++x)
			for(int y= 0; y<fields_.getHeight();++y)
				revealField(x,y);
	}
	
	/**@return How many fields did player reveal.*/
	public int getRevealedFields_() 
	{
		return revealedFields_;
	}
	
	/**@return How many mines did player reveal.*/
	public int getRevealedMines_()
	{
		return revealedMines_; 
	}
	
	/**@return How many fields did player flag.*/
	public int getFlaggedFields_()
	{
		return flaggedFields_;
	}
	
	/**@return How many mines did player flag.*/
	public int getFlaggedMines_()
	{
		return flaggedMines_;
	}
}
