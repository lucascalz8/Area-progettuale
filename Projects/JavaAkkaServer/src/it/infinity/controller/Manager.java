package it.infinity.controller;

import java.util.ArrayList;
import java.util.List;

import it.infinity.model.Event;

public class Manager {
	private static Manager manager;
	
	private List<Event> events;
	
	private Manager() {
		events = new ArrayList<Event>();
	}
	
	public static Manager getInstance() {
		if ( manager == null )
			manager = new Manager();
		return (manager);
	}
	
	public List<Event> getEvents() {
		return (events);
	}
	
	public void addEvent(Event event) {
		getEvents().add(event);
	}
	
}
