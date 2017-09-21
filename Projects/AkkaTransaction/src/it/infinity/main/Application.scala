package it.infinity.main

import scala.concurrent.Await

import akka.actor.ActorSystem
import akka.actor.Props
import akka.transactor.Coordinated
import akka.util.Timeout
import java.util.concurrent.TimeUnit

object Application {

	def main(args: Array[String]): Unit = {
		val system = ActorSystem("app")

		val counter1 = system.actorOf(Props[SumActor], name = "counter1")
		val counter2 = system.actorOf(Props[SubActor], name = "counter2");
		
		implicit val timeout = Timeout(60, TimeUnit.SECONDS);
		
		val incrementMessage = Transaction(Some(counter2));
		counter1 ! Coordinated(incrementMessage);
		
		Thread.sleep(500);
		counter1 ! PrintCount;
		counter2 ! PrintCount;
	}

}