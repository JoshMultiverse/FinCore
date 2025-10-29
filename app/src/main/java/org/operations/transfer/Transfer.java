package org.operations.transfer;

import java.util.Scanner;

import org.operations.Operations;

public class Transfer extends Operations {
    private double balanceAfterTransaction;
    private String sortCode;
    private String accountNumber;
    private String transfereeEmail;

    public Transfer(double currentBalance, String email) {
        super(currentBalance);
        this.transfereeEmail = email;
    }

    // Entry method for this class
    @Override
    public void displayText(Scanner scanner, double currentBalance) {
        // Get both the sort code and account number
        while (true) {
            try {
                System.out.print("Please enter the sort code of the account you wish to transfer to: ");
                sortCode = scanner.nextLine();

                // Check if sort code is valid
                if (isSortCodeValid()) {
                    // Get the account number
                    System.out.print("Please enter the account number of the account you wish to transfer to: ");
                    accountNumber = scanner.nextLine();

                    // Check the account number format is valid
                    if (isAccountNumberValid()) {
                        // Check that both sort code and account number match on the system
                    } else {
                        System.out.println("Account number format inavlid!");
                    }
                }

            } catch (Exception e) {
                // TODO: handle exception
            }
        }

    }

    @Override
    public void calculateNewBalance(double amountToTransfer, double currentBalance) {

    }

    @Override
    public double returnBalanceAfterTransaction() {
        return 0.0;
    }

    private boolean isSortCodeValid() {
        // Check if the sort code is length of 6 (UK Standard)
        if (sortCode.length() == 6) {
            sortCode = String.format("%s-%s-%s",
                    sortCode.substring(0, 2),
                    sortCode.substring(2, 4),
                    sortCode.substring(4, 6));
            return true;
        } else {
            System.out.println("Sort code must be a length of 6!");
            return false;
        }
    }

    private boolean isAccountNumberValid() {
        return accountNumber.length() == 8;
    }
}
