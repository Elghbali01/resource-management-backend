package com.university.fst.resourcemanagement.security;

import com.university.fst.resourcemanagement.entity.User;
import com.university.fst.resourcemanagement.enums.Status;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private final User user;
    private final String effectiveRole;

    // Rôle réel par défaut
    public UserDetailsImpl(User user) {
        this.user = user;
        this.effectiveRole = user.getRole().name();
    }

    // Rôle effectif injecté depuis le JWT si nécessaire
    public UserDetailsImpl(User user, String effectiveRole) {
        this.user = user;
        this.effectiveRole = (effectiveRole == null || effectiveRole.isBlank())
                ? user.getRole().name()
                : effectiveRole;
    }

    public Long getId() {
        return user.getId();
    }

    public String getNom() {
        return user.getNom();
    }

    public String getPrenom() {
        return user.getPrenom();
    }

    public String getEmail() {
        return user.getEmail();
    }

    // rôle effectif utilisé par la sécurité courante
    public String getRoleStr() {
        return effectiveRole;
    }

    // rôle réel stocké en base si jamais tu en as besoin plus tard
    public String getRealRoleStr() {
        return user.getRole().name();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + effectiveRole));
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
        return user.getStatus() == Status.ACTIVE;
    }
}