package View;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import Model.ModelDataPack;

public class SaperMainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private SaperBoardPanel saperBoard_;
	
	
	public SaperMainFrame(ModelDataPack dataPack)
	{
		setTitle("Minesweeper.");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		saperBoard_ = new SaperBoardPanel(dataPack.getFields());
		
		createMenuBar();
		
		add(saperBoard_,BorderLayout.CENTER);
		//setSize(View.BLOCK_SIZE*dataPack.getFields().length, View.BLOCK_SIZE*14);
		setVisible(true);
		
		
		
		
		
		
		setResizable(false);
	}
	void update(ModelDataPack dataPack)
	{
		saperBoard_.update(dataPack.getFields());
		setSize(View.BLOCK_SIZE*(dataPack.getFields()[0].length+1), View.BLOCK_SIZE*(dataPack.getFields().length+4));
	}
	/**
	 * Method that is responsible for building a menu bar and configuring it.
	 */
	private void createMenuBar()
	{
		JMenuBar menuBar = new JMenuBar();
		
		JMenu gameMenu = new JMenu("Game");
		
		JMenuItem pauseMenuItem = new JMenuItem("Pause");
		JMenuItem newGameMenuItem = new JMenuItem("New game");
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
		JMenuItem advancedOptionMenuItem = new JMenuItem("Advanced");
		JMenuItem expertOptionMenuItem = new JMenuItem("Expert");
		JMenuItem customOptionMenuItem = new JMenuItem("Custom");
		JMenuItem enableHintsMenuItem = new JMenuItem("Enable hints");
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
}
