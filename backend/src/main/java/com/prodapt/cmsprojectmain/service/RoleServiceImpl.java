package com.prodapt.cmsprojectmain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.cmsprojectmain.entities.ERole;
import com.prodapt.cmsprojectmain.entities.Role;
import com.prodapt.cmsprojectmain.repositories.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepository repo;

	@Override
	public Optional<Role> findRoleByName(ERole role) {
		return repo.findByName(role);

	}

	@Override
	public Optional<Role> findRoleById(Integer id) {
		return repo.findById(id);

	}

	@Override
	public Iterable<Role> getAllRole() {
		return repo.findAll();
	}

}
