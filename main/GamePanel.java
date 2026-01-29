package main;

import javax.swing.JPanel;

import entity.Player;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable {   
    
    final int OriginalTileSize = 16; // 16x16 tile
    final int Scale = 3;

    public final int TileSize = OriginalTileSize * Scale; // 48x48 tile
    final int MaxScreenCol = 16;
    final int MaxScreenRow = 12;
    final int ScreenWidth = TileSize * MaxScreenCol; // 768 pixels
    final int ScreenHeight = TileSize * MaxScreenRow; // 576 pixels

    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyH);

    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // for better rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true); // to receive key input
    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double delta = 0;       
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0; //
        int drawCount = 0;//

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;   
            timer += (currentTime - lastTime); //
            lastTime = currentTime;

            if (delta >= 1) { 
                update();
                repaint();
                delta--;
                drawCount++; //
            }
            if (timer >= 1000000000) { //
                System.out.println("FPS: " + drawCount); //
                drawCount = 0; //
                timer = 0; //
            }
        }
    }

    public void update() {
        
        player.update();

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        player.draw(g2);

        g2.dispose();
    }
}
