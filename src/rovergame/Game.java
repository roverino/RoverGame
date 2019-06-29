package rovergame;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import rovergame.graphics.ImageLoader;
import rovergame.graphics.SpriteSheet;

public class Game implements Runnable {

	private Display display;
	public int width, height;
	private String title; 
	private Thread thread;
	private boolean running=false;
	
	private BufferStrategy bs;
	private Graphics g;
	
	private BufferedImage sheet1;
	private BufferedImage map;
	private SpriteSheet rockSheet;
	
	public Game (String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
	}

	private void init() {
		display = new Display(title, width, height);
		map = ImageLoader.loadImage("/textures/marssurface.png");
		sheet1 = ImageLoader.loadImage("/textures/rocksheet.png");
		rockSheet = new SpriteSheet(sheet1);
		
	}
	
	private void tick() {
		
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
		
		g.drawImage(map,0,0,null);
		g.drawImage(rockSheet.crop(0,0,64,64),100,100,null);
		
		
		//End Draw
		bs.show();
		g.dispose();
	}
	
	public void run() {
		init();
		
		while (running) {
			tick();
			render();
		}
		
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
