package Model;

import java.util.Scanner;

public class Model{
	
	
	public static void main(String [] args)
	{
		Board board = new Board(10,10,10);
		Scanner input = new Scanner(System.in);
		boolean quit = false;
		while(quit != true)
		{
			board.drawInConsole();
			int x = Integer.parseInt(input.next());
			int y = Integer.parseInt(input.next());
			board.revealField(x-1, y-1);
			
			if(board.endGame())
			{	board.revealAll();
				board.drawInConsole();
				quit = true;
			}
			
		}
		input.close();
		
	}

}
