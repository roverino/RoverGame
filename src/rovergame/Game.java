package rovergame;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import rovergame.graphics.Assets;
import rovergame.states.GameState;
import rovergame.states.MenuState;
import rovergame.states.State;

public class Game implements Runnable {

	private Display display;
	public int width, height;
	private String title; 
	private Thread thread;
	private boolean running=false;
	
	private BufferStrategy bs;
	private Graphics g;
	
	//States
	private State gameState;
	private State menuState;
	
	
	public Game (String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
	}

	private void init() {
		display = new Display(title, width, height);
		Assets.init();
		
		gameState = new GameState();
		gameState = new MenuState();
		State.setState(gameState); //gamestate is now the game
		
	}

	private void tick() {
		if(State.getState() != null) //if a gamestate exists
			State.getState().tick(); //tick that gamestate
	}
	
	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if (bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics(); //create the ability to draw
		g.clearRect(0, 0, width, height); //clear screen every render
		//Draw Here
		
		
		g.drawImage(Assets.map, -1750, -1226, null);
		g.drawImage(Assets.player, 300, 300, null);
		g.drawImage(Assets.rock1, 0, 0, null);
		g.drawImage(Assets.rock1, 100, 100, null);
		g.drawImage(Assets.rock2, 300, 150, null);
		g.drawImage(Assets.rock3, 500, 600, null);
		
	
		if(State.getState() != null) //if a gamestate exists
			State.getState().render(g); //render that gamestate 


		
		
		//End Draw
		bs.show();
		g.dispose();
	}
	
	public void run() {
		
		init();
		
		//this is to stabilize frames per second, on slow/medium/fast computers
		int fps = 60; //max frames per second
		double timePerTick = 1000000000/fps;
		// this is 1 second (in nanoseconds) divided by frames per second
		// this equals maximum amount of time allowed to have to perform the 60fps action
		// max amount of time to run tick and render methods
		double delta = 0; //times we need to render to meet fps standards
		long now; //initialized in our game loop 
		long lastTime = System.nanoTime(); //current computer time
		long timer = 0; //time until we get to 1 second (so we can print our fps)
		int ticks = 0; //how many ticks were in a second (fps)
		
		while (running) {
			now = System.nanoTime(); //current time of our computer
			delta += (now - lastTime) / timePerTick; 
			//delta = adds (amount of time passed since this line was run since the last loop)/(max amount of alotted time)
			//delta = how much time we have until we must call tick and render again (bc frame cap)
			timer += now - (lastTime); //adding amount of time since above block was called?
			lastTime = now; //current time at the time this block was run
			
			if(delta >= 1) { //if delta is 1 or greater, that means we need to tick and render to achieve 60fps	
			tick();
			render();
			ticks++;
			delta--; //meaning we ticked and rendered so we can minus by 1
			}
			
			if (timer >= 1000000000) { //shows fps every second
				System.out.println("Ticks and Frames: " + ticks);
				ticks = 0;
				timer = 0; //these two = 0 to reset and constantly give us the ticks/sec
			}
		}
		
		stop();
		
	}
	public synchronized void start() {
		if (running)
			return;
		running=true;
		thread = new Thread(this);
		thread.start();
		
	}
	
	public synchronized void stop() {
		if (!running)
			return;
		running=false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
	}
}
