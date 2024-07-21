package org.example.attendancebackend.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.example.attendancebackend.models.Permission.*;

@RequiredArgsConstructor
public enum Role {

  FATHER(
          Set.of(
                  FATHER_CREATE,
                  FATHER_UPDATE,
                  FATHER_READ,
                  FATHER_DELETE
          )
  ),
  ADMIN(
          Set.of(
                  ADMIN_READ,
                  ADMIN_UPDATE,
                  ADMIN_DELETE,
                  ADMIN_CREATE,
                  FATHER_READ,
                  FATHER_UPDATE,
                  FATHER_DELETE,
                  FATHER_CREATE
          )
  ),

  ;

  @Getter
  private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities = getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return authorities;
  }
}
