package com.learning.sociallogin.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.learning.sociallogin.entity.UserSocialLogin;

public class UserMainFactory {

	public static UserMain build(UserSocialLogin user) {
		List<GrantedAuthority> authorities = user.getRoles().stream().map(
				role -> new SimpleGrantedAuthority(role.getRoleName().name()))
				.collect(Collectors.toList());
		return new UserMain(user.getEmail(), user.getPassword(), authorities);

	}

}
