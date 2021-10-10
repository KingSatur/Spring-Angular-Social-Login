package com.learning.sociallogin.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//create table user_social_login (id integer not null auto_increment, email varchar(255), password varchar(255), primary key (id)) engine=InnoDB
@NoArgsConstructor
@Data
public class UserSocialLogin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull
	@Column(unique = true)
	private String email;
	@NotNull
	private String password;
	// This annotation creates a transitional table where will be saved the
	// usersocialid and role_id in order to create joins and query which roles a
	// usersocial have
	@ManyToMany(fetch = FetchType.EAGER)
	// This annotations create the columns of the transitional table
	// Hibernate: create table user_social_login_roles (usersocial_id integer
	// not null, role_id integer not null, primary key (usersocial_id, role_id))
	// engine=InnoDB
	@JoinTable(joinColumns = @JoinColumn(name = "usersocial_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<Role> roles = new HashSet<>();

	public UserSocialLogin(String email, String password) {
		this.email = email;
		this.password = password;
	}

}
