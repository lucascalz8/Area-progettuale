package it.infinity.model;

import java.io.File;
import java.util.ArrayList;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import it.infinity.message.AddEventMessage;
import it.infinity.message.GetEventsMessage;

public class Application {
	public static void main(String[] args) {
		Config config = ConfigFactory.parseFile(new File("./configuration/application.conf"));
		ActorSystem system = ActorSystem.create("local", config);
		ActorRef local = system.actorOf(LocalActor.props(), "localActor");
		
		ActorSelection selection = system.actorSelection("akka.tcp://Application@localhost:8888/user/remoteActor");
		selection.tell("Message", local);
		selection.tell(new AddEventMessage( new Event(1, "name", "description", new Location(1, 1)) ), local);
		selection.tell(new AddEventMessage( new Event(2, "name2", "description2", new Location(1, 2)) ), local);
		selection.tell(new GetEventsMessage( new ArrayList<Event>() ), local);
	}
}