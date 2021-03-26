import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Area;
import java.io.File;
import java.util.ArrayList;

public class Tanks_Panel extends JPanel {

    private Tank p1Tank, p2Tank;
    private Timer timer;
    private ArrayList<Walls> wallList;
    private Walls leftBound, rightBound, topBound, bottomBound, barrier1, barrier2, barrier3, barrier4, barrier5, barrier6, barrier7;
    private ArrayList<Shells> p1Shells, p2Shells;
    private int shellDelayP1, shellCounterP1, shellDelayP2, shellCounterP2;
    private int p1Lives, p2Lives;
    private Image zero, one, two, Flag, three;
    private boolean background;


    public Tanks_Panel(int width, int height){
        setBounds(0, 0, width, height);
        setupKeyListener();

        setupGame();

        timer = new Timer(1000 / 60, e -> update());
        timer.start();

        //for(int i = 1; i < 4; i++){
        try{
            zero = ImageIO.read(new File("./res/0.png"));
            one = ImageIO.read(new File("./res/1.png"));
            two = ImageIO.read(new File("./res/2.png"));
            Flag = ImageIO.read(new File("./res/Flag.png"));
            three = ImageIO.read(new File("./res/3.png"));
        }
        catch(Exception e){e.printStackTrace();}
        //}
    }

    public void setupGame(){
        Sounds.OverThere.stop();
        Sounds.Erika.stop();
        Sounds.Anthem.stop();

        setupWalls();

        p1Tank = new Tank(10, 100, 0, one);
        p2Tank = new Tank(625, 615, 180, two);

        p1Shells = new ArrayList<>();
        p2Shells = new ArrayList<>();

        shellDelayP1 = 90;
        shellCounterP1 = shellDelayP1;

        shellDelayP2 = 90;
        shellCounterP2 = shellDelayP2;

        p1Lives = 3;
        p2Lives = 3;

        background = false;
    }

    public void setupWalls(){
        wallList = new ArrayList<>();

        wallList.add(leftBound = new Walls(0, 0, 5, 800));
        wallList.add(rightBound = new Walls(800, 0, 5, 800));
        wallList.add(topBound = new Walls(0, 0, 800, 5));
        wallList.add(bottomBound = new Walls(0, 800, 800+5, 5));

//        wallList.add(barrier1 = new Walls(120, 125, 30, 150));
//        wallList.add(barrier2 = new Walls(90, 125, 30, 30));  //top left L shape
//        wallList.add(barrier3 = new Walls(200, 550, 75, 75)); //bottom left square
//        wallList.add(barrier4 = new Walls(325, 100, 175, 30));    // top bar
//        wallList.add(barrier5 = new Walls(500, 300, 75, 75)); //middle right square
//        wallList.add(barrier6 = new Walls(450, 650, 175, 30));
//        wallList.add(barrier7 = new Walls(620, 555, 30, 125)); //bottom right L shape

    }

    public void update(){
        shellCounterP1++;
        shellCounterP2++;

        p1Tank.move();
        p2Tank.move();

        //move shells + shell vs walls
        //p1
        for(int i = 0; i < p1Shells.size(); i++){
            Shells shell = p1Shells.get(i);
            shell.move();
            if(shell.getX() <= 5 || shell.getX() >= 795 || shell.getY() <= 5 || shell.getY() >= 795){
                p1Shells.remove(shell);
                i--;
            }
        }
        //p2
        for(int i = 0; i < p2Shells.size(); i++){
            Shells shell = p2Shells.get(i);
            shell.move();
            if(shell.getX() <= 5 || shell.getX() >= 795 || shell.getY() <= 5 || shell.getY() >= 795){
                p2Shells.remove(shell);
                i--;
            }
        }

        //tanks vs walls collision
        //p1
        if(p1Tank.getX() < 5){
            p1Tank.setX(5);
        }
        if(p1Tank.getX() > 750){
            p1Tank.setX(750);
        }
        if(p1Tank.getY() < 17){
            p1Tank.setY(17);
        }
        if(p1Tank.getY() > 760){
            p1Tank.setY(760);
        }
        //p2
        if(p2Tank.getX() < 5){
            p2Tank.setX(5);
        }
        if(p2Tank.getX() > 750){
            p2Tank.setX(750);
        }
        if(p2Tank.getY() < 17){
            p2Tank.setY(17);
        }
        if(p2Tank.getY() > 765){
            p2Tank.setY(765);
        }

        //tank vs tank collision
//        if(p1Tank.getHitBox().intersects(p2Tank.getHitBox())){  //kinda works, could edit
//            p1Tank.setMoveForward();
//            p1Tank.setMoveBack();
//            p2Tank.setMoveForward();
//            p2Tank.setMoveBack();
//        }

        //shells vs tanks collision
        //p1Shells on p2
        for(int i = 0; i < p1Shells.size(); i++){
            Shells shell = p1Shells.get(i);
            Area sArea = new Area(shell.getHitBox());
            Area tArea = new Area(p2Tank.getHitBox());
            //if(shell.getHitBox().intersects(p2Tank.getHitBox())){
            sArea.intersect(tArea);  //updates sArea to be the intersection of the 2 areas.
            if(!sArea.isEmpty()){
                p2Lives--;
                p1Shells.remove(shell);
                i--;
                if(p2Lives <= 0){
                    p2Tank = new Tank(1200, 1000, 0, two);
                }
            }
        }
        //p2shells on p1
        for(int i = 0; i < p2Shells.size(); i++){
            Shells shell = p2Shells.get(i);
            Area sArea = new Area(shell.getHitBox());
            Area tArea = new Area(p1Tank.getHitBox());
            sArea.intersect(tArea);  //updates sArea to be the intersection of the 2 areas.
            if(!sArea.isEmpty()){ //if the area of intersection is non-empty...
            //if(shell.getHitBox().intersects(p1Tank.getHitBox())){
                p1Lives--;
                p2Shells.remove(shell);
                i--;
                if(p1Lives <= 0){
                    p1Tank = new Tank(1000, 1000, 0, one);
                }
            }
        }

        repaint();
    }

    public void setupKeyListener(){
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    if(shellCounterP1 >= shellDelayP1){
                        Shells shell = new Shells(p1Tank.getCentreX(), p1Tank.getCentreY(), p1Tank.getAngle());
                        p1Shells.add(shell);
                        shellCounterP1 = 0;
                    }
                }
                else if(e.getKeyCode() == KeyEvent.VK_SLASH){
                    if(shellCounterP2 > shellDelayP2){
                        Shells shell = new Shells(p2Tank.getCentreX(), p2Tank.getCentreY(), p2Tank.getAngle());
                        p2Shells.add(shell);
                        shellCounterP2 = 0;
                    }
                }
                else if(e.getKeyCode() == KeyEvent.VK_N){
                    setupGame();
                }
                else if(e.getKeyCode() == KeyEvent.VK_U){
                    background = true;
                    p1Tank = new Tank((int)p1Tank.getX(), (int)p1Tank.getY(), (int)p1Tank.getAngle(), three);
                    shellDelayP1 = 1;
                    Sounds.Anthem.play();
                }
                else{
                    p1Tank.pressedP1(e.getKeyCode());
                    p2Tank.pressedP2(e.getKeyCode());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

                p1Tank.releasedP1(e.getKeyCode());
                p2Tank.releasedP2(e.getKeyCode());

            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //draw background first
        if(!background) {
            g2.drawImage(zero, 0, 0, null);
        }
        else{
            g2.drawImage(Flag, -90, 0, null);
        }

        //draw tanks and shells
        if(p1Lives > 0) {
            p1Tank.draw(g2);

            for(Shells shell: p1Shells){
                g2.setColor(new Color(235, 224, 26));
                shell.draw(g2);
                g2.setColor(Color.black);
            }
        }
        if(p2Lives > 0) {
            p2Tank.draw(g2);

            for(Shells shell: p2Shells){
                g2.setColor(Color.red);
                shell.draw(g2);
                g2.setColor(Color.black);
            }
        }

        //walls
        leftBound.draw(g2);
        rightBound.draw(g2);
        topBound.draw(g2);
        bottomBound.draw(g2);

//        g2.setColor(Color.green);
//        barrier1.draw(g2);
//        barrier2.draw(g2);
//        barrier3.draw(g2);
//        barrier4.draw(g2);
//        barrier5.draw(g2);
//        barrier6.draw(g2);
//        barrier7.draw(g2);
//        setBackground(new Color(15, 120, 6));

        g2.setColor(Color.WHITE);
        if(p1Lives > 0 && p2Lives > 0){
            g2.drawString( "P1 Lives: " + p1Lives, 25, 25);
            g2.drawString("P2 Lives: " + p2Lives, 25, 60);
        }
        else{
            if(p1Lives <= 0){
                g2.drawString( "P1 Lives: " + p1Lives, 25, 25);
                g2.drawString("P2 Lives: " + p2Lives, 25, 60);

                g2.drawString("P2 Wins! Press N to start new game ", 150, 50);
                Sounds.Erika.play();
            }
            else if(p2Lives <= 0){
                g2.drawString( "P1 Lives: " + p1Lives, 25, 25);
                g2.drawString("P2 Lives: " + p2Lives, 25, 60);

                g2.drawString("P1 Wins! Press N to start new game ", 150, 50);
                Sounds.OverThere.play();
            }
        }

    }
}
