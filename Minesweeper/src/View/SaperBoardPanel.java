package View;

import java.awt.Color;

import Model.FieldOutlook;


import java.awt.Graphics;
import java.awt.Graphics2D;





import javax.swing.JLabel;
import javax.swing.JPanel;

public class SaperBoardPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private FieldOutlook[][] fields_;
	
	
	public SaperBoardPanel(FieldOutlook[][] fields)
	{
		fields_ = fields;
		add(new JLabel("State:"));
		add(new JLabel("Time:"));
		
		
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
   
        		 g2d.drawImage(Assets.getImage(fields_[i][j]),(getParent().getWidth()-View.BLOCK_SIZE*fields_[0].length)/2 + View.BLOCK_SIZE*j,View.BLOCK_SIZE*(i+1)+View.BLOCK_SIZE/2,null);
        	}
        }
       
     }
	
	void update(FieldOutlook[][] fields)
	{
		fields_ = fields;
		repaint();
	}
   }
