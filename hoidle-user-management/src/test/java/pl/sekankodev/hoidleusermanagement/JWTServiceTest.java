package pl.sekankodev.hoidleusermanagement;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sekankodev.hoidledata.model.HoidleUser;
import pl.sekankodev.hoidleusermanagement.model.HoidleAppUserDetails;
import pl.sekankodev.hoidleusermanagement.service.JWTService;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class JWTServiceTest {
    JWTService jwtService = new JWTService();
    String secretKey;


    @BeforeEach
    public void setup() {
       try {
           secretKey = "veryVeryVeryVeryVeryVeryVeryVeryVerySecretKey";
           jwtService = new JWTService();

           Field secretKeyField = JWTService.class.getDeclaredField("secretKey");
           secretKeyField.setAccessible(true);
           secretKeyField.set(jwtService, secretKey);
           secretKeyField.setAccessible(false);

       } catch (NoSuchFieldException | IllegalAccessException e) {
           e.printStackTrace();
       }
    }

    @Test
    public void generateTokenTest(){
        String email = "test@mail.com";
        String token = jwtService.generateToken(email);

        assertNotNull(token);

        Claims claims = Jwts.parser()
                .verifyWith(
                        Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
                )
                .build()
                .parseSignedClaims(token)
                .getPayload();

        assertEquals(email, claims.getSubject());
    }

    @Test
    public void extractUsernameTest(){
        String email = "test@mail.com";
        String token = jwtService.generateToken(email);

        String extractedUsername = jwtService.extractUsername(token);
        assertEquals(email, extractedUsername);
    }

    @Test
    public void validateTokenTest(){
        String email = "test@mail.com";
        String token = jwtService.generateToken(email);
        HoidleUser user = new HoidleUser().setEmail(email);
        HoidleAppUserDetails userDetails = new HoidleAppUserDetails(user);

        boolean validationResult = jwtService.validateToken(token, userDetails);
        assertTrue(validationResult);
    }
}
