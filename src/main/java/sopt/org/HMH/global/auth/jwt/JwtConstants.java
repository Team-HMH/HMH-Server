package sopt.org.HMH.global.auth.jwt;

public abstract class JwtConstants {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String CHARACTER_ENCODING = "UTF-8";
    public static final Integer ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 2;
    public static final Integer REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7 * 2;
}