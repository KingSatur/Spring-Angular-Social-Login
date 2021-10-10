package com.learning.sociallogin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.sociallogin.entity.Role;
import com.learning.sociallogin.enums.RoleName;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Optional<Role> findByRoleName(RoleName roleName);

	boolean existsByRoleName(RoleName roleName);

}
