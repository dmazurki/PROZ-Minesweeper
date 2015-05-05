package View;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Controller.Controller;
import Model.ModelDataPack;

public class View {
	
	final static int BLOCK_SIZE = 24;
	
	Controller controller_;
	private SaperMainFrame mainFrame_;
	
	/**
	 * View class construcror, it loads files to Assets class.
	 */
	public View()
	{
		Assets.load();
	}
	
	/**
	 * It takes initial data pack and initializes View, creating first window.
	 * @param dataPack
	 */
	public void getController(Controller c)
	{
		mainFrame_ = new SaperMainFrame(c);
		controller_ = c;
	}
	public void update(ModelDataPack dataPack)
	{
		mainFrame_.update(dataPack);
	}
}
