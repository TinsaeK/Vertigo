package TileMap;

import Main.GamePanel;

import java.awt.*;
import  java.awt.image.*;

import java.io.*;
import javax.imageio.ImageIO;


public class TileMap {
private double x;
private double y;

private int xmin;
private int ymin;
private int xmax;
private int ymax;

private double smooth;

private int[][] map;
private int tileSize;
private int numRows;
private int numCols;
private int w;
private int height;

private BufferedImage tileset;
private int numTilesAcross;
private Tile[][] tiles;

private int rowOffset;
private  int colOffset;
private int numRowsToDraw;
private int numColsToDraw;

public TileMap(int tileSize){
    this.tileSize = tileSize;
    numRowsToDraw= GamePanel.HEIGHT/ tileSize+2;
    numColsToDraw=GamePanel.w/tileSize+2;
    smooth=0.07;
}

public void loadTiles(String s){

    try{
        tileset=ImageIO.read( getClass().getResourceAsStream(s));
        numTilesAcross=tileset.getWidth()/tileSize;
        tiles=new Tile[2][numTilesAcross];
        BufferedImage sbImg;
        for(int col =0; col<numTilesAcross;col++){
            sbImg=tileset.getSubimage(col*tileSize,0,tileSize,tileSize);
            tiles[0][col]= new Tile(sbImg,Tile.NORMAL);
            sbImg=tileset.getSubimage(col*tileSize,tileSize,tileSize,tileSize);
            tiles[1][col]=new Tile(sbImg,Tile.BLOCKED);
        }
    }
    catch (Exception e){
        e.printStackTrace();
    }

}
public void loadMap(String s){
    try{
        InputStream in= getClass().getResourceAsStream(s);
        BufferedReader br= new BufferedReader(new InputStreamReader(in));
        numCols=Integer.parseInt(br.readLine());
        numRows=Integer.parseInt(br.readLine());
        map=new int[numRows][numCols];
        w=numCols*tileSize;
        height=numRows*tileSize;
        xmin=GamePanel.WIDTH-w;
        xmax=0;
        ymin=GamePanel.HEIGHT-height;
        ymax=0;

        String del="\\s+";
        for (int row=0;row<numRows; row++){
            String line=br.readLine();
            String[] tokens=line.split(del);
            for (int col=0;col<numCols;col++){
                map[row][col]=Integer.parseInt(tokens[col]);
            }
        }
    }
    catch (Exception e){
        e.printStackTrace();
    }
}
public int getTileSize(){return tileSize;}
public int getx(){return (int)x;}
public int gety(){return (int)y;}
public int getw(){return w;}
public int getHeight(){return  height;}

public int getType(int row,int col){
    int rc=map[row][col];
    int r=rc/numTilesAcross;
    int c=rc%numTilesAcross;
    return tiles[r][c].getType();
}
public void setPosition(double x, double y){
    this.x +=(x-this.x)*smooth;
    this.y+=(y-this.y)*smooth;

    fixBounds();
    colOffset=(int)-this.x/tileSize;
    rowOffset=(int)-this.y/tileSize;
}
public void setSmooth(int a){
    smooth=a;
}
private void fixBounds(){
    if(x<xmin)x=xmin;
    if (y<ymin)y=ymin;
    if (x>xmax)x=xmax;
    if (y>ymax)y=ymax;
}
public void draw(Graphics2D g){
    for(int row=rowOffset;row<rowOffset+numRowsToDraw;row++){
        if (row>=numRows) break;
        for(int col=colOffset;col<colOffset+numColsToDraw;col++){
            if (col>=numCols) break;

            if(map[row][col]==0) continue;

            int rc=map[row][col];
            int r =rc/numTilesAcross;
            int c=rc %numTilesAcross;

            g.drawImage(tiles[r][c].getImage(),(int)x+col*tileSize,(int)y+row*tileSize,null);
        }
    }
}























}
