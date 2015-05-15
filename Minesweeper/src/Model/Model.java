package Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidParameterException;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import Controller.Controller;
import Event.TimeEvent;
import Model.Settings.Score;



public class Model{
	
	final static String SETTINGS_FILE_NAME = "settings.xml";
	
	private Board board_;
	private GameState state_;
	private int time_;
	private Timer timer_;
	private Settings settings_;
	private FieldOutlook hint_;
	private Controller controller_;
	
	
	public Model()
	{
		
		settings_ = new Settings(SETTINGS_FILE_NAME);
		board_ = new Board(settings_.columns_,settings_.rows_,settings_.mines_);
		state_ = GameState.BEGINNING;
		time_ = 0;
		hint_ = FieldOutlook.COVERED;
		
		timer_ = new Timer(1000, new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable()
				{		
					@Override
					public void run() {
						time_++;
						if(controller_!=null)
						{
							try {
								controller_.blockingQueue_.put(new TimeEvent());
								} 
							catch (InterruptedException e) 
								{
								// TODO Auto-generated catch block
								e.printStackTrace();
								}
						}
					}
			
				});
			}
		});
	}
	public void  getController(Controller c)
	{
		controller_ = c;
	}
	
	public void switchHints()
	{
			
		if(settings_.hints_==false)
			settings_.hints_ = true;
		else if(state_ != GameState.RUNNING)
			settings_.hints_ = false;
	}
	
	/**
	 * Reveal field on the game board, may effect the state of the game, change it to WON or LOST.
	 * @param x
	 * @param y
	 */
	public void revealField(int x, int y)
	{
			if((state_ == GameState.BEGINNING) ||(state_ == GameState.RUNNING) || (state_ == GameState.PAUSED))
			{
				if(!timer_.isRunning())
					timer_.start();
				state_ = GameState.RUNNING;
				board_.revealField(x, y);
				if (gameWon())
				{
					state_ = GameState.WON;
					board_.revealAll();
					timer_.stop();
				}
				else if(gameLost())
				{
					state_ = GameState.LOST;
					board_.revealAll();
					timer_.stop();
				}
		}
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void hint(int x, int y)
	{
		
		if(settings_.hints_ == true)
		{
			hint_ = board_.getOutlook(x, y);
		}
	}
	
	public void cancelHint()
	{
		if(settings_.hints_ == true)
		{
			hint_ = FieldOutlook.FLAGGED;
		}
		else
			hint_ = FieldOutlook.COVERED;
	}
	
	/**
	 * This method sets or removed the flag on choosen field on game board. If the field is not flagged and is covered it 
	 * sets the flag, if not, it removes the flag from field. It uses method of class Board to perform these actions.
	 * @param x - column of the field
	 * @param y - row of the field
	 */
	public void switchFlag(int x, int y)
	{
		if(board_.getField(x, y).isFlagged())
			board_.unflagField(x, y);
		else
			board_.flagField(x, y);
	}
	
	/**
	 * Function that pauses the game, it stops the inner timer of Model and sets GameState variable to PAUSED.
	 */
	public void pause()
	{
		state_ = GameState.PAUSED;
		timer_.stop();
	}
	
	public void setMode(int columns, int rows, int mines) 
	{
		if(columns<8 || columns>40 || rows<8 || rows>40 || mines<1 || mines > columns*rows-1)
			throw new InvalidParameterException();
		settings_.rows_ = rows;
		settings_.columns_ = columns;
		settings_.mines_ = mines;
	}
	
	@SuppressWarnings("incomplete-switch")
	public void setMode(Settings.Mode mode) 
	{
		switch (mode)
		{
		case BEGINNER : setMode(10,10,10); break;
		case ADVANCED : setMode(16,16,40); break;
		case EXPERT : setMode(30,16,99); break;
		}
	}
	
	public ModelDataPack getDataPack()
	{	
		return new ModelDataPack(time_,
								board_.getFields(),
								state_,
								hint_,
								settings_.mines_- board_.getFlaggedFields_()
								); 
	}
	
	public void newGame()
	{
		board_ = new Board(settings_.columns_,settings_.rows_,settings_.mines_);
		state_ = GameState.BEGINNING;
		hint_ = FieldOutlook.COVERED;
		time_ = 0;
		timer_.restart();
		timer_.stop();
	}
	
	private boolean gameWon()
	{
		if(board_.getRevealedMines_() == 0 &&
		   board_.getFlaggedFields_() <= settings_.mines_ &&
		   		(settings_.rows_*settings_.columns_ - board_.getRevealedFields_()  == settings_.mines_ 
		   		|| board_.getFlaggedMines_() == settings_.mines_))
			return true;
		return false;
	}
	
	private boolean gameLost()
	{
		return board_.getRevealedMines_() != 0;
	}
	
	public Score[] getHighScores()
	{
		return settings_.getHighScores();
	}
	public int getColumns() {return settings_.columns_;}
	public int getRows() {return settings_.rows_;}
	public int getMines() {return settings_.mines_;}
	public boolean hintsEnabled() {return settings_.hints_;}

	
	public boolean canBeInHighScores(int time, Settings.Mode mode)
	{
		return settings_.canBeInHighScores(time, mode);
	}
	
	public void addHighscore(String playerName)
	{
		settings_.addHighscore(playerName, time_, Settings.Mode.getMode(settings_.columns_, settings_.rows_, settings_.mines_));
	}
	
	public Settings.Mode getMode()
	{
		return Settings.Mode.getMode(settings_.columns_, settings_.rows_, settings_.mines_);
	}
	
	public void block()
	{
		state_ = GameState.BLOCKED;
	}
	
	public void saveSettings()
	{
		settings_.saveSettings(SETTINGS_FILE_NAME);
	}

}
