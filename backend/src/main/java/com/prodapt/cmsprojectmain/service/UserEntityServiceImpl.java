package com.prodapt.cmsprojectmain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.cmsprojectmain.entities.ERole;
import com.prodapt.cmsprojectmain.entities.Role;
import com.prodapt.cmsprojectmain.entities.UserEntity;
import com.prodapt.cmsprojectmain.exceptions.UserNotFoundException;
import com.prodapt.cmsprojectmain.repositories.UserRepository;

@Service
public class UserEntityServiceImpl implements UserEntityService {

	@Autowired
	UserRepository repo;

	@Override
	public String updateRole(Integer userId, Role role) throws UserNotFoundException {
		Optional<UserEntity> user = repo.findById(userId);
		if (user.isPresent()) {
			user.get().setRole(role);
			repo.save(user.get());
			return "Role Updated Successfully!!!";

		} else {
			throw new UserNotFoundException("User with" + userId + "not found");
		}
	}

	@Override
	public UserEntity addUserEntity(UserEntity user) {

		return repo.save(user);
	}

	@Override
	public Optional<UserEntity> findByUsername(String username) {
		return repo.findByUsername(username);

	}

	@Override
	public Boolean existsByUsername(String username) {

		return repo.existsByUsername(username);
	}

	@Override
	public Optional<UserEntity> findByRole(ERole role) {
		return repo.findByRole(role);
	}

	@Override
	public UserEntity getUserEntityById(Integer id) throws UserNotFoundException {
		return repo.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
	}

	@Override
	public Optional<UserEntity> findById(Integer id) throws UserNotFoundException {

		return repo.findById(id);
	}

	@Override
	public Iterable<UserEntity> getAllUsers() {
		return repo.findAll();
	}

}
