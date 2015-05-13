package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controller.Controller;
import Model.ModelDataPack;
import View.Event.NewHighScoreEvent;

public class View {
	
	final static int BLOCK_SIZE = 24;
	
	Controller controller_;
	private SaperMainFrame mainFrame_;
	
	/**
	 * View class construcror, it loads files to Assets class.
	 */
	public View()
	{
		Assets.load();
	}
	
	/**
	 * It takes initial data pack and initializes View, creating first window.
	 * @param dataPack
	 */
	public void getController(Controller c)
	{
		mainFrame_ = new SaperMainFrame(c);
		controller_ = c;
	}
	public void update(ModelDataPack dataPack)
	{
		mainFrame_.update(dataPack);
	}
	
	/**
	 * Method that shows dialog window, in which player can type his/her name, then it calls the controller
	 * method, that tries to add new higscore to HighScores table.
	 */
	public void addHighScore()
	{
		JDialog dialog = new JDialog(mainFrame_,"New highscore");
		dialog.getContentPane().setLayout(new BorderLayout());
		JPanel texts = new JPanel(new FlowLayout());
			JLabel nameL =  new JLabel("Your name:");
			JTextField nameF = new JTextField(10);
			texts.add(nameL);
			texts.add(nameF);
		JPanel buttons = new JPanel(new FlowLayout());
			JButton okButton = new JButton("Ok");
			okButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					controller_.handleEvent(new NewHighScoreEvent(nameF.getText()));
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
}
