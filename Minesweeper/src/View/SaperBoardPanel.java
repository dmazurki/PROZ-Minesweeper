package View;

import java.awt.Color;

import Model.FieldOutlook;
import Model.GameState;
import Model.ModelDataPack;

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

public class SaperBoardPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private FieldOutlook[][] fields_;
	private FieldOutlook hint_;
	private JLabel time_;
	private JLabel flags_;
	private JButton emoticon_;
	
	
	public SaperBoardPanel()
	{
		fields_ = new FieldOutlook[1][1];
		fields_[0][0] = FieldOutlook.COVERED;
		hint_ = FieldOutlook.COVERED;
		add(new JLabel("Flags:"));
		flags_ = new JLabel("0");
		add(flags_);
		
		emoticon_ = new JButton();
		emoticon_.setIcon( new ImageIcon("Assets/faceSmiling.png"));
		emoticon_.setBorder(BorderFactory.createEmptyBorder());
		emoticon_.setContentAreaFilled(false);
		add(emoticon_);
		
		add(new JLabel("Time:"));
		time_ = new JLabel("0");
		add(time_);
	}

	@Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.blue);
      
		/*Drawing game board. */
        for(int i = 0; i<fields_.length;++i)
        	for(int j = 0; j<fields_[i].length; ++j)
        		g2d.drawImage(Assets.getImage(fields_[i][j]),getBoardX()+ View.BLOCK_SIZE*j,getBoardY()+View.BLOCK_SIZE*i,null);
        	
        /*Drawing hint. */
        if(hint_ != FieldOutlook.COVERED)
        {
        	/*Converting mouse coordinates from a screen to mouse position on game board. */
        	Point p = new Point(MouseInfo.getPointerInfo().getLocation().x,MouseInfo.getPointerInfo().getLocation().y);
        	SwingUtilities.convertPointFromScreen(p,this);
        	g2d.drawImage(Assets.getImage(hint_), p.x, p.y, null);
        }
       
      
     }
	
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
   

	public int getBoardX()
	{
		return (getParent().getWidth()-View.BLOCK_SIZE*fields_[0].length)/2;
	}
	public  int getBoardY()
	{
		return (View.BLOCK_SIZE*3)/2;
	}
}