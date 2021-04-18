package com.jpas.ebiblioteka.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Set;

@Component
public class JWTFilter extends BasicAuthenticationFilter {

    public JWTFilter(AuthenticationManager manager) {
        super(manager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")) {
            response.setStatus((HttpServletResponse.SC_UNAUTHORIZED));
            response.setContentType("application/json");
        } else {
            UsernamePasswordAuthenticationToken authResult = authenticateByToken(header);

            if(authResult == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
            }

            SecurityContextHolder.getContext().setAuthentication(authResult);
        }

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken authenticateByToken(String header) {
        try {
            String secretKey = new SecretKeyGenerator().getSecretKey();
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(header.replace("Bearer ", ""));

            String email = claims.getBody().get("email").toString();
            String role = claims.getBody().get("role").toString();

            Set<SimpleGrantedAuthority> authoritySet = Collections.singleton(new SimpleGrantedAuthority(role));

            return new UsernamePasswordAuthenticationToken(email, null, authoritySet);
        } catch (ExpiredJwtException exception) {
            System.out.println("Token expired!");
        }

        return null;
    }
}
