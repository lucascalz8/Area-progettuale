package it.infinity.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import it.infinity.controller.Manager;
import it.infinity.message.AddEventMessage;
import it.infinity.message.GetEventsMessage;

public class RemoteActor extends AbstractActor {
	private Manager manager;
	
	public RemoteActor() {
		manager = Manager.getInstance();
	}
	
	public Manager getManager() {
		return (manager);
	}
	
	@Override
	public Receive createReceive() {
		return (receiveBuilder()
				.match( AddEventMessage.class, message -> handleMessage(message) )
				.match( GetEventsMessage.class, message -> handleMessage(message) )
				.build()
			   );
	}
	
	private void handleMessage(AddEventMessage message) {
		
	}
	
	private void handleMessage(GetEventsMessage message) {
		
	}
	
	public static Props props() {
		return ( Props.create(RemoteActor.class, () -> new RemoteActor()) );
	}
	
}