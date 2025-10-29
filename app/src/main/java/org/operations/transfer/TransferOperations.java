package org.operations.transfer;

import java.util.Scanner;

public class TransferOperations extends Transfer {
    public TransferOperations(String email, double currentBalance) {
        super(currentBalance, email);
    }

    public static double getWithdrawalAmount(Scanner scanner) {
        return 0.0;
    }

    public void setTransfereeEmail() {
        // Read through file to find email
    }

    public boolean doesTransfereeExist(String email, String sortCode, String accountNumber) {
        return false;
    }

    public void addToTransactionHistory() {

    }
}
