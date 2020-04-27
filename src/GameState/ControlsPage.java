package GameState;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import Main.GamePanel;
import TileMap.Background;

import javax.imageio.ImageIO;

public class ControlsPage extends GameState{

    private Background back;
    private int cc=0;
    private String[] options ={
            "return",
            "Quit"
    };
    private Color tc;
    private Font tf;
    private Font font;
    public ControlsPage(GameStateManager stateMan){
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
    public void update() {back.update();}
    public void draw(Graphics2D g) {
        back.draw(g);
        BufferedImage rightpic;
        BufferedImage leftpic;
        BufferedImage spacepic;
        BufferedImage dbutton;
        BufferedImage abutton;
        g.setColor(tc);
        g.setFont(tf);
        g.drawString("Controls",100,50);
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString("Move Right", 60, 90);
        g.drawString("Move Left", 60,90+15);
        g.drawString("Jump",60,90+30);
        g.drawString("Shoot Arrow",60,90+45);
        g.drawString("Sword slash",60,90+60);
        try {
             rightpic= ImageIO.read(getClass().getResourceAsStream("/Resources/right.gif"));
             leftpic= ImageIO.read(getClass().getResourceAsStream("/Resources/left.gif"));
             spacepic= ImageIO.read(getClass().getResourceAsStream("/Resources/space.gif"));
             dbutton=ImageIO.read(getClass().getResourceAsStream("/Resources/d.gif"));
             abutton=ImageIO.read(getClass().getResourceAsStream("/Resources/a.gif"));


            g.drawImage(rightpic,200, 80,null);
            g.drawImage(leftpic,200, 95,null);;
            g.drawImage(spacepic,185, 110,null);;
            g.drawImage(dbutton,200, 125,null);
            g.drawImage(abutton,200, 140,null);
        }
        catch (Exception e){
            e.printStackTrace();
        }


        int j=5;
        for (int i=0; i<options.length; i++){
            if (i==cc){
                g.setColor(Color.WHITE);
            }
            else {
                g.setColor(Color.RED);
            }
            g.drawString(options[i], 130, 90 + j * 15);
            j++;
        }
    }
    public void select(){
        if(cc==0){
            stateMan.setState(GameStateManager.MENUSTATE);

        }
        if(cc==1){

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
