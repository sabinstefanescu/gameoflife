package com.epam.dojo.gameoflife.services;

import com.epam.dojo.gameoflife.domain.InitialState;

import java.io.BufferedReader;

public interface FileParsingService {

    InitialState populateFromReader(BufferedReader reader);

    static FileParsingService getInstance() {
        return new FileParsingServiceImpl();
    }
}
