package com.epam.dojo.gameoflife.domain;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameOfLifeServiceImpl implements GameOfLifeService {
    @Override
    public List<Point> getNextIteration(List<Point> previousIteration, Dimension gameBoardSize) {
       return previousIteration;
    }
}
