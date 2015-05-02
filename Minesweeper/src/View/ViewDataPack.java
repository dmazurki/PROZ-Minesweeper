package View;

public class ViewDataPack {
	public enum EventType
	{
		LEFT_MOUSE_BUTTON_ON_BOARD, NEW_GAME_MENU_ITEM
	}
	private EventType eventType_;
	private int rowNr_;
	private int columnNr_;
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
