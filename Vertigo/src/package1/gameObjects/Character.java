/**
 * 
 */
package gameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class Character implements GameObject {
	/**
	 * 
	 */
	BufferedImage [] [] visual;
	int [] position;
	int [] hitbox;
	int [] size;
	int currentState;
	//possible version: 0 = idle, 1 = walkingA, 2 = walkingB, 3 = runningA, 4 = runningB, 5 = jumping
	//alt 1: idle = 0, walking = 1 or -1,
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;

	public Character() {
		// Constructor for default character
	   	// load sprites
		this.position = new int [] {0, 0};
		this.size = new int [] {30, 30};
		this.hitbox = new int [] { 5, 5, 20, 20};
		this.currentState = 0;
		try {
			this.visual [IDLE] [0] = ImageIO.read(getClass().getResourceAsStream("/Visuals/Character/PlayerspriteIDLE.gif"));
			this.visual [WALKING] [0] = ImageIO.read(getClass().getResourceAsStream("/Visuals/Character/PlayerspriteWALKING.gif"));
			this.visual [JUMPING] [0] = ImageIO.read(getClass().getResourceAsStream("/Visuals/Character/PlayerspriteJUMPING.gif"));
			this.visual [FALLING] [0] = ImageIO.read(getClass().getResourceAsStream("/Visuals/Character/PlayerspriteFALLING.gif"));
			}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	      animation = new Animation();
	      currentState = this.currentState;
	      animation.setFrames(this.getVisualSet(currentState, 0));
	      animation.setDelay(400);
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
		return visual [currentState] [count];
	}
	@Override
	public BufferedImage getVisualSet(int currentState) {
		// Geting one visual based on character state.
		return visual [currentState];
	}

	@Override
	public int[] getHitbox() {
		// get method for the hitbox of the character
		// 2 ints distance from position start point
		// 2 ints hit box size, starting from the point given
		// ex {1, 2, 3, 4} gets a hit box in one up two over that is 3x4 in size
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
	
	
	  public void update() {
		   	
		   	// update position
		      getNextPosition();
		      checkTileMapCollision();
		      setPosition(xtemp, ytemp);
		   	
		   	// set animation

		     if(dy > 0)
		     {
		     if(currentState != FALLING) {
		            currentState = FALLING;
		            animation.setFrames(this.getVisual(currentState));
		            animation.setDelay(100);
		            width = 30;
		         }
		      }
		      else if(dy < 0) {
		         if(currentState != JUMPING) {
		            currentState = JUMPING;
		            animation.setFrames(this.getVisual(currentState));
		            animation.setDelay(-1);
		            width = 30;
		         }
		      }
		      else if(left || right) {
		         if(currentState != WALKING) {
		            currentstate = WALKING;
		            animation.setFrames(this.getVisual(currentState));
		            animation.setDelay(40);
		            width = 30;
		         }
		      }
		      else {
		         if(currentState != IDLE) {
		            currentState = IDLE;
		            animation.setFrames(this.getVisual(currentState));
		            animation.setDelay(400);
		            width = 30;
		         }
		      }
		   	
		      animation.update();
		   	
		   	// set direction
		      if(right) facingRight = true;
		      if(left) facingRight = false;
		   	
		   }

	public void draw (BufferedImage visual, int currentState)
	{
		int x = position [0];
		int y = position [1];
		int width = size [0];
		int height = size [1];
		
	      if(facingRight) {
	          g.drawImage(getPlayingImage(), (int)(x /*+ xmin*/ - width / 2), (int)(y /*+ ymin*/ - height / 2), null);
	       }
	       else {
	          g.drawImage(getPlayingImage(), (int)(x /*+ xmin*/ - width / 2 + width), (int)(y /*+ ymin*/ - height / 2), -width, height, null);
	       }
		
	}

}
