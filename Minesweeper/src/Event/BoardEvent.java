package Event;

/**Event from board.*/
public class BoardEvent extends Event {
	
		/**Number of row associated with event. */
		final public int row_;
		/**Number of column associated with event. */
		final public int column_;
		/**What happened.*/
		final public Action action_;
		/**Enum containing actions that player could perform on game board.*/
		public enum Action 
		{
			/**Player pressed left mouse button.*/
			LEFT_MOUSE_BUTTON_PRESSED,
			/**Player pressed right mouse button.*/
			RIGHT_MOUSE_BUTTON_PRESSED,
			/**Player pressed H key. */
			H_KEY_PRESSED,
			/**Player released H key. */
			H_KEY_RELASED
		}
		/**
		 * Create BoardEvent.
		 * @param column Number of column associated with event.
		 * @param row Number of row associated with event. 
		 * @param action What happened.
		 */
		public BoardEvent(int column, int row, Action action)
		{
			row_ = row;
			column_ = column;
			action_ = action;
		}
}
