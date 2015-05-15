package Event;

public class MenuEvent extends Event{
	public enum Action{
		PAUSE_MENU_ITEM_CLICKED,
		NEW_GAME_MENU_ITEM_CLICKED,
		HIGHSCORES_MENU_ITEM_CLICKED,
		BEGINNER_MENU_ITEM_CLICKED,
		ADVANCED_MENU_ITEM_CLICKED,
		EXPERT_MENU_ITEM_CLICKED,
		CUSTOM_MENU_ITEM_CLICKED,
		CUSTOM_OPTIONS_CHOSEN,
		HINT_MENU_ITEM_CLICKED,
		EXIT_MENU_ITEM_CLICKED,
	}
	
	final public Action action_;
	final public int rows_;
	final public int columns_;
	final public int mines_;
	
	public MenuEvent(Action action)
	{
		action_ = action; 
		rows_ = columns_ = mines_ = 0;
	}
	
	public MenuEvent(Action action, int columns, int rows, int mines)
	{
		action_ = action;
		columns_ = columns;
		rows_= rows;
		mines_ = mines;
	}
	
}
