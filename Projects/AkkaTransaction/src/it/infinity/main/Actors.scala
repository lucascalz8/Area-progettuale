package it.infinity.main

import scala.concurrent.stm._

import akka.actor._
import akka.transactor._
import akka.util.Timeout
import java.util.concurrent.TimeUnit

case class Transaction(friend: Option[ActorRef] = None) { }
case object PrintCount { }

class SumActor extends Actor {
	val count = Ref(0);
	
	def receive = {
		case coordinated @ Coordinated(Transaction(friend)) ⇒ {
			friend.foreach { ref ⇒ ref ! coordinated(Transaction()) };
			coordinated.atomic(implicit transaction ⇒ {
				// throw (new IllegalArgumentException("Exception"));
				Thread.sleep(5000);
				count.transform(_ + 1);
			});
		}
		case PrintCount ⇒ println("count: " + count.single.get);
	}
}

class SubActor extends Actor {
	val count = Ref(0);

	def receive = {
		case coordinated @ Coordinated(Transaction(friend)) ⇒ {
			friend.foreach { ref => ref ! coordinated(Transaction()) };
			coordinated.atomic(implicit transaction ⇒ {
				// throw (new IllegalArgumentException("Exception"));
				Thread.sleep(3000);
				count.transform(_ - 1);
			});
		}
		case PrintCount ⇒ println("count: " + count.single.get);
	}
}