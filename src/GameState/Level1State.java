package GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Entity.*;
import Entity.Enemies.Warbler;
import TileMap.*;
import Main.GamePanel;

import javax.swing.plaf.SliderUI;

public class Level1State extends GameState {
    private TileMap tm;
    private Background bg;
    private Player player;
    private HUD hud;
    private ArrayList<Enemy> enemies;
    private ArrayList<Smoke> smokes;

    public Level1State(GameStateManager gsm){
        this.stateMan=gsm;
        init();
    }
    public void init(){

        bg= new Background("/Resources/back.gif", 0.1);
        tm=new TileMap(30);

        tm.loadTiles("/Resources/try.gif");
        tm.loadMap("/Resources/level1-1.map");
        tm.setPosition(0,0);

        player=new Player(tm);
        player.setPos(70,200);
        populateEnemies();
        tm.setSmooth(1);

        smokes=new ArrayList<Smoke>();
        hud=new HUD(player);

    }
    private void healthCheck(){
        if (player.getHealth()==0){
            stateMan.setState(GameStateManager.GAMEOVER);
        }
    }
    private void populateEnemies(){
        Point[] posis=new Point[]{
                new Point(150,110),
                new Point(860,200),
                new Point(1525,200),
                new Point(1680,200),
                new Point(1800,200),
                new Point(2400,100),
                new Point(2000,100),
                new Point(2300,100),

        };
        enemies=new ArrayList<Enemy>();
        Warbler w;
        for (int i=0;i<posis.length;i++){
            w=new Warbler(tm);
            w.setPos(posis[i].x,posis[i].y);
            enemies.add(w);
        }


    }

    public void update(){
        if (player.checkWin()){
            stateMan.setState(GameStateManager.WINSTATE);
        }

        player.update();
        healthCheck();
        tm.setPosition(GamePanel.WIDTH/2-player.getX()+150,GamePanel.HEIGHT/2-player.getY());

        player.checkAttack(enemies);

        for (int i=0;i<enemies.size();i++){
            Enemy enemy= enemies.get(i);
            enemies.get(i).update();
            if (enemies.get(i).isDead()){
                enemies.remove(i);
                i--;
                smokes.add(new Smoke(enemy.getX(),enemy.getY(),!player.getRight()));
            }
        }

        for (int i=0;i<smokes.size();i++){
            smokes.get(i).update();
            if (smokes.get(i).shouldRemove()){
                smokes.remove(i);
                i--;
            }
        }

    }
    public void draw(Graphics2D g){

        bg.draw(g);

        tm.draw(g);
        player.draw(g);

        for (int i=0; i<enemies.size();i++){
            enemies.get(i).draw(g);
        }
        for (int i=0; i<smokes.size();i++){
            smokes.get(i).setMapPos((int)tm.getx(),(int)tm.gety());
            smokes.get(i).draw(g);
        }
        hud.draw(g);
    }
    public void keyPressed(int k){
        if(k==KeyEvent.VK_LEFT){
            if (player.getInverted()){
                player.setRight(true);
            }
            else{
                player.setLeft(true);
            }
        }
        if (k==KeyEvent.VK_RIGHT){
            if (player.getInverted()){
                player.setLeft(true);
            }
            else {
                player.setRight(true);
            }
        }
        if (k==KeyEvent.VK_UP){
            if (player.getInverted()){
                player.setDown(true);
            }
            else {
                player.setUp(true);
            }
        }
        if(k==KeyEvent.VK_DOWN){
            if (player.getInverted()){
                player.setUp(true);
            }
            else {
                player.setDown(true);
            }
        }
        if (k==KeyEvent.VK_SPACE){
            if (player.getInverted()){
                player.setSlashing();
            }
            else{
                player.setJumping(true);
            }
        }
        if (k==KeyEvent.VK_A){
            if (player.getInverted()){
                player.setShooting();
            }
            else {
                player.setSlashing();
            }
        }
        if (k==KeyEvent.VK_D){
            if (player.getInverted()){
                player.setJumping(true);
            }
            else {
                player.setShooting();
            }
        }
    }
    public void keyReleased(int k){
        if(k==KeyEvent.VK_LEFT){
            if (player.getInverted()){
                player.setRight(false);
            }
            else{
                player.setLeft(false);
            }
        }
        if (k==KeyEvent.VK_RIGHT){
            if (player.getInverted()){
                player.setLeft(false);
            }
            else {
                player.setRight(false);
            }
        }
        if (k==KeyEvent.VK_UP){
            if (player.getInverted()){
                player.setDown(false);
            }
            else {
                player.setUp(false);
            }
        }
        if(k==KeyEvent.VK_DOWN){
            if (player.getInverted()){
                player.setUp(false);
            }
            else {
                player.setDown(false);
            }
        }
        if (k==KeyEvent.VK_SPACE){
            if (!player.getInverted()){
                player.setJumping(false);
            }
        }
        if (k==KeyEvent.VK_D){
            if (player.getInverted()){
                player.setJumping(false);
            }
        }

    }

}
