package com.prodapt.cmsprojectmain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.prodapt.cmsprojectmain.entities.ERole;
import com.prodapt.cmsprojectmain.entities.Role;
import com.prodapt.cmsprojectmain.repositories.RoleRepository;

public class RoleServiceImplTest {

	@Mock
	private RoleRepository repo;

	@InjectMocks
	private RoleServiceImpl service;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testFindRoleByName() {
		// Prepare test data
		ERole roleName = ERole.ROLE_CUSTOMER;
		Role role = new Role();
		role.setId(1);
		role.setName(roleName);

		// Mock repository method
		when(repo.findByName(roleName)).thenReturn(Optional.of(role));

		// Invoke service method
		Optional<Role> foundRole = service.findRoleByName(roleName);

		// Assert result
		assertTrue(foundRole.isPresent());
		assertEquals(role.getId(), foundRole.get().getId());
		assertEquals(role.getName(), foundRole.get().getName());
	}

	@Test
	public void testFindRoleById() {
		// Prepare test data
		Integer roleId = 1;
		Role role = new Role();
		role.setId(roleId);
		role.setName(ERole.ROLE_CUSTOMER);

		// Mock repository method
		when(repo.findById(roleId)).thenReturn(Optional.of(role));

		// Invoke service method
		Optional<Role> foundRole = service.findRoleById(roleId);

		// Assert result
		assertTrue(foundRole.isPresent());
		assertEquals(role.getId(), foundRole.get().getId());
		assertEquals(role.getName(), foundRole.get().getName());
	}

	@Test
	public void testGetAllRole() {
		// Prepare test data
		Role role1 = new Role();
		role1.setId(1);
		role1.setName(ERole.ROLE_CUSTOMER);

		Role role2 = new Role();
		role2.setId(2);
		role2.setName(ERole.ROLE_ADMIN);

		List<Role> roles = new ArrayList<>();
		roles.add(role1);
		roles.add(role2);

		// Mock repository method
		when(repo.findAll()).thenReturn(roles);

		// Invoke service method
		Iterable<Role> allRoles = service.getAllRole();

		// Assert result
		assertNotNull(allRoles);
		List<Role> resultRoles = new ArrayList<>();
		allRoles.forEach(resultRoles::add);
		assertEquals(roles.size(), resultRoles.size());
		assertEquals(role1.getId(), resultRoles.get(0).getId());
		assertEquals(role2.getId(), resultRoles.get(1).getId());
	}
}
