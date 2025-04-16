package com.example.calculator.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Service
public class IOManager {

    private final Path filePath;
    private final Scanner scanner = new Scanner(System.in);

    public IOManager(@Value("${operations-file}") String filePath) throws IOException, URISyntaxException {

        Path newFilePath = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource(filePath)).toURI());

        if (!Files.exists(newFilePath)) {
            throw new FileNotFoundException("'" + newFilePath + "' was not found");
        }

        this.filePath = newFilePath;

    }


    public boolean isValidOperationInput(String operation) {
        return operation.equalsIgnoreCase("addition") ||
                operation.equalsIgnoreCase("subtraction") ||
                operation.equalsIgnoreCase("multiplication") ||
                operation.equalsIgnoreCase("division");
    }

    private void writeFile(String input) throws IOException {
        Files.write(filePath, input.getBytes(), StandardOpenOption.APPEND);
    }

    public void deleteAllHistory() throws IOException {
        if (isHistoryEmpty()) return;
        Files.write(filePath, "".getBytes());
        System.out.println("History is deleted successfully!");
    }

    public int getNumber() {

        int number;

        while (!scanner.hasNextInt()) {
            System.out.println("That's not a valid number. Please try again:");
            scanner.next();
        }

        number = scanner.nextInt();
        scanner.nextLine();

        return number;
    }

    public void writeOperationToFile(String operation, int num1, int num2) throws IOException {
        int operationNumber = parseOperationNumber();
        String normalizedOp = operation.toLowerCase();
        try {
            switch (normalizedOp) {
                case ("addition") ->
                        writeFile("Operation " + operationNumber + ": " + num1 + " + " + num2 + " = " + Calculator.add(num1, num2) + "\n");
                case ("subtraction") ->
                        writeFile("Operation " + operationNumber + ": " + num1 + " - " + num2 + " = " + Calculator.subtract(num1, num2) + "\n");
                case ("multiplication") ->
                        writeFile("Operation " + operationNumber + ": " + num1 + " * " + num2 + " = " + Calculator.multiply(num1, num2) + "\n");
                case ("division") ->
                        writeFile("Operation " + operationNumber + ": " + num1 + " / " + num2 + " = " + Calculator.divide(num1, num2) + "\n");
                default -> throw new IllegalArgumentException("You entered wrong operation!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int parseOperationNumber() throws IOException {
        List<String> lines = Files.readAllLines(filePath);
        if (lines.isEmpty()) {
            return 1;
        }
        String[] tokens = lines.getLast().split(":");
        String[] opNum = tokens[0].trim().split(" ");
        return Integer.parseInt(opNum[1]) + 1;
    }

    public void printOperationsHistory() throws IOException {
        List<String> lines = Files.readAllLines(filePath);
        if (isHistoryEmpty()) return;
        System.out.println("Here is your operation history:\n");
        for (String line : lines) {
            System.out.println(line);
        }
    }

    private boolean isHistoryEmpty() throws IOException {
        List<String> lines = Files.readAllLines(filePath);
        if (lines.isEmpty()) {
            System.out.println("Your history is empty!\n");
            return true;
        }
        return false;
    }

    public void deleteOperationFromHistory() throws IOException {
        List<String> lines = Files.readAllLines(filePath);
        if (isHistoryEmpty()) return;

        System.out.print("Please enter the operation number you would like to delete: ");
        String operation = findOperation();
        if (operation == null) return;

        List<String> updatedLines = lines.stream()
                .filter(line -> !line.startsWith(operation))
                .toList();

        try {
            Files.write(filePath, updatedLines);
            System.out.println("Operation successfully deleted from the history!");
        } catch (IOException e) {
            System.out.println("An error occurred while updating the file.");
            e.printStackTrace();
        }
    }

    private String findOperation() throws IOException {
        List<String> lines = Files.readAllLines(filePath);
        int operationNumber = getNumber();
        String operationPrefix = "Operation " + operationNumber + ":";

        boolean exists = lines.stream().anyMatch(line -> line.startsWith(operationPrefix));

        if (!exists) {
            System.out.println("There is no operation with that number!");
            return null;
        }
        return operationPrefix;
    }

    public String getAnswer() {
        String answer = scanner.nextLine();
        while (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n")) {
            System.out.println("Please enter either Y or N!");
            answer = scanner.nextLine();
        }
        return answer;
    }

    public void printResult(String operationType, int num1, int num2) {
        int result;
        String normalizedOp = operationType.toLowerCase();
        switch (normalizedOp) {
            case "addition" -> result = Calculator.add(num1, num2);
            case "subtraction" -> result = Calculator.subtract(num1, num2);
            case "multiplication" -> result = Calculator.multiply(num1, num2);
            case "division" -> result = Calculator.divide(num1, num2);
            default -> throw new IllegalStateException("Unexpected value: " + operationType);
        }
        System.out.println("Your result is " + result + ".\n");
    }
}
