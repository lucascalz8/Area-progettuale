package it.infinity.main

import akka.event.EventBus
import akka.event.LookupClassification
import javax.sql.StatementEvent
import akka.event.ActorEventBus
import akka.actor.ActorRef

class MessageBus extends EventBus with LookupClassification with ActorEventBus {
	type Event = String
	type Classifier = Boolean
	override type Subscriber = ActorRef

	def mapSize() = 2
	
	protected def classify(event: String) = {
		!event.isEmpty();
	}
	
	protected def publish(event: String, subscriber: Subscriber) {
		subscriber ! event;
	}
	
}