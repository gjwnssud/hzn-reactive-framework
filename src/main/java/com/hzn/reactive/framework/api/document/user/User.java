package com.hzn.reactive.framework.api.document.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hzn.reactive.framework.api.enums.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Document (collection = "user")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
	@Id
	private String id;
	private String username;
	private String password;
	private Boolean enabled;
	private List<Role> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities () {
		return roles.stream ()
					.map (role -> new SimpleGrantedAuthority (role.name ()))
					.collect (Collectors.toList ());
	}

	@JsonIgnore
	@Override
	public String getPassword () {
		return password;
	}

	@Override
	public String getUsername () {
		return username;
	}

	@Override
	public boolean isAccountNonExpired () {
		return false;
	}

	@Override
	public boolean isAccountNonLocked () {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired () {
		return false;
	}

	@Override
	public boolean isEnabled () {
		return enabled;
	}
}
