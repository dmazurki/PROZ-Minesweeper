package Model;

public class ModelDataPack {
	private int time_;
	private FieldOutlook[][] fields_;
	private GameState gameState_;
	
	public ModelDataPack(int time, FieldOutlook[][] fields,GameState gameState)
	{
		time_=time; fields_=fields; gameState_ = gameState;
	}
	
	public static ModelDataPack getNullDataPack() {return new ModelDataPack(0,new FieldOutlook[0][],GameState.BEGINNING);}
	
	public FieldOutlook[][] getFields() {return fields_;}
}
