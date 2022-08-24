package com.socialmediaclone.socialmediaclone.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
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

public class CustomAuthorizationFilter extends OncePerRequestFilter{


    private final UserDetailsServicePrincipal userDetailsServicePrincipal;
    private final AntPathRequestMatcher matcher = new AntPathRequestMatcher("/refresh");

    public CustomAuthorizationFilter(UserDetailsServicePrincipal userDetailsServicePrincipal) {
        this.userDetailsServicePrincipal = userDetailsServicePrincipal;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!matcher.matches(request)) {
            String header = request.getHeader(JwtProperties.HEADER_STRING);
            if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)){
                chain.doFilter(request,response);
                return;
            }
            Authentication authentication = getUserNameAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    private Authentication getUserNameAuthentication(HttpServletRequest request) {
        String token = request.getHeader(JwtProperties.HEADER_STRING).substring(JwtProperties.TOKEN_PREFIX.length());
        if (!token.equals(Strings.EMPTY)) {
            String userName = JWT.require(Algorithm.HMAC256(JwtProperties.SECRET.getBytes()))
                    .build().verify(token).getSubject();

            if (userName != null) {
                 UserDetailsPrincipal user = (UserDetailsPrincipal) userDetailsServicePrincipal.loadUserByUsername(userName);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, null, user.getAuthorities());
                return authenticationToken;
            }
        }
        return null;
    }
}
