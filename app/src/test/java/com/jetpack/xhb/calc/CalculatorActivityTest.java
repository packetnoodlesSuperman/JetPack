package com.jetpack.xhb.calc;

import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorActivityTest {

    @Test
    public void add() {
        assertEquals(4, 2 + 2);
    }

    @Test(timeout=10)
    public void iterable() {
        new CalculatorActivity().iterable(100000000);
    }
}