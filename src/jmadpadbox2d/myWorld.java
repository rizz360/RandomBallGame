package jmadpadbox2d;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.*;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.Random;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class myWorld {
    
    //private myWorld World = new myWorld();
    
    public static int x = 1200;     //1280
    public static int y = 700;      //756
    
    public static JFrame frame;
    private static Canvas canvas;
    private static BufferStrategy bufferstrategy;
    private static GraphicsEnvironment graphicsenvironment;
    private static GraphicsDevice graphicsdevice;
    private static GraphicsConfiguration graphicsconfig;
    private static BufferedImage bufferedimage;
    private static Graphics graphics;
    
    private static boolean doRun;
    
    public static Graphics2D g2d;
    public static World myWorld;
    
    public static ArrayList<Body> bodyarray = new ArrayList();
    public static ArrayList<Ball> ballslist = new ArrayList();
    public static ArrayList<Pad> padslist = new ArrayList();
    
    private float timeStep = 1.0f/60.0f;
    private Random newGenerator = new Random();
    
    private Body groundBody;
    private Body leftBody;
    private Body rightBody;
    
    
    public myWorld() {
        //myWorld World = new myWorld();
        initJFrame();
        doRun=true;
        //run();
    }
    
    private void initJFrame() {
        
        //init JFrame
        frame = new JFrame("MadPad v0.1");
        frame.setIgnoreRepaint(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(x, y);
        
        //GradientPaint background = new GradientPaint(0,0,Color.LIGHT_GRAY,x,y,Color.DARK_GRAY);
        frame.setBackground(Color.DARK_GRAY);
        frame.setResizable(false);
        frame.setLocationRelativeTo(frame);
        frame.setVisible(true);
        
        
        
        //init canvas for painting
        canvas = new Canvas();
        canvas.addKeyListener(new buttonListener());
        canvas.addMouseListener(new mouseListener());
        canvas.setIgnoreRepaint(true);
        canvas.setSize(x, y);
        frame.add(canvas);
        frame.pack();
        
        // Set up the BufferStrategy for double buffering.
        canvas.createBufferStrategy(2);
        bufferstrategy = canvas.getBufferStrategy();
        
        
        
        
        // Get graphics configuration...
        graphicsenvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        graphicsdevice = graphicsenvironment.getDefaultScreenDevice();
        graphicsconfig = graphicsdevice.getDefaultConfiguration();
        
        // Create off-screen drawing surface
        bufferedimage = graphicsconfig.createCompatibleImage(x, y);
        
        // Objects needed for rendering...
        graphics = null;
        g2d = null;
        
        //graphicsdevice.setFullScreenWindow(frame);
        
        
    }

    
    public void setWindowSize(int newx, int newy) {
        x = newx;
        y = newy;
        
        frame.setSize(x, y+22);
        frame.setLocationRelativeTo(frame);
        canvas.setSize(x, y);
        bufferedimage = graphicsconfig.createCompatibleImage(x, y);
        
        
        //graphicsdevice.setFullScreenWindow(frame);
        frame.setLocationRelativeTo(frame);
        
        
        //Lower border
        myWorld.destroyBody(groundBody);
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(0.0f,0.0f);
        groundBody = myWorld.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(x, 0.0f);
        
        //Left border
        BodyDef leftBodyDef = new BodyDef();
        leftBodyDef.position.set(0.0f,0.0f);
        leftBody = myWorld.createBody(leftBodyDef);
        PolygonShape leftBox = new PolygonShape();
        leftBox.setAsBox(0.0f, 50.0f);
        
        //Right border
        myWorld.destroyBody(rightBody);
        BodyDef rightBodyDef = new BodyDef();
        rightBodyDef.position.set((float)(x)/100,0.0f);
        rightBody = myWorld.createBody(rightBodyDef);
        PolygonShape rightBox = new PolygonShape();
        rightBox.setAsBox(0.0f, 50.0f);
        
        groundBody.createFixture(groundBox, 0.0f);
        leftBody.createFixture(leftBox, 0.0f);
        rightBody.createFixture(rightBox, 0.0f);
        
    }
    
    @SuppressWarnings("SleepWhileHoldingLock")
    public synchronized void drawOnce() {
        
            try
            {            
                    // clear back buffer...
                    g2d = bufferedimage.createGraphics();
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(0, 0, x, y);

                    
                    //should draw every ball
                    for (Ball ball : ballslist) {
                        ball.draw();
                    }   
                    
                    //should draw everypad
                    for (Pad pad : padslist) {
                        pad.setPosition(MouseInfo.getPointerInfo().getLocation());
                        updatePad(pad);
                        pad.draw();
                    }
                    
                    //draw position of mouse
                    g2d.setFont(new Font("Courrier New",Font.PLAIN,18));
                    g2d.setColor(Color.GREEN);
                    //Point mouseposition = MouseInfo.getPointerInfo().getLocation();
                    Point mouseposition = canvas.getMousePosition();
                    //SwingUtilities.convertPointFromScreen(mouseposition, canvas);
                    if (mouseposition != null) {
                        g2d.drawString(String.format("Mouse position in pixels: %s", mouseposition.toString()), canvas.getX()+5, canvas.getY()+45);
                    }
                    
                    if (getMousePosition() != null) {
                        g2d.drawString(String.format("Mouse position in float: %s", getMousePosition().toString()), canvas.getX()+5, canvas.getY()+30);
                    }
                    g2d.drawString("Number of bodies: " + myWorld.getBodyCount() , canvas.getX()+5, canvas.getY()+15);
                    
                    g2d.setColor(Color.RED);
                    g2d.drawString("this is text", canvas.getX()+5, canvas.getY()+60);
                    
                    
                    //frame.add(new JButton());
                    
                    
                    // Blit image and flip...
                    graphics = bufferstrategy.getDrawGraphics();
                    graphics.drawImage(bufferedimage, 0, 0, null);
                    if (!bufferstrategy.contentsLost()) bufferstrategy.show();
                    

                    //calm down CPU
                    Thread.sleep(1);
            }
            catch (InterruptedException e)
            {
            }
            
            finally {
                    // release resources
                    if (graphics != null) graphics.dispose();
                    if (g2d != null) g2d.dispose();
            }
        
    }
    
    public void addPad(String name) {
//        Pad newPad = new Pad(null,name);
//        padslist.add(newPad);
        
        
        Pad controlledPad;
        
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
//        Point mouse = MouseInfo.getPointerInfo().getLocation();
//        Vec2 mousevec = new Vec2(mouse.x/100,mouse.y/100);
        Vec2 mousePosition = getMousePosition();
        bodyDef.position.set(mousePosition);
        // use later for funnier experience bodyDef.angularVelocity = -1.0f;
        Body body = myWorld.createBody(bodyDef);

        controlledPad = new Pad(mousePosition,"Controller");
        controlledPad.setPosition(body.getPosition());
        controlledPad.setSize(2.0f);

        PolygonShape dynamicBox = new PolygonShape();
        dynamicBox.setAsBox(controlledPad.getSize()/3, controlledPad.getSize()/3);
        dynamicBox.setAsBox(0.1f, controlledPad.getSize(), new Vec2(0.0f,0.0f), 0.79f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = dynamicBox;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution=0.4f;

        body.createFixture(fixtureDef);
            
         
        padslist.add(controlledPad);
        bodyarray.add(body);        //maybe conflics?

        System.out.println("");
        
        
    }
    
    private void updatePad(Pad pad) {
        //Body padbody = bodyarray.get(padslist.indexOf(pad));
        
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
//        Point mouse = MouseInfo.getPointerInfo().getLocation();
//        Vec2 mousevec = new Vec2(mouse.x/100,7.0f-mouse.y/100);
        bodyDef.position.set(getMousePosition());
        
        PolygonShape dynamicBox = new PolygonShape();
        dynamicBox.setAsBox(pad.getSize()/3, pad.getSize()/3);
        dynamicBox.setAsBox(0.01f, 0.2f, new Vec2(0.0f,0.0f), 0.79f);

        bodyarray.set(padslist.indexOf(pad), myWorld.createBody(bodyDef));
        
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = dynamicBox;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution=0.4f;

        
        bodyarray.get(padslist.indexOf(pad)).createFixture(fixtureDef);
        //padbody = myWorld.createBody(bodyDef);
        
    }
    
    
    public void initPhysics() {
        //Init box2d world
        Vec2 gravity = new Vec2(0.0f,-10.0f);
        boolean doSleep = true;
        myWorld = new World(gravity,doSleep);
        
        
        //Lower border
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(0.0f,0.0f);
        groundBody = myWorld.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(x, 0.0f);
        
        //Left border
        BodyDef leftBodyDef = new BodyDef();
        leftBodyDef.position.set(0.0f,0.0f);
        leftBody = myWorld.createBody(leftBodyDef);
        PolygonShape leftBox = new PolygonShape();
        leftBox.setAsBox(0.0f, 50.0f);
        
        //Right border
        BodyDef rightBodyDef = new BodyDef();
        rightBodyDef.position.set((float)(x)/100,0.0f);
        rightBody = myWorld.createBody(rightBodyDef);
        PolygonShape rightBox = new PolygonShape();
        rightBox.setAsBox(0.0f, 50.0f);
        
        
        groundBody.createFixture(groundBox, 0.0f);
        leftBody.createFixture(leftBox, 0.0f);
        rightBody.createFixture(rightBox, 0.0f);
    }
    
    public void simulateWorld() {
        
        timeStep = 1.0f/60.f;
        int velocityIterations = 8;
        int positionIterations = 2;

        
        while(doRun) {
            
            //Step through world iterations
            myWorld.step(timeStep, velocityIterations, positionIterations);
            
            //Setting new positions for balls
            for(Ball ball : ballslist) {
                Vec2 pos = bodyarray.get(ballslist.indexOf(ball)).getPosition();
                ball.setPosition(pos);
            }
            
            drawOnce();
            
        }
    }
    
  
    public synchronized void addBall(float restitution, float density, float radius, Color color, Vec2 position) {
        Ball newBall;
        
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(position);
        // use later for funnier experience bodyDef.angularVelocity = 10.0f;
        Body body = myWorld.createBody(bodyDef);

        
        newBall = new Ball();
        
        try {
            newBall.setPosition(body.getPosition());
            if(newBall.getPosition()== null) System.out.println("newBall has illegal position");
        }
        catch(Exception error) {
            System.out.println("ERROR!!");
        }
        
        //newBall.setSize(0.1f);
        newBall.setSize(radius);
        
        CircleShape dynamicBall = new CircleShape();
        //PolygonShape dynamicBox = new CircleShape();
        //dynamicBox.setAsBox(newBall.getSize(), newBall.getSize());
        dynamicBall.m_radius = newBall.getSize();

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = dynamicBall;
        fixtureDef.density = density;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution=restitution;

        body.createFixture(fixtureDef);
            
        newBall.setColor(color);
        
        if(newBall!=null) {
            ballslist.add(newBall);
            //System.out.println("Ball after added to array : " + newBall.getPosition().toString() + "\n");
            bodyarray.add(body);
        }
        
    }
    
    public void addRandomBalls(int numberOfBalls) {
//        Random newGenerator = new Random();
        for(int i=0;i<numberOfBalls;i++) {
//            float bounce = newGenerator.nextFloat();
//            float density = newGenerator.nextFloat();
//            Color color = Color.getHSBColor(newGenerator.nextFloat(), newGenerator.nextFloat(), newGenerator.nextFloat());
//            float size = newGenerator.nextFloat()*0.2f;        
//          
            float xcoord = newGenerator.nextFloat()*((float)x/100);
            float ycoord = newGenerator.nextFloat()*8.0f;
            
            this.addRandomBallAtLocation(new Vec2(xcoord,ycoord));
        }
    }
    
    public void addRandomBallAtLocation (Vec2 location) {
            float bounce = newGenerator.nextFloat();
            float density = newGenerator.nextFloat();
            Color color = Color.getHSBColor(newGenerator.nextFloat(), newGenerator.nextFloat(), newGenerator.nextFloat());
            float size = newGenerator.nextFloat()*0.2f;
            this.addBall(bounce, density, size, color, location);
    }
    
    public Vec2 getMousePosition() {
        Point mousePositionPoint = canvas.getMousePosition();
        if(mousePositionPoint != null) {
            float xcoord = (float)mousePositionPoint.x/100;
            float ycoord = (float)(y-mousePositionPoint.y)/100;
            return new Vec2(xcoord,ycoord);
        }
        //return new Vec2(0,y/100);
        return null;
    }
    
    public class mouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent me) {
            try {
                JMadPadBox2D.newWorld.addRandomBallAtLocation(getMousePosition());
            }
            catch(NullPointerException ex) {
                System.out.println("Error trying to spawn ball at this location");
            }
        }

        @Override
        public void mousePressed(MouseEvent me) {
        
        }

        @Override
        public void mouseReleased(MouseEvent me) {
            
        }

        @Override
        public void mouseEntered(MouseEvent me) {
            
        }

        @Override
        public void mouseExited(MouseEvent me) {
            
        }
        
    }
    
    public class buttonListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent ke) {
            
        }

        @Override
        public synchronized void keyPressed(KeyEvent ke) {
           
            
            switch(ke.getKeyCode()) {
                
                case KeyEvent.VK_F : {
                    //JMadPadBox2D.newWorld.setWindowSize(graphicsconfig.getBounds().x, graphicsconfig.getBounds().y);
                    JMadPadBox2D.newWorld.setWindowSize(1000,500);
                    break;
                }
                
                case KeyEvent.VK_B : {
                    try {
                        JMadPadBox2D.newWorld.addBall(1.0f, 5000.0f, 0.5f, Color.red, new Vec2((float)(JMadPadBox2D.newWorld.x/200),10.0f));
                        System.out.println("BUTTON B PRESSED");
                    }
                    catch (NullPointerException ex) {
                        System.out.println("Error trying to spawn ball.");
                        
                    }
                    break;
                
                }
                
                
                    
                case KeyEvent.VK_1 : {
                    timeStep = 1.0f/10.0f;
                    break;
                } 
                    
                case KeyEvent.VK_2 : {
                    timeStep = 1.0f/30.0f;
                    break;
                } 
                    
                case KeyEvent.VK_3 : {
                    timeStep = 1.0f/60.0f;
                    break;
                }
                    
                case KeyEvent.VK_4 : {
                    timeStep = 1.0f/100.0f;
                    break;
                } 
                    
                case KeyEvent.VK_5 : {
                    timeStep = 1.0f/200.0f;
                    break;
                } 
                    
                case KeyEvent.VK_6 : {
                    timeStep = 1.0f/400.0f;
                    break;
                }
                    
                case KeyEvent.VK_7 : {
                    timeStep = 1.0f/600.0f;
                    break;
                } 
                    
                case KeyEvent.VK_8 : {
                    timeStep = 1.0f/1000.0f;
                    break;
                } 
                    
                case KeyEvent.VK_9 : {
                    timeStep = 1.0f/2000.0f;
                    break;
                } 
                
                    
                case KeyEvent.VK_0 : {
                    timeStep = 1.0f/9001.0f;
                    break;
                }
                    
            }
        }

        @Override
        public void keyReleased(KeyEvent ke) {
           
        }
        
    }
    
}
    

