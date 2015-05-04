package View.Event;

public class MenuEvent {
	public enum Action{
		PAUSE_MENU_ITEM_CLICKED,
		NEW_GAME_MENU_ITEM_CLICKED,
		HIGHSCORED_MENU_ITEM,
		BEGINNER_MENU_ITEM_CLICKED,
		ADVANCED_MENU_ITEM_CLICKED,
		EXPERT_MENU_ITEM_CLICKED,
		CUSTOM_MENU_ITEM_CLICKED,
		HINT_MENU_ITEM_CLICKED,
	}
	
	final public Action action_;
	
	public MenuEvent(Action action)
	{
		action_ = action;
	}
	
}
