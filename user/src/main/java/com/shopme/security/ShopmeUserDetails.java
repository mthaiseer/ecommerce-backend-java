package com.shopme.security;

import com.shopme.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.shopme.entity.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ShopmeUserDetails  implements UserDetails {

    private User user;

    public ShopmeUserDetails(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<Role> roles = user.getRoles();
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
        for(Role role:  roles){
            simpleGrantedAuthorities.add( new SimpleGrantedAuthority(role.getName()));
        }
        return simpleGrantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
        return user.isEnabled();
    }
}
