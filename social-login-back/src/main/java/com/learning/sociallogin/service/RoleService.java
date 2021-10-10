package com.learning.sociallogin.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learning.sociallogin.entity.Role;
import com.learning.sociallogin.enums.RoleName;
import com.learning.sociallogin.repository.RoleRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class RoleService {

	private final RoleRepository roleRepository;

	public Optional<Role> getByRoleName(RoleName roleName) {
		return this.roleRepository.findByRoleName(roleName);
	}

	public boolean exists(RoleName roleName) {
		return this.roleRepository.existsByRoleName(roleName);
	}

	public Role save(Role role) {
		return this.roleRepository.save(role);
	}
}
