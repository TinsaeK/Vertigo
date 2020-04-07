package gameObjects;

import java.awt.image.BufferedImage;

public interface GameObject {

	int [] getPosition();
	
	void setPosition(int [] newPosition);
	
	BufferedImage getVisual(int current);
	
	int [] getHitbox();
	
	void setHitbox(int [] hitbox);
	
	int [] getSize();
	
	void setSize(int [] size);
	
}
