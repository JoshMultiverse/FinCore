package org.helpers;

import java.io.*;

// Class which is responsible for makign changed in this file
public class UserCredentialFileEditor {
    private String userEmail;
    private String userPassword;
    private String firstName;

    public UserCredentialFileEditor(String name, String emailToBe, String passwordToBe) {
        this.userEmail = emailToBe;
        this.firstName = name;
        this.userPassword = passwordToBe;
    }

    public boolean AppendUserCredentialsToFile() throws IOException {
        try (FileWriter userCredentialsWriter = new FileWriter(org.App.directoryPath + "/csv/userCredentials.csv",
                true)) {
            userCredentialsWriter.write(firstName + "," + userEmail + "," + userPassword + "\n");

            return true;
        } catch (IOException eIoException) {
            eIoException.printStackTrace();
            return false;
        }
    }
}
