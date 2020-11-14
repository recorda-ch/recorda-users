package com.recorda.admin.users.service;

import com.recorda.admin.users.exception.BusinessException;
import com.recorda.admin.users.exception.UserException;
import com.recorda.admin.users.i18n.MessageResolver;
import com.recorda.admin.users.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    @Mock
    MongoTemplate mongoTemplate;

    @Mock
    MessageResolver messageResolver;

    @InjectMocks
    MongoUserService userService;

    @Test
    public void testAdd_Nominal() throws BusinessException {

        // Forge a User (no matter of field valorization for this test)
        User user = new User();

        // Simulating that there is no user with matching email
        when(mongoTemplate.find(any(),any())).thenReturn(null);

        // Invoke service method
        userService.add(user);

        // Check save
        verify(mongoTemplate).save(any());
    }

    /**
     * Here we test "RULE : an email is related to a single user"
     *
     * @throws BusinessException
     */
    @Test(expected = UserException.class)
    public void testAdd_withExistingUser() throws BusinessException {

        // Forge a User (no matter of field valorization for this test)
        User user = new User();

        // Simulating that a user with matching email has been found
        when(mongoTemplate.find(any(),any())).thenReturn(Stream.of(new User()).collect(toCollection(ArrayList::new)));

        // Invoke service method
        userService.add(user);
    }
}
