package com.example.calculator;

import com.example.calculator.domain.CalculatorRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootApplication
public class CalculatorApplication {

    public static void main(String[] args) throws IOException, URISyntaxException {
        SpringApplication.run(CalculatorApplication.class, args);
        CalculatorRunner calculatorRunner = new CalculatorRunner();
        calculatorRunner.run();
    }

}
