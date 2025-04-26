package com.example.calculator.domain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class TaskManager {

    private final Scanner scanner = new Scanner(System.in);
    private final IOManager ioManager = new IOManager("data/operations-history.txt");

    public TaskManager() throws IOException, URISyntaxException {
    }

    public int getSelectedOperation() {
        int selectedOp;
        selectedOp = ioManager.getNumber();

        while (selectedOp < 1 || selectedOp > 5) {
            System.out.println("Please enter a valid menu option between 1 and 5!");
            selectedOp = ioManager.getNumber();
        }
        return selectedOp;
    }

    public boolean continueApp(int selectedOp) throws IOException {
        boolean continueApp = true;
        switch (selectedOp) {
            case 1 -> doOperation();
            case 2 -> ioManager.printOperationsHistory();
            case 3 -> ioManager.deleteOperationFromHistory();
            case 4 -> ioManager.deleteAllHistory();
            case 5 -> continueApp = quitApp();
            default -> throw new IllegalArgumentException("Please enter a correct menu option!");
        }
        return continueApp;
    }

    public boolean quitApp() throws IOException {
        ioManager.deleteAllHistory();
        System.out.println("Thanks for using the Calculator app, see you next time!");
        return false;
    }

    public void doOperation() throws IOException {
        System.out.println("Let's calculate! Which operation would you like to begin with:\n" +
                "Addition\n" +
                "Subtraction\n" +
                "Multiplication\n" +
                "Division\n");

        String operationType = getOperationType();

        Input inputs = getInputs(operationType);

        ioManager.printResult(operationType, inputs.firstNumber(), inputs.secondNumber());

        saveToHistory(operationType, inputs.firstNumber(), inputs.secondNumber());
    }

    private Input getInputs(String operationType) {
        System.out.println("Please enter the numbers you would like to operate on: ");

        int firstNumber = ioManager.getNumber();
        System.out.println("Your first number is " + firstNumber);

        int secondNumber = getSecondNumber(operationType);
        return new Input(firstNumber, secondNumber);
    }

    private String getOperationType() {
        String operationType = scanner.nextLine();

        while (!ioManager.isValidOperationInput(operationType)) {
            System.out.println("Please enter a valid operation type:\n" +
                    "Addition\n" +
                    "Subtraction\n" +
                    "Multiplication\n" +
                    "Division\n");
            operationType = scanner.nextLine();
        }
        return operationType;
    }

    private int getSecondNumber(String operationType) {
        System.out.println("Please enter your second number: ");
        int secondNumber = ioManager.getNumber();

        while (operationType.equalsIgnoreCase("division") && secondNumber == 0) {
            System.out.println("Cannot divide by zero. Try again.");
            secondNumber = ioManager.getNumber();
            scanner.nextLine();
        }

        System.out.println("Your second number is " + secondNumber);
        return secondNumber;
    }

    private void saveToHistory(String operationType, int num1, int num2) throws IOException {
        System.out.println("Would you like to save it to your operations history? Y/N");

        String answer = ioManager.getAnswer();

        if (answer.equalsIgnoreCase("Y")) {
            ioManager.writeOperationToFile(operationType, num1, num2);
            System.out.println("Operation successfully written to history!");
        } else {
            System.out.println("The operation was not written to the history.");
        }
    }

    public void printMenu() {
        System.out.println("What would you like to do?\n" +
                "1-Do calculations\n" +
                "2-See your operations history\n" +
                "3-Delete an operation from your history\n" +
                "4-Delete your whole operations history\n" +
                "5-Quit");
    }
}
