package org.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;
import java.util.Scanner;

import org.example.log_in.LogIn;
import org.example.operations.transactions.*;

class LogInTest {
    private Scanner scanner;
    private LogIn logInInstance = new LogIn();

    @BeforeEach
    void setUp() {
        App.directoryPath = "src/test/resources";
    }

    @Test
    void checkUserCredentials_whenUserExists_True() {
        // Arrange
        String userInput = "test@example.com\ntest\n\n\n";
        scanner = new Scanner(userInput);

        // Act
        boolean result = logInInstance.checkUserCredentials(scanner);

        // Assign
        assertTrue(result);
    }

    @Test
    void testCheckUserCredentials_whenUserDoesNotExist_False() {
        // Arrange
        String userInput = "dummy\nn\n\n\n";
        scanner = new Scanner(userInput);

        // Act + Assert <-- Will go to the sign up class to log in
        assertFalse(logInInstance.checkUserCredentials(scanner));
    }

    @Test
    void testCheckUserCredentials_whenEmailIsBlank_Error() {
        // Arrange
        String userInput = "\n";
        scanner = new Scanner(userInput);

        // Act + Assert <-- Will go to the sign up class to log in
        assertThrows(NoSuchElementException.class, () -> {
            boolean result = logInInstance.checkUserCredentials(scanner);
        });
    }

    @Test
    void testDoesEmailExist_validEmail_True() {
        // Arrange
        String userInput = "test@example.com";
        scanner = new Scanner(userInput);

        // Act
        boolean result = LogIn.doesEmailExist(userInput);

        // Assert
        assertTrue(result);
    }

    @Test
    void testDoesEmailExist_invalidEmail_False() {
        // Arrange
        String userInput = "t";
        scanner = new Scanner(userInput);

        // Act
        boolean result = LogIn.doesEmailExist(userInput);

        // Assert
        assertFalse(result);
    }

    @Test
    void testDoesEmailExist_blankEmail_False() {
        // Arrange
        String userInput = "";
        scanner = new Scanner(userInput);

        // Act
        boolean result = LogIn.doesEmailExist(userInput);

        // Assert
        assertFalse(result);
    }

    @Test
    void testDoesPasswordMatch_correctPassword_True() {
        // Act
        boolean result = LogIn.doesPasswordMatchCurrent("", "test@example.com", "test");

        // Assert
        assertTrue(result);
    }

    @Test
    void testFindUserBalance_Valid_True() {
        // Act
        String email = "test@example.com";

        // Arrange
        double userBalance = LogIn.findUserBalance(email);

        // Assert
        assertEquals(userBalance, App.currentBalance);
    }

    @Test
    void testFindUserBalance_invalid_Null() {
        // Act
        String email = "test@example";

        // Arrange
        double userBalance = LogIn.findUserBalance(email);

        // Assert
        assertEquals(userBalance, -1.0);
    }
}
