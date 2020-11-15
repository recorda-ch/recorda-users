package com.recorda.admin.users.service;

import com.recorda.admin.users.exception.BusinessException;
import com.recorda.admin.users.exception.FeatureException;
import com.recorda.admin.users.exception.TechnicalException;
import com.recorda.admin.users.exception.UserException;
import com.recorda.admin.users.filter.LocationFilter;
import com.recorda.admin.users.helper.IpValidator;
import com.recorda.admin.users.i18n.MessageResolver;
import com.recorda.admin.users.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests the user management service ({@link UserService}
 *
 * TODO: add tests concerning other usecases (update, find, ...)
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    @Mock
    MongoTemplate mongoTemplate;

    @Mock
    MessageResolver messageResolver;

    @Mock
    IpValidator ipValidator;

    @Mock
    LocationFilter locationFilter;

    @InjectMocks
    MongoUserService userService;

    @Test
    public void testAdd_Nominal() throws BusinessException, TechnicalException {

        // Forge a User (no matter of field valorization for this test)
        User user = new User();

        // Simulating that there is no user with matching email
        when(ipValidator.validate(any())).thenReturn(true);
        when(locationFilter.filter(any())).thenReturn(true);
        when(mongoTemplate.find(any(),any())).thenReturn(null);

        // Invoke service method
        userService.add(user);

        // Check save
        verify(mongoTemplate).save(any());
    }

    /**
     * Here we test / RULE 0 : a valid user IP MUST be provided
     */
    @Test(expected = UserException.class)
    public void testAdd_withNoIP() throws BusinessException, TechnicalException {

        // Forge a User (no matter of field valorization for this test)
        User user = new User();

        // Simulating that a user with matching email has been found
        when(ipValidator.validate(any())).thenReturn(false);

        // Invoke service method
        userService.add(user);
    }
    /**
     * Here we test / RULE 1 : only user requesting from Switzerland are authorized to add users
     */
    @Test(expected = FeatureException.class)
    public void testAdd_withNotEligibleUser() throws BusinessException, TechnicalException {

        // Forge a User (no matter of field valorization for this test)
        User user = new User();

        // Simulating that a user with matching email has been found
        when(ipValidator.validate(any())).thenReturn(true);
        when(locationFilter.filter(any())).thenReturn(false);

        // Invoke service method
        userService.add(user);
    }

    /**
     * Here we test / RULE 2 : an email is related to a single user"
     */
    @Test(expected = UserException.class)
    public void testAdd_withYetExistingUser() throws BusinessException, TechnicalException {

        // Forge a User (no matter of field valorization for this test)
        User user = new User();

        // Simulating that a user with matching email has been found
        when(locationFilter.filter(any())).thenReturn(true);
        when(mongoTemplate.find(any(),any())).thenReturn(Stream.of(new User()).collect(toCollection(ArrayList::new)));

        // Invoke service method
        userService.add(user);
    }
}
