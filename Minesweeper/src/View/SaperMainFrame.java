package View;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import Controller.Controller;
import Model.ModelDataPack;
import Model.Settings;
import View.Event.BoardEvent;
import View.Event.MenuEvent;

/**
 * 
 * @author Damian
 */
public class SaperMainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private SaperBoardPanel saperBoard_;
	private Controller controller_;
	private Settings settings_;

	
	
	public SaperMainFrame(ModelDataPack dataPack, Controller controller)
	{
		
		setIconImage(Assets.getImage(Model.FieldOutlook.MINE));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(0, 0);
		controller_ = controller;
		settings_ = controller.getSettings();
		saperBoard_ = new SaperBoardPanel(dataPack);
		saperBoard_.addMouseListener(new MouseOnGameBoardListener());
		createMenuBar(controller_);
		setTitle();
		
		add(saperBoard_,BorderLayout.CENTER);
		setSize(View.BLOCK_SIZE*(dataPack.fields_[0].length+1), View.BLOCK_SIZE*(dataPack.fields_.length+4));
		
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
				controller_.handleEvent(new MenuEvent(MenuEvent.Action.PAUSE_MENU_ITEM_CLICKED));
				}
			
		});
		JMenuItem newGameMenuItem = new JMenuItem("New game");
		newGameMenuItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller_.handleEvent(new MenuEvent(MenuEvent.Action.NEW_GAME_MENU_ITEM_CLICKED));
				
			}
			
		});
		JMenuItem highScoresMenuItem = new JMenuItem("Highscores");
		
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
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
				controller_.handleEvent(new MenuEvent(MenuEvent.Action.BEGINNER_MENU_ITEM_CLICKED));
				settings_ = controller_.getSettings();
				setTitle();
			
			}
	    }
	    );
		JMenuItem advancedOptionMenuItem = new JMenuItem("Advanced");
		advancedOptionMenuItem.addActionListener(new ActionListener()
		    {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					controller_.handleEvent(new MenuEvent(MenuEvent.Action.ADVANCED_MENU_ITEM_CLICKED));
					settings_ = controller_.getSettings();
					setTitle();
				
				}
		    }
		 );
	    JMenuItem expertOptionMenuItem = new JMenuItem("Expert");
	    expertOptionMenuItem.addActionListener(new ActionListener()
	    {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller_.handleEvent(new MenuEvent(MenuEvent.Action.EXPERT_MENU_ITEM_CLICKED));
				settings_ = controller_.getSettings();
				setTitle();
			}
	    }
	    );
	    JMenuItem customOptionMenuItem = new JMenuItem("Custom");
	    customOptionMenuItem.addActionListener(new ActionListener()
	    {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller_.handleEvent(new MenuEvent(MenuEvent.Action.CUSTOM_MENU_ITEM_CLICKED));
				settings_ = controller_.getSettings();
			}
	    }
	    );
	    
      
        
	    JRadioButtonMenuItem enableHintsMenuItem = new JRadioButtonMenuItem("Enable hints");
		enableHintsMenuItem.setSelected(settings_.hints_ );
		enableHintsMenuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller_.handleEvent(new MenuEvent(MenuEvent.Action.HINT_MENU_ITEM_CLICKED));
				settings_ = controller_.getSettings();
				((JRadioButtonMenuItem)arg0.getSource()).setSelected(settings_.hints_ == true);
		
			}
			
		});
		optionsMenu.add(beginnerOptionMenuItem);
		optionsMenu.add(advancedOptionMenuItem);
		optionsMenu.add(expertOptionMenuItem);
		optionsMenu.add(customOptionMenuItem);
		optionsMenu.addSeparator();
		optionsMenu.add(enableHintsMenuItem);
	
		
		JMenu helpMenu = new JMenu("Help");
		JMenuItem gameRulesMenuItem = new JMenuItem("Rules");
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
	 */
	private class MouseOnGameBoardListener extends MouseAdapter
	{
		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1)
			{
				controller_.handleEvent(new BoardEvent(
					((e.getX()-saperBoard_.getBoardX())/View.BLOCK_SIZE),
					(e.getY()-saperBoard_.getBoardY())/View.BLOCK_SIZE,
					BoardEvent.Action.LEFT_MOUSE_BUTTON_PRESSED));
			}
			else if (e.getButton() == MouseEvent.BUTTON3)
			{
				controller_.handleEvent(new BoardEvent(
					((e.getX()-saperBoard_.getBoardX())/View.BLOCK_SIZE),
					(e.getY()-saperBoard_.getBoardY())/View.BLOCK_SIZE,
					BoardEvent.Action.RIGHT_MOUSE_BUTTON_PRESSED));
			}
		}	
	}
	
	public void setTitle()
	{
		if(settings_.columns_ == 10 && settings_.rows_ == 10 && settings_.mines_ == 10)
			super.setTitle("Sapper (beginner)");
		else
		if(settings_.columns_ == 16 && settings_.rows_ == 16 && settings_.mines_ == 40)
			super.setTitle("Sapper (advanced)");
		else
		if(settings_.columns_ == 30 && settings_.rows_ == 16 && settings_.mines_ == 99)
			super.setTitle("Sapper (expert)");
		else
			super.setTitle("Sapper");
	}
}
