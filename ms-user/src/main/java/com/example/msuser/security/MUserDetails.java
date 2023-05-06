package com.example.msuser.security;

import com.example.msuser.repository.entity.User;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Builder
@AllArgsConstructor
public class MUserDetails implements UserDetails {

    private final long id;
    private final String username;
    private final String password;
    private final boolean isLocked;
    private final boolean isCredentialsExpired;
    private final boolean isEnabled;
    private final Collection<? extends GrantedAuthority> authorities;

    public static MUserDetails create(User user) {
        var authorities = user
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getType().name()))
                .collect(Collectors.toList());

        return MUserDetails
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .isEnabled(user.getIsActive())
                .authorities(authorities)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isCredentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
