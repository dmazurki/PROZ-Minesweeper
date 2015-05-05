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
	}
	
	public  void run()
	{
		model_.getController(this);
		view_.getController(this);
		updateView();
	}
	
	public void updateView(){view_.update(model_.getDataPack());}
	
	public void handleEvent(BoardEvent e)
	{
		switch(e.action_)
		{
			case LEFT_MOUSE_BUTTON_PRESSED : model_.revealField(e.column_,e.row_); break;
			case RIGHT_MOUSE_BUTTON_PRESSED : model_.switchFlag(e.column_,e.row_);break;
			case H_KEY_PRESSED : model_.hint(e.column_,e.row_); break;
			case H_KEY_RELASED : model_.cancelHint(); break;
			
		default:
		}
		updateView();	
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
		updateView();	
	}
	
	public Settings getSettings()
	{
		return model_.getSettings();
	}

}


