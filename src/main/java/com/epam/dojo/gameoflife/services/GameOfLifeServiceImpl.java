package com.epam.dojo.gameoflife.services;

import java.awt.*;
import java.util.List;

public class GameOfLifeServiceImpl implements GameOfLifeService {
    @Override
    public List<Point> getNextIteration(List<Point> previousIteration, Dimension gameBoardSize) {
        return previousIteration;
    }
}