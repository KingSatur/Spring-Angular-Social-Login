package com.learning.sociallogin.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learning.sociallogin.entity.UserSocialLogin;
import com.learning.sociallogin.service.UsersocialService;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UsersocialService userSocialService;

	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		UserSocialLogin user = this.userSocialService.getByEmail(email)
				.orElseThrow(
						() -> new UsernameNotFoundException("Email not found"));
		// TODO Auto-generated method stub
		return UserMainFactory.build(user);
	}

}
