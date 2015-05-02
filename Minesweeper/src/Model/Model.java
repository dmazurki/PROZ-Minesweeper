package Model;



public class Model{
	private Board board_;
	private GameState state_;
	private int time_;
	private Settings settings_;
	
	
	public Model()
	{
		settings_ = new Settings("settings.xml");
		board_ = new Board(40,26,300);
		state_ = GameState.BEGINNING;
		time_ = 0;
		
	}
	
	public void revealField(int x, int y)
	{
		board_.revealField(x, y);
		if(board_.endGame())
			board_.revealAll();
		
	}
	
	public ModelDataPack getDataPack()
	{
		return  new ModelDataPack(time_,board_.getFields(),state_); 
	}
	
	public void newGame()
	{
		board_ = new Board(40,26,300);
		state_ = GameState.BEGINNING;
		time_ = 0;
	}


}
