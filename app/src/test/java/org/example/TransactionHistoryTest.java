package test.java.org.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.Scanner;

import org.example.App;
import org.example.operations.transactions.*;

public class TransactionHistoryTest {
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        App.directoryPath = "src/test/resources";
    }

    @Test
    void testBuildLinkedList_Valid_True() {
        // Arrange
        String history = "deposit;0.0;10.0:withdraw;10.0;5.0:withdraw;5.0;0.0";
        String[] historyArray = history.split(":");

        // Act
        LinkedList<String> result = TransactionHistory.buildLinkedList(historyArray);

        // Assert
        assertEquals(result.size(), 3);
    }

    @Test
    void testBuildLinkedList_NullValues_False() {
        // Arrange
        String nullHistory = null + ":" + null + ":" + null;
        String[] nullHistoryArray = nullHistory.split(":");

        // Act
        LinkedList<String> result = TransactionHistory.buildLinkedList(nullHistoryArray);

        // Assert
        assertEquals(result.size(), 0);
    }

    @Test
    void testReadFile_Valid_True() {
        // Arrange
        var transactionHistoryInstance = new TransactionHistory("test@example.com");

        // Act
        transactionHistoryInstance.readFile("test@example.com");
    }

    @Test
    void testCreateObject_Valid_True() {
        // Arrange
        var transactionHistoryInstance = new TransactionHistory("test100@example.com");

        // Act
        transactionHistoryInstance.createObject(new String[] { "deposit", "0", "100" });
    }
}
