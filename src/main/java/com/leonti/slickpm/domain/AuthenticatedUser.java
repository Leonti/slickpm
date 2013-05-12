package com.leonti.slickpm.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class AuthenticatedUser extends User {

	private static final long serialVersionUID = 1937644057159194993L;
	private com.leonti.slickpm.domain.User user;

	public AuthenticatedUser(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
	}

	public AuthenticatedUser(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities,
			com.leonti.slickpm.domain.User user) {
		super(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);

		this.user = user;
	}

	public com.leonti.slickpm.domain.User getUser() {
		return user;
	}

	public void setUser(com.leonti.slickpm.domain.User user) {
		this.user = user;
	}
}
