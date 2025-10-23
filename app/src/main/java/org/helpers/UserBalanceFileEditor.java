package org.helpers;

import org.log_in.LogIn;

import java.io.*;
import java.util.*;

// Class to handle all of my file operations which I need to do
public class UserBalanceFileEditor {
    private String currentLine;
    private int LineCounter;
    private LogIn logInInstance;

    public UserBalanceFileEditor(int resetLineCounter, LogIn logInInstance) {
        this.LineCounter = resetLineCounter;
        this.logInInstance = logInInstance;
    }

    // Method to intiliase the balance to 0 when a user creates an account
    public void IntialiseUserAccount(String email, float startingBalance) {
        try (FileWriter userBalanceFile = new FileWriter("csv/userBalances.csv", true)) {
            userBalanceFile.write(email + "," + String.format("%.2f", startingBalance) + "\n");
        } catch (IOException eIoException) {
            eIoException.printStackTrace();
        }
    }

    // Method to change the balance whenever a user makes a change to it
    public void ChangeBalance(String currentBalance) {
        List<String> linesInFile = new ArrayList<>();
        LineCounter = 0;

        // Refresh the list
        try (Scanner scanner = new Scanner(new File("csv/userBalances.csv"))) {
            while (scanner.hasNextLine()) {
                // Set the currentLine variable to the new line
                currentLine = scanner.nextLine();

                // Add the current line to the ArrayList
                linesInFile.add(currentLine);

                // Incrment the line counter
                LineCounter += 1;

                // Check if the email is in the first field in the CSV file
                String[] currentLineParts = currentLine.split("\\s{0,},\\s{0,}");

                // If the email element is equal to the email
                if (currentLineParts[0].equals(logInInstance.getEmail())) {
                    // Parse the balance as a double so I can change the updated balance
                    double doubleTypeBalance = Double.parseDouble(currentLineParts[1]);

                    // Set the balance to the current balance - parse to double as currently string
                    doubleTypeBalance = Double.parseDouble(currentBalance);

                    // Update the record in the CSV file
                    try {
                        String lineToWrite = currentLineParts[0] + "," + String.format("%.2f", doubleTypeBalance);

                        WriteLinesBackIntoFile(lineToWrite, linesInFile);
                        return;
                    } catch (IOException eIOException) {
                        throw new Error(eIOException);
                    }
                }
            }

            return;
        } catch (FileNotFoundException eFileNotFoundException) {
            // Throw an error if file not found
            throw new Error("File not found in current directory: " + eFileNotFoundException);
        }
    }

    public void WriteLinesBackIntoFile(String lineToWrite, List<String> linesInFile) throws IOException {
        linesInFile.set(LineCounter - 1, lineToWrite);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("csv/userBalances.csv", false))) {
            for (String line : linesInFile) {
                writer.write(line);
                writer.newLine();
            }

            writer.close();
        }
    }
}
