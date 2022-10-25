package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    // Screen Settings
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3; // Scale factor for larger screens
    public final int tileSize = originalTileSize * scale;

    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;

    public final int screenWidth = maxScreenCol * tileSize;  // 16 * 16 = 768 pixels
    public final int screenHeight = maxScreenRow * tileSize; // 16 * 12 = 576 pixels

    // Initializing system classes
    TileManager tileManager = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler();
    Sound soundManager = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public ObjectSetter objectSetter = new ObjectSetter(this);
    Thread gameThread;

    // Entities and Objects
    public Player player = new Player(this, keyHandler);
    public SuperObject obj[] = new SuperObject[10];

    // World Settings
    // We're not limited within the screen size, it's part of a bigger world screen
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int maxWorldWidth = maxWorldCol * tileSize;
    public final int maxWorldHeight = maxWorldRow * tileSize;

    int FPS = 60;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame(){
        objectSetter.setObject();

        playMusic(0);
    }

    // Start the game main thread
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        // Frame rate management
        double drawInterval = 1000000000/FPS; // Draw every 0.0167s
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null){
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval; // If that ratio is more than 1, it means that the elapsed time is more than 0.0167s, the draw rate.
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta > 1){
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000){
                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void update(){player.update();};

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileManager.draw(g2);

        for( int i = 0 ; i < obj.length ; i++)
        {
            if(obj[i] != null){
                obj[i].draw(g2, this);
            }
        }
        player.draw(g2);
        g2.dispose();
    }

    public void playMusic(int index){
        soundManager.setFile(index);
        soundManager.play();
        soundManager.loop();
    }
    public void stopMusic(int i)
    {
        soundManager.stop();
    }
    public void playSFX(int i){
        soundManager.setFile(i);
        soundManager.play();
    }
}
