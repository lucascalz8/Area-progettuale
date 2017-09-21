package it.infinity.message;

import java.io.Serializable;
import java.util.List;

import it.infinity.model.Event;

public class GetEventsMessage implements Serializable {
	private static final long serialVersionUID = 8888L;
	
	private List<Event> events;
	
	public GetEventsMessage(List<Event> events) {
		this.events = events;
	}
	
	public List<Event> getEvents() {
		return (events);
	}

}
