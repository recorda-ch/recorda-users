package com.recorda.admin.users.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recorda.admin.users.event.EventPublisher;
import com.recorda.admin.users.exception.UserException;
import com.recorda.admin.users.model.User;
import com.recorda.admin.users.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests the user management endpoints as provided in ({@link UserResource}
 *
 * TODO: add tests concerning other endpoints (update, find, ...)
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserResource.class)
public class UserResourceTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    EventPublisher eventPublisher;

    @Test
    public void testCreate_Nominal() throws Exception {

        // Forge request (we do not care about the fields : response is mocked)
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
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(equalTo("12345"))))
                .andExpect(jsonPath("firstname", is(equalTo("Doug"))))
                .andExpect(jsonPath("lastname", is(equalTo("Lea"))));

        // Check Event has been sent
        verify(eventPublisher).sendMessage(any());

    }

    @Test
    public void testCreate_whenUserException() throws Exception {

        // Forge request (we do not care about the fields...)
        User requestBody = new User();
        requestBody.setEmail("doug.lea@java.net");
        String jsonRequest = new ObjectMapper().writeValueAsString(requestBody);

        // Forge mock response (we do not care about the fields...)
        User responseBody = new User();

        // Forge mock behavior
        when(userService.add(any())).thenThrow(new UserException("(no matter))"));

        // Invoke API and check
        this.mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                //.andDo(print())
                .andExpect(status().isConflict());

        // Check Event has NOT been sent
        verify(eventPublisher, never()).sendMessage(any());
    }
}
