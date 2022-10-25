package main;
import javax.swing.*;

public class Main {
    public static void main(String[] args){
        // Game Window
        JFrame window = new JFrame();

        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("Chronologia");

        // Game panel Initialization
        GamePanel gamePanel = new GamePanel();

        window.add(gamePanel);
        window.pack();

        // Additional Window Properties
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
