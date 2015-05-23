package Event;
/**Event that should be put in event queue when player chose custom game settings.*/
public class CustomOptionsChosenEvent extends Event {
	/**Number of columns chosen in "custom" dialog.*/
	final public int columns_;
	/**Number of rows chosen in "custom" dialog.*/
	final public int rows_;
	/**Number of mines chosen in "custom" dialog.*/
	final public int mines_;
	
	/**
	 * Create new CustomOptionsChosenEvent.
	 * @param columns Number of columns chosen in "custom" dialog.
	 * @param rows Number of rows chosen in "custom" dialog.
	 * @param mines Number of mines chosen in "custom" dialog.
	 */
	public CustomOptionsChosenEvent(int columns, int rows, int mines)
	{
		columns_ = columns;
		rows_ = rows;
		mines_ = mines;
	}
}
