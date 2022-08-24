package com.socialmediaclone.socialmediaclone.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.socialmediaclone.socialmediaclone.exceptions.RefreshTokenException;
import com.socialmediaclone.socialmediaclone.exceptions.UserNotFollowerException;
import com.socialmediaclone.socialmediaclone.security.JwtProperties;
import com.socialmediaclone.socialmediaclone.security.UserDetailsPrincipal;
import com.socialmediaclone.socialmediaclone.security.UserDetailsServicePrincipal;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class RefreshTokenFilter extends OncePerRequestFilter {

    private final UserDetailsServicePrincipal userDetailsServicePrincipal;
    private final AntPathRequestMatcher matcher = new AntPathRequestMatcher("/refresh");

    public RefreshTokenFilter(UserDetailsServicePrincipal userDetailsServicePrincipal) {
        this.userDetailsServicePrincipal = userDetailsServicePrincipal;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (matcher.matches(request)){
            String header = request.getHeader(JwtProperties.REFRESH_TOKEN_HEADER);
            if (header == null || !header.startsWith(JwtProperties.REFRESH_TOKEN_PREFIX)){
                chain.doFilter(request,response);
                return;
            }
            Authentication authentication = getUserNameAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            String accessToken = JWT.create().withSubject(authentication.getName())
                    .withExpiresAt(new Date(System.currentTimeMillis()+ JwtProperties.EXPIRATION))
                    .sign(algorithm);
            response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + accessToken);
        }
        chain.doFilter(request, response);
    }
    private Authentication getUserNameAuthentication(HttpServletRequest request) {
        try {
            String token = request.getHeader(JwtProperties.REFRESH_TOKEN_HEADER).substring(JwtProperties.REFRESH_TOKEN_PREFIX.length());
            if (!token.equals(Strings.EMPTY)) {
                String userName = JWT.require(Algorithm.HMAC256(JwtProperties.SECRET.getBytes()))
                        .build().verify(token).getSubject();
                userName = userName.substring(0, userName.length() - JwtProperties.REFRESH_TOKEN_SUFFIX.length());
                UserDetailsPrincipal user = (UserDetailsPrincipal) userDetailsServicePrincipal.loadUserByUsername(userName);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, null, user.getAuthorities());
                return authenticationToken;
            }
        }
        catch (Exception e) {
            throw new RefreshTokenException("Refresh Token Has Expired");
        }



        return null;
    }
}
