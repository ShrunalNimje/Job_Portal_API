package my.mood.JobPortalAPI.Job_Portal_API.Security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import my.mood.JobPortalAPI.Job_Portal_API.Entity.User_Entity;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    
    private final User_Entity user;
    
    public CustomUserDetails(User_Entity user) {
        this.user = user;
    }

    public int getId() {
        return user.getId();
    }

    public String getRole() {
        return user.getRole().name();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}

