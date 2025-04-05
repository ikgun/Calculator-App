package com.example.calculator.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    int a;
    int b;

    @BeforeEach
    void setup(){
        a = 2;
        b = 3;
    }

    @Test
    void shouldAddCorrectly(){
        int sum = Calculator.add(a,b);
        assertEquals(5, sum);
    }

    @Test
    void shouldSubtractCorrectly(){
        int difference = Calculator.subtract(a,b);
        assertEquals(-1, difference);
    }

    @Test
    void shouldMultiplyCorrectly(){
        int product = Calculator.multiply(a,b);
        assertEquals(6, product);
    }
}