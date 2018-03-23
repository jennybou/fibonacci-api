package com.jmb.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RunWith(Parameterized.class)
public class FibonacciServiceTest {

    private FibonacciService service;

    private final int length;
    private final List<BigInteger> sequence;

    @Before
    public void initialize() {
        service = new FibonacciService();
    }

    public FibonacciServiceTest(Integer length, List<BigInteger> sequence) {
        this.length = length;
        this.sequence = sequence;
    }

    @Parameterized.Parameters
    public static Collection testData() {
        return Arrays.asList(new Object[][] {
                {0, Collections.EMPTY_LIST},
                {1, Arrays.asList(BigInteger.ZERO)},
                {2, Arrays.asList(BigInteger.ZERO, BigInteger.ONE)},
                {3, Arrays.asList(BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE)},
                {4, Arrays.asList(BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.valueOf(2))},
                {10, Arrays.asList(BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.valueOf(2), BigInteger.valueOf(3),
                BigInteger.valueOf(5), BigInteger.valueOf(8), BigInteger.valueOf(13), BigInteger.valueOf(21), BigInteger.valueOf(34)) },
                {23, Arrays.asList(BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.valueOf(2), BigInteger.valueOf(3),
                        BigInteger.valueOf(5), BigInteger.valueOf(8), BigInteger.valueOf(13), BigInteger.valueOf(21), BigInteger.valueOf(34),
                        BigInteger.valueOf(55), BigInteger.valueOf(89), BigInteger.valueOf(144), BigInteger.valueOf(233), BigInteger.valueOf(377),
                        BigInteger.valueOf(610), BigInteger.valueOf(987), BigInteger.valueOf(1597), BigInteger.valueOf(2584),
                        BigInteger.valueOf(4181), BigInteger.valueOf(6765), BigInteger.valueOf(10946), BigInteger.valueOf(17711)) }
        });
    }

    @Test
    public void getSequenceReturnsExpectedFibonacciSequence() throws Exception {
        List<BigInteger> result = service.getSequence(this.length);
        assert(Arrays.equals(this.sequence.toArray(), result.toArray()));
    }
}
