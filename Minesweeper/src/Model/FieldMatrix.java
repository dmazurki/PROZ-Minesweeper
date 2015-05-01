package Model;

import java.util.ArrayList;
import java.util.Random;
/**
 * This class makes operating on field Matrix easier.
 * It holds two dimenstional array of fields.
 * @author Damian Mazurkiewicz
 *
 */
public class FieldMatrix {
	
	ArrayList<Field> fields_;
	int width_, height_;
	Random generator_;
	
	
	/**
	 * Constructor that creates Field matrix and fills it with fields, initially
	 * all fields are covered and not mines.
	 * @param width - width of field matrix (how many fields horizontally)
	 * @param height - height of field matrix (how many fields vertically)
	 */
	public FieldMatrix(int width,int height)
	{
		width_ = width;
		height_ = height;
		generator_ = new Random();
		fields_ = new ArrayList<Field>();
		
		for (int i = 0; i<width_*height_; ++i)
			fields_.add(new Field());
	}
	
	int getWidth() {return width_;}
	int getHeight(){return height_;}
	
	/**
	 * Function that allows accessing fields in the matrix.
	 * @param x - number of column
	 * @param y - number of row
	 * @return Field at given coordinates.
	 */
	public Field getField(int x, int y) throws IllegalArgumentException
	{
		if(correctCoordinates(x,y))
			return fields_.get(x+y*width_);
		else throw new IllegalArgumentException("Given coordinates x="+x+" y="+y+" are not legal for this FieldMatrix.");
	}
	
	/**
	 * Checks if field with coordinates x, y exists on the field matrix.
	 * @param x 
	 * @param y
	 * @return true - if coordinates are correct, false - otherwise.
	 */
	public boolean correctCoordinates(int x, int y)
	{
		if(x>=0 && y>=0 && x<width_ && y<height_)
			return true;
		return false;
	}
	/**
	 * This method returns the amount of fields adjacent to field at given coordinates, which contain a mine.
	 * @param x
	 * @param y
	 * @return Number of adjacent fields with mines. 
	 */
	public int adjacentMines(int x, int y) throws IllegalArgumentException
	{
		if(!correctCoordinates(x,y))
			throw new IllegalArgumentException("Given coordinates x="+x+" y="+y+" are not legal for this FieldMatrix.");
		
		int adjacent = 0;
		if(x>0 && y>0 && getField(x-1,y-1).isMine())
			++adjacent;
		if(y>0 && getField(x,y-1).isMine())
			++adjacent;
		if(x<width_-1 && y>0 && getField(x+1,y-1).isMine())
			++adjacent;
		if(x<width_-1 && getField(x+1,y).isMine())
			++adjacent;
		if(x<width_-1 && y<height_-1 && getField(x+1,y+1).isMine())
			++adjacent;
		if(y<height_-1 && getField(x,y+1).isMine())
			++adjacent;
		if(x>0 && y<height_-1 && getField(x-1,y+1).isMine())
			++adjacent;
		if(x>0 && getField(x-1,y).isMine())
			++adjacent;
			
		return adjacent;
	}
	
	/**
	 * This method randomly puts given number of mines in covered fields.
	 * @param minesQuantity
	 */
	void throwMines(double minesQuantity)
	{
		double coveredPlacesCount  = 0;
		double mineProbability;
		int minesCount = 0;
	
		for(Field i : fields_)
		{
			i.disarm();
			if(!i.isRevealed())
				++coveredPlacesCount;
		}
		
		mineProbability = minesQuantity/coveredPlacesCount;
		
		for(Field i : fields_)
		{
				if(!i.isRevealed() && generator_.nextDouble()<=mineProbability)
				{
					i.putMine();
					++minesCount;
				}
		}
		
		while(minesCount!=minesQuantity)
		{
			int index = generator_.nextInt(width_*height_);
			Field field = fields_.get(index);
			
			if(minesCount<minesQuantity)
			{
				
				if(field.isMine() == false && !field.isRevealed())
				{
					field.putMine();
					++minesCount;
				}
			}
			else
			{
				if(field.isMine() == true && !field.isRevealed())
				{
					field.disarm();
					--minesCount;
				}
			}
		}
		
	}
	
	void drawInConsole()
	{
		for(int i = 0; i<height_; ++i)
		{
			for(int j = 0; j<width_;++j)
			{
				Field temp = getField(j,i);
				if(!temp.isRevealed())
					System.out.print("X ");
				else if(temp.isMine() == true)
					System.out.print("M ");
				else
					System.out.print(adjacentMines(j,i)+" ");
			}
			System.out.println();
		}
	}
	
	

}
