package package1;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.Thread;
import java.util.ArrayList;

import gameObjects.*;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;
    private Thread thread;
    private boolean running = false;
    private ArrayList<GameObject> objects = new ArrayList<>();
    private Window window;

    public Game() {
        this.setSize(WIDTH,HEIGHT);
        this.window = new Window(WIDTH, HEIGHT, "Vertigo", this);
        
        int n;
        for(n=0;n<10;n++){
            objects.add(new Enemy());
        }
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(true){
            repaint();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void paint(Graphics g){
        for(GameObject o: objects){
            o.update();
            int[] position = o.getPosition();
            BufferedImage image = o.getVisual(0,0);
            g.drawImage(image, position[0], position[1], window);
        }
    }

    public static void main (String args[]){
        new Game();
    }

}
