package Model;

import java.util.ArrayList;
/**
 * This class makes operating on field Matrix easier.
 * It holds two dimenstional array of fields.
 * @author Damian Mazurkiewicz
 *
 */
public class FieldMatrix {
	
	ArrayList<Field> fields;
	int width, height;
	
	public FieldMatrix(int width,int height)
	{
		this.width = width;
		this.height = height;
		
		for (int i = 0; i<width*height; ++i)
		{
			fields.add(new Field());
		}
	}
	
	public Field getField(int x, int y)
	{
		return fields.get(x+y*width);
	}
	
	public int adjacentMines(int x, int y)
	{
		return 0;
	}
	

}
