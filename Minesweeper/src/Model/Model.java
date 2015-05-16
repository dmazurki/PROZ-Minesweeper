package Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidParameterException;

import javax.swing.Timer;

import Controller.Controller;
import Event.TimeEvent;
import Model.Settings.Score;


/**
 * Model is the class that holds whole logic of the game. It provides with all necessary operations on
 * game and game board, and allows insight into the state of the game. It sends current board state in
 * ModelDataPack. It also includes a timer, that can notify controller about another secont passing in
 * the game. 
 * @author Damian Mazurkiewicz
 *
 */
public class Model{
	
	final static String SETTINGS_FILE_NAME = "settings.xml";
	
	private Board board_;			// Board on which the game is played.
	private GameState state_;		// Enum that represents current state of a game.
	private int time_;				// Counts how many seconds current game lasts.
	private Timer timer_;			// Increments time_ variable.
	private Settings settings_;		// Holds setting of game such as difficulty mode, hints permission, high scores etc...
	private FieldOutlook hint_;		// Holds current hint.
	private Controller controller_;	// Controller that is manipulating this Model instance.
	
	/**
	 * Constructor of Model. It loads Model settings from file, creates game board and sets the
	 * state of the game to "BEGINNING", it also initialises timer that will count seconds in 
	 * the game.
	 */
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
			public void actionPerformed(ActionEvent arg0) 
			{
						time_++;
						if(controller_!=null)
						{
							try {
								controller_.blockingQueue_.put(new TimeEvent());
								} 
							catch (InterruptedException e) 
								{
								e.printStackTrace();
								}
						}
			}
		});
	}
	/**
	 * This method sets Controller for Model.
	 * @param c - Controller object.
	 */
	public void  setController(Controller c)
	{
		controller_ = c;
	}
	
	/**
	 * This method enables hints, when they are disabled, and it switches off the hints when they are 
	 * enabled ( but it is not possible to switch off the hints during the game ). 
	 */
	public void switchHints()
	{
		if(settings_.hints_==false)
			settings_.hints_ = true;
		else if(state_ != GameState.RUNNING && state_ != GameState.PAUSED)
			settings_.hints_ = false;
	}
	
	/**
	 * Reveal field on the game board, may effect the state of the game, change it to WON or LOST.
	 * @param x - number of column with the field.
	 * @param y - number of row with the field.
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
	 * This method sets the hint for the player, it receives Outlook of the field from given position.
	 * @param x - number of column with the field.
	 * @param y - number of row with the field.
	 */
	public void hint(int x, int y)
	{
		if(settings_.hints_ == true)
		{
			hint_ = board_.getOutlook(x, y);
		}
	}
	
	/**
	 * Resetting the hint field. Default value of variable hint_ is COVERED, but if hint was cancelled it 
	 * becames FLAGGED, that means that in this game player used hints.
	 */
	public void cancelHint()
	{
		hint_ = FieldOutlook.FLAGGED;
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
	 * Method that pauses the game, it stops the inner timer of Model and sets GameState variable to PAUSED.
	 */
	public void pause()
	{
		state_ = GameState.PAUSED;
		timer_.stop();
	}
	
	/**
	 * Set mode/difficulty of the board. This method only changes settings, not the board. 
	 * Thus, method newGame() has to be called to create new board with new properties.
	 * @param columns - new number of columns.
	 * @param rows - new number of rows.
	 * @param mines - new number of mines.
	 */
	public void setMode(int columns, int rows, int mines) 
	{
		if(columns<8 || columns>40 || rows<8 || rows>40 || mines<1 || mines > columns*rows-1)
			throw new InvalidParameterException();
		settings_.rows_ = rows;
		settings_.columns_ = columns;
		settings_.mines_ = mines;
	}

	/**
	 * Generate Data Pack that includes all needed information about current state of the board.
	 * @return ModelDataPack - Time passed since beginning of the game, states of all fields in the game,
	 * 		   game state, hint, flags left.
	 */
	public ModelDataPack getDataPack()
	{	
		return new ModelDataPack(time_,
								board_.getFields(),
								state_,
								hint_,
								settings_.mines_- board_.getFlaggedFields_()
								); 
	}
	
	/** 
	 * Start a new game with settings specified in settings_ field.
	 */
	public void newGame()
	{
		board_ = new Board(settings_.columns_,settings_.rows_,settings_.mines_);
		state_ = GameState.BEGINNING;
		hint_ = FieldOutlook.COVERED;
		time_ = 0;
		timer_.restart();
		timer_.stop();
	}
	
	/**
	 * Information about the game state.
	 * @return true if current game is won, false otherwise.
	 */
	private boolean gameWon()
	{
		if(board_.getRevealedMines_() == 0 &&
		   board_.getFlaggedFields_() <= settings_.mines_ &&
		   		(settings_.rows_*settings_.columns_ - board_.getRevealedFields_()  == settings_.mines_ 
		   		|| board_.getFlaggedMines_() == settings_.mines_))
			return true;
		return false;
	}
	
	/**
	 * Information about the game state.
	 * @return true if current game is lost, false otherwise.
	 */
	private boolean gameLost()
	{
		return board_.getRevealedMines_() != 0;
	}
	
	/**
	 * Get table with the HighScores.
	 * @return Score[] table with the HighScores.
	 */
	public Score[] getHighScores()
	{
		return settings_.getHighScores();
	}
	
	/**
	 * @return Number of columns in the settings.
	 */
	public int getColumns() {return settings_.columns_;}
	/**
	 * @return Number of rows in the settings.
	 */
	public int getRows() {return settings_.rows_;}
	/**
	 * @return Number of mines in the settings.
	 */
	public int getMines() {return settings_.mines_;}
	/**
	 * @return true if hints are enabled, false otherwise.
	 */
	public boolean hintsEnabled() {return settings_.hints_;}

	/**
	 * Can the results of the game good enough to be in HighScores?
	 * @param time - low long it took for the player to solve game.
	 * @param mode - on which settings did player play.
	 * @return true if given Score is good enough to be in HighScores, 
	 * 		  false otherwise.
	 */
	public boolean canBeInHighScores(int time, Settings.Mode mode)
	{
		return settings_.canBeInHighScores(time, mode);
	}
	
	/**
	 * Add new HighScore when game is won. 
	 * @param playerName - name of the player who won.
	 */
	public void addHighscore(String playerName)
	{
			settings_.addHighscore(playerName, time_, Settings.Mode.getMode(settings_.columns_, settings_.rows_, settings_.mines_));
	}
	
	/**
	 * @return Mode Enum corresponding to current game settings. 
	 * 		   EXPERT,ADVANCED,BEGINNER,CUSTOM.
	 */
	public Settings.Mode getMode()
	{
		return Settings.Mode.getMode(settings_.columns_, settings_.rows_, settings_.mines_);
	}
	
	/**
	 * Blocks the game.
	 */
	public void block()
	{
		state_ = GameState.BLOCKED;
	}
	
	/**
	 * Save current game settings to file.
	 */
	public void saveSettings()
	{
		settings_.saveSettings(SETTINGS_FILE_NAME);
	}

}
