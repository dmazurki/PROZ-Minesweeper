package Model;

/**
 * Objects of this class hold informations about current state of a game. They are sent to Controller
 * and then to View, which draws the board according to the data in this object. 
 * @author Damian Mazurkiewicz
 */
public class ModelDataPack {
	/**Time passed since starting the game.*/
	final public int time_;
	/**How many flags left for player to mark mines.*/
	final public int flags_;
	/**States of all fields on the game board.*/
	final public FieldOutlook[][] fields_;
	/**State of a game.*/
	final public GameState gameState_;
	/**Field represents hint sent for player, it is the state of the field.
	 * Special values: COVERED - no hint and player didn't use hints before.
	 * 				   FLAGGED - no hint and player used hints before.
	 */
	final public FieldOutlook hint_;
	
	/**Constructor
	 * @param time Time passed since starting the game.
	 * @param fields How many flags left for player to mark mines.
	 * @param gameState States of all fields on the game board.
	 * @param hint State of a game.
	 * @param flags Hint to send for player, it is the state of the field.
	 */
	public ModelDataPack(int time, FieldOutlook[][] fields,GameState gameState, FieldOutlook hint, int flags)
	{
		time_=time;
		fields_=fields;
		gameState_ = gameState;
		hint_ = hint;
		flags_ = flags;
	}

}
