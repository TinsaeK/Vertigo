package GameState;


public abstract class GameState {

    protected GameStateManager stateMan;

    public abstract void init();
    public abstract void update();
    public abstract void draw(java.awt.Graphics2D g);
    public abstract void keyPressed(int n);
    public abstract void keyReleased(int n);
}
