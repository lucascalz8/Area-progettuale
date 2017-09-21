package it.infinity.main

import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.PoisonPill
import akka.actor.DeadLetter

object Main {

	def main(args: Array[String]): Unit = {
		val system = ActorSystem("actorSystem");
		val firstActor = system.actorOf(Props[FirstActor], name = "firstActor")
		val secondActor = system.actorSelection("user/firstActor/secondActor");
		firstActor ! "Hello";
		Thread.sleep(1000);
		secondActor ! 3;
		println("Message sent");
		// Thread.sleep(3000);
		// secondActor ! "ciao";
		
		
		Thread.sleep(7000);
		system.terminate();
	}

}