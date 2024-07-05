package sopt.org.hmh.global.auth.security;

import java.util.Collection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class UserAuthentication extends UsernamePasswordAuthenticationToken {

    private UserAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public static UserAuthentication createUserAuthentication(String subjectId) {
        return new UserAuthentication(subjectId, null, null);
    }
}