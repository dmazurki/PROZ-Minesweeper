package View;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Assets {
	private static Image[] images_;
	
	public static void load()
	{
		images_ = new Image[1];
		images_[0] = new ImageIcon("Assets/test.png").getImage();
	}
	
	public static Image getImage(int nr)
	{
		return images_[nr];
	}

}
