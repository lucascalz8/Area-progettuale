package it.infinity.main

import akka.event.EventBus
import akka.event.LookupClassification
import javax.sql.StatementEvent
import akka.event.ActorEventBus
import akka.actor.ActorRef

class OtherMessageBus extends EventBus with LookupClassification with ActorEventBus {
	type Event = String
	type Classifier = String
	override type Subscriber = ActorRef
	
	def mapSize() = 2
	
	protected def classify(event: String) = {
		"actor1";
	}

	protected def publish(event: String, subscriber: Subscriber) {
		subscriber ! event;
	}
	
}