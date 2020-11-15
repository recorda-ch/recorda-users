package com.recorda.admin.users.helper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Tests the helper {@link IpValidator}
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class IpValidatorTest {

    IpValidator validator = new IpValidator();

    @Test
    public void test_IP_Valid() {
        assertTrue(validator.validate("8.8.8.8"));
        assertTrue(validator.validate("127.0.0.1"));
        assertTrue(validator.validate("46.14.0.12"));
    }


    @Test
    public void test_IP_Invalid() {
        assertFalse(validator.validate(null));
        assertFalse(validator.validate(""));
        assertFalse(validator.validate("127.0.0"));
    }
}
