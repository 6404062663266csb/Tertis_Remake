package tetris;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    public static  final int WIDTH=1150;
    public static  final int HEIGHT=620;

    final int FPS =60; // to update screen 60 times a second (frames per seconds)
    Thread GameThread; // to run game loop

    PlayManager pm = new PlayManager();

    public static Sound music = new Sound();
    public static Sound se = new Sound();


    public GamePanel(){
        // panel settings

        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setBackground(Color.GRAY);
        this.setLayout(null);
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);

    }

    public void LaunchGame(){
        GameThread = new Thread(this);
        GameThread.start();
        music.play(0,true);
        music.loop();
    }

    @Override
    public void run() {

        // so  in every game loop
        // we perform 2 task
        // update + draw
        // in update we update position x and y

        double drawInterval = 1000000000/FPS;
        double delta =0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(GameThread != null){

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >=1){
                update();
                repaint();
                delta--;

            }
        }
    }

    private void update(){

        if(KeyHandler.pausePressed == false && pm.gameOver == false) // only update when game not paused
            // if pressed space game will act like freeze
        {

        pm.update();
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        pm.draw(g2);
    }

}
