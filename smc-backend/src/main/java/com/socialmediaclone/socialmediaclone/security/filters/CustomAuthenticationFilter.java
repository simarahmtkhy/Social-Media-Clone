package com.socialmediaclone.socialmediaclone.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.socialmediaclone.socialmediaclone.entities.User;
import com.socialmediaclone.socialmediaclone.exceptions.UserNotFoundException;
import com.socialmediaclone.socialmediaclone.security.JwtProperties;
import com.socialmediaclone.socialmediaclone.security.UserDetailsPrincipal;
import com.socialmediaclone.socialmediaclone.services.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final DaoAuthenticationProvider authenticationProvider;

    private final UserService userService;

    public CustomAuthenticationFilter(DaoAuthenticationProvider authenticationProvider, UserService userService) {
        this.authenticationProvider = authenticationProvider;
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userService.getUser(username);
        if (!user.isVerified()){
            throw new UserNotFoundException("User Not Verified");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationProvider.authenticate(authenticationToken);
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetailsPrincipal user = (UserDetailsPrincipal) authResult.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String accessToken = JWT.create().withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+ JwtProperties.EXPIRATION))
                .sign(algorithm);
        String refreshToken = JWT.create().withSubject(user.getUsername() + JwtProperties.REFRESH_TOKEN_SUFFIX)
                .withExpiresAt(new Date(System.currentTimeMillis()+ JwtProperties.EXPIRATION * 60))
                .sign(algorithm);
        response.getOutputStream().print("successfully logged in");
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + accessToken);
        response.addHeader(JwtProperties.REFRESH_TOKEN_HEADER, JwtProperties.REFRESH_TOKEN_PREFIX + refreshToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        try {
            throw new Exception("wrong credentials");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
