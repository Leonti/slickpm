package com.leonti.slickpm.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.leonti.slickpm.domain.AuthenticatedUser;
import com.leonti.slickpm.domain.User;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Resource(name = "UserService")
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		User user = userService.getByEmail(username);

		if (user == null)
			throw new UsernameNotFoundException("User not found");

		if (user.getConfirmationKey() != null)
			throw new UsernameNotFoundException("User not confirmed");

		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		return new AuthenticatedUser(user.getEmail(), user.getPassword(),
				enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, getAuthorities(user.getRole()), user);
	}

	public Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
		List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
		return authList;
	}

	public List<String> getRoles(Integer role) {
		List<String> roles = new ArrayList<String>();

		if (role.intValue() == 1) {
			roles.add("ROLE_USER");
			roles.add("ROLE_ADMIN");

		} else if (role.intValue() == 2) {
			roles.add("ROLE_USER");
		}

		return roles;
	}

	public static List<GrantedAuthority> getGrantedAuthorities(
			List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}

}
