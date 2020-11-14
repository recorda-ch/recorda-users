package com.recorda.admin.users.helpers;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * A class responsible for parsing several information concerning client
 */
@Component
public class ClientParser {

    public String getIpAddress(HttpServletRequest request) {
        String ip = null;
        if (request != null) {
            ip = request.getHeader("X-FORWARDED-FOR");
            if (ip == null || "".equals(ip)) {
                ip = request.getRemoteAddr();
            }
        }
        return ip;
    }
}
