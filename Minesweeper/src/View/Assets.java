package View;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import Model.FieldOutlook;
/**
 * Class that holds all graphics for the game.
 * @author Damian Mazurkiewicz
 */
public class Assets {
	/**Table of images representing fields on the game board. */
	private static Image[] images_;
	/** Table of icons for emoticon. */
	private static Icon[] icons_;
	
	
	/** Load images from disc.*/
	public static void load()
	{
		/**Fields on the board.*/
		images_ = new Image[16];
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
		
		/**Emoticons.*/
		icons_ = new Icon[4];
		icons_[0] = new ImageIcon("Assets/faceSmiling.png");
		icons_[1] = new ImageIcon("Assets/faceSad.png");
		icons_[2] = new ImageIcon("Assets/faceCool.png");
		icons_[3] = new ImageIcon("Assets/facePirate.png");
	}
	
	/**
	 * Get image of given number.
	 * @param nr Number index of the image in image table.
	 * @return Image Image corresponding to given number.
	 */
	public static Image getImage(int nr)
	{
		return images_[nr];
	}
	
	/**
	 * Get icon of given number.
	 * @param nr Number index of the icon in icon table.
	 * @return Icon Icon corresponding to given number.
	 */
	public static Icon getIcon(int nr)
	{
		return icons_[nr];
	}
	
	/**
     * Get image corresponding to given FieldOutlook.
	 * @param field FieldOutlook.
	 * @return Image Image connected with given FieldOutlook.
	 */
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
			default	:	return images_[0]; 
		}
	}
}
