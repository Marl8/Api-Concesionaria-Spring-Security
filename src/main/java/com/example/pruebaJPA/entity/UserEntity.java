package com.example.pruebaJPA.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder // Permite construir un objeto
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message ="Debe indicarse el nombre del user")
    private String name;
    @NotBlank(message ="Debe indicarse el username del user")
    private String username;
    @NotBlank(message ="Debe indicarse el password del user")
    private String password;

    @NotEmpty(message = "El user debe tener asignado algún rol")
    @ManyToMany(targetEntity = Rol.class, cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "fk_user"),
            inverseJoinColumns = @JoinColumn(name = "fk_rol"))
    private Set<Rol> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = this.roles.stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getName().name())).toList();
    return authorities;
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
