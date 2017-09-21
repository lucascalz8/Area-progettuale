package it.infinity.main

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import akka.stream.scaladsl.Tcp
import akka.stream.scaladsl.Tcp.ServerBinding

object Application {

	def main(args: Array[String]): Unit = {
		implicit val system = ActorSystem();
		implicit val actorMaterializer = ActorMaterializer();

		val binding: Future[ServerBinding] =
			Tcp().bind("127.0.0.1", 8888).to(Sink.ignore).run();

		binding.map { serverBinding =>
			serverBinding.unbind() onComplete {
				case _ => // ...
			}
		}
		
	}

}