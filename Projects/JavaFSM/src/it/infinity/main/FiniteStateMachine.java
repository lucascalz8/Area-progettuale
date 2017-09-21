package it.infinity.main;

import akka.actor.AbstractFSM;
import akka.actor.Props;

public class FiniteStateMachine extends AbstractFSM<States, StateData> {
	{
		startWith(States.INITIAL_STATE, new StateData(""));

		when( States.INITIAL_STATE, matchEvent(Message.class, StateData.class, (message, data) -> {
					String x = message.getX();
					if (x.equals("a")) {
						StateData newStateData = new StateData( stateData().getStr() + message.getX() );
						return ( goTo(States.FIRST_STATE).using(newStateData) );
					} else if (x.equals("b")) {
						return ( goTo(States.ERROR_STATE).using(new StateData("")) );
					}
					
					throw (new IllegalArgumentException("Invalid message."));
				} )
			);
		
		when( States.INITIAL_STATE, matchEvent(ResetMessage.class, StateData.class, (message, data) -> goTo(States.INITIAL_STATE).using(new StateData(""))) );
		
		when( States.INITIAL_STATE, matchEvent(PrintMessage.class, StateData.class, (message, data) -> {
					printInformation();
					return ( stay().using(stateData()) );
				} )
			);
		
		when( States.FIRST_STATE, matchEvent(Message.class, StateData.class, (message, data) -> {
					StateData newStateData = new StateData( stateData().getStr() + message.getX() );
					String x = message.getX();
					if (x.equals("a")) {
						return ( goTo(States.FINAL_STATE).using(newStateData) );
					} else if (x.equals("b")) {
						return ( stay().using(newStateData) );
					}
					
					throw (new IllegalArgumentException("Invalid message."));
				} )
			);

		when( States.FIRST_STATE, matchEvent(PrintMessage.class, StateData.class, (message, data) -> {
					printInformation();
					return ( stay().using(stateData()) );
				} )
			);
		
		when( States.FIRST_STATE, matchEvent(ResetMessage.class, StateData.class, (message, data) -> goTo(States.INITIAL_STATE).using(new StateData(""))) );
		
		when( States.FINAL_STATE, matchEvent(Message.class, StateData.class, (message, data) -> {
					String x = message.getX();
					if (x.equals("a")) {
						return ( goTo(States.ERROR_STATE).using(new StateData("")) );
					} else if (x.equals("b")) {
						return ( goTo(States.ERROR_STATE).using(new StateData("")) );
					}
					
					throw (new IllegalArgumentException("Invalid message."));
				} )
			);

		when( States.FINAL_STATE, matchEvent(PrintMessage.class, StateData.class, (message, data) -> {
					printInformation();
					return ( stay().using(stateData()) );
				} )
			);
		
		when( States.FINAL_STATE, matchEvent(ResetMessage.class, StateData.class, (message, data) -> goTo(States.INITIAL_STATE).using(new StateData(""))) );
		
		when( States.ERROR_STATE, matchEvent(Message.class, StateData.class, (message, data) -> {
					String x = message.getX();
					if (x.equals("a")) {
						return ( stay().using(new StateData("")) );
					} else if (x.equals("b")) {
						return ( stay().using(new StateData("")) );
					}
					throw (new IllegalArgumentException("Invalid message."));
				} )
			);

		when( States.ERROR_STATE, matchEvent(PrintMessage.class, StateData.class, (message, data) -> {
					printInformation();
					return ( stay().using(stateData()) );
				} )
			);
		
		when( States.ERROR_STATE, matchEvent(ResetMessage.class, StateData.class, (message, data) -> goTo(States.INITIAL_STATE).using(new StateData(""))) );
		
		initialize();
	}

	private void printInformation() {
		System.out.println("Info: " + this.stateName().toString() + "," + this.stateData().getStr());
	}
	
	public static Props props() {
		return ( Props.create(FiniteStateMachine.class, () -> new FiniteStateMachine()) );
	}

}
