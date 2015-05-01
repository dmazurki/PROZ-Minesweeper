package View;

import java.awt.Color;

import Model.FieldOutlook;




import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;




import javax.swing.JFrame;
import javax.swing.JPanel;

public class SaperBoardPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private FieldOutlook[][] fields_;
	
	
	public SaperBoardPanel(FieldOutlook[][] fields)
	{
		fields_ = fields;
		
		
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
        		 g2d.drawImage(Assets.getImage(fields_[i][j]),View.BLOCK_SIZE*i,View.BLOCK_SIZE*(j+2),null);
        	}
        }
       
     }
	
	void update(FieldOutlook[][] fields)
	{
		fields_ = fields;
		repaint();
	}
   }
