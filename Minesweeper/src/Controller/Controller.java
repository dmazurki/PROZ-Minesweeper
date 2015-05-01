/**
 * Controller class.
 */

package Controller;

import Model.Model;
import View.View;

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
		view_.getController(this);
		view_.update(model.getDataPack());
	}

}


