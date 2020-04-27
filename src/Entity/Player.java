package Entity;
import TileMap.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends MapObject {

    private int health;
    private int maxHealth;
    private int numBolts;
    private int maxNumBolts;
    private boolean dead;
    private boolean flinching;
    private long flinchTimer;
    private long now;

    private boolean shooting;
    private int boltsUsed;
    private int boltDamage;
    private ArrayList<Arrow> arrows;

    private boolean slashing;
    private int slashDamage;
    private int slashRange;

    private boolean inverted;

    private ArrayList<BufferedImage[]> sprites;
    private final int[] numFrames = {2, 8, 1, 2, 8, 2, 5};
    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 2;
    private static final int FALLING = 3;
    private static final int INVERTED = 4;
    private static final int SHOOTING = 5;
    private static final int SLASHING = 6;
    private long lastTrueTime;

    public Player(TileMap tileMap){

        super(tileMap);

        w=30;
        h=30;
        cw=20;
        ch=20;
        movSpeed=0.3;
        maxSpeed=1.6;
        stpSpeed=0.4;
        flSpeed=0.15;
        maxFlSpeed=4.0;
        jmpStart=-4.8;
        faceRight=true;
        health=maxHealth=5;
        numBolts= maxNumBolts= 12000;


        boltsUsed=1000;
        boltDamage=8;
        arrows=new ArrayList<Arrow>();

        slashDamage=4;
        slashRange=40;

        try{
            BufferedImage spriteSheet=ImageIO.read(getClass().getResourceAsStream("/Resources/drew.gif"));
            sprites=new ArrayList<BufferedImage[]>();
            for (int i=0; i<7; i++){
                BufferedImage[] piece = new BufferedImage[numFrames[i]];

                for(int j=0; j<numFrames[i]; j++){
                    if(i!=6) {
                        piece[j] = spriteSheet.getSubimage(j * w, i * h, w, h);
                    }
                    else{
                        piece[j] = spriteSheet.getSubimage(j * w*2, i * h, w*2, h);
                    }
                }
                sprites.add(piece);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        anim=new Animation();
        currAction=IDLE;
        anim.setFrames(sprites.get(IDLE));
        anim.setDelay(400);
    }
    public int getHealth(){return health;}
    public int getMaxHealth(){return maxHealth;}
    public int getNumBolts(){return numBolts;}
    public int getMaxNumBolts(){return maxNumBolts;}
    public boolean getRight(){return faceRight;}
    public boolean getInverted(){return inverted;}
    public void setShooting(){
        shooting=true;
    }
    public void setSlashing(){
        slashing=true;
    }
    public void setInverted(boolean b){

        inverted=b;
        if(inverted=true){
            now= System.currentTimeMillis();
            lastTrueTime=now;
        }
    }
    public void checkTime() {

        now= System.currentTimeMillis();
        if (lastTrueTime+4000<now) {
            inverted = false;
        }

    }
    private void getNextPosition(){
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
        else {
            if (done>0){
                done-=stpSpeed;
                if (done<0){
                    done=0;
                }
            }
            else if (done<0){
                done+=stpSpeed;
                if (done>0){
                    done=0;
                }
            }
        }

        if ((currAction==SLASHING||currAction==SHOOTING)&&!(jumping||falling)){
            done=0;
        }
        if (jumping&&!falling){
            dtwo=jmpStart;
            falling=true;
        }
        if (falling){
            dtwo+=flSpeed;
            if (dtwo>0)jumping=false;
            if (dtwo<0&&!jumping)dtwo+=stpJmpSpeed;
            if (dtwo>maxFlSpeed)dtwo=maxFlSpeed;
        }
    }
    public void checkAttack(ArrayList<Enemy> enemies){
        for (int i=0;i<enemies.size();i++) {
            Enemy enemy = enemies.get(i);
            if (slashing){
                if (faceRight){
                    if (enemy.getX()>one&& enemy.getX()<one+slashRange&&enemy.getY()>two-h/2&&enemy.getY()<two+h/2){
                        enemy.hit(slashDamage);

                    }
                }
                else {

                    if (enemy.getX()<=one&& enemy.getX()>one-slashRange&&enemy.getY()>two-h/2&&enemy.getY()<two+h/2){
                        enemy.hit(slashDamage);
                    }
                }
            }

            for (int j=0; j<arrows.size();j++){
                if (arrows.get(j).intersects(enemy)){
                    enemy.hit(boltDamage);
                    arrows.get(j).setCollide();
                }

            }
            if (intersects(enemy)){
                hit(enemy.getDamage());
            }

        }



    }
    public void hit(int damage){
        if(flinching) return;
        health -= damage;
        setInverted(true);
        if (health<0) health=0;
        if (health==0) dead=true;
        flinching =true;
        flinchTimer=System.nanoTime();
    }
    public boolean checkWin(){
        if (one>=2772){
            return true;
        }
        return false;
    }

    public void update(){

        getNextPosition();
        checkCollision();
        setPos(xTemp,yTemp);
        checkTime();

        numBolts+=2;
        if (numBolts>maxNumBolts)numBolts=maxNumBolts;
        if(shooting&&currAction!=SHOOTING){
            if (numBolts>=1000){
                numBolts-=1000;
                Arrow a=new Arrow(tm,faceRight);
                a.setPos(one,two);
                arrows.add(a);
            }
        }
        for (int i=0;i<arrows.size();i++){
            arrows.get(i).update();
            if (arrows.get(i).shouldDelete()){
                arrows.remove(i);
                i--;
            }
        }
        if (flinching){
            long elapsed=(System.nanoTime()-flinchTimer )/1000000;
            if (elapsed>1000){
                flinching=false;
            }
        }
        if (currAction==SLASHING){
            if (anim.hasPlayedOnce())slashing=false;
            shooting=false;
        }
        if (currAction==SHOOTING){
            if (anim.hasPlayedOnce())shooting=false;
        }

        if (slashing){
            if (currAction!=SLASHING){
                currAction=SLASHING;
                shooting=false;
                anim.setFrames(sprites.get(SLASHING));
                anim.setDelay(50);
                w=60;
            }
        }
        else if(shooting){
            if (currAction!=SHOOTING){
                currAction=SHOOTING;
                anim.setFrames(sprites.get(SHOOTING));
                w=30;
            }
        }
        else if (dtwo>0){
            if (currAction!=FALLING){
                currAction=FALLING;
                anim.setFrames(sprites.get(FALLING));
                anim.setDelay(100);
                w=30;
            }
        }
        else if (dtwo<0){
            if (currAction!=JUMPING){
                currAction=JUMPING;
                anim.setFrames(sprites.get(JUMPING));
                anim.setDelay(-1);
                w=30;
            }
        }
        else if (left||right){
            if (inverted){
                if (currAction!=INVERTED){
                    currAction=INVERTED;
                    anim.setFrames(sprites.get(INVERTED));
                    anim.setDelay(40);
                    w=30;
                }
            }
            else {
                if (currAction != WALKING) {
                    currAction = WALKING;
                    anim.setFrames(sprites.get(WALKING));
                    anim.setDelay(40);
                    w = 30;
                }
            }
        }
        else {
            if (currAction!=IDLE){
                currAction=IDLE;
                anim.setFrames(sprites.get(IDLE));
                anim.setDelay(400);
                w=30;
            }
        }
        anim.update();

        if (currAction!=SLASHING&&currAction!=SHOOTING){
            if (right) faceRight=true;
            if (left) faceRight=false;
        }


    }

    public void draw(Graphics2D graphics2D){
        setMapPos();
            for (int i = 0; i < arrows.size(); i++) {
                arrows.get(i).draw(graphics2D);
            }
        if (flinching){
            long el =   (System.nanoTime()-flinchTimer)/1000000;
            if (el/100%2==0){
                return;
            }
        }
      super.draw(graphics2D);
    }


}

























































