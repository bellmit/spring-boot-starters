package org.shield.gateway.filter.factory;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AccessTokenGatewayFilterFactoryTest {

    @Test
    public void matchTest() {
        String url = "/admin/captchas";
        String url2 = "/admin/captchas/sms-codes";
        String pattern = "/admin/captchas(/.*)?";
        assertTrue(url.matches(pattern));
        assertTrue(url2.matches(pattern));
    }
}
