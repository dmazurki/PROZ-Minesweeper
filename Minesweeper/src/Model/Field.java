package Model;

/**
 * This class represents field on the game board. It allows diffrent operations and initialisation of field.
 * @author Damian
 */
public class Field
{
	private boolean isMine_;
	private FieldOutlook outlook_;
	
	public Field(){isMine_=false; outlook_ = FieldOutlook.COVERED;}
	public void setOutlook(FieldOutlook arg) {outlook_ = arg; }
	public FieldOutlook getOutlook() { return outlook_; }
	public boolean isRevealed(){return (outlook_!=FieldOutlook.COVERED)&&(outlook_!=FieldOutlook.FLAGGED);}
	public boolean isMine(){return isMine_;}
	public boolean isFlagged() {return outlook_==FieldOutlook.FLAGGED;}
	void putMine(){isMine_ = true;}
	void disarm(){isMine_ = false;}
}
