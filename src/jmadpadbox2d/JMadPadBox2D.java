package jmadpadbox2d;

import java.awt.Color;
import org.jbox2d.common.Vec2;


/*
 * TO DO
 * 
 * ------------------------
 * 
 * Threading
 * 
 * Pad Drawing and physics
 * Pad coords
 * 
 * Collision detection
 * 
 * change windows size
 * 
 */

public class JMadPadBox2D {
    
    public static myWorld newWorld;
    
    public static void main(String[] args) {
        
        
        newWorld = new myWorld();
        newWorld.initPhysics();
        newWorld.addRandomBalls(500);
        //newWorld.addBall(0.1f, 50.0f, 1.0f, Color.red, new Vec2((float)(myWorld.x/200),50.0f));
        
        //COMET SHOWER
        /*
        newWorld.addBall(0.1f, 50000000.0f, 0.1f, Color.black, new Vec2((float)(myWorld.x/200),100.0f));
        newWorld.addBall(0.1f, 50000000.0f, 0.1f, Color.black, new Vec2((float)(myWorld.x/200),200.0f));
        newWorld.addBall(0.1f, 50000000.0f, 0.1f, Color.black, new Vec2((float)(myWorld.x/200),300.0f));
        newWorld.addBall(0.1f, 50000000.0f, 0.1f, Color.black, new Vec2((float)(myWorld.x/200)+4,320.0f));
        newWorld.addBall(0.1f, 50000000.0f, 0.1f, Color.black, new Vec2((float)(myWorld.x/200)-4,270.0f));
        newWorld.addBall(0.1f, 50000000.0f, 0.1f, Color.black, new Vec2((float)(myWorld.x/200)+2,280.0f));
        newWorld.addBall(0.1f, 50000000.0f, 0.1f, Color.black, new Vec2((float)(myWorld.x/200)-2,330.0f));
        */
        
        //newWorld.addPad("Player 1");
        newWorld.simulateWorld();
        
        return;
        
    }
    

                           
}
