package org.operations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

// Class to create a hashMap of the transaction history using the hashmapDetails.csv file
public class TransactionHistory {
    // Attributes
    private static HashMap<String, LinkedList<String>> transactionHistory = new HashMap<String, LinkedList<String>>();
    private static String userEmail;
    private static boolean doesUserHaveTransactionHistory = false;

    public TransactionHistory(String userEmail) {
        this.userEmail = userEmail;
        loadHashMapContents();
    }

    // Method to load the hashmap contents containing the transaction history
    public static void loadHashMapContents() {
        try (Scanner scanner = new Scanner(new File(org.App.directoryPath + "/csv/hashmapDetails.csv"))) {
            // Loop through the file and get the key + value and add to the hashmap
            while (scanner.hasNextLine()) {
                String[] currentLineSplit = scanner.nextLine().split(",");
                transactionHistory.put(currentLineSplit[0], buildLinkedList(currentLineSplit[1].split(":")));

                // Split the history of the currentLineSplit[1] into a linked list

                // Break the loop if the userEmail is found because do not need to add anymore
                // values to hashmap
                if (currentLineSplit[0].equals(userEmail)) {
                    // Change the boolean value to true
                    doesUserHaveTransactionHistory = true;
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("The intended file was not found in this directory peth: " + org.App.directoryPath);
        }
    }

    public static LinkedList<String> buildLinkedList(String[] history) {
        LinkedList<String> transactionHistoryistoryLinkedList = new LinkedList<>();

        // Loop through the array of strings and add each value to the linked list
        for (String transaction : history) {
            transactionHistoryistoryLinkedList.add(transaction);
        }

        // Return the linked list
        return transactionHistoryistoryLinkedList;
    }

    // Method to check if the user has existing transaction history
    public static boolean checkIfUserHasTransactionHistory() {
        return doesUserHaveTransactionHistory;
    }

    // Method to check if the user has a new key value
    public static void createNewKeyValue(String[] transactionArray) {
        // Add to transaction history hashmap
        transactionHistory.put(userEmail, buildLinkedList(transactionArray));

        // Try add a new key + value to the file
        try (FileWriter fileWriter = new FileWriter(org.App.directoryPath + "/csv/hashmapDetails.csv", true)) {
            fileWriter.write("\n" + userEmail + "," + transactionArray.toString() + ":");
        } catch (IOException e) {
            System.out.println("Appending to back of file failed!");
        }
    }

    // Method to add the most recent transaction to the hashmap.
    public static void editValue() {

    }
}
