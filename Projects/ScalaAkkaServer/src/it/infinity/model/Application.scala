package it.infinity.model

import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import java.io.File

object Application {

	def main(args: Array[String]): Unit = {
		val config = ConfigFactory.parseFile(new File("./remote/application.conf"));
		val system = ActorSystem("remote", config);
		val remoteActor = system.actorOf(Props[RemoteActor], name = "remoteactor")
	}

	class RemoteActor extends Actor {
		def receive = {
			case msg: String =>
				println("string: " + msg);
				sender ! msg;
			case Message(content) => println("message: " + content); sender ! content;
		}
	}
	
	case class Message(val content:String) { }

}