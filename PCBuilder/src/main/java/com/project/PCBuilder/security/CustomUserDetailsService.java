// com.project.PCBuilder.security.CustomUserDetailsService.java
package com.project.PCBuilder.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import com.project.PCBuilder.persistence.entities.Accounts;
import com.project.PCBuilder.persistence.repositories.AccountsRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountsRepository repo;

    public CustomUserDetailsService(AccountsRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Accounts acc = repo.findByEmail(email)
           .orElseThrow(() -> new UsernameNotFoundException("No account " + email));
        return new UserPrincipal(acc);
    }
}
