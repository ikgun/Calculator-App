package com.example.calculator.domain;

import java.io.IOException;
import java.net.URISyntaxException;

public class CalculatorRunner {

    public static void run() throws IOException, URISyntaxException {

        boolean continueApp = true;
        final IOManager ioManager = new IOManager("data/operations-history.txt");
        final TaskManager taskManager = new TaskManager();

        System.out.println("Welcome to the Calculator app, please select an option between 1 and 5 below to start.\n");

        do {
            taskManager.printMenu();

            int selectedOp = ioManager.getNumber();

            while (selectedOp < 1 || selectedOp > 5) {
                System.out.println("Please enter a valid menu option between 1 and 5!");
                selectedOp = ioManager.getNumber();
            }

            switch (selectedOp) {
                case 1 -> taskManager.doOperation();
                case 2 -> ioManager.printOperationsHistory();
                case 3 -> ioManager.deleteOperationFromHistory();
                case 4 -> ioManager.deleteAllHistory();
                case 5 -> continueApp = taskManager.quitApp();
                default -> throw new IllegalArgumentException("Please enter a correct menu option!");
            }

        } while (continueApp);

    }

}
