package com.example.security.full.security.config;

import com.example.security.full.security.UserSecurity.dao.JpaUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JpaUserDetailsService jpaUserDetailsService;
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String userEmail;
        String jwtToken = null;

        // removeSessionAndCookies(request);
        // if (authHeader == null || !authHeader.startsWith("Bearer")) {
        // filterChain.doFilter(request, response);
        // return;
        // }
        if (request != null && request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("jwt")) {
                    jwtToken = cookie.getValue();
                    // System.out.println(cookie.getValue());
                }
            }
        }
        if (jwtToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // jwtToken = authHeader.substring(7);
        userEmail = jwtUtils.extractUsername(jwtToken);
        try {
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = jpaUserDetailsService.loadUserByEmail(userEmail);
                if (jwtUtils.validateToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                            null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (ExpiredJwtException e) {
            removeCookies(request);
        }
        filterChain.doFilter(request, response);
    }

    public static void removeSessionAndCookies(HttpServletRequest request) {
        SecurityContextHolder.getContext().setAuthentication(null);
        SecurityContextHolder.clearContext();
        HttpSession hs = request.getSession();
        Enumeration e = hs.getAttributeNames();
        while (e.hasMoreElements()) {
            String attr = e.nextElement().toString();
            hs.setAttribute(attr, null);
        }
        removeCookies(request);
        hs.invalidate();
    }

    public static void removeCookies(HttpServletRequest request) {
        if (request == null || request.getCookies() == null) {return;}
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {
                cookies[i].setMaxAge(-1);
            }
        }
    }
}
