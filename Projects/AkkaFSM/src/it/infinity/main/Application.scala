package it.infinity.main

import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.FSM.StopEvent

object Application {
	
	def main(args: Array[String]): Unit = {
		val system = ActorSystem("actorSystem");
		val actor = system.actorOf(Props[MyActor], name = "actor");
		
		Thread.sleep(3000);
		
		val str: String = "abbba";
		str.foreach( c => {
				val x: String = String.valueOf(c);
				val message: Message = Message(x);
				actor ! message
				actor ! PrintMessage();
			} 
		);
		
		Thread.sleep(3000);
		
		system.terminate();
	}
	
}