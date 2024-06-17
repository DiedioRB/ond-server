package com.ond.ond.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.ond.ond.models.dto.UserDTO;
import com.ond.ond.models.entities.User;
import com.ond.ond.repositories.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService {

    @Autowired
	private UserRepository userRepository;

    @Transactional(readOnly = true)
	public List<UserDTO> findAll() {
		List<User> result = userRepository.findAll();
		return result.stream().map(UserDTO::new).toList();
	}

    @Transactional(readOnly = true)
	public UserDTO findById(@PathVariable Long id) {
		User result = userRepository.findById(id).get();
		return new UserDTO(result);
	}
}
