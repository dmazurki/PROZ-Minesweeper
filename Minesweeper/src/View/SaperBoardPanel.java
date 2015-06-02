package View;


import Model.FieldOutlook;
import Model.GameState;
import Model.ModelDataPack;


import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
/**
 * Class that shows current state of game board and also information about
 * time that passed since beginning of the game and flags left for the player.
 * @author Damian Mazurkiewicz
 */
public class SaperBoardPanel extends JPanel {
	
	private static final long serialVersionUID = 24101994L;
	
	/**State of all the fields on the game board.*/
	private FieldOutlook[][] fields_;
	/**Current hint showed to player.*/
	private FieldOutlook hint_;
	/**How much time passed since beginning of the game.*/
	private JLabel time_;
	/**How much flags left for the player.*/
	private JLabel flags_;
	/**Additional graphic element showing game state.*/
	private JButton emoticon_;
	
	/**Create game board and initialize it with default values.*/
	public SaperBoardPanel()
	{
		fields_ = new FieldOutlook[1][1];
		fields_[0][0] = FieldOutlook.COVERED;
		hint_ = FieldOutlook.COVERED;
		
		Font font = new Font("Verdana",Font.BOLD,13);
		
		JLabel flagLabel = new JLabel("FLAGS:");
		flagLabel.setFont(font);
		add(flagLabel);
		
		flags_ = new JLabel("0");
		flags_.setFont(font);
		add(flags_);
		
		emoticon_ = new JButton();
		emoticon_.setIcon( new ImageIcon("Assets/faceSmiling.png"));
		emoticon_.setBorder(BorderFactory.createEmptyBorder());
		emoticon_.setContentAreaFilled(false);
		add(emoticon_);
		
		JLabel timeLabel = new JLabel("TIME:");
		timeLabel.setFont(font);
		add(timeLabel);
		time_ = new JLabel("0");  
		time_.setFont(font);
		add(time_);
	}
	
	/**Override of JComponent.paintComponent. This method draws game board and hint for the player.*/
	@Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
    	
      /**Drawing game board. */
        for(int i = 0; i<fields_.length;++i)
        	for(int j = 0; j<fields_[i].length; ++j)
        		g2d.drawImage(Assets.getImage(fields_[i][j]),getBoardX()+ View.BLOCK_SIZE*j,getBoardY()+View.BLOCK_SIZE*i,null);
        	
        /**Drawing hint. */
        if(hint_ != FieldOutlook.COVERED && hint_!=FieldOutlook.FLAGGED)
        {
        	/**Converting mouse coordinates from a screen to mouse position on game board. */
        	Point p = new Point(MouseInfo.getPointerInfo().getLocation().x,MouseInfo.getPointerInfo().getLocation().y);
        	SwingUtilities.convertPointFromScreen(p,this);
        	g2d.drawImage(Assets.getImage(hint_), p.x-24, p.y-24, null);
        }
    }
	
	/**
	 * Update board, draw it again.
	 * @param dataPack Data Pack from model, containing all data needed for updating.
	 */
	void update(ModelDataPack dataPack)
	{
		fields_ = dataPack.fields_;
		hint_ = dataPack.hint_;
		flags_.setText(new Integer(dataPack.flags_).toString());
		
		
		time_.setText((new Integer(dataPack.time_)).toString());
		if(hint_!=FieldOutlook.COVERED)
			emoticon_.setIcon(Assets.getIcon(3));
		else if(dataPack.gameState_==GameState.LOST)
			emoticon_.setIcon((Icon) Assets.getIcon(1));
		else if(dataPack.gameState_==GameState.WON)
			emoticon_.setIcon((Icon) Assets.getIcon(2));
		else if(dataPack.gameState_==GameState.BEGINNING)
			emoticon_.setIcon((Icon) Assets.getIcon(0));
		
		repaint();
	}
   
	/** @return X position of board on the Frame.*/
	public int getBoardX()
	{
		return (getParent().getWidth()-View.BLOCK_SIZE*fields_[0].length)/2;
	}
	
	/** @return Y position of board on the Frame.*/
	public  int getBoardY()
	{
		return (View.BLOCK_SIZE*3)/2;
	}
}