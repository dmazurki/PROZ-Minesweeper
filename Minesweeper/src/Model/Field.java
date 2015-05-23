package Model;

/**
 * This class represents field on the game board. It allows diffrent operations and initialisation of field.
 * @author Damian Mazurkiewicz
 */
public class Field
{
	/**True if the field is mine, false otherwise.*/
	private boolean isMine_; 
	/**How this field should be represented.*/
	private FieldOutlook outlook_;
	
	/**By default, field is not a mine and is covered.*/
	public Field()
	{
		isMine_=false;
		outlook_ = FieldOutlook.COVERED;
	}
	
	/**
	 * Determine how the field should be represented.
	 * @param arg new field outlook.
	 */
	public void setOutlook(FieldOutlook arg) 
	{
		outlook_ = arg; 
	}
	
	/**@return How this field should be represented. */
	public FieldOutlook getOutlook() 
	{ 
		return outlook_; 
	}
	
	/** @return True if field was already revealed, false if it is still covered.*/
	public boolean isRevealed()
	{
		return (outlook_!=FieldOutlook.COVERED)&&(outlook_!=FieldOutlook.FLAGGED);
	}
	
	/** @return True if field is mine, false otherwise.*/
	public boolean isMine()
	{
		return isMine_;
	}
	
	/** @return True if field is flagged, false otherwise.*/
	public boolean isFlagged() 
	{
		return outlook_==FieldOutlook.FLAGGED;
	}
	
	/**After calling this method, field becomes mine.*/
	public void putMine()
	{
		isMine_ = true;
	}
    
	/**After calling this method, field will not be mine..*/
	public void disarm()
	{
		isMine_ = false;
	}
}
