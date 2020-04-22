/**
 * 
 */
package gameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Enemy implements GameObject {

	/**
	 * 
	 */
	BufferedImage [] visual;
	int [] position;
	int [] hitbox;
	int [] size;
	int currentState;
	//possible version: 0 = idle, 1 = walkingA, 2 = walkingB, 3 = runningA, 4 = runningB, 5 = jumping
  //alt 1: idle = 0, walking = 1 or -1,
  int hitPoints;
	
	public Enemy() {
    size = new int[2];
    size[0] = 20;
    size[1] = 20;
    hitPoints = 100;
    visual = new BufferedImage[1];
    visual[0] = new BufferedImage(size[0],size[1],BufferedImage.TYPE_INT_RGB);
    Graphics2D g = visual[0].createGraphics();
    g.setColor(Color.RED);;
    g.fillOval(0, 0, size[0], size[1]);

    position = new int[2];
    position[0] = (int) Math.random()*100;
    position[1] = (int) Math.random()*100;
	}

	@Override
	public int[] getPosition() {
		//get method for the position of the character 
		//to be mesured from the ____(top left or bottom left?)
		return position;
	}

	@Override
	public void setPosition(int[] newPosition) {
		// Set method for the character position
		this.position = newPosition;
	}

	@Override
	public BufferedImage getVisual(int currentState, int count) {
		// Geting one visual based on character state.
		return visual [currentState];
	}
	
	@Override
	public BufferedImage getVisualSet(int current){
		return visual [currentState];
	}
	
	@Override
	public int[] getHitbox() {
		// get method for the hitbox of the character
		// 2 ints distance from position start point
		// 2 ints hit box size, starting from the point given
		// ex {1, 2, 3, 4} gets a hit box in one up two that is 3x4 in size
		return hitbox;
	}

	@Override
	public void setHitbox(int[] newHitbox) {
		//set method for the hitbox
		this.hitbox = newHitbox;
	}
	
	public int [] getSize()
	{
		//getter for the size of the character from (top left?) to (bottom right?) of character
		return size;
	}
		
	public void setSize(int [] newSize)
	{
		//setter for the character size
		this.size = newSize;
		
	}
	public int getCurrentState()
	{
		//gets the state of the character for images (Running, Jumping, Idle, etc) 
		return this.currentState;
	}
	public void setCurrentState(int newState)
	{
		//sets the curent state of the Character 
		this.currentState = newState;
  }
  
  void inflictDamage()
  {
    hitPoints -= 25;
  }

  public void update()
  {
    position[0] = (int) (Math.random()*100);
    position[1] = (int) (Math.random()*100);
  }

}
