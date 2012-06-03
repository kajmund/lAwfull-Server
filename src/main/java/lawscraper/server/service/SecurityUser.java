package lawscraper.server.service;

import lawscraper.server.entities.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("serial")
public class SecurityUser implements UserDetails {
    private final User user;
    private Collection<GrantedAuthority> authorities;

    public SecurityUser(User user) {
        this.user = user;
        authorities = getAuthorities();
    }

    public Collection<GrantedAuthority> getAuthorities() {

        if (authorities == null) {
            authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority(this.user.getUserRole().getDesignation()));
        }

        return authorities;
    }

    public String getPassword() {
        return user.getPassword();
    }

    public String getUsername() {
        return user.getUserName();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    public User getUser() {
        return user;
    }
}
