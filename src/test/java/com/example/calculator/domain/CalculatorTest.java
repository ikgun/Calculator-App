package com.example.calculator.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void shouldAddCorrectly(){
        int a = 2;
        int b = 3;
        int sum = Calculator.add(a,b);
        assertEquals(5, sum);
    }

    @Test
    void shouldSubtractCorrectly(){
        int a = 2;
        int b = 3;
        int difference = Calculator.subtract(a,b);
        assertEquals(-1, difference);
    }
}