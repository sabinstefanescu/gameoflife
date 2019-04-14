package com.epam.dojo.gameoflife;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class OpenLifFile implements ActionListener {
    private final ConwaysGameOfLife gameOfLife;

    public OpenLifFile(ConwaysGameOfLife gameOfLife ) {
        this.gameOfLife = gameOfLife;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final JFileChooser fc = new JFileChooser();

        int returnVal = fc.showOpenDialog(gameOfLife);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            Charset charset = Charset.forName("US-ASCII");
            ParseLifFile parser = new ParseLifFile(this.gameOfLife);

            try (BufferedReader reader = Files.newBufferedReader(file.toPath(), charset)) {
                parser.populateFromReader(reader);
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            }

        } else {
            System.out.println("Open command cancelled by user.");
        }
    }
}
