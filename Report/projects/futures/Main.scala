package it.infinity.main

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Success
import scala.util.Failure

object Application {
	
	def main(args: Array[String]): Unit = {
		simple();
		println("");
		pipeline();
		println("");
		combination();
	}
	
	private def simple(): Unit = {
		val future: Future[String] = Future {
			Thread.sleep(3000); // long-time operation
			"Hello world!";
		}
		
		future.onComplete { 
			case Success(x) => println(x)
			case Failure(exception) => println(exception);	
		}
		
		println("Waiting...");
		Thread.sleep(5000); // doing something else
	}
	
	private def pipeline(): Unit = {
		val future1: Future[String] = Future {
			Thread.sleep(3000); // long-time operation
			"Hello world!";
		}
		
		val future2: Future[Int] = future1.map { x =>
			Thread.sleep(3000); // long-time operation
			1;
		}
		
		future2.onComplete { 
			case Success(x) => println(x)
			case Failure(exception) => println(exception);	
		}
		
		println("Waiting...");
		Thread.sleep(7000); // doing something else
	}
	
	private def combination(): Unit = {
		val first = 1;
		val second = 2;
		val third = 3;
		val fourth = 4;
		
		val future1: Future[Int] = Future {
			Thread.sleep(5000); // long-time operation
			first;
		}
		
		val future2: Future[Int] = Future {
			Thread.sleep(6000); // long-time operation
			second;
		}
		
		val future3: Future[Int] = Future { third }
		val future4: Future[Int] = Future { fourth }
		
		val future5 = Future.firstCompletedOf(List(future1, future2));
		val future6 = future3.zip(future4).map {
			case (a, b) => a + b;
		}
		
		val future7 = future5.zip(future6).map {
			case (a, b) => a + b;
		}
		
		future7.onComplete { 
			case Success(x) => println(x);
			case Failure(exception) => println(exception);	
		}
		
		println("Waiting for result...");
		Thread.sleep(7000); // doing something else
	}
	
}