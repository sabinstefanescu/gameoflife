package com.epam.dojo.gameoflife;


import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseLifFile {
    private final ConwaysGameOfLife gameOfLife;
    Pattern containsWidthDeclaration = Pattern.compile("x[ ]?=[ ]?([0-9]+)");
    Pattern containsHeightDeclaration = Pattern.compile("y[ ]?=[ ]?([0-9]+)");
    Pattern cellsLine = Pattern.compile("^[ob$0-9!]+$");
    Pattern cellGroup = Pattern.compile("(([0-9]*)(o))|(([0-9]*)(b))|(\\$)|(!)");
    private int noCol;


    public ParseLifFile(ConwaysGameOfLife gameOfLife) {
        this.gameOfLife = gameOfLife;
    }


    public void populateFromReader(BufferedReader reader) throws IOException {
        String line = null;
        StringBuilder cellDefinition = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("#")) {
                continue;
            }
            Matcher heightDeclaration = containsHeightDeclaration.matcher(line);
            Matcher widthDeclaration = containsWidthDeclaration.matcher(line);

            if (heightDeclaration.find() && widthDeclaration.find()) {
                this.noCol = Integer.parseInt(widthDeclaration.group(1));
                this.gameOfLife.updateGameSize(Integer.parseInt(widthDeclaration.group(1)), Integer.parseInt(heightDeclaration.group(1)));
            }

            Matcher cellDeclaration = cellsLine.matcher(line);

            if (cellDeclaration.find()) {
                cellDefinition.append(line);
            }
        }

        populateLivingCells(cellDefinition.toString());
    }

    private void populateLivingCells(String cellDefinition) {
        List<Point> pointList = new ArrayList<>();
        System.out.println(cellDefinition);
        Matcher cellGroups = cellGroup.matcher(cellDefinition);
        int line = 0;
        int col = 0;

        while (cellGroups.find()) {
            String group = cellGroups.group(0);
            System.out.println(group);
            if ("$".equals(group)) {
                line++;
                col = 0;
            } else if (group.equals("!")){
                this.gameOfLife.setPoints(pointList);
            } else if ("o".equals(group)) {
                pointList.add(new Point(col, line));
                col++;
            } else if ("b".equals(group)) {
                col++;
            } else if (group.length() > 1) {
                String status = group.substring(group.length()-1);
                int count = Integer.parseInt(group.substring(0, group.length()-1));
                if ("o".equals(status)) {
                    for (int i = 0; i<count; i ++) {
                        pointList.add(new Point(col, line));
                        col++;
                    }
                } else {
                    for (int i = 0; i<count; i ++) {
                        col++;
                    }
                }
            }
        }
    }
}
