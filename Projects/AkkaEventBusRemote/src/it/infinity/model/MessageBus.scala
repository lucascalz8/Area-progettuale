package it.infinity.model

import akka.event.EventBus
import akka.event.LookupClassification
import javax.sql.StatementEvent
import akka.event.ActorEventBus
import akka.actor.ActorRef

class MessageBus extends EventBus with LookupClassification with ActorEventBus {
	type Event = String
	type Classifier = Int
	override type Subscriber = ActorRef

	def mapSize() = 1
	
	protected def classify(event: String) = {
		0;
	}
	
	protected def publish(event: String, subscriber: Subscriber) {
		subscriber ! event;
	}
	
}