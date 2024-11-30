package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsageLog4j {

    private static final Logger LOG = LoggerFactory.getLogger(UsageLog4j.class.getName());

    public static void main(String[] args) {
        byte a = 2;
        short b = 3;
        int c = 33;
        long d = 4L;
        float e = 9.2F;
        double f = 0.2;
        char g = 'c';
        boolean h = false;
        LOG.debug("a : {}, b : {}, c : {}, d : {}, e : {}, f : {}, g : {}, h : {}", a, b, c, d, e, f, g, h);
    }
}