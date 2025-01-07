package app.core.util;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import app.core.util.JwtUtil.Client.ClientType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Data;

@Component
public class JwtUtil {
	
		// algorithm to code secret key
		private String alg = SignatureAlgorithm.HS256.getJcaName();
		
		// secret key (String)
		@Value("${jwt.util.secret.key}")
		private String secret;
		
		// secret key (Key object).
		// only a references because the key doesn't exist yet (it will arrive after the context is up)
		private Key key;
		
		// time units for the token to be valid after initiation
		@Value("${jwt.util.chrono.unit.number}")
		private int unitsNumber;
		
		// type of time units (in this case minutes)
		@Value("${jwt.util.chrono.unit}")
		private String chronoUnit;

		
		
		/** creates the secret key of the token */
		
		@PostConstruct
		public void init() {
			byte[] secretDecoded = Base64.getDecoder().decode(secret.getBytes());  // create the decoded key
			this.key = new SecretKeySpec(secretDecoded, alg);  // create the key
		}

		
		
		/** Creates a map of the logged client details and provides it to the createToken() method,
		 * than activates it.
		 * 
		 * @param client
		 * @return String (token created by createToken() method)
		 */
		public String generateToken(Client client) {
			
			Map<String, Object> claims = new HashMap<>();
			claims.put("clientType", client.getType());
			claims.put("clientId", client.getId());
			
			return createToken(claims, client.getEmail());
		}

		
		/** creates the token using a map of all the non standard values
		 * @param claims
		 * @param subject
		 * @return String (token)
		 */
		private String createToken(Map<String, Object> claims, String subject) {
			
			Instant now = Instant.now();
			Instant exp = now.plus(this.unitsNumber, ChronoUnit.valueOf(this.chronoUnit));

			String token = Jwts.builder()

					.setClaims(claims) // setting the claims (non standard) - should be first
					.setSubject(subject)
					.setIssuedAt(Date.from(now))
					.setExpiration(Date.from(exp))
					.signWith(key)
					.compact();

			return token;
		}

		
		
		/** activates the extractAllClaims() method to get a claims from token,
		 * than creates a client object from it
		 * @param token
		 * @return Client
		 */
		public Client extractClient(String token) {
			
			Claims claims = extractAllClaims(token);
			
			int clientId = claims.get("clientId", Integer.class);
			String email = claims.getSubject();
			ClientType clientType = ClientType.valueOf(claims.get("clientType", String.class));
			
			Client client = new Client(email, clientType, clientId);
			return client;
		}

		
		/** decode the body of the token
		 * @param token
		 * @return Claims (map)
		 */
		private Claims extractAllClaims(String token) {
			
			JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
			Jws<Claims> jws = jwtParser.parseClaimsJws(token);
			return jws.getBody();
		}
		
		

		public Date getTokenExp(String token) {
			return extractAllClaims(token).getExpiration();
		}

		public Date getTokenIssuedAt(String token) {
			return extractAllClaims(token).getIssuedAt();
		}

		
		
		
		/** inner class to create the client
		 */
		
		@Data
		@AllArgsConstructor
		public static class Client {

			public String email;
			public ClientType type;
			public int id;

			public enum ClientType {
				ADMIN, COMPANY, CUSTOMER
			}
		}

}
