package rovergame.graphics;

import java.awt.image.BufferedImage;

public class Assets {
	
		private static final int width = 64, height = 64;

		public static BufferedImage rock1, rock2, rock3, map, player;
		
		public static void init() {
			SpriteSheet rockSheet = new SpriteSheet(ImageLoader.loadImage("/textures/rocksheet.png"));
			rock1 = rockSheet.crop(0,0,width,height);
			rock2 = rockSheet.crop(width, 0, width, height);
			rock3 = rockSheet.crop(width*2, 0, width, height);
			
			player = ImageLoader.loadImage("/textures/roverprototype2.png");
			map = ImageLoader.loadImage("/textures/marssurface.png");
		}
}
