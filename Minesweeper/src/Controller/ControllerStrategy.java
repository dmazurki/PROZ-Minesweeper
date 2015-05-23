package Controller;

import Event.Event;

/**
 * Abstract base class for strategies corresponding to concrete game events.
 * @author Damian Mazurkiewicz
 */
public abstract class ControllerStrategy {
	
	/**@param e Event for which we do something.*/
	public abstract void execute(Event e);
}
