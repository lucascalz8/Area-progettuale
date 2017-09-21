package it.infinity.main

import akka.actor.Actor

class SecondActor extends Actor {
	
	override def receive = {
		case msg: String => 
			println("Message: " + msg);
		case n: Integer => 
			throw ( new IllegalArgumentException("") );
	}
	
}