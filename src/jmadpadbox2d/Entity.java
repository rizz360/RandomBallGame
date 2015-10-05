package jmadpadbox2d;

import java.awt.Color;
import java.awt.Point;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;



public class Entity {
    
    protected float size;
    protected Vec2 position;
    protected Color color;
    protected Body body;
    
    public Entity() {
        
    }
    
    public Vec2 setPosition(Vec2 newposition) {
        Vec2 oldposition=position;
        position = newposition.mul(100.0f);
        return oldposition;
    }
    
    public synchronized Vec2 setPosition(Point newposition) {
        Vec2 oldposition = position;
        position = new Vec2(newposition.x,newposition.y);
        return oldposition;
    }
    
    public Body getBody() {
        return body;
    }
    
    public Body setBody(Body newBody) {
        Body oldBody = body;
        body = newBody;
        return oldBody;
    }
    
    
    public Vec2 getPosition() {
        return position;
    }
    
    //set new size
    public float setSize(float newsize) {
        float oldSize=size;
        size=newsize;
        return oldSize;
    }
    
    public float getSize() {
        return size;
    }
    
    //setColor
    public Color setColor(Color newColor) {
        Color oldColor=color;
        color=newColor;
        return oldColor;
    }
    
    public Color getColor() {
        return color;
    }
    
    public void draw() {
        return;
    }
    
}
