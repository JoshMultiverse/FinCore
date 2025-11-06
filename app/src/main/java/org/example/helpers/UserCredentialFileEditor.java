package org.example.helpers;

import java.io.*;
import java.util.List;

import org.example.interfaces.*;

// Class which is responsible for makign changed in this file
public class UserCredentialFileEditor implements DataManager {
    public void createObject(String[] newUserArray) {
        try (FileWriter userCredentialsWriter = new FileWriter(
                org.example.App.directoryPath + "/csv/userCredentials.csv",
                true)) {
            userCredentialsWriter.write(newUserArray[0] + "," + newUserArray[1] + "," + newUserArray[2] + "\n");
        } catch (IOException eIoException) {
            eIoException.printStackTrace();
        }
    }

    public void readFile(String target) {
    }

    public void updateHistory(String lineToWrite, List<String> linesInFile) {
    }
}
