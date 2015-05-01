package View;

import Model.ModelDataPack;

public class View {
	private SaperMainFrame mainFrame_;
	
	public View(ModelDataPack dataPack)
	{
		Assets.load();
		mainFrame_ = new SaperMainFrame(dataPack);
	}
	
	void update(ModelDataPack dataPack)
	{
		
	}
}
