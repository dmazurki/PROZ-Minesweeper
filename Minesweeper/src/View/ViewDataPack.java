package View;

public class ViewDataPack {
	public enum EventType
	{
		REVEAL_FIELD,
		FLAG_FIELD,UNFLAG_FIELD,
		NEW_GAME_MENU_ITEM,
		PAUSE_GAME_MENU_ITEM,
		MODE_SET,
		CHEATING_ACTIVATED, CHEATING_DEACTIVATED,
		HIGH_SCORES_REQUEST,
	}
	public EventType eventType_;
	public int rowNr_;
	public int columnNr_;
	public int mines_;
	
	public ViewDataPack(EventType e)
	{
		eventType_ = e;
	}
	public ViewDataPack(EventType e, int r, int c)
	{
		eventType_ = e; rowNr_ = r; columnNr_ = c;
	}
	public EventType getEventType() {return eventType_;}
	
	public int getRow() {return rowNr_;}
	public int getColumn() {return columnNr_;}
	
	
	

}
