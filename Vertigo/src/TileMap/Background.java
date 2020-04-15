package TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import Main.GamePanel;

public class Background {

    private BufferedImage image;

    private double one;
    private double two;
    private double done;
    private double dtwo;

    private double moveScale;

    public Background(String s, double speed){

        try {
            image = ImageIO.read(getClass().getResourceAsStream(s));
            moveScale= speed;
        }
        catch (Exception e){
            System.out.println(image);
            e.printStackTrace();
        }
    }
    public void setPos(double x, double y){
        this.one=(x* moveScale)%GamePanel.WIDTH;
        this.two=(y *moveScale) %GamePanel.HEIGHT;

    }
    public void setVec(double x, double y){
        this.done = x;
        this.dtwo = y;
    }
    public void update(){
        one += done;
        two += dtwo;
    }
    public void draw(Graphics2D g) {
        g.drawImage(image, (int) one, (int) two, null);
        if (one < 0) {
            g.drawImage(image, (int) one + GamePanel.WIDTH, (int) two, null);
        }
        if (one > 0) {
            g.drawImage(image, (int) one - GamePanel.WIDTH, (int) two, null);

        }
    }
}
