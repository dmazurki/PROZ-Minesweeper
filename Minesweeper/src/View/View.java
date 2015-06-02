package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidParameterException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Controller.Controller;
import Model.ModelDataPack;
import Model.Settings;
import Model.Settings.Score;
import Event.CustomOptionsChosenEvent;
import Event.NewHighScoreEvent;

/**
 * Class responsible for drawing graphical user interface.
 * @author Damian Mazurkiewicz
 */
public class View {
	
	/** Size of the Block, in other word Field in pixels.*/
	final static int BLOCK_SIZE = 24;
	/** Name of file with view settings. */
	final static String SETTINGS_FILE_NAME = "guiSettings.xml";  
	
	/**
	 * Controller object that sets and updates this View basing on
	 * events putted in a BlockingQueue and Model behavior.
	 */
	private Controller controller_;
	
	/**Frame, it draws window with a game and all components.*/
	private SaperMainFrame mainFrame_;
	/**Some initial settings for View object.*/
	private ViewSettings settings_;
	
	/**View class construcror, it loads graphic to Assets class. It also loads Settings from file*/
	public View()
	{
		Assets.load();
		settings_ = new ViewSettings(SETTINGS_FILE_NAME);
	}
	
	/**
	 * Set Controller for View and create window.
	 * @param c Controller we want to set for this object.
	 */
	public void setController(Controller c)
	{
		mainFrame_ = new SaperMainFrame(c,settings_.xWindowPosition_,settings_.yWindowPosition_);
		controller_ = c;
	}
	
	/**
	 * Update View using data passed as a parameter.
	 * @param dataPack Data pack from Model.
	 */
	public void update(ModelDataPack dataPack)
	{
		SwingUtilities.invokeLater(new Runnable() {		
			@Override
			public void run() {
				mainFrame_.update(dataPack);
			}
		});
	}
	
	/**
	 * Method that shows dialog window, in which player can type his/her name, then puts event in
	 * the BlockingQueue that makes Controller try to add new score to the HighScores table.
	 */
	public void addHighScore()
	{
		JDialog dialog = new JDialog(mainFrame_,"New highscore");
		dialog.getContentPane().setLayout(new BorderLayout());
		JPanel texts = new JPanel(new FlowLayout());
			JLabel nameL =  new JLabel("Your name:");
			JTextField nameF = new JTextField(10);
			nameF.setText(settings_.lastGivenPlayerName_);
			texts.add(nameL);
			texts.add(nameF);
		JPanel buttons = new JPanel(new FlowLayout());
			JButton okButton = new JButton("Ok");
			okButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						controller_.blockingQueue_.put(new NewHighScoreEvent(nameF.getText()));
						settings_.lastGivenPlayerName_ = nameF.getText();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					dialog.dispose();
					
				}
			});
			JButton cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					dialog.dispose();
				}
			});
			buttons.add(okButton);
			buttons.add(cancelButton);
		
		dialog.add(texts,BorderLayout.CENTER);
		dialog.add(buttons,BorderLayout.SOUTH);
		dialog.setVisible(true);
		dialog.pack();
		dialog.setLocationRelativeTo(mainFrame_);
	}
	
	/**
	 * Show the dialog window.
	 * @param message Message that will be displayed in the dialog.
	 */
	public void showDialog(String message)
	{
		JDialog dialog = new JDialog(mainFrame_,"Message");
		dialog.getContentPane().setLayout(new  BorderLayout());
		JLabel messageL = new JLabel(message);
		JPanel messagePanel = new JPanel(new FlowLayout());
		messagePanel.add(messageL);
		dialog.getContentPane().add(messagePanel, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialog.dispose();
				
			}
		});
		buttonPanel.add(okButton);
		dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		dialog.setVisible(true);
		dialog.pack();
		dialog.setLocationRelativeTo(mainFrame_);
	}
	

	
	/**Show dialog window that enables player to chose custom parameters of Sapper Board. */
	public void showCustomOptionsDialog()
	{
		/** Creating dialog window for choosing custom board settings. */ 
		JDialog options = new JDialog(mainFrame_,"Custom");
		options.setLayout(new BorderLayout());
		
		JPanel input = new JPanel();
		input.setLayout(new GridLayout(3,2));
		
		JLabel columnsL = new JLabel("Columns:");
		JTextField columnsF = new JTextField((new Integer(settings_.customColumns_)).toString());
		JLabel rowsL = new JLabel("Rows:");
		JTextField rowsF = new JTextField((new Integer(settings_.customRows_)).toString());
		JLabel minesL = new JLabel("Mines:");
		JTextField minesF = new JTextField((new Integer(settings_.customMines_)).toString());
		
		input.add(columnsL);input.add(columnsF);
		input.add(rowsL);	input.add(rowsF);
		input.add(minesL);	input.add(minesF);
		
		JPanel buttons = new JPanel(new FlowLayout());
		
		JButton apply = new JButton("Ok");
		apply.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
					
				controller_.blockingQueue_.put(new CustomOptionsChosenEvent(
						Integer.parseInt(columnsF.getText())
						,Integer.parseInt(rowsF.getText())
						,Integer.parseInt(minesF.getText())));
				settings_.customColumns_ = Integer.parseInt(columnsF.getText());
				settings_.customRows_ = Integer.parseInt(rowsF.getText());
				settings_.customMines_ = Integer.parseInt(minesF.getText());
				
				}
				catch(InvalidParameterException e)
				{
					JOptionPane.showMessageDialog(mainFrame_, "Invalid parameters.");
				}
				 catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
				
				options.dispose();
			}
			
		});
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				options.dispose();
				
			}
			
		});
		buttons.add(apply); buttons.add(cancel);
		
		options.getContentPane().add(input,BorderLayout.CENTER);
		options.getContentPane().add(buttons,BorderLayout.SOUTH);

		options.pack();
		options.setResizable(false);
		options.setLocationRelativeTo(mainFrame_);
		options.setVisible(true);
	
	}
	

	/**
	 * Show window containing current table of HighScores.
	 * @param highScores Table of HighScores.
	 */
	public void showHighScoresTable(Score[] highScores)
	{

		JDialog scores = new JDialog(mainFrame_,"Highscores");
		
		scores.setLayout(new BorderLayout());
		JPanel tables = new JPanel(new FlowLayout());
		
		JPanel tablePanel = new JPanel(new BorderLayout());
		
		Object [][] scoresMatrix =  new Object[Settings.SCORE_NUMBER][];
	
		for(int i =0;i<Settings.SCORE_NUMBER; ++i)
		{
			scoresMatrix[i] = new Object[3];
			scoresMatrix[i][0] = highScores[i].playerName_;
			scoresMatrix[i][1] = highScores[i].mode_.toString();
			scoresMatrix[i][2] = new Integer(highScores[i].time_).toString();
		}
		
		Object [] columns = new Object[3];
		columns[0] = new String("Player");
		columns[1] = new String("Mode");
		columns[2] = new String("Time");
		
		JTable scoreTable = new JTable(scoresMatrix,columns);
		scoreTable.setEnabled(false);
		scoreTable.getColumn("Player").setPreferredWidth(120);
		scoreTable.getColumn("Mode").setPreferredWidth(80);
		scoreTable.getColumn("Time").setPreferredWidth(50);
		scoreTable.setBackground(Color.WHITE);
		
		tablePanel.add(scoreTable.getTableHeader(),BorderLayout.NORTH);
		tablePanel.add(scoreTable,BorderLayout.CENTER);
		
		tables.add(tablePanel);
	
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				scores.dispose();
				
			}
		});
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(okButton);
		scores.add(buttonPanel,BorderLayout.SOUTH);
		scores.add(tables,BorderLayout.CENTER);
		
		scores.pack();
		scores.setResizable(false);
		scores.setLocationRelativeTo(mainFrame_);
		scores.setVisible(true);
		
	}
	
	/**
	 * Set title of the window.
	 * @param title Title to set.
	 */
	public void setTitle(String title)
	{
		mainFrame_.setTitle(title);
	}
		
	/**
	 * Method responsible for setting the state of button "enable Hints". If hints are enabled, this menu 
	 * item should be checked.
	 * @param state if true, button is checked.
	 */
	public void setHintsButtonState(boolean state)
	{
		mainFrame_.setHintsButtonState(state);
	}
	
	/**Save settings of the GUI to XML file.*/
	public void saveSettings()
	{
		settings_.xWindowPosition_ = mainFrame_.getX();
		settings_.yWindowPosition_ = mainFrame_.getY();
		settings_.saveSettings(SETTINGS_FILE_NAME);
	}
}
