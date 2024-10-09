package with_yu.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import with_yu.domain.user.entity.Role;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final Duration accessTokenExpiration;
    private final Duration refreshTokenExpiration;
    private final String issuer;

    public JwtUtil(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration.access-token}") Duration accessTokenExpiration,
            @Value("${jwt.expiration.refresh-token}") Duration refreshTokenExpiration,
            @Value("${jwt.issuer}") String issuer
    ){
        this.secretKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.issuer = issuer;

    }

    public String generateAccessToken(String email, Role role){
        return generateToken(email, role, accessTokenExpiration, "accessToken");
    }

    public String generateRefreshToken(String email, Role role){
        return generateToken(email, role, refreshTokenExpiration, "refreshToken");
    }

    public Claims parseToken(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getSubject(String token){
        return this.parseToken(token).getSubject();
    }

    public long getRefreshTokenExpiration(){
        return this.refreshTokenExpiration.toMillis();
    }


    private String generateToken(String email, Role role, Duration expiration, String type){
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .issuer(issuer)
                .expiration(createExpiration(expiration))
                .claim("role", role)
                .claim("type", type)
                .signWith(secretKey)
                .compact();

    }

    private Date createExpiration(Duration expiration){
        return new Date(System.currentTimeMillis() + expiration.toMillis());
    }


}
