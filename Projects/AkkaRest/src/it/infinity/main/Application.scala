package it.infinity.main

import java.io.File

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.io.StdIn

import com.typesafe.config.ConfigFactory

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport.sprayJsonMarshaller
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport.sprayJsonUnmarshaller
import akka.http.scaladsl.marshalling.ToResponseMarshallable.apply
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directive.addByNameNullaryApply
import akka.http.scaladsl.server.Directive.addDirectiveApply
import akka.http.scaladsl.server.Directives.LongNumber
import akka.http.scaladsl.server.Directives._enhanceRouteWithConcatenation
import akka.http.scaladsl.server.Directives._segmentStringToPathMatcher
import akka.http.scaladsl.server.Directives.as
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.Directives.entity
import akka.http.scaladsl.server.Directives.get
import akka.http.scaladsl.server.Directives.onComplete
import akka.http.scaladsl.server.Directives.onSuccess
import akka.http.scaladsl.server.Directives.path
import akka.http.scaladsl.server.Directives.pathPrefix
import akka.http.scaladsl.server.Directives.post
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.RouteResult.route2HandlerFlow
import akka.http.scaladsl.server.directives.OnSuccessMagnet.apply
import akka.stream.ActorMaterializer
import spray.json.DefaultJsonProtocol.LongJsonFormat
import spray.json.DefaultJsonProtocol.StringJsonFormat
import spray.json.DefaultJsonProtocol.jsonFormat2

object Application {

	implicit val itemFormat = jsonFormat2(Item);
	
	private val data = new Data();

	def main(args: Array[String]): Unit = {
		val file = new File("./application.conf");
		val config = ConfigFactory.parseFile(file);
		implicit val system = ActorSystem("system", config);
		implicit val materializer = ActorMaterializer();

		val route =
			get {
				pathPrefix("item" / LongNumber) { id =>
					val maybeItem: Future[Option[Item]] = findItem(id);

					onSuccess(maybeItem) {
						case Some(item) => complete(maybeItem);
						case None => complete(StatusCodes.NotFound)
					}
				}
			} ~
			post {
				path("create") {
					entity(as[Item]) { item =>
						val saved: Future[Unit] = saveItem(item);
						onComplete(saved) {
							done => complete(StatusCodes.Created);
						}
					}
				}
			}
			
		val bindingFuture = Http().bindAndHandle(route, "localhost", 8080);
		println("Server online at 'http://localhost:8080'");
		println("Presso RETURN to stop...");
		StdIn.readLine();

		bindingFuture.map { serverBinding => serverBinding.unbind() }
		bindingFuture.onComplete { serverBinding => system.terminate() }
	}

	def findItem(id: Long) = {
		val future = Future { data.getItem(id) }
		future;
	}

	def saveItem(item: Item) = {
		val future = Future { data.addItem(item) }
		future;
	}
}