package Model;

public class ModelDataPack {
	final public int time_;
	final public FieldOutlook[][] fields_;
	final public GameState gameState_;
	final public FieldOutlook hint_;
	
	public ModelDataPack(int time, FieldOutlook[][] fields,GameState gameState, FieldOutlook hint)
	{
		time_=time; fields_=fields; gameState_ = gameState; hint_ = hint;
	}

}
