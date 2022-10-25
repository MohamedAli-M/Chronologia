package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    // Screen Settings
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3; // Scale factor for larger screens
    public final int tileSize = originalTileSize * scale;

    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;

    public final int screenWidth = maxScreenCol * tileSize;  // 16 * 16 = 768 pixels
    public final int screenHeight = maxScreenRow * tileSize; // 16 * 12 = 576 pixels

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }
}
