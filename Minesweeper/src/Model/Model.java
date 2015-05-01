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
		return  new ModelDataPack(time_,board_.getFields(),state_); 
	}
	


}
