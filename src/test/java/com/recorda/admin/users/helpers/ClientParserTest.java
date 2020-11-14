package com.recorda.admin.users.helpers;

import com.recorda.admin.users.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit tests the user information helper ({@link ClientParser}
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ClientParserTest {

    private MockHttpServletRequest httpRequest;

    private ClientParser clientParser = new ClientParser();

    @Before
    public void before() {
        httpRequest = new MockHttpServletRequest();
    }

    @Test
    public void getIpAddress_testNominal_withHeader() {

        // Forge request
        httpRequest.addHeader("X-FORWARDED-FOR", "8.8.8.8");

        // Test and assert
        Assert.assertEquals("8.8.8.8", clientParser.getIpAddress(httpRequest));
    }

    @Test
    public void getIpAddress_testNominal_withRemoteAddr() {

        // Forge request
        httpRequest.setRemoteAddr("8.8.8.8");

        // Test and assert
        Assert.assertEquals("8.8.8.8", clientParser.getIpAddress(httpRequest));
    }
    @Test
    public void getIpAddress_testCaseInsensitive() {

        // Forge request
        httpRequest.addHeader("X-Forwarded-For", "8.8.8.8");

        // Test and assert
        Assert.assertEquals("8.8.8.8", clientParser.getIpAddress(httpRequest));
    }

    @Test
    public void getIpAddress_testHeaderPrimes() {

        // Forge request
        httpRequest.addHeader("X-Forwarded-For", "8.8.8.8");
        httpRequest.setRemoteAddr("9.9.9.9");

        // Test and assert
        Assert.assertEquals("8.8.8.8", clientParser.getIpAddress(httpRequest));
    }

}
