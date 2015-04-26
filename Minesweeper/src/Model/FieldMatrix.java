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
	
	public FieldMatrix(int width,int height)
	{
		width_ = width;
		height_ = height;
		generator_ = new Random();
		fields_ = new ArrayList<Field>();
		
		for (int i = 0; i<width_*height_; ++i)
		{
			fields_.add(new Field());
		}
	}
	
	public Field getField(int x, int y)
	{
		return fields_.get(x+y*width_);
	}
	
	public boolean correctCoordinates(int x, int y)
	{
		if(x>=0 && y>=0 && x<width_ && y<height_)
			return true;
		return false;
	}
	
	public int adjacentMines(int x, int y)
	{
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
	
	void throwMines(double minesQuantity)
	{
		double coveredPlacesCount  = 0;
		double mineProbability;
		int minesCount = 0;
	
		System.out.println("MINES!");
		for(Field i : fields_)
		{
			i.disarm();
			if(i.getState() == Field.State.COVERED)
				++coveredPlacesCount;
		}
		
		mineProbability = minesQuantity/coveredPlacesCount;
		
		for(Field i : fields_)
		{
			if(i.getState() == Field.State.COVERED)
			{
				if(generator_.nextDouble()<=mineProbability)
				{
					i.putMine();
					++minesCount;
				}
			}
		}
		
		while(minesCount!=minesQuantity)
		{
			int index = generator_.nextInt(width_*height_);
			Field field = fields_.get(index);
			
			if(minesCount<minesQuantity)
			{
				
				if(field.isMine() == false && field.getState() == Field.State.COVERED)
				{
					field.putMine();
					++minesCount;
				}
			}
			else
			{
				if(field.isMine() == true && field.getState() == Field.State.COVERED)
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
				if(temp.getState() == Field.State.COVERED)
					System.out.print("X ");
				else if(temp.isMine() == true)
					System.out.print("M ");
				else
					System.out.print(adjacentMines(j,i)+" ");
			}
			System.out.println();
		}
	}
	
	int getWidth() {return width_;}
	int getHeight(){return height_;}

}
