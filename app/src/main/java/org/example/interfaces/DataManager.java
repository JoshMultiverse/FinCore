package org.example.interfaces;

import java.util.List;

public interface DataManager {
    void createObject(String[] object);

    void readFile(String target);

    void updateHistory(String lineToWrite, List<String> linesInFile);
}