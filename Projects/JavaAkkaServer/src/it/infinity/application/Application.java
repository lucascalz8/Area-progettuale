package it.infinity.application;

import java.io.File;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSystem;
import it.infinity.actor.RemoteActor;
import it.infinity.view.MainFrame;

public class Application {
	private static final String APPLICATION_NAME = "Application";
	
	public static void main(String[] args) {
		File configurationFile = new File("./configuration/application.conf");
		Config config = ConfigFactory.parseFile(configurationFile);
		ActorSystem system = ActorSystem.create(APPLICATION_NAME, config);
		
		system.actorOf(RemoteActor.props(), "remoteActor");
		
		new MainFrame().setVisible(true);
	}
	
}