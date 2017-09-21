package it.infinity.model

import com.typesafe.config.ConfigFactory

import akka.actor._
import java.io.File

object Application {

	def main(args: Array[String]): Unit = {
		val file = new File("");
		val config = ConfigFactory.parseFile(new File("./local/application.conf"));
		val system = ActorSystem("local", config);
		val local1 = system.actorOf(Props[LocalActor], name = "local1")
		val local2 = system.actorOf(Props[LocalActor], name = "local2")
		val connection = "akka.tcp://remote@localhost:2555/user/remoteActor";
		val remote = system.actorSelection(connection);
		
		remote ! Subscribe(local1, 0);
		remote ! Subscribe(local2, 0);
		
		remote ! Publish("Hello actor!");
	}
	
}

class LocalActor extends Actor {

	def receive = {
		case message: String => { 
			println("(" + self.path.name + ")" + " " + message);
		}
	}
	
}

case class Subscribe(val actorRef: ActorRef, val n: Int) { }
case class Publish(val content: String) { }
