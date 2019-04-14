package com.epam.dojo.gameoflife.domain;

import java.awt.*;
import java.util.List;

public interface GameOfLifeService {

    List<Point> getNextIteration(List<Point> previousIteration, Dimension gameBoardSize);

    static GameOfLifeService getInstance() {
        return new GameOfLifeServiceImpl();
    }

}
