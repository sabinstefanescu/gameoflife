package com.epam.dojo.gameoflife;

import javax.swing.*;
import java.awt.*;

public class Application {
    private static final Dimension DEFAULT_WINDOW_SIZE = new Dimension(800, 600);
    private static final Dimension MINIMUM_WINDOW_SIZE = new Dimension(400, 400);

    public static void main(String[] args) {
        // Setup the swing specifics
        JFrame game = new ConwaysGameOfLife();
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setTitle("Conway's Game of Life");

        game.setSize(DEFAULT_WINDOW_SIZE);
        game.setMinimumSize(MINIMUM_WINDOW_SIZE);
        game.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - game.getWidth())/2,
                (Toolkit.getDefaultToolkit().getScreenSize().height - game.getHeight())/2);
        game.setVisible(true);
    }

}
