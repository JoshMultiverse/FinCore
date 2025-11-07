package org.example.operations.transactions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.example.App;
import org.example.interfaces.*;

// Class to create a hashMap of the transaction history using the hashmapDetails.csv file
public class TransactionHistory implements DataManager {
    // Attributes
    private static HashMap<String, LinkedList<String>> transactionHistory = new HashMap<>();
    private static String userEmail;
    private static boolean doesUserHaveTransactionHistory = false;
    private static int lineCounter = 0;
    private static int targetLine = 0;
    private static String lineToWrite = "";
    // Intialising global variables
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";

    public TransactionHistory(String email) {
        userEmail = email;
        loadHashMapContents();
    }

    // Method to load the hashmap contents containing the transaction history
    public static void loadHashMapContents() {
        lineCounter = 0;
        try (Scanner scanner = new Scanner(new File(App.directoryPath + "/csv/hashmapDetails.csv"))) {
            // Loop through the file and get the key + value and add to the hashmap
            while (scanner.hasNextLine()) {
                lineCounter++;
                String[] currentLineSplit = scanner.nextLine().split(",");

                if (lineCounter > 1) {
                    // Break the loop if the userEmail is found because do not need to add anymore
                    // values to hashmap
                    if (currentLineSplit[0].equals(userEmail)) {
                        // Split the history of the currentLineSplit[1] into a linked list
                        transactionHistory.put(currentLineSplit[0], buildLinkedList(currentLineSplit[1].split(":")));

                        // Change the boolean value to true
                        doesUserHaveTransactionHistory = true;
                        break;
                    }

                    // Split the history of the currentLineSplit[1] into a linked list
                    transactionHistory.put(currentLineSplit[0], buildLinkedList(currentLineSplit[1].split(":")));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("The intended file was not found in this directory peth: " + App.directoryPath);
        }
    }

    // Method which will build the link list using the splitted string
    public static LinkedList<String> buildLinkedList(String[] history) {
        LinkedList<String> transactionHistoryistoryLinkedList = new LinkedList<>();

        // Loop through the array of strings and add each value to the linked list
        for (String transaction : history) {
            if (transaction.equals("null")) {
                continue;
            }

            transactionHistoryistoryLinkedList.add(transaction);
        }

        // Return the linked list
        return transactionHistoryistoryLinkedList;
    }

    // Method to check if the user has existing transaction history
    public static boolean getIfUserHasTransactionHistory() {
        return doesUserHaveTransactionHistory;
    }

    public static HashMap<String, LinkedList<String>> getHashMapContents() {
        return transactionHistory;
    }

    @Override
    // Method to check if the user has a new key value
    public void createObject(String[] transactionArray) {
        // Check if email exists
        if (transactionHistory.get(userEmail) == null) {
            // Add to transaction history hashmap
            transactionHistory.put(userEmail, buildLinkedList(transactionArray));

            // Try add a new key + value to the file
            try (FileWriter fileWriter = new FileWriter(App.directoryPath + "/csv/hashmapDetails.csv", true)) {
                fileWriter.write(userEmail + "," + transactionArray[0] + ";" + transactionArray[1] + ";"
                        + transactionArray[2] + ":" + "\n");
            } catch (IOException e) {
                System.out.println("Appending to back of file failed!");
            }
        }

        // If not null, return as value exists
        return;
    }

    public String formatLineToWrite(String[] currentLineSplit, List<String> targetLinkedList) {
        // Format the string we want to write
        lineToWrite = currentLineSplit[0] + "," + targetLinkedList.toString()
                .replace("[", "")
                .replace("]", "")
                .replace(", ", ":");

        return lineToWrite;
    }

    @Override
    // Method to add the most recent transaction to the hashmap.
    public void readFile(String userEmail) {
        lineCounter = 0;
        List<String> linesInFile = new ArrayList<>();
        String[] transactionArray = App.buildTransactionArray();
        List<String> targetLinkedList = new LinkedList<>();
        String[] targetLineArray = new String[3];

        // Refresh the list
        try (Scanner scanner = new Scanner(new File(App.directoryPath + "/csv/hashmapDetails.csv"))) {
            while (scanner.hasNextLine()) {
                // Set the currentLine variable to the new line
                String currentLine = scanner.nextLine();

                // Add the current line to the ArrayList
                linesInFile.add(currentLine);

                // Incrment the line counter
                lineCounter += 1;

                String[] currentLineSplit = currentLine.split(",");

                // Get the email element from the linked list and change when that line is
                // reached.
                if (currentLineSplit[0].equals(userEmail)) {
                    try {
                        // Get the key (userEmail)
                        targetLinkedList = transactionHistory.get(userEmail);

                        // Set the target line
                        targetLineArray = currentLineSplit;

                        // Add the new object into the LL
                        targetLinkedList.add(transactionArray[0] + ";" + transactionArray[1] + ";"
                                + transactionArray[2] + ":");

                        // Set the target line
                        targetLine = lineCounter;
                    } finally {
                        System.out.println(ANSI_GREEN + "-------------------------");
                    }
                }
            }

            // If the target has not been assigned, return the method with value null
            if (targetLineArray.length == 0 && targetLinkedList.size() == 0) {
                return;
            }

            // Update the history by building the line to write
            updateHistory(formatLineToWrite(targetLineArray, targetLinkedList), linesInFile);
        } catch (FileNotFoundException e) {
            System.out.println("File is not found in working directory");
        }
    }

    @Override
    // Method to write the lines back into the file from the ArrayList
    public void updateHistory(String lineToWrite, List<String> linesInFile) {
        linesInFile.set(targetLine - 1, lineToWrite);

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(App.directoryPath + "/csv/hashmapDetails.csv", false))) {
            for (String line : linesInFile) {
                writer.write(line);
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Could not read the file");
        }
    }

    public static void displayTransactionHistory() {
        System.out.println("-------------------------");
        // Fill the hash map with values
        loadHashMapContents();

        // [deposit,207.74,208.74] : [deposit,208.74,209.74] : [deposit,209.74,210.74]:
        LinkedList<String> transactions = transactionHistory.get(userEmail);

        // [deposit] ,[207.74], [208.74]
        for (String transaction : transactions) {
            String[] transactionComponents = transaction.split(";");
            System.out.println((transactionComponents[0].equals("deposit") ? ANSI_GREEN : ANSI_RED)
                    + "Transaction Type: " + transactionComponents[0] + "\n" +
                    "Old Balance: $" + transactionComponents[1] + "\n" +
                    "New Balance: $" + transactionComponents[2] + "\n" + ANSI_RESET);
            System.out.println("-------------------------");
        }
    }

    public static String getUserEmail() {
        return userEmail;
    }
}
