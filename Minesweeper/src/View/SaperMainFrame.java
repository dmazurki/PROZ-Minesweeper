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
 * @author Damian
 */
public class SaperMainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private SaperBoardPanel saperBoard_;
	private Controller controller_;
	private SaperMainFrame thisFrame_;
	private JRadioButtonMenuItem enableHintsMenuItem_; 
	
	
	/**
	 * 
	 * @param controller - object that controls View.
	 * @param xPosition  - xPosition of window.
	 * @param yPosition  - yPosition of window.
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
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_H)
				{
					try {
						controller_.blockingQueue_.put(new BoardEvent(0,0,BoardEvent.Action.H_KEY_RELASED));
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
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
	void update(ModelDataPack dataPack)
	{
		saperBoard_.update(dataPack);
		setSize(View.BLOCK_SIZE*(dataPack.fields_[0].length+1), View.BLOCK_SIZE*(dataPack.fields_.length+4));
	}
	/**
	 * Method that is responsible for building a menu bar and configuring it.
	 */
	private void createMenuBar(Controller controller)
	{
		JMenuBar menuBar = new JMenuBar();
	
		JMenu gameMenu = new JMenu("Game");
		
		JMenuItem pauseMenuItem = new JMenuItem("Pause");
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
		gameRulesMenuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JDialog dialog = new JDialog(thisFrame_,"Game Rules");
			    dialog.add(new JLabel("<html>RULES:"
			    		+ "<br>The goal of the game is to reveal all the fields that are not mines</br>"
			    		+ "<br>or flag all thr fields thar are mines. </br>"
			    		+ "<br>HINTS:</br>"
			    		+ "<br>You can turn on the hints in the options menuu then if you press H button</br>"
			    		+ "<br>you will see what is the field on the position that you are holding our mouse.</br>"
			    		+ "<br>but when you play the game with hints on, you will not be able to be in highscores.</br></html>"));
			    dialog.setVisible(true);
			    dialog.pack();
			    dialog.setLocationRelativeTo(thisFrame_);
				
			}
			
		});
		JMenuItem aboutSaperMenuItem = new JMenuItem("About Saper");
		helpMenu.add(gameRulesMenuItem);
		helpMenu.add(aboutSaperMenuItem);
		
		menuBar.add(gameMenu);
		menuBar.add(optionsMenu);
		menuBar.add(helpMenu);
		
		
		super.setJMenuBar(menuBar);
		
	}
	
	/**
	 * Mouse listener that handles player moves on game board. Then it sends proper package to controller.
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
	
	/**
	 * This method sets the actual title of the frame depending on the game mode saved in Settings object.
	 */
	public void setTitle(String title)
	{
		super.setTitle(title);
	
	}
	
	/**
	 * Method responsible for setting the state of button "enable Hints". If hints are enabled, this menu 
	 * item should be checked.
	 * @param state - if true, button is checked.
	 */
	public void setHintsButtonState(boolean state)
	{
		enableHintsMenuItem_.setSelected(state);
	}
	
}
