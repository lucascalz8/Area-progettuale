package it.infinity.main

import akka.actor.Actor
import akka.actor.Props
import akka.actor.OneForOneStrategy
import akka.actor.SupervisorStrategy._

class FirstActor extends Actor {
	
	override def preStart() = {
		context.actorOf(Props[SecondActor], name = "secondActor");
	}
	
	override def receive = {
		case msg: String =>
			println("Start handling message...");
			Thread.sleep(7000);
			println("Message: " + msg);
		case n: Integer => 
			throw ( new IllegalArgumentException("") );
	}
	
	override def supervisorStrategy = OneForOneStrategy() {
		case _: IllegalArgumentException =>
			println("Actor resumed.");
			Resume; 
	}
	
}