package it.infinity.main

import akka.actor.ActorSystem
import akka.actor.Props

object Main {

	def main(args: Array[String]): Unit = {
		val system = ActorSystem("actorSystem");
		val actor1 = system.actorOf(Props[MyActor], name = "actor1")
		val actor2 = system.actorOf(Props[MyActor], name = "actor2")
		
		val messageBus = new MessageBus();
		messageBus.subscribe(actor1, false);
		messageBus.subscribe(actor2, true);
		
		messageBus.publish("Ciao");
		messageBus.publish("");
		
		Thread.sleep(3000);
		
		system.terminate();
	}

}