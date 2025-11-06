package test.java.org.example;

import org.example.operations.Operations;
import org.example.operations.transfer.TransferOperations;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Scanner;

import org.example.App;
import org.example.log_in.LogIn;

public class TransferOperationsTest {
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        App.directoryPath = "src/test/resources";
    }

    @Test
    void testGetTransferAmount_ValidInput_True() {
        // Arrange
        TransferOperations transferOperationsInstance = new TransferOperations("test@example.com", 0);
        String userInput = "5\n";
        scanner = new Scanner(userInput);

        // Act
        double result = transferOperationsInstance.getTransferAmount(scanner);

        // Arrange
        assertEquals(result, 5.0);
    }

    @Test
    void testGetTransferAmount_ValidInput_False() {
        // Arrange
        TransferOperations transferOperationsInstance = new TransferOperations("test@example.com", 0);
        String userInput = "hello\n";
        scanner = new Scanner(userInput);

        // Act
        double result = transferOperationsInstance.getTransferAmount(scanner);

        // Arrange
        assertEquals(result, -1);
    }

    @Test
    void testDoesTransfereeExist_ValidEmail_True() {
        // Arannge
        TransferOperations transferOperationsInstance = new TransferOperations("test@example.com", 0);

        // Act
        boolean result = transferOperationsInstance.doesTransfereeExist("test", "00-00-00", "00000000");

        // Arrange
        assertTrue(result);
    }

    @Test
    void testDoesTransfereeExist_ValidEmail_False() {
        // Arannge
        TransferOperations transferOperationsInstance = new TransferOperations("test@example.com", 0);

        // Act
        boolean result = transferOperationsInstance.doesTransfereeExist("fakename", "00-00-00", "00000000");

        // Arrange
        assertFalse(result);
    }

    @Test
    void testDoesTransfereeExist_ValidEmail_IOException() {
        // Arannge
        TransferOperations transferOperationsInstance = new TransferOperations("test@example.com", 0);
        App.directoryPath = "src";

        // Act
        boolean result = transferOperationsInstance.doesTransfereeExist("test", "00-00-00", "00000000");

        // Arrange
        assertFalse(result);
    }

    @Test
    void testDoesValueExistInUserBalances_True() {
        // Arrange
        TransferOperations transferOperationsInstance = new TransferOperations("test@example.com", 0);
        transferOperationsInstance.setTransfereeEmail("test@example.com");

        // Act
        boolean result = transferOperationsInstance.doesValueExistInUserBalances();

        // Assert
        assertTrue(result);
    }

    @Test
    void testDoesValueExistInUserBalances_False() {
        // Arrange
        TransferOperations transferOperationsInstance = new TransferOperations("test@example.com", 0);
        transferOperationsInstance.setTransfereeEmail("test");

        // Act
        boolean result = transferOperationsInstance.doesValueExistInUserBalances();

        // Assert
        assertFalse(result);
    }

    @Test
    void testDoesValueExistInUserBalances_IOException() {
        // Arrange
        TransferOperations transferOperationsInstance = new TransferOperations("test@example.com", 0);
        App.directoryPath = "src";

        // Act + Assert
        assertThrows(Error.class, () -> {
            transferOperationsInstance.doesValueExistInUserBalances();
        });
    }

    @Test
    void testReadAllLinesAfterTarget() {
        // Act
        TransferOperations transferOperationsInstance = new TransferOperations("test@example.com", 0);
        transferOperationsInstance.doesTransfereeExist("test", "00-00-00", "00000000");

        // Arrange
        transferOperationsInstance.readAllLinesAfterTargetLine(10.0);
    }
}
