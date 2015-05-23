package Controller;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import Event.BoardEvent;
import Event.CustomOptionsChosenEvent;
import Event.Event;
import Event.MenuEvent;
import Event.NewHighScoreEvent;
import Event.TimeEvent;
import Model.GameState;
import Model.Model;
import Model.ModelDataPack;
import Model.Settings;
import View.View;

/**
 * This class updates game logic in Model basing on Events from the Blocking Queue.
 * @author Damian Mazurkiewicz
 */
public class Controller {
	/**Model that contains game logic.*/
	private Model model_;
	/**Graphical user interface.*/
	private View view_;
	
	/** Map that holds strategy for diffrent types of Events. */
	private Map<Class<? extends Event>, ControllerStrategy> strategyMap_= new HashMap<Class<? extends Event>, ControllerStrategy>();
	/** In this queue, other objects, in particular View, can put events. They are then processed by this Controller.*/
	public BlockingQueue<Event> blockingQueue_ = new LinkedBlockingQueue<Event>();
	
	/**
	 * Controller constructor, it creates controller, that manipulates Model using View interface.
	 * it also sends first data pack to View, so it can display Model initial state.
	 * @param model Model object.
	 * @param view View object.
	 */
	public Controller(Model model, View  view)
	{
		model_ = model;
		view_ = view;
		setStrategies();
		model_.setController(this);
		view_.setController(this);
		updateView();
		run();
	}
	
	/**Start an infinite loop, that takes events from Blocking Queue based on them performs appriopriate actions.*/ 
	public void run()
	{
		while(true)
			{
				try 
				{
					Event event = blockingQueue_.take();
					ControllerStrategy strategy = strategyMap_.get(event.getClass());
					strategy.execute(event);
				}
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			
			}
	}
	/**Set reactions for different events putted in the event queue.*/
	private void setStrategies()
	{
		/**Handle events from game board.*/
		strategyMap_.put(BoardEvent.class, new ControllerStrategy(){
			public void execute(Event event){
				BoardEvent e = (BoardEvent) event;
				try{
					switch(e.action_)
					{
						case LEFT_MOUSE_BUTTON_PRESSED : model_.revealField(e.column_,e.row_); break;
						case RIGHT_MOUSE_BUTTON_PRESSED : model_.switchFlag(e.column_,e.row_);break;
						case H_KEY_PRESSED : model_.hint(e.column_,e.row_); break;
						case H_KEY_RELASED : model_.cancelHint(); break;
						default:
					}
				}
				catch(IllegalArgumentException ex){}
				
				updateView();	
				ModelDataPack pack = model_.getDataPack();
				if(pack.gameState_ == GameState.WON && !model_.hintsEnabled())
				{
					
					if(model_.canBeInHighScores(pack.time_, model_.getMode()))
					{
						view_.addHighScore();
					}
					else
						view_.showDialog("You have won!");
					model_.block();
				}
				else if(pack.gameState_ == GameState.LOST)
				{
					view_.showDialog("You have lost the game!");
					model_.block();
				}
			}
		});
		
		/** Handling events fromm menu. */
		strategyMap_.put(MenuEvent.class, new ControllerStrategy(){
			public void execute(Event event){
				MenuEvent e = (MenuEvent) event;
				
					switch(e.action_)
					{
						case  PAUSE_MENU_ITEM_CLICKED: model_.pause(); break;
						case  NEW_GAME_MENU_ITEM_CLICKED: model_.newGame(); break;
						case  HIGHSCORES_MENU_ITEM_CLICKED : view_.showHighScoresTable(model_.getHighScores()); break;
						case  BEGINNER_MENU_ITEM_CLICKED : model_.setMode(10, 10, 10);  model_.newGame(); break;
						case  ADVANCED_MENU_ITEM_CLICKED : model_.setMode(16, 16, 40);  model_.newGame(); break;
						case  EXPERT_MENU_ITEM_CLICKED : model_.setMode( 30, 16, 99); model_.newGame(); break;
						case  CUSTOM_MENU_ITEM_CLICKED :  view_.showCustomOptionsDialog(); break;
						case  HINT_MENU_ITEM_CLICKED : model_.switchHints(); break;
						case  EXIT_MENU_ITEM_CLICKED : model_.saveSettings(); view_.saveSettings(); System.exit(0); break;
					
						default:
					}
				
				updateView();	
				}
		});
		
		/**Handle situation when player wrote his custom parameters of the game in the "custom options" window.*/
		strategyMap_.put(CustomOptionsChosenEvent.class, new ControllerStrategy(){
			public void execute(Event event){
				try
				{
					CustomOptionsChosenEvent e = (CustomOptionsChosenEvent) event;
					model_.setMode(e.columns_, e.rows_, e.mines_);
					model_.newGame(); 
				}
				catch(InvalidParameterException ex)
				{
					view_.showDialog("Invalid parameters!");
				}
				updateView();
			}
		});
		
		/**Handle situation, when player deciced to add new highscore. */
		strategyMap_.put(NewHighScoreEvent.class, new ControllerStrategy(){
			public void execute(Event event){
				NewHighScoreEvent e = (NewHighScoreEvent) event;
				model_.addHighscore(e.playerName_);
			}
		});
		
		/**Handle information from Model about another second passing in game.*/
		strategyMap_.put(TimeEvent.class, new ControllerStrategy(){
			public void execute(Event event){
				updateView();
			}
		});
	}
	
	/**This method should be called after processing an event, it updates the graphical interface. */
	public void updateView(){
		view_.update(model_.getDataPack());
		switch(Settings.Mode.getMode(model_.getColumns(), model_.getRows(), model_.getMines()))
		{
			case BEGINNER : view_.setTitle("Sapper(beginner)"); break;
			case ADVANCED: view_.setTitle("Sapper(advanced)"); break;
			case EXPERT : view_.setTitle("Sapper(expert)"); break;
			default : view_.setTitle("Sapper(custom)"); break;
		}
		view_.setHintsButtonState(model_.hintsEnabled());
	}
}


