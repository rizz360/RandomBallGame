package jmadpadbox2d;


import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.geom.Ellipse2D;

public class Ball extends Entity{
    

    //Constructor
    public Ball() {
    }
    

    
    @Override
    public void draw() {
        
        
        //GradientPaint gradient = new GradientPaint(0,0,Color.blue, 1200, 600, Color.red);
        //myWorld.g2d.setPaint(gradient);
        
        float r = size*100;
        float xcoord = (position.x-r);
        float ycoord = myWorld.y-(position.y+r);
        
        myWorld.g2d.setColor(color);
        
        myWorld.g2d.fill(new Ellipse2D.Float(xcoord, ycoord, r*2, r*2));
        
        myWorld.g2d.setColor(Color.BLACK);
        myWorld.g2d.draw(new Ellipse2D.Float(xcoord, ycoord, r*2, r*2));
        
        

    }
    
    
}
