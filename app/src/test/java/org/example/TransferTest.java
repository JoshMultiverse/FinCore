package test.java.org.example;

import org.example.operations.Operations;
import org.example.operations.transfer.Transfer;
import org.junit.jupiter.api.*;
import org.example.App;
import org.example.log_in.LogIn;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class TransferTest {
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        App.directoryPath = "src/test/resources";
    }

    @Test
    void testIsSortCodeValid_Valid_True() {
        // Arrange
        var transferInstance = new Transfer(0, "test@example.com");
        Transfer.setSortCode("000000");

        // Act
        boolean result = transferInstance.isSortCodeValid();

        // Assert
        assertTrue(result);

    }

    @Test
    void testIsSortCodeValid_Valid_LowerBound_False() {
        // Arrange
        var transferInstance = new Transfer(0, "test@example.com");
        Transfer.setSortCode("00000"); // <-- 5 0s

        // Act
        boolean result = transferInstance.isSortCodeValid();

        // Assert
        assertFalse(result);

    }

    @Test
    void testIsSortCodeValid_Valid_UpperBound_False() {
        // Arrange
        var transferInstance = new Transfer(0, "test@example.com");
        Transfer.setSortCode("0000000");

        // Act
        boolean result = transferInstance.isSortCodeValid();

        // Assert
        assertFalse(result);

    }

    @Test
    void testIsAccountNumber_Valid_True() {
        // Arrange
        var transferInstance = new Transfer(0, "test@example.com");
        Transfer.setAccountNumber("00000000"); // <---- Valid input

        // Act
        boolean result = transferInstance.isAccountNumberValid();

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsAccountNumber_LowerBound_False() {
        // Arrange
        var transferInstance = new Transfer(0, "test@example.com");
        Transfer.setAccountNumber("0000000"); // <---- 7 0s

        // Act
        boolean result = transferInstance.isAccountNumberValid();

        // Assert
        assertFalse(result);
    }

    @Test
    void testIsAccountNumber_UpperBound_False() {
        // Arrange
        var transferInstance = new Transfer(0, "test@example.com");
        Transfer.setAccountNumber("000000000"); // <---- 9 0s

        // Act
        boolean result = transferInstance.isAccountNumberValid();

        // Assert
        assertFalse(result);
    }

    @Test
    void testDoesUserWantToTryAgain_True() {
        // Arrange
        var transferInstance = new Transfer(0, "test@example.com");
        String input = "y";
        scanner = new Scanner(input);

        // Act
        boolean result = transferInstance.doesUserWantToTryAgain(scanner);

        // Assert
        assertTrue(result);
    }

    @Test
    void testDoesUserWantToTryAgain_False() {
        // Arrange
        var transferInstance = new Transfer(0, "test@example.com");
        String input = "N";
        scanner = new Scanner(input);

        // Act
        boolean result = transferInstance.doesUserWantToTryAgain(scanner);

        // Assert
        assertFalse(result);
    }

    @Test
    void testDoesUserWantToTryAgain_Error() {
        // Arrange
        var transferInstance = new Transfer(0, "test@example.com");
        String input = "10";
        scanner = new Scanner(input);

        // Act
        boolean result = transferInstance.doesUserWantToTryAgain(scanner);

        // Assert
        assertFalse(result);
    }

    @Test
    void testConfirmUserWantsToDoTransaction_True() {
        // Arrange
        var transferInstance = new Transfer(0, "test@example.com");
        String input = "y";
        scanner = new Scanner(input);

        // Act
        boolean result = transferInstance.confirmUserWantsToDoTransaction(scanner, 0);

        // Assert
        assertTrue(result);
    }

    @Test
    void testConfirmUserWantsToDoTransaction_False() {
        // Arrange
        var transferInstance = new Transfer(0, "test@example.com");
        String input = "N";
        scanner = new Scanner(input);

        // Act
        boolean result = transferInstance.confirmUserWantsToDoTransaction(scanner, 0);

        // Assert
        assertFalse(result);
    }

    @Test
    void testConfirmUserWantsToDoTransaction_Number() {
        // Arrange
        var transferInstance = new Transfer(0, "test@example.com");
        String input = "10";
        scanner = new Scanner(input);

        // Act
        boolean result = transferInstance.confirmUserWantsToDoTransaction(scanner, 0);

        // Assert
        assertFalse(result);
    }

    @Test
    void displayTest_Valid() {
        // Arrange
        LogIn.setEmail("teste@example.com");
        scanner = new Scanner("000000\n00000000\ntest\n2\ny");
        var transferInstance = new Transfer(5, "test@example.com");

        // Act
        transferInstance.displayText(scanner, 5);
    }

    @Test
    void displayTest_NoUser_False() {
        // Arrange
        LogIn.setEmail("teste@example.com");
        scanner = new Scanner("000000\n00000000\nfake\n2\ny");
        var transferInstance = new Transfer(5, "test@example.com");

        // Act
        transferInstance.displayText(scanner, 5);
    }

    @Test
    void displayTest_transferBiggerThanBalance() {
        // Arrange
        LogIn.setEmail("teste@example.com");
        scanner = new Scanner("000000\n00000000\ntest\n6\nn");
        var transferInstance = new Transfer(5, "test@example.com");

        // Act
        transferInstance.displayText(scanner, 5);
    }
}
