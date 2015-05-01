package View;

import Controller.Controller;
import Model.ModelDataPack;

public class View {
	
	final static int BLOCK_SIZE = 24;
	
	Controller controller_;
	private SaperMainFrame mainFrame_;
	
	public View()
	{
		Assets.load();
		mainFrame_ = new SaperMainFrame(ModelDataPack.getNullDataPack());
	}
	
	public void getController(Controller c)
	{
		controller_ = c;
	}
	public void update(ModelDataPack dataPack)
	{
		mainFrame_.update(dataPack);
		
	}
}
