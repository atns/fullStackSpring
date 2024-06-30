package com.atns.fullstackatns.utility;

import jakarta.servlet.http.HttpServletRequest;

public class UrlUtil {

    public static String getApplicationUrl(HttpServletRequest request) {

        String appUrl = request.getRequestURL().toString();
        System.out.println("appUrl: "+appUrl);
        return appUrl.replace(request.getServletPath(), "");
    };
}
