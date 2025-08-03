package pl.sekankodev.hoidleusermanagement.model;

import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.sekankodev.hoidledata.model.HoidleUser;

import java.util.Collection;
import java.util.Collections;

@EqualsAndHashCode
public class HoidleAppUserDetails implements UserDetails {
    private HoidleUser user;

    public HoidleAppUserDetails(HoidleUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }
}
