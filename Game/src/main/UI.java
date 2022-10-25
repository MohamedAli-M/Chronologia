package main;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class UI {
    GamePanel gamePanel;
    Font font;

    public UI(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        loadFont();
    }

    public void loadFont(){
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(getClass().getResource("/fonts/arial.ttf").getFile()));
            font = font.deriveFont(32f); // Set size to 32
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2){
        g2.setFont(font);
        g2.setColor(Color.WHITE);
        g2.drawString("Key = " + gamePanel.player.hasKey, 50, 50);
    }
}
