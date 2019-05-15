package com.epam.dojo.gameoflife.renderer;

import com.epam.dojo.gameoflife.domain.InitialState;
import com.epam.dojo.gameoflife.services.FileParsingService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class OpenLifeFile implements ActionListener {
    private final ConwaysGameOfLife gameOfLife;

    public OpenLifeFile(ConwaysGameOfLife gameOfLife ) {
        this.gameOfLife = gameOfLife;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final JFileChooser fc = new JFileChooser();

        int returnVal = fc.showOpenDialog(gameOfLife);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            Charset charset = Charset.forName("US-ASCII");
            FileParsingService parser = FileParsingService.getInstance();

            try (BufferedReader reader = Files.newBufferedReader(file.toPath(), charset)) {
                InitialState initialState = parser.populateFromReader(reader);
                this.gameOfLife.setPoints(initialState.points);
                this.gameOfLife.updateGameSize(initialState.width, initialState.height);
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            }


        } else {
            System.out.println("Open command cancelled by user.");
        }
    }
}
