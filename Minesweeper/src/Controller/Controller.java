/**
 * Controller class.
 */

package Controller;

import Model.Model;
import View.View;
import View.ViewDataPack;

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
		view_.set(model.getDataPack(),this);
	}
	
	public  void receiveViewDataPack(ViewDataPack viewDataPack)
	{
		switch(viewDataPack.getEventType())
		{
		case LEFT_MOUSE_BUTTON_ON_BOARD:
		
			model_.revealField(viewDataPack.getRow(),viewDataPack.getColumn());
			
			break;
		
		case NEW_GAME_MENU_ITEM:
			model_.newGame();
			break;
		}
			view_.update(model_.getDataPack());
	}

}


