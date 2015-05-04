package View;

import java.awt.Color;

import Model.FieldOutlook;


import Model.ModelDataPack;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;











import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SaperBoardPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private FieldOutlook[][] fields_;
	private JLabel time_;
	private JLabel flags_;
	private JButton emoticon_;
	
	
	public SaperBoardPanel(ModelDataPack dataPack)
	{
		fields_ = dataPack.fields_;
		add(new JLabel("Flags:"));
		flags_ = new JLabel("00");
		add(flags_);
		emoticon_ = new JButton();
		emoticon_.setIcon( new ImageIcon("Assets/happy.png"));
		//emoticon_.setBackground(Color.WHITE);
		emoticon_.setBorder(BorderFactory.createEmptyBorder());
		emoticon_.setContentAreaFilled(false);
		add(emoticon_);
		add(new JLabel("Time:"));
		time_ = new JLabel( (new Integer(dataPack.time_)).toString());
		add(time_);
		
		
	}

	@Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.blue);
      
		
        for(int i = 0; i<fields_.length;++i)
        {
        	for(int j = 0; j<fields_[i].length; ++j)
        	{
   
        		 g2d.drawImage(Assets.getImage(fields_[i][j]),getBoardX()+ View.BLOCK_SIZE*j,getBoardY()+View.BLOCK_SIZE*i,null);
        	}
        }
       
     }
	
	void update(ModelDataPack dataPack)
	{
		fields_ = dataPack.fields_;
		time_.setText((new Integer(dataPack.time_)).toString());
		repaint(); getParent();
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