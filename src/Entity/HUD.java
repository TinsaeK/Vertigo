package Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HUD {
    private Player player;
    private BufferedImage hud;
    private Font font;
    private BufferedImage distorted;

    public HUD(Player player1){
        player=player1;
        try {
            hud= ImageIO.read(getClass().getResourceAsStream("/Resources/HUD.gif"));
            font=new Font("Helvetica",Font.PLAIN, 14);
            distorted=ImageIO.read(getClass().getResourceAsStream("/Resources/dazed.gif"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D graphics2D){
        graphics2D.drawImage(hud,0,10,null);
        graphics2D.setFont(font);
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(player.getHealth()+"/"+player.getMaxHealth(), 30,25);
        graphics2D.drawString(player.getNumBolts()/1000+"/"+player.getMaxNumBolts()/1000,20,45);

        if (player.getInverted()){
            graphics2D.drawImage(distorted,0,50,null);
        }


    }
}
