package com.openclassrooms.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.openclassrooms.CustomProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenProvider {

	private static final Logger log = LoggerFactory.getLogger(TokenProvider.class);

	private static final String AUTHORITIES_KEY = "Role";
	
    private final CustomProperties customProperties;
    

	public TokenProvider(CustomProperties customProperties) {
		this.customProperties = customProperties;
	}

	@SuppressWarnings("deprecation")
	public String createToken(Authentication authentication) {
		String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		long now = (new Date()).getTime();
		Date validity = new Date(now + customProperties.getTokenValidity());

		return Jwts.builder()
				.subject(authentication.getName())
				.claim("Role", authorities)
				.signWith(getSigningKey())
				.setExpiration(validity).compact();

	}

	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(customProperties.getSecret());
		return Keys.hmacShaKeyFor(keyBytes);
	}

	@SuppressWarnings("deprecation")
	public Authentication getAuthentication(String token) {
		Claims claims = Jwts.parser().setSigningKey(customProperties.getSecret()).build().parseClaimsJws(token).getBody();

		Collection<? extends GrantedAuthority> authorities = Arrays
				.stream(claims.get(AUTHORITIES_KEY).toString().split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		User principal = new User(claims.getSubject(), "", authorities);

		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}

	@SuppressWarnings("deprecation")
	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(customProperties.getSecret()).build().parseClaimsJws(authToken);
			return true;
		} catch (Exception e) {
			log.warn("Error");
		}
		return false;
	}
}
