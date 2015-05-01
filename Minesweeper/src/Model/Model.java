package Model;



public class Model{
	private Board board_;
	private GameState state_;
	private int time_;
	
	public Model()
	{
		board_ = new Board(10,10,10);
		state_ = GameState.BEGINNING;
		time_ = 0;
	}
	
	public void updateModel()
	{
		
	}
	
	public ModelDataPack getDataPack()
	{
		ModelDataPack dataPack = new ModelDataPack(); 
		dataPack.time_ = time_;
		dataPack.gameState_ = state_;
		dataPack.fields_ = board_.getFields();
		return dataPack;
	}
	


}
