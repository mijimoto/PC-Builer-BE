// com.project.PCBuilder.security.UserPrincipal.java
package com.project.PCBuilder.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.project.PCBuilder.persistence.entities.Accounts;
import java.util.Collection;
import java.util.Collections;

public class UserPrincipal implements UserDetails {
    private final Accounts account;

    public UserPrincipal(Accounts account) {
        this.account = account;
    }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() {
        // you can expand this when you add roles
        return Collections.emptyList();
    }

    @Override public String getPassword() {
        return account.getPasswordhased();
    }

    @Override public String getUsername() {
        return account.getEmail();
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() {
        return Boolean.TRUE.equals(account.getIsverified());
    }

    // getter for the full Accounts if you need
    public Accounts getAccount() {
        return account;
    }
}
