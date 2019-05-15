package com.epam.dojo.gameoflife.services;


import com.epam.dojo.gameoflife.domain.InitialState;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParsingServiceImpl implements FileParsingService{

    private Pattern containsWidthDeclaration = Pattern.compile("x[ ]?=[ ]?([0-9]+)");
    private Pattern containsHeightDeclaration = Pattern.compile("y[ ]?=[ ]?([0-9]+)");
    private Pattern cellsLine = Pattern.compile("^[ob$0-9!]+$");
    private Pattern cellGroup = Pattern.compile("(([0-9]*)(o))|(([0-9]*)(b))|(\\$)|(!)");

    @Override
    public InitialState populateFromReader(BufferedReader reader) {
        String line;
        InitialState is = new InitialState();
        StringBuilder cellDefinition = new StringBuilder();

        while ((line = readLineFromReader(reader)) != null) {
            if (line.startsWith("#")) {
                continue;
            }
            Matcher heightDeclaration = containsHeightDeclaration.matcher(line);
            Matcher widthDeclaration = containsWidthDeclaration.matcher(line);

            if (heightDeclaration.find() && widthDeclaration.find()) {
                is.width = Integer.parseInt(widthDeclaration.group(1));
                is.height = Integer.parseInt(heightDeclaration.group(1));
            }

            Matcher cellDeclaration = cellsLine.matcher(line);

            if (cellDeclaration.find()) {
                cellDefinition.append(line);
            }
        }

        is.points = populateLivingCells(cellDefinition.toString());

        return is;
    }

    private String readLineFromReader(BufferedReader reader) {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Point> populateLivingCells(String cellDefinition) {
        List<Point> pointList = new ArrayList<>();

        Matcher cellGroups = cellGroup.matcher(cellDefinition);
        int line = 0;
        int col = 0;

        while (cellGroups.find()) {
            String group = cellGroups.group(0);

            if ("$".equals(group)) {
                line++;
                col = 0;
            } else if (group.equals("!")){
                return pointList;
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

        throw new InvalidParameterException("No end to file found");
    }
}
