package com.socialmediaclone.socialmediaclone.security;


public class JwtProperties {
    public static final String SECRET = "secret";
    public static final int EXPIRATION = 10 * 1000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    public static final String REFRESH_TOKEN_HEADER = "Refresh";

    public static final String REFRESH_TOKEN_PREFIX = "Refresh Bearer ";

    public static final String REFRESH_TOKEN_SUFFIX = " refresh";
}
