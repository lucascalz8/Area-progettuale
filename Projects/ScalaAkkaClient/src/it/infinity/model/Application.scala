package it.infinity.model

import com.typesafe.config.ConfigFactory

import akka.actor._
import java.io.File

object Application {

	def main(args: Array[String]): Unit = {
		val config = ConfigFactory.parseFile(new File("./local/application.conf"));
		val system = ActorSystem("local", config);
		val localActor = system.actorOf(Props[LocalActor], name = "localactor")
	}

	class LocalActor extends Actor {
		var counter = 0

		override def preStart() {
			val selection = context.actorSelection("akka.tcp://remote@192.168.1.127:2555/user/remoteactor");
			selection.tell(Message("Hello"), self);
		}

		def receive = {
			case msg: String =>
				println("LocalActor received message: " + msg)
				if (counter < 5) {
					sender ! "Hello back to you"
					counter += 1
				}
		}
	}

	case class Message(val content: String) {}
}



