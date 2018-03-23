package com.jmb.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmb.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void loginSuccessfulWithCorrectCredentials() throws Exception {

        User user = new User();
        user.setUsername("admin");
        user.setPassword("password");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        this.mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void loginFailsWithIncorrectCredentials() throws Exception {

        User user = new User();
        user.setUsername("admin");
        user.setPassword("abc");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        this.mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isUnauthorized());
    }


    @Test
    public void securedEndpointNotAccessibleWithoutToken() throws Exception {
        this.mockMvc.perform(get("/fibonacci-secure").param("length", "10"))
                .andDo(print()).andExpect(status().isForbidden());
    }

    @Test
    public void securedEndpointAccessibleWithToken() throws Exception {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("password");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);

        this.mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        MvcResult result = this.mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk()).andReturn();

        String authHeader = result.getResponse().getHeader("AUTHORIZATION");

        this.mockMvc.perform(get("/fibonacci-secure").param("length", "10").header("Authorization", authHeader))
                .andDo(print()).andExpect(status().isOk());
    }

}

