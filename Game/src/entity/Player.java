package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    // We need to add it because we're going to draw on it
    GamePanel gamePanel;
    // Need to see which keys are pressed for movement
    KeyHandler keyHandler;

    // Only the player will have additional sprites to make it richer
    BufferedImage up3, down3, left3, right3;

    public int hasKey = 0;

    // Screen Coordinates
    public final int screenX;
    public final int screenY;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth/2 - gamePanel.tileSize/2;
        screenY = gamePanel.screenHeight/2 - gamePanel.tileSize/2;

        setDefaultValues();
        getPlayerImages();

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 30;
    }

    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 23;
        worldY = gamePanel.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    // Load images from resource folder
    public void getPlayerImages() {
        up1 = setup("up1");
        up2 = setup("up2");
        up3 = setup("up3");
        down1 = setup("down1");
        down2 = setup("down2");
        down3 = setup("down3");
        left1 = setup("left1");
        left2 = setup("left2");
        left3 = setup("left3");
        right1 = setup("right1");
        right2 = setup("right2");
        right3 = setup("right3");
    }

    public BufferedImage setup(String imageName){
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;
        BufferedImage image2 = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/player/"+imageName+".png"));
            image2 = utilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
        return image2;
    }

    public void update() {
        if (keyHandler.upPressed == true ||
                keyHandler.downPressed == true ||
                keyHandler.leftPressed == true ||
                keyHandler.rightPressed == true) {
            if (keyHandler.upPressed == true) {
                direction = "up";
            } else if (keyHandler.downPressed == true) {
                direction = "down";

            } else if (keyHandler.leftPressed == true) {
                direction = "left";
            } else if (keyHandler.rightPressed == true) {
                direction = "right";
            }


            // Tile collision detection
            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);

            // Object collision detection
            int objectIndex = gamePanel.collisionChecker.checkObject(this, true);
            pickUpObject(objectIndex);

            // If collision is false player can move
            if (collisionOn == false) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                if (keyHandler.upPressed || keyHandler.downPressed) {
                    if (spriteNum == 2) {
                        spriteNum = 1;
                    } else if (spriteNum == 1) {
                        spriteNum = 3;
                    } else if (spriteNum == 3) {
                        spriteNum = 1;
                    }
                } else if (keyHandler.leftPressed || keyHandler.rightPressed) {
                    if (spriteNum == 1){
                        spriteNum = 2;
                    } else if (spriteNum == 2 || spriteNum == 3){
                        spriteNum = 1;
                    }
                }
                spriteCounter = 0;
            }
        }
    }

    public void pickUpObject(int i){
        if(i != 999){
            String objectName = gamePanel.obj[i].name;

            switch(objectName){
                case "Key":
                    gamePanel.playSFX(1);
                    hasKey++;
                    gamePanel.obj[i] = null;
                    gamePanel.ui.showMessage("You collected a key !");
                    break;
                case "Door":
                    if(hasKey > 0){
                        gamePanel.playSFX(3);
                        gamePanel.obj[i] = null;
                        hasKey--;
                        gamePanel.ui.showMessage("You opened the door !");
                    }
                    else {
                        gamePanel.ui.showMessage("You need a key.");
                    }

                    break;
                case "Boots":
                    gamePanel.playSFX(2);
                    speed+=1;
                    gamePanel.obj[i] = null;
                    gamePanel.ui.showMessage("Speed increased.");
                    break;
                case "Chest":
                    gamePanel.ui.gameFinished = true;
                    gamePanel.stopMusic();
                    gamePanel.playSFX(4);
                    break;
            }
        }
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;

        switch(direction){
            case "up":
                if(spriteNum == 1) image = up1;
                if(spriteNum == 2) image = up2;
                if(spriteNum == 3) image = up3;

                break;
            case "down":
                if(spriteNum == 1) image = down1;
                if(spriteNum == 2) image = down2;
                if(spriteNum == 3) image = down3;

                break;
            case "left":
                if(spriteNum == 1) image = left1;
                if(spriteNum == 2) image = left2;
                if(spriteNum == 3) image = left3;

                break;
            case "right":
                if(spriteNum == 1) image = right1;
                if(spriteNum == 2) image = right2;
                if(spriteNum == 3) image = right3;

                break;
        }
        g2.drawImage(image, screenX, screenY, null);
    }
}
