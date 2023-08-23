package com.hzn.reactive.framework.documents.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hzn.reactive.framework.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Document(collection = "user")
@Setter
@Getter
public class User implements UserDetails {
	@Id
	private String     id;
	private String     username;
	private String     password;
	private Boolean    enabled;
	private List<Role> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities () {
		return roles.stream ().map (role -> new SimpleGrantedAuthority (role.name ())).collect (Collectors.toList ());
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
