package com.jusoft.util;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HostUtils {

    private static final String DEFAULT_HOST = "192.168.99.100";
    private static final int DEFAULT_PORT = 8080;
    private static final String HTTP = "http://";

    private static final Logger log = LoggerFactory.getLogger(HostUtils.class);

    public static int getPort() {
        String portProperty = System.getProperty("port");
        int port = portProperty == null || !NumberUtils.isCreatable(portProperty) ? DEFAULT_PORT : Integer.parseInt(portProperty);
        log.info("Port used: {}", port);
        return port;
    }

    public static String getHost() {
        String host = System.getProperty("host");
        host = host == null ? DEFAULT_HOST : host;
        log.info("Host used: {}", host);
        return HTTP + host;
    }
}
