package com.jmb.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class FibonacciService {

    private static final Logger logger = LoggerFactory.getLogger(FibonacciService.class);

    // remember the sequence we have already calculated
    private List<BigInteger> fibonacciSequence = new CopyOnWriteArrayList<>();

    {
        // we know the first 2 elements are 0 and 1
        fibonacciSequence.add(BigInteger.ZERO);
        fibonacciSequence.add(BigInteger.ONE);
    }

    public List<BigInteger> getSequence(int length) {
        logger.info("Processing request for Fibonacci Sequence with length: "+length);

        if (length < 0) {
            String message = "Request could not be processed, 'length' parameter contains a negative value: "+length;
            logger.error(message);
            throw new IllegalArgumentException(message);
        }

        if (length == 0) {
            return Collections.<BigInteger>emptyList();
        }

        // we may have already created a sequence of this size which we can reuse
        if (fibonacciSequence.size() >= length) {
            logger.info("Getting sublist from cache");
            return fibonacciSequence.subList(0, length);
        }

        extendStoredSequence(length);

        return fibonacciSequence;
    }

    /**
     * Extends the stored sequence by the number of elements necessary to satisfy this request
     * @param length
     */
    private void extendStoredSequence(int length) {
        int count = length - fibonacciSequence.size();
        while (count > 0) {
            // get the last 2 values to calculate the next
            BigInteger x = fibonacciSequence.get(fibonacciSequence.size() - 1);
            BigInteger y = fibonacciSequence.get(fibonacciSequence.size() - 2);
            fibonacciSequence.add(x.add(y));
            count--;
        }
    }
}
