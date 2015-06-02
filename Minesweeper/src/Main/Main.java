package Main;

import Controller.Controller;
import Model.Model;
import View.View;

/**
 * Here is the main method of the game. It creates model and view instance, 
 * then links them with Controller class. Subsequently it runs loop in conroller
 * that takes the events from event queue and handles them properly.
 * @author Damian Mazurkiewicz
 */
public class Main 
{
		public static void main(String[] args) 
		{
				Model model = new Model();
				View view = new View();
				Controller controller = new Controller(model,view);
				controller.run();
		}
}
