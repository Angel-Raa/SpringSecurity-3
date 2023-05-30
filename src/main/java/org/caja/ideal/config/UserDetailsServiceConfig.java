package org.caja.ideal.config;

import org.caja.ideal.models.UserModels;
import org.caja.ideal.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceConfig implements UserDetailsService {
    @Autowired
    private IUserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModels user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Es username no exite"));
        Collection<? extends GrantedAuthority> authorities = user.getRoles()
                .stream().map(rol -> new SimpleGrantedAuthority("ROLE_".concat(rol.getName().name())))
                .collect(Collectors.toSet());
        return new User(user.getUsername(), user.getPassaword(),
                true, true, true, true,authorities);
    }
}
