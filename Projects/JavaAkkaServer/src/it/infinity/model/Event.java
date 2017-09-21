package it.infinity.model;

import java.io.Serializable;

public class Event implements Serializable {
	private static final long serialVersionUID = 8888L;
	
	private long identifier;
	private String name;
	private String description;
	private Location location;
	
	public Event(long identifier, String name, String description, Location location) {
		this.identifier = identifier;
		this.name = name;
		this.description = description;
		this.location = location;
	}

	public long getIdentifier() {
		return (identifier);
	}
	
	public String getName() {
		return (name);
	}
	
	public String getDescription() {
		return (description);
	}
	
	public Location getLocation() {
		return (location);
	}
	
}
