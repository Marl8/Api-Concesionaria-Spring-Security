package com.example.pruebaJPA.service.securityServices.jwt;

import com.example.pruebaJPA.entity.UserEntity;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.time.expiration}")
    private String timeExpiration;

    public String generate(UserEntity user, Map<String, Object> extraClaims) {

        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date((issuedAt.getTime() + Long.parseLong(timeExpiration)));

        try{
            return Jwts.builder()
                    .claims(extraClaims)
                    .subject(user.getUsername())
                    .issuedAt(issuedAt)
                    .expiration(expiration)
                    .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                    .signWith(generatedKey())
                    .compact();
        }catch (JwtException ex){
            throw new RuntimeException(ex);
        }
    }

    private SecretKey generatedKey(){
        byte[] decodificado = Decoders.BASE64.decode(secretKey);
        System.out.println("Clave secreta es: " + new String(decodificado));
        return Keys.hmacShaKeyFor(decodificado);
    }

    public String extractUsername(String token) {
        return Jwts.parser().verifyWith(generatedKey()).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }

    /*
     *NOTA:
     *
     * 1. La clave segura debe ser de más de 256 bytes o arrojará error por no
     * considerarla lo suficientemente segura.
     *
     * 2. Siempre en la base de datos la contraseña debe estar encriptada por
     * BCryptPasswordEncoder porque si no arrogará error.
     * **/
}
