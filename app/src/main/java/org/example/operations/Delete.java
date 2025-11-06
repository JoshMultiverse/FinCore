package org.example.operations;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.example.App;

public class Delete {
    private static String emailOfAccountToDelete;
    private static int listPointer = 0;
    private static List<String> linesToWrite = new ArrayList<>();
    private static Deque<String> filesToReadUser = new ArrayDeque<>();
    private static Deque<String> filesToWriteTo = new ArrayDeque<>();
    private static Map<String, List<String>> fileContents = new HashMap<>();
    private String[] files = new String[] { "/csv/bankDetails.csv", "/csv/hashmapDetails.csv", "/csv/userBalances.csv",
            "/csv/userCredentials.csv" };

    public Delete(String email) {
        emailOfAccountToDelete = email;

        // Popualate both of the queues
        for (String file : files) {
            filesToReadUser.add(file);
            filesToWriteTo.add(file);
        }
    }

    public static void removeUserRecord() {
        // Check that the queue is not empty, if it is - call the method to write the
        // data back into the files
        boolean isQueueEmpty = false;

        while (!isQueueEmpty) {
            // Check the queue isnt empty
            if (filesToReadUser.peek() == null) {
                // Set the boolean value to true
                isQueueEmpty = true;
                break;
            }

            // Call method to write the data back into the files
            // If ArrayList contains values, clear the list
            if (linesToWrite.size() > 0) {
                listPointer = linesToWrite.size();
            }

            // Get the next file that is in the queue
            try (Scanner fileScanner = new Scanner(new File(App.directoryPath + filesToReadUser.peek()))) {
                // Find the email line in the file
                while (fileScanner.hasNextLine()) {
                    String currentLine = fileScanner.nextLine();

                    if (currentLine.contains(emailOfAccountToDelete)) {
                        if (filesToReadUser.peek().equals("/csv/userBalances.csv")) {
                            // Handle differently as money is involved
                            transferBalancesToMainBank(currentLine);
                        }

                        // Dont add this line to the list.
                        continue;
                    }

                    // If doesnt contain email, add the line to the ArrayList
                    linesToWrite.add(currentLine);
                }

                // When all files have been added, add the new ArrayList to the hashmap
                // Add only the elements from the old listPointer to the end
                fileContents.put(filesToReadUser.poll(),
                        new ArrayList<>(linesToWrite.subList(listPointer, linesToWrite.size())));

                // Update the pointer
                listPointer = linesToWrite.size();
            } catch (FileNotFoundException e) {
                System.out.println("File not found in working directory! ");
                return;
            }
        }

        // Write data back into files
        writeDataBackIntoFiles();
    }

    public static void writeDataBackIntoFiles() {
        // Loop through the writing files queue
        boolean isQueueEmpty = false;
        listPointer = 0;

        while (!isQueueEmpty) {
            // Check the queue isnt empty
            if (filesToWriteTo.peek() == null) {
                // Set the boolean value to true
                isQueueEmpty = true;
                break;
            }

            // Call method to write the data back into the files
            // If ArrayList contains values, clear the list
            if (linesToWrite.size() > 0) {
                listPointer = linesToWrite.size();
            }

            // Try to write back to all of the data into each of the files
            try (var dataWriter = new BufferedWriter(new FileWriter(App.directoryPath + filesToWriteTo.peek()))) {
                // Get the data from the newly created HashMap
                for (String line : fileContents.get(filesToWriteTo.poll())) {
                    dataWriter.write(line);
                    dataWriter.newLine();
                }

            } catch (IOException e) {
                System.out.println("Failed to write all of the data to this file!");
                return;
            }
        }
    }

    public static void transferBalancesToMainBank(String currentLine) {
        // Split the line
        String[] split = currentLine.split(",");
        double amountToReturn = Double.parseDouble(split[1]); // <-- Parse the double

        // Reform the line to write index 1 <-- Use the listPointer to get the index of
        // the current file contents
        String[] masterBankRecord = linesToWrite.get(listPointer + 1).split(",");
        String lineToAdd = "master@fincore.com," + (Double.parseDouble(masterBankRecord[1]) + amountToReturn);

        // Set this new line in the ArrayList
        linesToWrite.set(listPointer + 1, lineToAdd);
    }
}
