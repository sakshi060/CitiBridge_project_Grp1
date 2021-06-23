package com.citi.trade.recommendation.controller;


import com.citi.trade.recommendation.model.UserMaster;
import com.citi.trade.recommendation.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserController.class})
@WebMvcTest
 class UserControllerTest {

    private static final Logger logger = LogManager.getLogger(UserControllerTest.class);
    MockMvc mockMvc;

    @MockBean
    UserService userService;
    
    
    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    
    }
    
    @Test
     void userLogin() {
    	String expectedResult = "Kiran";
        UserMaster mockResult = null;
        UserMaster checkuser = new UserMaster();
        checkuser.setUserId("Kiran");
        checkuser.setPassword("MzIxbmFyaUsNCg==");
        Mockito.when(userService.checkLogin(checkuser)).thenReturn(mockResult);
        try {
            MvcResult result = (MvcResult) mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                    .content(asJsonString(checkuser))
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"));
            logger.info("Result is {}", result.getResponse().getContentAsString());
            Assertions.assertTrue(result.getResponse().getContentAsString().contains(expectedResult));
            Assertions.assertEquals(200, result.getResponse().getStatus());
        } catch (Exception e) {
            logger.error("error in userLoginTest");
        }


    }
    
     static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
    