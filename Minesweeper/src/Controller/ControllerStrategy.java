package Controller;

import Event.Event;

public abstract class ControllerStrategy {
	public abstract void execute(Event e);
}
