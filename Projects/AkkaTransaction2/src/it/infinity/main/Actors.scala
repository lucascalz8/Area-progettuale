package it.infinity.main

import scala.concurrent.stm._

import akka.actor._
import akka.transactor._
import akka.util.Timeout
import java.util.concurrent.TimeUnit

case object StartTransaction {}
case object PrintResult {}

case object Process {}
case object PrintCount {}

class Manager extends Transactor {
	var counter1: ActorRef = null;
	var counter2: ActorRef = null;

	override def preStart = {
		counter1 = context.actorOf(Props[SumActor], name = "counter1");
		counter2 = context.actorOf(Props[SubActor], name = "counter2");
	}

	override def coordinate = {
		case StartTransaction ⇒ { sendTo(counter1 -> Process, counter2 -> Process) }
		case PrintResult ⇒ { sendTo(counter1 -> PrintCount, counter2 -> PrintCount) }
	}

	def atomically = implicit txn ⇒ {
		case _ ⇒ // nothing...
	}

}

class SumActor extends Transactor {
	val count = Ref(0);

	def atomically = implicit txn ⇒ {
		case Process ⇒ count.transform { x => x + 1 };
		case PrintCount ⇒ println(count.single.get);
	}

}

class SubActor extends Transactor {
	val count = Ref(0);

	def atomically = implicit txn ⇒ {
		case Process ⇒ 
			// throw (new IllegalArgumentException("Exception")); 
			count.transform { x => x - 1 };
		case PrintCount ⇒ println(count.single.get);
	}

}