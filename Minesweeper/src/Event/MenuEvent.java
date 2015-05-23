package Event;
/**Event from game menu.*/
public class MenuEvent extends Event{
	/**What action was performed on menu.*/
	public enum Action{
		/**Player clicked "pause" menu item.*/
		PAUSE_MENU_ITEM_CLICKED,
		/**Player clicked "new game" menu item. */
		NEW_GAME_MENU_ITEM_CLICKED,
		/**Player clicked "HighScores" menu item. */
		HIGHSCORES_MENU_ITEM_CLICKED,
		/**Player chose beginner game mode.*/
		BEGINNER_MENU_ITEM_CLICKED,
		/**Player chose advanced game mode.*/
		ADVANCED_MENU_ITEM_CLICKED,
		/**Player chose expert game mode.*/
		EXPERT_MENU_ITEM_CLICKED,
		/**Player chose custom game mode.*/
		CUSTOM_MENU_ITEM_CLICKED,
		/**Player chose his custom parameters of the board in the "custom" dialog window.*/ 
		CUSTOM_OPTIONS_CHOSEN,
		/**Player clicked "hints enabled" menu item.*/
		HINT_MENU_ITEM_CLICKED,
		/**Player cliced "exit" menu item. */
		EXIT_MENU_ITEM_CLICKED,
	}
	
	/**What happened.*/
	final public Action action_;
	/**
	 * Create MenuEvent.
	 * @param action Which menu action was performed. 
	 */
	public MenuEvent(Action action)
	{
		action_ = action; 
	}
}
