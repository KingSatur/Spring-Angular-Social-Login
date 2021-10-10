package com.learning.sociallogin.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learning.sociallogin.entity.UserSocialLogin;
import com.learning.sociallogin.repository.UsersocialRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class UsersocialService {

	private final UsersocialRepository usersocialRepository;

	public Optional<UserSocialLogin> getByEmail(String email) {
		return this.usersocialRepository.findByEmail(email);
	}

	public boolean exists(String email) {
		return this.usersocialRepository.existsByEmail(email);
	}

	public UserSocialLogin save(UserSocialLogin user) {
		return this.usersocialRepository.save(user);
	}
}
