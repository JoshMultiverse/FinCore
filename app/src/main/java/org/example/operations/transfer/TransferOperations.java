package org.example.operations.transfer;

import java.io.*;
import java.util.*;

import org.example.App;

public class TransferOperations extends Transfer {
    private String transfereeEmail;
    private int targetLine;

    public TransferOperations(String email, double currentBalance) {
        super(currentBalance, email);
    }

    public double getTransferAmount(Scanner scanner) {
        while (true) {
            // Get the amount to user wishes to transfer
            System.out.print(App.ANSI_GREEN + "Enter the amount you wish to transfer: ");

            try {
                double amountToTransfer = scanner.nextInt();
                scanner.nextLine();

                return amountToTransfer;
            } catch (InputMismatchException eInputMismatchException) {
                System.out.println("Cannot enter text! ");
            }
        }
    }

    public void setTransfereeEmail(String emailToSet) {
        // Read through file to find email
        transfereeEmail = emailToSet;
    }

    public boolean doesTransfereeExist(String name, String sortCode, String accountNumber) {
        int line = 0; // <-- This will be used to keep track of the line

        // Make a new scanner object to scan through the bank details file
        try (Scanner bankDetailsScanner = new Scanner(new File(App.directoryPath + "/csv/bankDetails.csv"))) {
            String[] recordToCompare = new String[] { name, sortCode, accountNumber };

            while (bankDetailsScanner.hasNextLine()) {
                // Split the line into a string array
                String[] currentLineSplit = bankDetailsScanner.nextLine().split(",");
                boolean isAValueIncorrect = true; // <-- If an index does not match, this will change to true

                // Check if all values are equal to their indexes (-1 as email is ignored)
                for (int index = 0; index < currentLineSplit.length - 1; index++) {
                    if (!currentLineSplit[index].equals(recordToCompare[index])) {
                        isAValueIncorrect = false; // <-- Change because value is now false
                    }
                }

                // Return the value if it is false, else continue
                if (isAValueIncorrect) {
                    setTransfereeEmail(currentLineSplit[3]);
                    targetLine = line;
                    return false;
                }

                line++;
            }

            return true;
        } catch (FileNotFoundException eFileNotFoundException) {
            System.out.println("File could not be found in working directory");
            return true;
        }
    }

    public boolean doesValueExistInUserBalances() {
        // Refresh the list
        try (Scanner userBalancesScanner = new Scanner(
                new File(App.directoryPath + "/csv/userBalances.csv"))) {
            while (userBalancesScanner.hasNextLine()) {
                // Split the next line
                String[] currentLineParts = userBalancesScanner.nextLine().split(",");

                // If the email element is equal to the email - return true
                if (currentLineParts[0].equals(transfereeEmail)) {
                    return true;
                }
            }

            return false;
        } catch (FileNotFoundException eFileNotFoundException) {
            // Throw an error if file not found
            throw new Error("File not found in current directory: " + eFileNotFoundException);
        }
    }

    // public void addToTransactionHistory() {

    // }

    public void readAllLinesAfterTargetLine(double amountToTransfer) {
        List<String> linesInFile = new ArrayList<>();
        int currentRowNumber = 0;
        String lineToWrite = "";

        try (Scanner userBalancesScanner = new Scanner(new File(App.directoryPath + "/csv/userBalances.csv"))) {
            while (userBalancesScanner.hasNextLine()) {
                // Set the line
                if (currentRowNumber == targetLine) {
                    String[] split = userBalancesScanner.nextLine().split(",");
                    lineToWrite = split[0] + "," + Double.toString(Double.parseDouble(split[1]) + amountToTransfer);
                    linesInFile.add(lineToWrite);
                }

                // Add to the arraylist
                String currentLine = userBalancesScanner.nextLine();
                linesInFile.add(currentLine);

                // Increment the current row number
                currentRowNumber++;
            }

            // Set line and write back to file
            writeLinesBackToFile(linesInFile, lineToWrite);
        } catch (FileNotFoundException e) {
            System.out.println("File not found in current working directory!");
        }
    }

    public void writeLinesBackToFile(List<String> linesInFile, String lineToWrite) {
        // Write the line back to the file after the target line
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(App.directoryPath + "/csv/userBalances.csv", false))) {
            for (String line : linesInFile) {
                writer.write(line);
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Error when updating the balance");
        }
    }
}
