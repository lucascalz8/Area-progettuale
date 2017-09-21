package it.infinity.message;

import java.io.Serializable;

import it.infinity.model.Event;

public class AddEventMessage implements Serializable {
	private static final long serialVersionUID = 8888L;
	
	private Event event;
	
	public AddEventMessage(Event event) {
		this.event = event;
	}
	
	public Event getEvent() {
		return (event);
	}
	
}
