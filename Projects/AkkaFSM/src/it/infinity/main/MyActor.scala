package it.infinity.main

import akka.actor.Actor
import akka.actor.FSM

case class Message(x: String) { }
case class PrintMessage() { }

trait State { }

case object InitialState extends State;
case object FirstState extends State;
case object FinalState extends State;
case object ErrorState extends State;

case class StateData(str: String);

class MyActor extends Actor with FSM[State, StateData] {
	startWith(InitialState, StateData(""));
	
	when(InitialState) {
		case Event(message: Message, data: StateData) => {
			(message.x) match {
				case "a" => goto(FirstState) using StateData(data.str + "a");
				case "b" => goto(ErrorState) using StateData("");
			}
		}
		case Event(printMessage: PrintMessage, data: StateData) => {
			println("INFO: " + stateName + ", " + data.str);
			stay();
		}
	}
	
	when(FirstState) {
		case Event(message: Message, data: StateData) => {
			(message.x) match {
				case "a" => goto(FinalState) using StateData(data.str + "a");
				case "b" => stay() using StateData(data.str + "b");
			}
		}
		case Event(printMessage: PrintMessage, data: StateData) => {
			println("INFO: " + stateName + ", " + data.str);
			stay();
		}
	}
	
	when(FinalState) {
		case Event(message: Message, data: StateData) => {
			(message.x) match {
				case "a" => goto(ErrorState) using StateData("");
				case "b" => goto(ErrorState) using StateData("");
			}
		}
		case Event(printMessage: PrintMessage, data: StateData) => {
			println("INFO: " + stateName + ", " + data.str);
			stay();
		}
	}
	
	when(ErrorState) {
		case Event(message: Message, data: StateData) => {
			stay() using StateData("");
		}
		case Event(printMessage: PrintMessage, data: StateData) => {
			println("INFO: " + stateName + ", " + data.str);
			stay();
		}
	}
	
	whenUnhandled {
		case Event(event, stateData) => println("unhandled"); stay();
	}
	
	onTransition {
		case x -> y => {
			println("transition from " + x + " to " + y);
		}
	}
	
	onTermination {
		case StopEvent(FSM.Normal, state, stateData) => {
			println("Normal termination: " + state + ", " + stateData);
		}
	}
	
	initialize();
	
}