package jmadpadbox2d;


import java.awt.Color;
import java.awt.GradientPaint;
//import java.awt.geom.Ellipse2D;
//import java.awt.geom. check this later
import org.jbox2d.common.Vec2;



public class Pad extends Entity{
    
    protected static String owner;
    
    
    public Pad(Vec2 initposition,String newOwner) {
        position=initposition;
        owner=newOwner;
    }
    
    public String setOwner(String newOwner) {
        String oldOwner=owner;
        owner=newOwner;
        return oldOwner;
    }
    
    @Override
    public void draw() {
        GradientPaint gradient = new GradientPaint(0,0,Color.blue, 1200, 600, Color.red);
        myWorld.g2d.setPaint(gradient);
        myWorld.g2d.drawLine((int)position.x, (int)position.y, (int)position.x+100, (int)position.y+100);
    }
    
    @Override
    public Vec2 setPosition(Vec2 newposition) {
        Vec2 oldposition=position;
        position = newposition;
        return oldposition;
    }
    
}
