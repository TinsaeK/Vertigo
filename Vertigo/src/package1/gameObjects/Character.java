/**
 * Graphics need to be sent to Characeter from where the character is declared.
 */
package gameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Character implements GameObject {
	/**
	 * 
	 */
	Graphics g;
	BufferedImage [] [] visual;
	int [] position;
	int [] hitbox;
	int [] size;
	int currentState;
    Animation animation;
    int moveSpeed = 0;
    int stopSpeed = 0;
    int fallSpeed = 0;
    int jumpSpeed = 0;
    int stopJumpSpeed = 0;
    boolean falling = false;
    boolean jumping = false;
    boolean movingLeft = false;
    boolean movingRight = false;
    int dx = 0;
    int dy = 0;
	//possible version: 0 = idle, 1 = walkingA, 2 = walkingB, 3 = runningA, 4 = runningB, 5 = jumping
	//alternate 1: idle = 0, walking = 1 or -1,
    boolean facingRight;
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int MAXSPEED = 200;
	private static final int MAXFALLSPEED = 200;

	public Character(Graphics g) {
		// Constructor for default character
	   	// load sprites
		this.g = g;
		this.position = new int [] {0, 0};
		this.size = new int [] {30, 30};
		this.hitbox = new int [] {5, 5, 20, 20};
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
	    animation.setFrame(this.currentState);
	    animation.setFrames(this.getVisualSet(currentState));
	    animation.setDelay(400);
	}

		
	@Override
	public int[] getPosition() {
		//get method for the position of the character 
		//to be measured from the ____(top left or bottom left?)
		return position;
	}

	@Override
	public void setPosition(int[] newPosition) {
		// Set method for the character position
		this.position = newPosition;
	}
	
	@Override
	public BufferedImage getVisual(int currentState, int inset) {
		// Getting one visual based on character state.
		return visual [currentState] [0];
	}
	
	@Override
	public BufferedImage [] getVisualSet(int currentState) {
		// Getting one visual based on character state.
		BufferedImage [] set = visual[currentState];
		return set;
	}

	@Override
	public int[] getHitbox() {
		// get method for the hit box of the character
		// 2 integers distance from position start point
		// 2 integers hit box size, starting from the point given
		// example {1, 2, 3, 4} gets a hit box in one up two over that is 3x4 in size
		return hitbox;
	}

	@Override
	public void setHitbox(int[] newHitbox) {
		//set method for the hit box
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
		//sets the current state of the Character 
		this.currentState = newState;
	}
	
	   private void getNextPosition() {
		   	
		   	// movement
		      if(movingLeft) {
		         dx -= moveSpeed;
		         if(dx < -MAXSPEED) {
		            dx = -MAXSPEED;
		         }
		      }
		      else if(movingRight) {
		         dx += moveSpeed;
		         if(dx > MAXSPEED) {
		            dx = MAXSPEED;
		         }
		      }
		      else {
		         if(dx > 0) {
		            dx -= stopSpeed;
		            if(dx < 0) {
		               dx = 0;
		            }
		         }
		         else if(dx < 0) {
		            dx += stopSpeed;
		            if(dx > 0) {
		               dx = 0;
		            }
		         }
		      }

		   	
		   	// jumping
		      if(jumping && !falling) {
		         dy = jumpSpeed;
		         falling = true;	
		      }
		   	
		   	// falling
		      if(falling) {
		    	  dy += fallSpeed;
		         if(dy > 0) jumping = false;
		         if(dy < 0 && !jumping) dy += stopJumpSpeed;   	
		         if(dy > MAXFALLSPEED) dy = MAXFALLSPEED;
		      	
		      }
		   	
		   }

	
	  public void update() {
		   	
		   	// update position
		  getNextPosition();
		  int [] xy = getPosition();
		  checkTileMapCollision();
		  xy [0] += dx;
		  xy [1] += dy;
		  setPosition(xy);
		   	
		   	// set animation

		     if(dy > 0)
		     {
		     if(currentState != FALLING) {
		            currentState = FALLING;
		            animation.setFrames(this.getVisualSet(currentState));
		            animation.setDelay(100);
		         }
		      }
		      else if(dy < 0) {
		         if(currentState != JUMPING) {
		            currentState = JUMPING;
		            animation.setFrames(this.getVisualSet(currentState));
		            animation.setDelay(-1);
		         }
		      }
		      else if(dx != 0) {
		         if(currentState != WALKING) {
		            currentState = WALKING;
		            animation.setFrames(this.getVisualSet(currentState));
		            animation.setDelay(40);
		         }
		      }
		      else {
		         if(currentState != IDLE) {
		            currentState = IDLE;
		            animation.setFrames(this.getVisualSet(currentState));
		            animation.setDelay(400);
		         }
		      }
		   	
		      animation.update();
		   	
		   	// set direction
		      if(dx > 0) facingRight = true;
		      if(dx < 0) facingRight = false;
		   	
		   }

	private void checkTileMapCollision() {
		// TODO Auto-generated method stub
		
	}


	public void draw (BufferedImage visual)
	{
		int x = position [0];
		int y = position [1];
		int width = size [0];
		int height = size [1];
		
		if(facingRight) {
	          g.drawImage(animation.getImage(), (int)(x /*+ x minimum*/ - width / 2), (int)(y /*+ y minimum*/ - height / 2), null);
	       }
	       else {
	          g.drawImage(animation.getImage(), (int)(x /*+ x minimum*/ - width / 2 + width), (int)(y /*+ y minimum*/ - height / 2), -width, height, null);
	       }
		
	}

}
