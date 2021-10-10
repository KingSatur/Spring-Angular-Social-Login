package com.learning.sociallogin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.learning.sociallogin.enums.RoleName;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//create table role (id integer not null auto_increment, role_name varchar(255), primary key (id)) engine=InnoDB
@NoArgsConstructor
@Data
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Enumerated(EnumType.STRING)
	@Column(unique = true)
	private RoleName roleName;

}
