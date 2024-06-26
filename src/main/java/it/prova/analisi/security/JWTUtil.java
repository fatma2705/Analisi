package it.prova.analisi.security;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import it.prova.analisi.model.Ruolo;
import it.prova.analisi.model.Utente;
import it.prova.analisi.repository.utente.UtenteRepository;

@Component
public class JWTUtil {
	
	@Autowired
	private UtenteRepository utenteRepository;

	@Value("${jwt-secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long jwtExpirationMs;

	// Method to sign and create a JWT using the injected secret
	public String generateToken(String username) throws IllegalArgumentException, JWTCreationException {
		Optional<Utente> optionalUser = utenteRepository.findByUsername(username);
		Utente utente = optionalUser.get();
		String[] ruoliArray = utente.getRuoli().stream()
                .map(Ruolo::getCodice)
                .toArray(String[]::new); // Converti in array di Stringhe

		return JWT.create()
				.withSubject("User Details")
				.withClaim("username", username)
				.withClaim("nome", utente.getNome())
				.withArrayClaim("currentUser", ruoliArray)
				.withIssuedAt(new Date())
				.withIssuer("ANALISI")
				.withExpiresAt(new Date((new Date()).getTime() + jwtExpirationMs))
				.sign(Algorithm.HMAC256(secret));

	}

	// Method to verify the JWT and then decode and extract the username stored in
	// the payload of the token
	public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {
		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).withSubject("User Details").withIssuer("ANALISI")
				.build();
		DecodedJWT jwt = verifier.verify(token);
		return jwt.getClaim("username").asString();
	}

}
