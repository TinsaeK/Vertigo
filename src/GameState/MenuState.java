package GameState;

import java.awt.*;
import java.awt.event.KeyEvent;

import TileMap.Background;

public class MenuState extends GameState {

    private Background back;
    private int cc=0;
    private String[] options ={
            "Start",
            "Controls",
            "Quit"
    };
    private Color tc;
    private Font tf;
    private Font font;
    public MenuState(GameStateManager stateMan){
        this.stateMan= stateMan;

        try {
            back = new Background("/Resources/back.gif",1);
            back.setVec(-0.1, 0);
            tc= Color.GRAY;
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
        g.drawString("Vertigo",100,50);
        g.setFont(font);
        for (int i=0; i<options.length; i++){
            if (i==cc){
                g.setColor(Color.WHITE);
            }
            else {
                g.setColor(Color.RED);
            }
            g.drawString(options[i], 145, 140 + i * 15);
        }
    }
    public void select(){
        if(cc==0){
            stateMan.setState(GameStateManager.LEVEL1STATE);

        }
        if(cc==1){
            stateMan.setState(GameStateManager.CONTROLSPAGE);
        }
        if (cc==2){
            System.exit(0);
        }
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
