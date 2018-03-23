package com.jmb.controllers;

import com.jmb.services.FibonacciService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class FibonacciController {

    private static final Logger logger = LoggerFactory.getLogger(FibonacciController.class);

    @Autowired
    FibonacciService fibonacciService;

    /**
     * Public endpoint
     * @param length required
     */
    @RequestMapping("/fibonacci")
    public List fibonacciSequence(HttpServletResponse response, @RequestParam(value="length", required=true) int length) {
        long start = System.currentTimeMillis();
        logger.info("Received request for Fibonacci Sequence with length: "+length);
        List result = fibonacciService.getSequence(length);
        long duration = (System.currentTimeMillis() - start);
        logger.info("Returning list with "+result.size()+" elements after "+duration+" ms");
        response.addHeader("Cache-Control", "public, max-age=3600");
        return result;
    }

    /**
     * This endpoint requires a valid JWT
     * @param length required
     */
    @RequestMapping("/fibonacci-secure")
    public List fibonacciSequenceSecure(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="length", required=true) int length) {
        return this.fibonacciSequence(response, length);
    }

    @ExceptionHandler
    void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
}
