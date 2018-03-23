package com.jmb.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class FibonacciControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void noLengthParamBadRequest() throws Exception {
        this.mockMvc.perform(get("/fibonacci"))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void negativeLengthParamBadRequest() throws Exception {
        this.mockMvc.perform(get("/fibonacci").param("length", "-2"))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void validRequestShouldReturnList() throws Exception {
        this.mockMvc.perform(get("/fibonacci").param("length", "10"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json("[0,1,1,2,3,5,8,13,21,34]"));
    }

    @Test
    public void responseContainsExpectedCacheControlHeader() throws Exception {
        this.mockMvc.perform(get("/fibonacci").param("length", "10"))
                .andExpect(MockMvcResultMatchers.header().string("cache-control", "public, max-age=3600"))
                .andExpect(status().isOk());
    }
}
