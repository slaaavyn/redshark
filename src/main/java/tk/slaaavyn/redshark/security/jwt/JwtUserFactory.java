package tk.slaaavyn.redshark.security.jwt;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import tk.slaaavyn.redshark.model.User;

import java.util.Collections;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                true,
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().getName()))
        );
    }
}
