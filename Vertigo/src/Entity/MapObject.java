package Entity;

import Main.GamePanel;
import TileMap.TileMap;
import TileMap.Tile;

import java.awt.*;

public abstract class MapObject {

    protected TileMap tm;
    protected int ts;
    protected double xmap;
    protected double ymap;

    protected double one;
    protected double two;
    protected double done;
    protected double dtwo;

    protected int w;
    protected int h;

    protected int cw;
    protected int ch;

    protected int currentRow;
    protected int currentColumn;
    protected double xDest;
    protected double yDest;
    protected double xTemp;
    protected double yTemp;
    protected boolean tpLeft;
    protected boolean tpRight;
    protected boolean btLeft;
    protected boolean btRight;

    protected Animation anim;
    protected int currAction;
    protected int prevAction;
    protected boolean faceRight;

    protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean down;
    protected boolean jumping;
    protected boolean falling;


    protected double movSpeed;
    protected double maxSpeed;
    protected double stpSpeed;
    protected double flSpeed;
    protected double maxFlSpeed;
    protected double jmpStart;
    protected double stpJmpSpeed;


    public MapObject(TileMap tileMap){
        tm = tileMap;
        ts = tileMap.getTileSize();

    }

    public boolean intersects(MapObject o) {
        Rectangle r1= new Rectangle((int)one-cw,(int)two-ch,cw,ch);
        Rectangle r2= o.getRectangle();
        return r1.intersects(r2);
    }

    public Rectangle getRectangle(){
        return new Rectangle((int)one-cw,(int)two-ch,cw,ch);
    }
    public void calculateCorners(double x, double y){
        int leftTile=(int)(x-cw/2)/ts;
        int rightTile=(int)(x+cw/2-1)/ts;
        int topTile = (int)(y-ch/2)/ts;
        int bottom = (int)(y+ch/2-1)/ts;

        int tl=tm.getType(topTile,leftTile);
        int tr=tm.getType(topTile,rightTile);
        int bl=tm.getType(bottom,leftTile);
        int br=tm.getType(bottom,rightTile);
        tpLeft  = tl == Tile.BLOCKED;
        tpRight = tr == Tile.BLOCKED;
        btLeft  = bl == Tile.BLOCKED;
        btRight = br == Tile.BLOCKED;
    }

    public void checkCollision(){
        currentColumn= (int)one/ts;
        currentRow=(int)two/ts;

        xDest = one + done;
        yDest = two + dtwo;

        xTemp = one;
        yTemp = two;

        calculateCorners(one,yDest);
        if (dtwo<0){
            if (tpLeft || tpRight){
                dtwo = 0;
                yTemp = currentRow *ts+ch/2;
            }
            else{
                yTemp += dtwo;
            }
        }
        if (dtwo>0){
            if(btLeft || btRight){
                dtwo = 0;
                falling= false;
                yTemp = (currentRow+1)*ts-ch/2;
            }
            else {
                yTemp += dtwo;
            }
        }
        calculateCorners(xDest, two);
        if (done<0){
            if (tpLeft || btLeft){
                done = 0;
                xTemp = currentColumn*ts+cw/2;
            }
            else{
                xTemp += done;
            }
        }
        if (done> 0){
            if (tpRight || btRight){
                done = 0;
                xTemp = (currentColumn+1)*ts - cw/2;
            }
            else{
                xTemp+=done;
            }
        }
        if (!falling){
            calculateCorners(one,yDest+1);
            if (!btLeft&&!btRight){
                falling=true;
            }
        }
    }

    public int getX(){return (int)one;}
    public int getY(){return (int)two;}
    public int getWidth(){return w;}
    public int getHeight(){return h;}
    public int getCw(){return cw;}
    public int getCh(){return ch;}

    public void setPos(double x, double y){
        this.one = x;
        this.two = y;
    }
    public void setVect(double dx, double dy){
        this.done=dx;
        this.dtwo=dy;
    }
    public void setMapPos(){
        xmap =tm.getx();
        ymap=tm.gety();
    }
    public void setLeft(boolean b){left=b;}
    public void setRight(boolean b){right=b;}
    public void setUp(boolean b){up=b;}
    public void setDown(boolean b){down=b;}
    public void setJumping(boolean b){jumping=b;}
    public boolean onScreen(){
        return one+xmap+w<0 || one+xmap-w> GamePanel.WIDTH || two +ymap+h<0 || two + ymap - h>GamePanel.HEIGHT;
    }

}
