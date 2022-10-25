package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class UI {
    GamePanel gamePanel;
    Font arial, arial_40p, arial_80p;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public boolean gameFinished = false;
    public String message = "";
    int messageTimeCounter = 0;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        loadFont();
        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;
    }

    public void showMessage(String text){
        message = text;
        messageOn = true;
    }

    public void loadFont(){
        try {
            arial = Font.createFont(Font.TRUETYPE_FONT, new File(getClass().getResource("/fonts/arial.ttf").getFile()));
            arial_40p = arial.deriveFont(40f); // Set size to 32
            arial_80p = arial.deriveFont(80f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2){
        if(gameFinished == true){
            g2.setFont(arial_40p);
            g2.setColor(Color.WHITE);

            String text;
            int textLength;
            int x, y;

            text = "You found the final treasure.";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gamePanel.screenWidth/2 - textLength/2;
            y = gamePanel.screenHeight/2 - gamePanel.tileSize*3;
            g2.drawString(text, x, y);

            text = "Your time is :" + dFormat.format(playTime);
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gamePanel.screenWidth/2 - textLength/2;
            y = gamePanel.screenHeight/2 + gamePanel.tileSize*4;
            g2.drawString(text, x, y);

            g2.setFont(arial_80p);
            g2.setColor(Color.YELLOW);

            text = "Congratulations !";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gamePanel.screenWidth/2 - textLength/2;
            y = gamePanel.screenHeight/2 + gamePanel.tileSize*2;
            g2.drawString(text, x, y);

            gamePanel.gameThread = null;
        }
        else {
            g2.setFont(arial_40p);
            g2.setColor(Color.WHITE);
            g2.drawImage(keyImage, gamePanel.tileSize/2,gamePanel.tileSize/2, gamePanel.tileSize, gamePanel.tileSize, null);
            g2.drawString("x " + gamePanel.player.hasKey, 74, 65);

            // Time display
            playTime += (double)1/60;
            g2.drawString("Time : " + dFormat.format(playTime), gamePanel.tileSize*11, 65);

            // Render message
            if(messageOn == true){
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message, gamePanel.tileSize/2, gamePanel.tileSize*5);

                messageTimeCounter++;

                if(messageTimeCounter > 120){
                    messageTimeCounter = 0;
                    messageOn = false;
                }
            }
        }
    }
}
