package org.shield.security.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.junit.Test;
import org.shield.security.exception.InvalidTokenException;
import org.shield.security.user.User;
import org.shield.security.user.impl.AdminUser;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
public class JwtTokenUtilsTest {

    private static JwtTokenUtils jwtTokenUtils = new JwtTokenUtils("XLLlcm3ggfl/PUx+48w31f9bKMjc28LJ9qm69Nm1Vks=",
            60 * 60*24*365*100);

    private User getUser() {
        User user = new AdminUser();
        user.setName("name");
        user.setUserId("userId");
        user.setUid(1L);
        user.setUsername("username");
        return user;
    }

    @Test
    public void createToken() {
        String token = jwtTokenUtils.createToken(getUser());
        System.out.printf("token=%s", token);
        assertNotNull(token);
    }

    @Test
    public void createTokenWithProps() {
        Map<String, String> props = Map.of("key", "value");
        String token = jwtTokenUtils.createToken(getUser(), props);
        assertNotNull(token);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTokenBodyWithEmptyToken() throws InvalidTokenException {
        jwtTokenUtils.getTokenBody("");
    }

    @Test(expected = MalformedJwtException.class)
    public void getTokenBodyWithInvalidToken() {
        jwtTokenUtils.getTokenBody("Invalid Token");
    }

    @Test(expected = ExpiredJwtException.class)
    public void getTokenBodyWithExipred() {
        String expiredJwtToken = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VybmFtZSI6InVzZXJuYW1lIiwidWlkIjoxLCJpc3MiOiJBZG1pblVzZXIiLCJuYW1lIjoibmFtZSIsImlhdCI6MTYzNjA5ODc0Mywic3ViIjoidXNlcklkIiwiZXhwIjoxNjM2MDk4NzQ0fQ.LqhdOcRqlxO0761UxVfghyQHNrleuW7J9phTSEknr38";
        jwtTokenUtils.getTokenBody(expiredJwtToken);
    }

    @Test(expected = SignatureException.class)
    public void getTokenBodyWithInvadBase64Secret() {
        String expiredJwtToken = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VybmFtZSI6InVzZXJuYW1lIiwidWlkIjoxLCJpc3MiOiJBZG1pblVzZXIiLCJuYW1lIjoibmFtZSIsImlhdCI6MTYzNjA5OTIzMSwic3ViIjoidXNlcklkIiwiZXhwIjo0OTQ3MzE5MzV9.0k9C5KFBqLI4oOzI1kleRclOAfipCCE50Z4oXeXmlR4";
        jwtTokenUtils.getTokenBody(expiredJwtToken);
    }
}
