package com.citi.trade.recommendation;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Matchers;
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

import com.citi.trade.recommendation.controller.UserController;
import com.citi.trade.recommendation.model.UserMaster;
import com.citi.trade.recommendation.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;




@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserController.class})
@WebMvcTest
public class UserControllerTest {

    private static final Logger logger = LogManager.getLogger(UserControllerTest.class);
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    
    
    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    
    }
    
    @Test
    public void userLogin() {
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
            
        }


    }
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
    