package com.ond.ond.models.dto;

import com.ond.ond.models.entities.User;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
	private String name, email, password;

	public UserDTO(User entity) {
		id = entity.getId();
		name = entity.getName();
		email = entity.getEmail();
		password = entity.getPassword();
	}
    
}
