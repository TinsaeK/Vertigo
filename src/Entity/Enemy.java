package Entity;
import java.awt.*;
import java.awt.image.BufferedImage;
import TileMap.TileMap;
public class Enemy extends MapObject {


    protected int health;
    protected boolean dead;
    protected boolean staggered;
    protected long staggerTime;
    protected int maxHealth;
    protected int damage;

        public Enemy(TileMap tileMap) {
            super(tileMap);
        }

        public boolean isDead(){ return dead;}

        public int getDamage(){return damage;}

        public void hit(int damage){
            if (dead||staggered) return;
            health-=damage;
            if (health<0) health=0;
            if (health==0) dead=true;
            staggered=true;
            staggerTime=System.nanoTime();
        }

        public void update(){}

}
