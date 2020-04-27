package Entity;

import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Arrow extends MapObject {
    private boolean collide;
    private boolean delete;
    private BufferedImage[] sprites;
    private BufferedImage[] collisionSprites;

    public Arrow(TileMap tm, boolean right){
        super(tm);
        movSpeed=3.8;
        faceRight=!right;
        if (right) done=movSpeed;
        else done = -movSpeed;
        w  =30;
        h=30;
        cw=14;
        ch=14;
        try {

            BufferedImage spritesheet= ImageIO.read(getClass().getResourceAsStream("/Resources/flip.gif"));

            sprites=new BufferedImage[4];
            for(int i=0;i<sprites.length; i++){
                sprites[i]=spritesheet.getSubimage(i*w,0,w,h);
            }
            collisionSprites=new BufferedImage[3];
            for (int i=0; i<collisionSprites.length;i++){
                collisionSprites[i]=spritesheet.getSubimage(i*w,h,w,h);
            }
            anim=new Animation();
            anim.setFrames(sprites);
            anim.setDelay(70);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setCollide(){
        collide=true;
        anim.setFrames(collisionSprites);
        anim.setDelay(70);
        done=0;
    }
    public boolean shouldDelete(){return delete;}
    public void update(){
        checkCollision();
        setPos(xTemp,yTemp);
        if (done==0&&!collide){
            setCollide();
        }
        anim.update();
        if (collide&&anim.hasPlayedOnce()){
            delete=true;
        }
    }
    public void draw(Graphics2D graphics2D){
        setMapPos();
        super.draw(graphics2D);
    }
}
