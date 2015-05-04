/**
 * Controller class.
 */

package Controller;

import Model.Model;
import Model.Settings;
import View.View;
import View.ViewDataPack;
import View.Event.BoardEvent;
import View.Event.MenuEvent;

public class Controller {
	
	private Model model_;
	private View view_;
	
	/**
	 * Controller constructor, it creates controller, that manipulates Model using View interface.
	 * <br> it also sends first data pack to View, so it can display Model initial state.
	 * @param model
	 * @param view
	 */
	public Controller(Model model, View  view)
	{
		model_ = model;
		view_ = view;
		model_.getController(this);
		view_.set(model.getDataPack(),this);
	}
	public void update()
	{
		view_.update(model_.getDataPack());
	}
	public  void receiveViewDataPack(ViewDataPack viewDataPack)
	{
		switch(viewDataPack.getEventType())
		{
		case REVEAL_FIELD:
		
			model_.revealField(viewDataPack.getRow(),viewDataPack.getColumn());
			
			break;
		
		case NEW_GAME_MENU_ITEM:
			model_.newGame();
			break;
		}
			view_.update(model_.getDataPack());
	}
	
	public void handleEvent(BoardEvent e)
	{
		switch(e.action_)
		{
			case LEFT_MOUSE_BUTTON_PRESSED : model_.revealField(e.row_,e.column_);
			case RIGHT_MOUSE_BUTTON_PRESSED : model_.switchFlag(e.row_, e.column_);
			
		default:
		}
		view_.update(model_.getDataPack());	
	}
	
	public void handleEvent(MenuEvent e)
	{
		switch(e.action_)
		{
			case  PAUSE_MENU_ITEM_CLICKED: model_.pause(); break;
			case  NEW_GAME_MENU_ITEM_CLICKED: model_.newGame(); break;
			case  BEGINNER_MENU_ITEM_CLICKED : model_.setMode(10, 10, 10); model_.newGame(); break;
			case  ADVANCED_MENU_ITEM_CLICKED : model_.setMode(16, 16, 40); model_.newGame(); break;
			case  EXPERT_MENU_ITEM_CLICKED : model_.setMode( 30, 16, 99); model_.newGame(); break;
			case HINT_MENU_ITEM_CLICKED : model_.switchHints(); break;
			
		default:
		}
		view_.update(model_.getDataPack());	
	}
	
	public Settings getSettings()
	{
		return model_.getSettings();
	}

}


