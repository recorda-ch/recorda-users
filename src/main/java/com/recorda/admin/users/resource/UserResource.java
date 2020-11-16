package com.recorda.admin.users.resource;

import com.recorda.admin.users.event.EventPublisher;
import com.recorda.admin.users.event.model.EventSource;
import com.recorda.admin.users.event.model.EventTypeRest;
import com.recorda.admin.users.event.model.Message;
import com.recorda.admin.users.event.model.UserMessage;
import com.recorda.admin.users.exception.BusinessException;
import com.recorda.admin.users.exception.FeatureException;
import com.recorda.admin.users.exception.TechnicalException;
import com.recorda.admin.users.exception.UserException;
import com.recorda.admin.users.model.User;
import com.recorda.admin.users.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;

/**
 * REST controller for users management endpoints
 */
@RestController
@RequestMapping("/user")
public class UserResource {

    private static final Logger logger = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    private UserService userService;

    @Autowired
    private EventPublisher eventPublisher;

    /**
     * Endpoint for user creation
     *
     * @param user user to create
     * @return
     */
    @ResponseBody
    @PostMapping
    public User create(@RequestBody User user) {

        User response = null;
        try {
            // Add user
            response = userService.add(user);

            // Send event
            Message event = new UserMessage();
            event.setId(response.getId());
            event.setSource(EventSource.REST);
            event.setType(EventTypeRest.POST);
            event.setPayload(user);
            eventPublisher.sendMessage(event);

            return response;

        } catch (UserException ue) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ue.getMessage(), ue);
        } catch (FeatureException ue) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ue.getMessage(), ue);
        } catch (BusinessException ue) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ue.getMessage(), ue);
        } catch (TechnicalException ue) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ue.getMessage(), ue);
        }
    }

    @ResponseBody
    @PutMapping(path = "/{id}")
    public User update(@PathVariable("id") String id, @RequestBody User user) {

        User response = null;
        try {
            // Full update
            response = userService.update(id,user);

            // Send event/message
            Message message = new UserMessage();
            message.setId(id);
            message.setSource(EventSource.REST);
            message.setType(EventTypeRest.PUT);
            message.setPayload(user);
            eventPublisher.sendMessage(message);

            return response;
        } catch (BusinessException be) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, be.getMessage(), be);
        }

    }

    @ResponseBody
    @PatchMapping(path = "/{id}")
    public User updatePartial(@PathVariable("id") String id,@RequestBody HashMap<String, String> fields)  {
        User response = null;
        try {
            // Partial update
            response = userService.partialUpdate(id, fields);

            // Send event/message
            Message message = new UserMessage();
            message.setId(id);
            message.setSource(EventSource.REST);
            message.setType(EventTypeRest.PATCH);
            message.setPayload(fields);
            eventPublisher.sendMessage(message);

            return response;
        } catch (BusinessException be) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, be.getMessage(), be);
        }
    }

    @ResponseBody
    @GetMapping(path = "/{id}")
    public User findById(@PathVariable("id") String id) throws BusinessException {
        return userService.findById(id);
    }

    @GetMapping(path = "/all")
    public List<User> findAll() throws BusinessException {
        return userService.findAll();
    }

    @GetMapping(path = "/byEmail/{email}")
    public List<User> findByEmail(@PathVariable("email") String email) throws BusinessException {
        return userService.findByEmail(email);
    }


    @GetMapping(path = "/byName/{name}")
    public List<User> findByName(@PathVariable("name") String name) throws BusinessException {
        return userService.findByName(name);
    }
}
