package it.infinity.model

import akka.actor.Actor
import akka.actor.ActorRef

class RemoteActor extends Actor {
	private val messageBus: MessageBus = new MessageBus();
	
	def receive = {
		case Subscribe(actorRef, n) => messageBus.subscribe(actorRef, n);
		case Publish(message) => messageBus.publish(message);
	}
}

case class Subscribe(val actorRef: ActorRef, val n: Int) { }
case class Publish(val content: String) { }