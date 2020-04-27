package Entity.Enemies;
import Entity.*;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Warbler extends Enemy {

    private BufferedImage[] sprites;

    public Warbler(TileMap tileMap){
        super(tileMap);
        movSpeed=0.3;
        maxSpeed=0.3;
        flSpeed=0.2;
        maxFlSpeed=10.0;
        w=30;
        h=30;
        cw=20;
        ch=20;
        health=maxHealth=2;
        damage=1;

        try{
            BufferedImage sprite= ImageIO.read(getClass().getResourceAsStream("/Resources/warbler.gif"));
            sprites=new BufferedImage[3];
            for (int i=0; i<sprites.length;i++){
                sprites[i]=sprite.getSubimage(i*w,0,w,h);
            }


        }
        catch (Exception e){
            e.printStackTrace();
        }
        anim=new Animation();
        anim.setFrames(sprites);
        anim.setDelay(300);
        right=true;
        faceRight=true;
    }

    private void getNextPos(){
        if (left){
            done-=movSpeed;
            if (done<-maxSpeed){
                done=-maxSpeed;
            }
        }
        else if (right){
            done+=movSpeed;
            if (done>maxSpeed){
                done=maxSpeed;
            }
        }
        if (falling){
            dtwo+=flSpeed;
        }

    }

    public void update(){
        getNextPos();
        checkCollision();
        setPos(xTemp,yTemp);
        if (staggered){
            long elapsed=(System.nanoTime()-staggerTime)/1000000;
            if (elapsed>400){
                staggered=false;
            }
        }
        if (right&&done==0){
            right=false;
            left=true;
            faceRight=false;
        }
        else  if (left&&done==0){
            left=false;
            right=true;
            faceRight=true;
        }
        anim.update();
    }
    public void draw(Graphics2D graphics2D){


        setMapPos();
        super.draw(graphics2D);
    }
}
