/**
 * 
 */
package gameObjects;

import java.awt.image.BufferedImage;


public class Character implements GameObject {

	/**
	 * 
	 */
	BufferedImage [] visual;
	int [] position;
	int [] hitbox;
	int [] size;
	
	
	public Character() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int[] getPosition() {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public void setPosition(int[] newPosition) {
		// TODO Auto-generated method stub
		this.position = newPosition;
	}

	@Override
	public BufferedImage getVisual(int current) {
		// TODO Auto-generated method stub
		return visual [current];
	}

	@Override
	public int[] getHitbox() {
		// TODO Auto-generated method stub
		return hitbox;
	}

	@Override
	public void setHitbox(int[] newHitbox) {
		// TODO Auto-generated method stub
		this.hitbox = newHitbox;
	}
	
	public int [] getSize()
	{
		
		return size;
	}
		
	public void setSize(int [] newSize)
	{
		this.size = newSize;
		
	}

}
