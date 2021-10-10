package com.learning.sociallogin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.sociallogin.entity.UserSocialLogin;

public interface UsersocialRepository
		extends JpaRepository<UserSocialLogin, Integer> {

	Optional<UserSocialLogin> findByEmail(String email);

	boolean existsByEmail(String email);

}
