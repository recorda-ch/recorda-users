package com.recorda.admin.users.helper;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * A class responsible for parsing several information concerning client
 */
@Deprecated
public class RequestParser {

    public String getIpAddress(HttpServletRequest req) {
        String ip = null;
        if (req != null) {
            ip = req.getHeader("x-forwarded-for");
            if (ip == null || "".equals(ip)) {
                ip = req.getRemoteAddr();
            }
        }
        return ip;
    }
}
