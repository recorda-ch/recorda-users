package com.recorda.admin.users.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recorda.admin.users.model.User;
import com.recorda.admin.users.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserResource.class)
public class UserResourceTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    public void testCreate_Nominal() throws Exception {

        // Forge request (no matter field : response is mocked)
        User requestBody = new User();

        // Forge mock response
        User responseBody = new User();
        responseBody.setFirstname("Doug");
        responseBody.setLastname("Lea");
        responseBody.setEmail("doug.lea@gmail.com");
        responseBody.setAddress("San Francisco");
        responseBody.setPassword("changeme");
        responseBody.setId("12345");
        String jsonRequest = new ObjectMapper().writeValueAsString(requestBody);

        // Forge mock behavior
        when(userService.add(any())).thenReturn(responseBody);

        // Invoke API and check
        this.mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                // .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(equalTo("12345"))))
                .andExpect(jsonPath("firstname", is(equalTo("Doug"))))
                .andExpect(jsonPath("lastname", is(equalTo("Lea"))));
    }

}
