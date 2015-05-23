package Model;

import java.util.ArrayList;
import java.util.Random;
/**
 * This class makes operating on field Matrix easier.
 * It holds two dimenstional array of fields.
 * @author Damian Mazurkiewicz
 */
public class FieldMatrix {
	/**All fields in the matrix.*/
	ArrayList<Field> fields_;
	/**How many columns the matrix has.*/
	int width_;
	/**How many rows the matrix has.*/
	int height_;
	/**Generator of pseudo-random numbers used to throw mines randomly on the matrix fields.*/
	Random generator_;
	
	
	/**
	 * Constructor that creates Field matrix and fills it with fields, initially
	 * all fields are covered and not mines.
	 * @param width  width of field matrix (how many fields horizontally)
	 * @param height  height of field matrix (how many fields vertically)
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
	
	/**@return Number of columns in the matrix.*/
	int getWidth() 
	{
		return width_;
	}
	
	/** @return Number of rows in the matrix. */
	int getHeight()
	{
		return height_;
	}
	
	/**
	 * Function that allows accessing fields in the matrix.
	 * @param x  number of columns.
	 * @param y  number of rowr.
	 * @return Field at given coordinates.
	 * @throws IllegalArgumentException if requested fields is out of bounds of the matrix.
	 */
	public Field getField(int x, int y) throws IllegalArgumentException
	{
		if(correctCoordinates(x,y))
			return fields_.get(x+y*width_);
		else throw new IllegalArgumentException("Given coordinates x="+x+" y="+y+" are not legal for this FieldMatrix.");
	}
	
	/**
	 * Checks if field with coordinates x, y exists on the field matrix.
	 * @param x column number.
	 * @param y row number.
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
	 * @param x column number.
	 * @param y row number.
	 * @return Number of adjacent fields with mines. 
	 * @throws IllegalArgumentException if requested fields is out of bounds of the matrix.
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
	 * @param minesQuantity How many mines has to be in the matrix.
	 * @throws IllegalArgumentException if requested fields is out of bounds of the matrix.
	 */
	void throwMines(double minesQuantity) throws IllegalArgumentException
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
		
		if(coveredPlacesCount<minesQuantity)
			throw new IllegalArgumentException("It is impossible to throw that number of mines on this Matrix.");
		
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

}
