package by.bsuir.crowdfunding.utils;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.time.LocalDate;

import by.bsuir.crowdfunding.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Component
public class JwtUtils {

    public static final String TOKEN_PREFIX = "Bearer ";

    private static final String CLAIM_KEY_USERNAME = "username";
    private static final String CLAIM_KEY_ID = "id";
    private static final String CLAIM_KEY_ROLE = "role";
    private static final String CLAIM_KEY_AUDIENCE = "audience";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String CLAIM_KEY_ACTIVE = "active";
    private static final String UTF_8 = "UTF-8";
    private static final String SECRET = "superSecretySecret";

    public String generateJwt(User user) throws UnsupportedEncodingException {
        return Jwts.builder()
                .setSubject(user.getLogin())
                .claim(CLAIM_KEY_ID, user.getId())
                .claim(CLAIM_KEY_USERNAME, user.getLogin())
                .claim(CLAIM_KEY_ROLE, user.getRole())
                .setIssuedAt(Date.valueOf(LocalDate.now()))
                .setExpiration(Date.valueOf(LocalDate.now().plusMonths(1)))
                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes(UTF_8))
                .compact();
    }

    public String getUsernameFromToken(String token) throws UnsupportedEncodingException {
        Claims claims = getClaimsFromToken(token);
        return nonNull(claims) ? claims.get(CLAIM_KEY_USERNAME).toString() : EMPTY;
    }

    public boolean validateToken(String authToken, UserDetails userDetails) {
        return true;
    }

    private Claims getClaimsFromToken(String token) throws UnsupportedEncodingException {
        if (nonNull(token)) {
            return Jwts.parser()
                    .setSigningKey(SECRET.getBytes(UTF_8))
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, EMPTY))
                    .getBody();
        }
        return null;
    }
}
