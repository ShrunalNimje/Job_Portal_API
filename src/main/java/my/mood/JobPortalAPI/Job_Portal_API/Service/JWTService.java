package my.mood.JobPortalAPI.Job_Portal_API.Service;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

	private String secret_key;
	
	public JWTService() {
		
		this.secret_key = Base64.getEncoder()
				.encodeToString("MyVerySecretKey1234567890dfhbnbkidfneirg25r34hr65jn!".getBytes());
	}
	
	public String generateToken(String email) {
		
		Map<String, Object> claims = new HashMap<>();
		
		return Jwts.builder()
				.claims()
				.add(claims)
				.subject(email)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
				.and()
				.signWith(getKey())
				.compact();
		
	}

	private SecretKey getKey() {
		byte [] keyBytes = Decoders.BASE64.decode(secret_key);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	 public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractEmail(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
	
}
