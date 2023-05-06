package com.example.msuser.security;

import com.example.msuser.model.response.TokenResp;
import com.example.msuser.util.enums.type.RoleType;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiry}")
    private long expiryDefault;

    private final String prefix = "Bearer";

    public String getRawToken(String token) {
        return token.substring(prefix.length()).trim();
    }

    public TokenResp createToken(Long userId, Set<RoleType> roles) {
        return generateToken(String.valueOf(userId), roles);
    }

    TokenResp generateToken(String userId, Set<RoleType> roles) {
        var now = new Date();
        var expiryDate = new Date(now.getTime() + expiryDefault * 1000);
        return TokenResp
                .builder()
                .token(Jwts.builder()
                        .setSubject(userId)
                        .claim("roles", roles)
                        .setIssuedAt(now)
                        .setExpiration(expiryDate)
                        .signWith(SignatureAlgorithm.HS512, jwtSecret)
                        .compact())
                .build();
    }

    public JwtPayload getPayloadFromJWT(String token) {
        var rawToken = getRawToken(token);

        var claims = Jwts
                .parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(rawToken)
                .getBody();

        return JwtPayload
                .builder()
                .userId(claims.getSubject())
                .roles((List<RoleType>) claims.get("roles"))
                .build();
    }

    public boolean validateToken(String authToken) {
        try {
            var rawToken = getRawToken(authToken);
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(rawToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }


}
