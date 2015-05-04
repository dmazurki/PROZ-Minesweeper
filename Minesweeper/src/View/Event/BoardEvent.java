package View.Event;

public class BoardEvent {
	
		final public int row_;
		final public int column_;
		final public Action action_;
		public enum Action {LEFT_MOUSE_BUTTON_PRESSED, RIGHT_MOUSE_BUTTON_PRESSED, H_KEY_PRESSED}
		
		public BoardEvent(int row, int column, Action action)
		{
			row_ = row;
			column_ = column;
			action_ = action;
		}
}
