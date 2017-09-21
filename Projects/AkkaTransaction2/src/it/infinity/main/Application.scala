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

		val manager = system.actorOf(Props[Manager], name = "manager")
		manager ! StartTransaction;
		Thread.sleep(3000);
		manager ! PrintResult;
	}

}