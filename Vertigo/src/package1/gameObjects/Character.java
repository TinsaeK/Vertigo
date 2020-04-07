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
	int currentState;
	//possible version: 0 = idle, 1 = walkingA, 2 = walkingB, 3 = runningA, 4 = runningB, 5 = jumping
	//alt 1: idle = 0, walking = 1 or -1, 
	
	
	public Character() {
		// Constructor for default character
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
	public BufferedImage getVisual(int currentState) {
		// Geting one visual based on character state.
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
		return this.currentState
	}
	public void setCurrentState(int newState)
	{
		//sets the curent state of the Character 
		this.currentState = newState;
	}

}
