package View;

import java.awt.BorderLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;

import Controller.Controller;
import Model.ModelDataPack;
import Event.BoardEvent;
import Event.MenuEvent;
import Event.MenuEvent.Action;

/**
 * Main frame of the game.
 * @author Damian Mazurkiewicz
 */
public class SaperMainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**Game board.*/
	private SaperBoardPanel saperBoard_;
	/**Controller needed to initialize Frame, and then to put events in its BlockingQueue. */
	private Controller controller_;
	/**Reference of object to itself, needed to show dialog windows.*/
	private SaperMainFrame thisFrame_;
	/**Menu item used to dectde whether player wants to use hints or not. 
	 *state of this mutton must be set from outside thus this reference must be held here.*/
	private JRadioButtonMenuItem enableHintsMenuItem_; 
	
	
	/**
	 * Create Frame, menu mar, add eventlisteners to all components.
	 * @param controller  object that controls View.
	 * @param xPosition   xPosition of window.
	 * @param yPosition   yPosition of window.
	 */
	public SaperMainFrame(Controller controller, int xPosition, int yPosition)
	{
		setIconImage(Assets.getImage(Model.FieldOutlook.MINE));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocation(0, 0);
		controller_ = controller;
		thisFrame_ = this;
		setLocation(xPosition, yPosition);
		saperBoard_ = new SaperBoardPanel();
		
		setFocusable(true);
		saperBoard_.addMouseListener(new BoardListener());
		createMenuBar(controller_);
	
		add(saperBoard_,BorderLayout.CENTER);
		
		/**
		 * Main frame has its own key listener that checks, if the H key on the keyboard was pressed.
		 * If so, it puts proper event in Blocking Queue. H - key is responsible for showing hint. 
		 */
		addKeyListener(new KeyListener()
		{

			@Override
			public void keyPressed(KeyEvent e) {
				/**Player presses H to see hint.*/
				if(e.getKeyCode() == KeyEvent.VK_H)
				{
					Point p = new Point(MouseInfo.getPointerInfo().getLocation().x,MouseInfo.getPointerInfo().getLocation().y);
					SwingUtilities.convertPointFromScreen(p,saperBoard_);
					try {
						controller_.blockingQueue_.put(new BoardEvent(
								
								((p.x -saperBoard_.getBoardX())/View.BLOCK_SIZE),
								( p.y-saperBoard_.getBoardY())/View.BLOCK_SIZE,
								BoardEvent.Action.H_KEY_PRESSED));
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				/**Player releases H. Time to hide the hint.*/
				if(e.getKeyCode() == KeyEvent.VK_H)
				{
					try {
						controller_.blockingQueue_.put(new BoardEvent(0,0,BoardEvent.Action.H_KEY_RELASED));
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				
			}
			@Override
			public void keyTyped(KeyEvent e) {}	
		}
	   );
		
		setVisible(true);
		setResizable(false);
		
	}
	
	/**
	 * Update frame.
	 * @param dataPack Frame is updated based on ModelDataPack.
	 */
	void update(ModelDataPack dataPack)
	{
		saperBoard_.update(dataPack);
		setSize(View.BLOCK_SIZE*(dataPack.fields_[0].length+1), View.BLOCK_SIZE*(dataPack.fields_.length+4));
	}
	
	/**Build a menu bar and configure it. 
	 * @param controller Menu items will put their events in Controller BlockingQueue.
	 */
	private void createMenuBar(Controller controller)
	{
		JMenuBar menuBar = new JMenuBar();
	
		JMenu gameMenu = new JMenu("Game");
		
		JMenuItem pauseMenuItem = new JMenuItem("Pause");
		
		/**Pause game if player clicked "pause" menu item.*/
		pauseMenuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					controller_.blockingQueue_.put(new MenuEvent(MenuEvent.Action.PAUSE_MENU_ITEM_CLICKED));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			
			});
		
		JMenuItem newGameMenuItem = new JMenuItem("New game");
		
		/**Set new game if player clicked "new game" menu item.*/
		newGameMenuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					controller_.blockingQueue_.put(new MenuEvent(MenuEvent.Action.NEW_GAME_MENU_ITEM_CLICKED));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		JMenuItem highScoresMenuItem = new JMenuItem("Highscores");
		
		/**Show highScores if player clicked "HighScores" menu item.*/
		highScoresMenuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					controller_.blockingQueue_.put(new MenuEvent(MenuEvent.Action.HIGHSCORES_MENU_ITEM_CLICKED));
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
			
		});
		
		/**Exit the game if player clicked "exit" menu item.*/
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try {
					controller_.blockingQueue_.put(new MenuEvent(Action.EXIT_MENU_ITEM_CLICKED));
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		);
		gameMenu.add(pauseMenuItem);
		gameMenu.add(newGameMenuItem);
		gameMenu.addSeparator();
		gameMenu.add(highScoresMenuItem);
		gameMenu.addSeparator();
		gameMenu.add(exitMenuItem);
		
		JMenu optionsMenu = new JMenu("Options");
	    JMenuItem beginnerOptionMenuItem = new JMenuItem("Beginner");
	    
	    /**Set game mode to BEGINNER.*/
	    beginnerOptionMenuItem.addActionListener(new ActionListener()
	    {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					controller_.blockingQueue_.put(new MenuEvent(MenuEvent.Action.BEGINNER_MENU_ITEM_CLICKED));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
			}
	    }
	    );
		JMenuItem advancedOptionMenuItem = new JMenuItem("Advanced");
		/**Set game mode to ADVANCED.*/
		advancedOptionMenuItem.addActionListener(new ActionListener()
		    {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						controller_.blockingQueue_.put(new MenuEvent(MenuEvent.Action.ADVANCED_MENU_ITEM_CLICKED));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				
				}
		    }
		 );
	    JMenuItem expertOptionMenuItem = new JMenuItem("Expert");
	    /**Set game mode to EXPERT.*/
	    expertOptionMenuItem.addActionListener(new ActionListener()
	    {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					controller_.blockingQueue_.put(new MenuEvent(MenuEvent.Action.EXPERT_MENU_ITEM_CLICKED));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			}
	    }
	    );
	    JMenuItem customOptionMenuItem = new JMenuItem("Custom");
	    /**Set game mode to CUSTOM.*/
	    customOptionMenuItem.addActionListener(new ActionListener()
	    {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					controller_.blockingQueue_.put(new MenuEvent(MenuEvent.Action.CUSTOM_MENU_ITEM_CLICKED));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
	    }
	    );
	   
	    enableHintsMenuItem_ = new JRadioButtonMenuItem("Enable hints");
		/**Enable or disable hints.*/
		enableHintsMenuItem_.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
					
					
				try {
					
					controller_.blockingQueue_.put(new MenuEvent(MenuEvent.Action.HINT_MENU_ITEM_CLICKED));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
		optionsMenu.add(beginnerOptionMenuItem);
		optionsMenu.add(advancedOptionMenuItem);
		optionsMenu.add(expertOptionMenuItem);
		optionsMenu.add(customOptionMenuItem);
		optionsMenu.addSeparator();
		optionsMenu.add(enableHintsMenuItem_);
	
		
		JMenu helpMenu = new JMenu("Help");
		JMenuItem gameRulesMenuItem = new JMenuItem("Rules");
		
		/**If Rules button was clicked, show Dialog window with rules.*/
		gameRulesMenuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JDialog dialog = new JDialog(thisFrame_,"Game Rules");
			    dialog.add(new JLabel("<html><b>RULES:</b>"
			    		+ "<br>The goal of the game is to reveal all the fields that are not mines</br>"
			    		+ "<br>or flag all thr fields thar are mines. After revealing the field it can </br>" 
			    		+ "<br>come up as a mine, then you lose, when not, it shows how many adjacent fields </br>"
			    		+ "<br>are mines. You reveal field with left mouse button, you flag field with</br>"
			    		+ "<br>right mouse button.</br>"
			    		+ "<br><strong>HINTS:</strong></br>"
			    		+ "<br>You can turn on the hints in the options menu then if you press H button</br>"
			    		+ "<br>you will see what is the field on the position that you are holding our mouse.</br>"
			    		+ "<br>But when you play the game with hints on, you will not be able to be in highscores.</br></html>"));
			    dialog.setVisible(true);
			    dialog.pack();
			    dialog.setLocationRelativeTo(thisFrame_);
				
			}
			
		});
		JMenuItem aboutSaperMenuItem = new JMenuItem("About Saper");
		/**If About button was clicked, show Dialog window with informations about saper.*/
		aboutSaperMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new JDialog(thisFrame_,"AboutSaper");
				dialog.add(new JLabel("<html>Author: Damian Mazurkiewicz"
									+ "<br>Date: 1.06.2015</br>"
									+ "<br>For \"Programowanie Zdarzeniowe\"</br></html>"));
				dialog.setVisible(true);
				dialog.pack();
				dialog.setLocationRelativeTo(thisFrame_);
				
			}
		});
		helpMenu.add(gameRulesMenuItem);
		helpMenu.add(aboutSaperMenuItem);
		
		menuBar.add(gameMenu);
		menuBar.add(optionsMenu);
		menuBar.add(helpMenu);
		
		
		super.setJMenuBar(menuBar);
		
	}
	
	/**
	 * Mouse listener that handles player moves on game board. Then it puts proper event in the BlockingQueue.
	 * When left mouse button was pressed, it tries to uncover field pointed  by mouse.
	 * When right mouse button was pressed, it tries to flag field selected by mouse.
	 */
	private class BoardListener extends MouseAdapter 
	{
		@Override
		public void mousePressed(MouseEvent e) {
			try {
			if (e.getButton() == MouseEvent.BUTTON1)
			{
				controller_.blockingQueue_.put(new BoardEvent(
					((e.getX()-saperBoard_.getBoardX())/View.BLOCK_SIZE),
					(e.getY()-saperBoard_.getBoardY())/View.BLOCK_SIZE,
					BoardEvent.Action.LEFT_MOUSE_BUTTON_PRESSED));
			}
			else if (e.getButton() == MouseEvent.BUTTON3)
			{
				controller_.blockingQueue_.put(new BoardEvent(
					((e.getX()-saperBoard_.getBoardX())/View.BLOCK_SIZE),
					(e.getY()-saperBoard_.getBoardY())/View.BLOCK_SIZE,
					BoardEvent.Action.RIGHT_MOUSE_BUTTON_PRESSED));
			}
		}
			catch (InterruptedException e1) {
				e1.printStackTrace();
			}
	}
	}
	
	/**This method sets the title of the frame. 
	*@param title Title to be set for frame.
	*/
	public void setTitle(String title)
	{
		super.setTitle(title);
	}
	
	/**
	 * Method responsible for setting the state of button "enable Hints". If hints are enabled, this menu 
	 * item should be checked.
	 * @param state  if true, button is checked.
	 */
	public void setHintsButtonState(boolean state)
	{
		enableHintsMenuItem_.setSelected(state);
	}
	
}
