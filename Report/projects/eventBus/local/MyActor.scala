package it.infinity.main

import akka.actor.Actor

class MyActor extends Actor {
	
	def receive = {
		case message: String => 
			println("Message: " + message + ", " + self.path.name);
	}
	
}