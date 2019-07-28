package rovergame.states;

import java.awt.Graphics;

//game states are anything like the game, main menu, settings menu, etc 
public abstract class State {
	
	//GAMESTATE MANAGER
	private static State currentState = null;
	
	public static void setState(State state) {
		currentState = state;
	}
	
	public static State getState() {
		return currentState;
	}
	
	
	//CLASS
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
}
