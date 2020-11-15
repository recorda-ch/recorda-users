package com.recorda.admin.users.helper;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * A helper class for IP validation
 */
@Component
public class IpValidator {

    static private final String IP_REGEXP = "(([0-1]?[0-9]{1,2}\\.)|(2[0-4][0-9]\\.)|(25[0-5]\\.)){3}(([0-1]?[0-9]{1,2})|(2[0-4][0-9])|(25[0-5]))";
    static private Pattern pattern = Pattern.compile(IP_REGEXP);

    public boolean validate(String ipAddress) {
        if (ipAddress == null) return false;
        return pattern.matcher(ipAddress).matches();
    }
}
