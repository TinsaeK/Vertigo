package GameState;

	import java.awt.*;
	import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import GameState.GameStateManager;
import TileMap.Background;

	public class SettingsMenu {


		private Background back;
	    private int cc=0;
	    private String[] options ={
	            "Volume",
	            "Mute",
	            "Controls",
	            "Return",
	            "Quit"
	    };
	    private Color tc;
	    private Font tf;
	    private Font font;
		private GameStateManager stateMan;
		private int volume;
		private int controlSetup = 0;
	    private static final int MAXCONTROLSETUPS = 3;
	    public SettingsMenu(GameStateManager stateMan){
	        this.stateMan= stateMan;

	        try {
	            back = new Background("/Resources/menu.gif",1);
	            back.setVec(-0.1, 0);
	            tc=new Color(0, 0, 0);
	            tf= new Font("Century Gothic", Font.BOLD, 28);

	            font= new Font("Arial", Font.PLAIN, 12);
	        }
	        catch(Exception e) {

	            e.printStackTrace();
	        }
	    }
	    public void init() { }
	    public void update() {
	        back.update();
	    }
	    public void draw(Graphics2D g) {
	        back.draw(g);

	        g.setColor(tc);
	        g.setFont(tf);
	        g.drawString("Vertigo",100,70);
	        g.setFont(font);
	        for (int i=0; i<options.length; i++){
	        	
	            if (i==cc){
	                g.setColor(Color.WHITE);
	            }
	            else {
	                g.setColor(Color.RED);
	            }
	            g.drawString(options[i], 145, 140 + i * 15);
	            
	             //Switch statement for adding features after initial text
	        	switch(i) {
	        
	        	case 0:
	        		int volume = getVolume(); //get the current volume of the system.
	        		//draws a series of ten rectangles starting green and turning red to display the level of volume
	        		for(int j = 0; j < 10; j++)
	        		{
	        			if (j < volume)
	        				{g.setColor(Color.GREEN);}
	        			else
	        				{g.setColor(Color.BLACK);}
	        			g.fill3DRect( 185 + j * 5, 140 + 1 * 15, 5, 5, true);
					}
	        		break;
	        	case 1:
	        		//prints a volume megaphone icon if volume is active and one with a not circle if muted.
	        		
	        		BufferedImage volHorn = null;
	        		if (getVolume() != 0) 
	        		try {
						volHorn = ImageIO.read(getClass().getResourceAsStream("/Resources/volHorn.gif"));
					} catch (IOException e) {
						e.printStackTrace();
					}
	        		else
		        	try {
						volHorn = ImageIO.read(getClass().getResourceAsStream("/Resources/mutedVolHorn.gif"));
					} catch (IOException e) {
						e.printStackTrace();
					}
	        		g.drawImage(volHorn, null, 185, 140 + i * 15);
	        		break;
	        	case 2:
	        		
	        		break;
	        	case 3:
	        		
	        		break;
	        	default:
	        	}
	        	
	        	
	        }
	    }
	    private int getVolume() {
			return volume;
		}
	    private void setVolume(int volume)
	    {
	    	this.volume=volume;
	    }
		public void select(){
	        if(cc==0){
	        	
	        	//Volume controls to get volume from the volume handler and increment by 1. 
	        	int cv = getVolume();
	        	cv += 1;
	        	if(cv > 10)
	        		cv = 1;
	        	setVolume(cv);
	        	
	        }
	        if(cc==1){
	        	setVolume(0);
	        }
	        if (cc==2){
	        	IncrementControlSetup();
	        }
	        if (cc==3){
	        	stateMan.setState(GameStateManager.MENUSTATE);
	        }
	        if(cc==4){
	            System.exit(0);
	        }
	    }
	    private void IncrementControlSetup() {
			controlSetup += 1;
			if (controlSetup >= MAXCONTROLSETUPS)
				controlSetup = 0;
			
		}
		public void keyPressed(int n) {
	        if(n== KeyEvent.VK_ENTER){
	            select();
	        }
	        if (n==KeyEvent.VK_UP){
	            cc--;
	            if(cc==-1){
	                cc=options.length-1;
	            }
	        }
	        if (n==KeyEvent.VK_DOWN){
	            cc++;
	            if (cc==options.length){
	                cc=0;
	            }
	        }
	    }
	    public void keyReleased(int n) { }
	}

