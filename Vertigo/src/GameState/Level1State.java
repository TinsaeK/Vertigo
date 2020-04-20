package GameState;

import java.awt.*;
import TileMap.*;
import Main.GamePanel;
public class Level1State extends GameState {
    private TileMap tm;
    private Background bg;
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

    }
    public void update(){}
    public void draw(Graphics2D g){
        //g.setColor(Color.BLACK);
        //g.fillRect(0,0,GamePanel.WIDTH,GamePanel.HEIGHT);
        bg.draw(g);

        tm.draw(g);
    }
    public void keyPressed(int k){}
    public void keyReleased(int k){}

}
