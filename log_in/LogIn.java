package FinCore.log_in;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

// Public class used to log people in
public class LogIn {
    // Define global variables - dont want password being used outside of the class
    public static String userEmail;
    private static String userPassword;

    // Method to check that the user credentials are valid - entry method for this
    // class
    public boolean checkUserCredentials(Scanner scanner) {
        // Get the user's email
        IO.print("Please enter your email: ");
        userEmail = scanner.nextLine();

        // Check if their email exists in the CSV file
        if (doesEmailExist(userEmail)) {
            // Get the password
            IO.print("Please enter your password: ");
            userPassword = scanner.nextLine();

            // If the password matches the one stored in the CSV file
            if (doesPasswordMatchCurrent(userEmail, userPassword)) {
                // Verify log in success
                return true;
            }
            // Indicate the password was not correct + return unsucessful sign in
            IO.println("Password not correct");
            return false;
        }
        // Indicate the email was not correct + return unsucessful sign in
        IO.println("Email not found");
        return false;
    }

    // Method which returns the result of the `findUserBalance` method
    public double getUserBalance(String userEmail) {
        return findUserBalance(userEmail);
    }

    // Method which checks if the password entered matches the one stored
    private static boolean doesPasswordMatchCurrent(String userEmail, String userPassword) {
        // Opens the CSV file, compares each line to the email and password entered. If
        // they match - return a true value.
        try (Scanner scanner = new Scanner(new File("csv/userCredentials.csv"))) {
            String[] emailAndPassword = new String[] { userEmail, userPassword };

            while (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split(",");

                if (values[0].equals(emailAndPassword[0])) {
                    if (values[1].equals(emailAndPassword[1])) {
                        return true;
                    }
                }
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method which scans through the users CSV file and checks if the email exists.
    private static boolean doesEmailExist(String userEmail) {
        try (Scanner scanner = new Scanner(new File("csv/userBalances.csv"))) {
            while (scanner.hasNextLine()) {
                if (scanner.nextLine().contains(userEmail)) {
                    return true;
                }
            }

            return false;
        } catch (FileNotFoundException eFileNotFoundException) {
            System.err.println("File not found in current working directory");
            return false;
        }
    }

    // Method to find the current user balance which is stored in the CSV file.
    private static Double findUserBalance(String userEmail) {
        try (Scanner scanner = new Scanner(new File("csv/userBalances.csv"))) {
            while (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split(",");

                // Loop through each line and cancel out the headers.
                for (String element : values) {
                    if (element.equals("User") || element.equals("Balance")) {
                        continue;
                    } else {
                        // If the element is equal to the user email.
                        if (element.equals(userEmail)) {
                            return Double.parseDouble(values[1]);
                        }

                        continue;
                    }
                }
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
