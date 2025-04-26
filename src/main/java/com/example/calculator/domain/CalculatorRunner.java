package com.example.calculator.domain;

import java.io.IOException;
import java.net.URISyntaxException;

public class CalculatorRunner {

    private final TaskManager taskManager;

    public CalculatorRunner() throws IOException, URISyntaxException {
        taskManager = new TaskManager();
    }

    public void run() throws IOException{
        System.out.println("Welcome to the Calculator app, please select an option between 1 and 5 below to start.\n");
        do taskManager.printMenu();
        while (taskManager.continueApp(taskManager.getSelectedOperation()));
    }
}
