package it.infinity.main;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class Main {
	private static final String APPLICATION_NAME = "Application";

	public static void main(String[] args) throws Exception {
		ActorSystem system = ActorSystem.create(APPLICATION_NAME);
		ActorRef ref = system.actorOf(FiniteStateMachine.props(), "fsm");
		
		List<String> list = new ArrayList<String>();
		list.add("abbba");
		list.add("baba");
		list.add("abaa");
		list.add("abbbab");
		list.add("aaba");
		
		for (String str : list) {
			System.out.println("String: " + str);
			
			for ( char chr : str.toCharArray() ) {
				String x = String.valueOf(chr);
				Thread.sleep(1000);
				ref.tell( new Message(x), null );
				Thread.sleep(1000);
				ref.tell( new PrintMessage(), null);
			}
			
			Thread.sleep(1000);
			ref.tell( new ResetMessage() , null);
			Thread.sleep(1000);
			System.out.println();
		}
		
		system.terminate();
	}
}
