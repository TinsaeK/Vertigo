package Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Smoke {
private int x;
private int y;
private int xmap;
private int ymap;
private int w;
private int h;
private Animation anim;
private BufferedImage[] sprites;
private boolean remove;
private boolean left;
    public Smoke(int x, int y, boolean left){
        this.x=x;
        this.y=y;
        this.left=left;
        w=30;
        h=30;
        try {
            BufferedImage spritesheet=ImageIO.read(getClass().getResourceAsStream("/Resources/death.gif"));
            sprites=new BufferedImage[6];
            for(int i=0;i<sprites.length;i++){
                sprites[i]=spritesheet.getSubimage(i*w,0,w,h);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        anim=new Animation();
        anim.setFrames(sprites);
        anim.setDelay(70);
    }
    public void update(){
        anim.update();
        if (anim.hasPlayedOnce()){
            remove=true;
        }
    }
    public boolean shouldRemove(){return remove;}
    public void setMapPos(int x, int y){
        xmap=x;
        ymap=y;
    }
    public void draw(Graphics2D graphics2D){

            graphics2D.drawImage(anim.getImage(),x+xmap-w/w,y+ymap-h/2,null);


    }
}
