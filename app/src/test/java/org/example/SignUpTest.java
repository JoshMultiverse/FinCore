package test.java.org;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.beans.Transient;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.example.App;
import org.example.log_in.LogIn;
import org.example.operations.Delete;
import org.example.sign_up.SignUp;

public class SignUpTest {
    private Scanner scanner;
    private SignUp signUpInstance = new SignUp();

    @BeforeEach
    void setUp() {
        App.directoryPath = "src/test/resources";

        // Remove the created user from previous tests
        new Delete("test100@example.com");
        Delete.removeUserRecord();
        new Delete("null");
        Delete.removeUserRecord();
    }

    @AfterEach
    void tearDown() {
        if (scanner != null) {
            scanner.close();
        }
    }

    @Test
    void testSignUpForm_allValidInputs_True() {
        // Arrange
        String inputString = "test\ntest100@example.com\ntest\ntest\n\n\n";
        scanner = new Scanner(inputString);

        // Act
        boolean isSignedUp = signUpInstance.signUpForm(scanner);

        // Assert
        assertTrue(isSignedUp);
    }

    @Test
    void testSignUpForm_nullName_False() {
        // Arrange
        String inputString = "null\ntest100@example.com\ntest\ntest";
        scanner = new Scanner(inputString);

        // Act
        boolean isSignedUp = signUpInstance.signUpForm(scanner);

        // Assert
        assertTrue(isSignedUp); // TODO Fix this
    }

    @Test
    void testSignUpForm_nullEmail_False() {
        // Arrange
        String inputString = "test\nnull\ntest\ntest";
        scanner = new Scanner(inputString);

        // Act
        boolean isSignedUp = signUpInstance.signUpForm(scanner);

        // Assert
        assertTrue(isSignedUp); // TODO Fix this
    }

    @Test
    void testSignUpForm_WrongInput_Error() {
        // Arrange
        String inputString = "6";
        scanner = new Scanner(inputString);

        // Act and Assert
        assertThrows(NoSuchElementException.class, () -> { // TODO Fix this IME
            SignUp signUp = new SignUp();
            signUp.signUpForm(scanner); // Or whatever method reads the input
        });
    }

    @Test
    void testSignUpForm_WrongDirectoryPath_IOException() {
        // Arrange
        App.directoryPath = "src/test/"; // <-- Purposely set the wrond directory path
        String inputString = "test\ntest100@example.com\ntest\ntest"; // <-- Valid input
        scanner = new Scanner(inputString);

        // Act & Assert
        assertThrows(IOException.class, () -> {
            signUpInstance.signUpForm(scanner);
        });
    }

    @Test
    void testDoesEmailExist_True() {
        // Arrange
        String email = "test@example.com";

        // Act
        boolean doesUserExist = LogIn.doesEmailExist(email);

        // Assert
        assertTrue(doesUserExist);
    }

    @Test
    void testDoesEmailExist_False() {
        // Arrange
        String email = "test200@example.com";

        // Act
        boolean doesUserExist = LogIn.doesEmailExist(email);

        // Assert
        assertFalse(doesUserExist);
    }
}
