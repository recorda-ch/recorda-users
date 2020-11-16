package com.recorda.admin.users.filter;

import com.recorda.admin.users.exception.BusinessException;
import com.recorda.admin.users.exception.TechnicalException;
import com.recorda.admin.users.client.ApiClient;
import com.recorda.admin.users.i18n.MessageResolver;
import com.recorda.admin.users.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit tests the filter on IP Location ({@link LocationFilter}
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class LocationFilterTest {

    @Mock
    ApiClient apiClient;

    @Mock
    MessageResolver messageResolver;

    @InjectMocks
    LocationFilter filter;


    @Before
    public void setup() {
        ReflectionTestUtils.setField(filter, LocationFilter.class, "apiEndpoint", "(no matter)", String.class);
    }

    @Test
    public void testFilter_Nominal() throws TechnicalException, BusinessException {

        // Forge user to filter
        User user = new User();
        user.setIp("8.8.8.8");

        // Mock actors
        when(apiClient.get(any(),any(),any())).thenReturn("CH");

        // Invoke filter and check
        assertTrue(filter.filter(user));
    }

    @Test
    public void testFilter_whenNotFromSwitzerland() throws TechnicalException, BusinessException {

        // Forge user to filter
        User user = new User();
        user.setIp("8.8.8.8");

        // Mock actors
        when(apiClient.get(any(),any(),any())).thenReturn("FR");

        // Invoke filter and check
        assertFalse(filter.filter(user));
    }

    @Test
    public void testFilter_whenIPUndefined() throws TechnicalException, BusinessException {

        // Forge user to filter
        User user = new User();
        user.setIp(null);

        // Mock actors
        when(messageResolver.getContent(any(),any())).thenReturn("(no matter)");
        when(apiClient.get(any(),any(),any())).thenReturn("undefined");

        // Invoke filter
        assertFalse(filter.filter(user));
    }
}
