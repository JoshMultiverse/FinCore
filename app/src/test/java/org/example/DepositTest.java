package test.java.org.example;

import org.junit.jupiter.api.*;
import org.example.operations.Deposit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import org.example.App;

public class DepositTest {
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        App.directoryPath = "src/test/resources";
        org.example.App.returnToMainMenu = false;
    }

    @Test
    void displayText_mm_True() {
        // Act
        var depositInstance = new Deposit(0);
        scanner = new Scanner("mm");

        // Arrange
        depositInstance.displayText(scanner, 0);

        // Assert
        assertEquals(org.example.App.returnToMainMenu, true);
    }

    @Test
    void displayText_PositiveInput_True() {
        // Arrange
        var depositInstance = new Deposit(0);
        scanner = new Scanner("5");

        // Act
        depositInstance.displayText(scanner, 0);

        // Assert
        double balance = depositInstance.returnBalanceAfterTransaction();
        System.out.println(balance);
        assertEquals(5.0, balance);
    }

    @Test
    void displayText_MM_True() {
        // Act
        var depositInstance = new Deposit(0);
        scanner = new Scanner("MM");

        // Arrange
        depositInstance.displayText(scanner, 0);

        // Assert
        assertEquals(org.example.App.returnToMainMenu, true);
    }

    @Test
    void displayText_m_False() {
        // Act
        var depositInstance = new Deposit(0);
        scanner = new Scanner("m\n\n\nmm\n");

        // Arrange
        depositInstance.displayText(scanner, 0);
    }

    @Test
    void displayText_negative1_False() {
        // Act
        var depositInstance = new Deposit(0);
        scanner = new Scanner("-1.0\n\nmm\n");

        // Arrange
        depositInstance.displayText(scanner, 0);
    }
}
