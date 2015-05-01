package View;

import java.awt.Image;

import javax.swing.ImageIcon;

import Model.FieldOutlook;

public class Assets {
	private static Image[] images_;
	
	public static void load()
	{
		images_ = new Image[12];
		images_[0] = new ImageIcon("Assets/covered.png").getImage();
		images_[1] = new ImageIcon("Assets/mine.png").getImage();
		images_[2] = new ImageIcon("Assets/flagged.png").getImage();
		images_[3] = new ImageIcon("Assets/zeroAdjacent.png").getImage();
		images_[4] = new ImageIcon("Assets/oneAdjacent.png").getImage();
		images_[5] = new ImageIcon("Assets/twoAdjacent.png").getImage();
		images_[6] = new ImageIcon("Assets/threeAdjacent.png").getImage();
		images_[7] = new ImageIcon("Assets/fourAdjacent.png").getImage();
		images_[8] = new ImageIcon("Assets/fiveAdjacent.png").getImage();
		images_[9] = new ImageIcon("Assets/sixAdjacent.png").getImage();
		images_[10] = new ImageIcon("Assets/sevenAdjacent.png").getImage();
		images_[11] = new ImageIcon("Assets/eightAdjacent.png").getImage();
	}
	
	public static Image getImage(int nr)
	{
		return images_[nr];
	}
	
	public static Image getImage(FieldOutlook field)
	{
		switch(field)
		{
		
		case MINE : return images_[1]; 
		case FLAGGED : return images_[2]; 
		case ZERO_ADJACENT : return images_[3]; 
		case ONE_ADJACENT : return images_[4]; 
		case TWO_ADJACENT : return images_[5]; 
		case THREE_ADJACENT : return images_[6]; 
		case FOUR_ADJACENT : return images_[7]; 
		case FIVE_ADJACENT : return images_[8]; 
		case SIX_ADJACENT : return images_[9]; 
		case SEVEN_ADJACENT : return images_[10]; 
		case EIGHT_ADJACENT : return images_[11]; 
		default:	return images_[0]; 
		
		}
	}

}
