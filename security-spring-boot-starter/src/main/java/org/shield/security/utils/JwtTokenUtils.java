package org.shield.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;
import org.springframework.util.StringUtils;
import javax.crypto.SecretKey;
import org.shield.security.exception.InvalidTokenException;
import org.shield.security.user.User;
import java.util.Date;

/**
 * @author zacksleo@gmail.com
 * @description JWT工具类
 */
public class JwtTokenUtils {
    /**
     * 生成足够的安全随机密钥，以适合符合规范的签名
     */
    private SecretKey secretKey;

    private long expiration;

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_TYPE = "JWT";

    public JwtTokenUtils(String base64Secret, long expiration) {
        byte[] keySecretBytes = Decoders.BASE64.decode(base64Secret);
        secretKey = Keys.hmacShaKeyFor(keySecretBytes);
        this.expiration = expiration;
    }

    public String createToken(User user) {
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expiration * 1000);
        String tokenPrefix = Jwts.builder().setHeaderParam("type", TOKEN_TYPE)
                .signWith(secretKey, SignatureAlgorithm.HS256).claim("username", user.getUsername())
                .claim("uid", user.getUid())
                .setIssuer(user.getCatalog()).claim("name", user.getName()).setIssuedAt(createdDate)
                .setSubject(user.getUserId()).setExpiration(expirationDate).compact();
        return tokenPrefix;
    }

    public Claims getTokenBody(String token) throws Exception {
        try {
            if (StringUtils.isEmpty(token)) {
                throw new InvalidTokenException("Token为空");
            }
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        } catch (DecodingException e) {
            throw new InvalidTokenException("Token格式错误");
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenException("Token已过期");
        } catch (IllegalArgumentException e) {
            throw new InvalidTokenException("Token 非法");
        } catch (Exception e) {
            throw new InvalidTokenException("Token格式错误");
        }
    }
}
