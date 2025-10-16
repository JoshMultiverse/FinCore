package FinCore.helpers;

import java.util.Scanner;

import FinCore.log_in.LogIn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

// Class to handle all of my file operations which I need to do
public class FileEditor {
    private String currentLine;

    public void ChangeBalance(String currentBalance, String operation) {
        IO.println("Getting file...");

        try (Scanner scanner = new Scanner(new File("csv/userBalances.csv"))) {
            while (scanner.hasNextLine()) {
                // Set the currentLine variable to the new line
                currentLine = scanner.nextLine();
                IO.println(currentLine);

                // Check if the email is in the first field in the CSV file
                String[] currentLineParts = currentLine.split("\\s{0,},\\s{0,}");

                // If the email element is equal to the email
                if (currentLineParts[0].equals(LogIn.userEmail)) {
                    IO.println("changing balance");

                    // Parse the balance as a double so I can change the updated balance
                    double doubleTypeBalance = Double.parseDouble(currentLineParts[1]);

                    // Set the balance to the current balance
                    if (operation.equals("deposit")) {
                        doubleTypeBalance = Double.parseDouble(currentBalance);
                    } else {
                        doubleTypeBalance = Double.parseDouble(currentBalance);
                    }

                    // Update the record in the CSV file
                    try {
                        FileWriter fileWriter = new FileWriter("csv/userBalances.csv");
                        fileWriter.write(currentLineParts[0] + "," + Double.toString(doubleTypeBalance));

                        fileWriter.close();
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
}
