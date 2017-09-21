package it.infinity.model;

import akka.actor.AbstractActor;
import akka.actor.Props;
import it.infinity.message.GetEventsMessage;

public class LocalActor extends AbstractActor {

	public static Props props() {
		return Props.create(LocalActor.class, () -> new LocalActor());
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(GetEventsMessage.class, message -> getEvents(message))
				.build();
	}
	
	private void getEvents(GetEventsMessage message) {
		for (Event event : message.getEvents())
			System.out.println(event.getName() + " " + event.getDescription());
	}

}