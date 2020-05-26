package tk.slaaavyn.redshark.security;

public abstract class SecurityConstants {
    public static final String TOKEN_PREFIX = "Bearer_";
    public static final String HEADER_STRING = "Authorization";
    public static final long TOKEN_VALIDITY_TIME = 86400000L; // 24H
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
}
