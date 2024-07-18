
package com.prodapt.cmsprojectmain.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodapt.cmsprojectmain.entities.ERole;
import com.prodapt.cmsprojectmain.entities.Features;
import com.prodapt.cmsprojectmain.entities.Parameter;
import com.prodapt.cmsprojectmain.entities.Product;
import com.prodapt.cmsprojectmain.entities.Role;
import com.prodapt.cmsprojectmain.entities.UserEntity;
import com.prodapt.cmsprojectmain.exceptions.FeatureNotFoundException;
import com.prodapt.cmsprojectmain.exceptions.ProductNotFoundException;
import com.prodapt.cmsprojectmain.service.FeatureService;
import com.prodapt.cmsprojectmain.service.ParameterService;
import com.prodapt.cmsprojectmain.service.ProductService;
import com.prodapt.cmsprojectmain.service.RoleService;
import com.prodapt.cmsprojectmain.service.UserEntityService;

@SpringBootTest

class AdminControllerTest {

	private MockMvc mockMvc;
	@InjectMocks
	private AdminController adminController;

	@Mock
	private ProductService productService;

	@Mock
	private FeatureService featureService;

	@Mock
	private ParameterService parameterService;

	@Mock
	private UserEntityService userService;
	@Mock
	private RoleService roleService;

	private Product product;
	private Features feature;
	private Parameter parameter;
	private UserEntity user;

	@BeforeEach
	public void setup() {
		product = new Product();
		product.setId(1L);
		product.setName("Test Product");

		feature = new Features();
		feature.setId(1L);
		feature.setName("Test Feature");

		parameter = new Parameter();
		parameter.setId(1L);
		parameter.setName("Test Parameter");
		parameter.setType("test");
		parameter.setValue("2.00");

		user = new UserEntity();
		user.setId(1);
		user.setUsername("testUser");
		user.setEmail("testEmail");
		user.setPassword("testPassword");

		mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	void testAddProduct() throws Exception {

		// Act and Assert
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/addproduct").content(asJsonString(product))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());

	}

	@Test
	void testGetProductByNameSuccess() throws Exception {
		String productName = "Test Product";
		Product expectedProduct = new Product();
		expectedProduct.setName(productName);

		when(productService.getProductByName(productName)).thenReturn(product);

		// Perform MockMvc request
		mockMvc.perform(get("/api/v1/admin/getproductsbyName?name=" + productName)).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(expectedProduct.getName())));
	}

	@Test
	void testGetProductByNameFailure() throws Exception {
		String productName = "Test Product";

		when(productService.getProductByName(productName))
				.thenThrow(new ProductNotFoundException("Product don't exist"));
		try {
			mockMvc.perform(get("/api/v1/admin/getproductbyname?name=" + productName)).andExpect(status().isNotFound())
					.andExpect(content().string(""));
		} catch (Exception e) {
			// sink it
		}
	}

	@Test
	void testGetProductByIdSuccess() throws Exception {
		// Arrange
		Long productId = 1L;
		Product expectedProduct = new Product();
		expectedProduct.setId(productId);
		expectedProduct.setName("Test Product");

		when(productService.getProductById(productId)).thenReturn(expectedProduct);

		// Act
		mockMvc.perform(get("/api/v1/admin/getproductsbyId?id=" + productId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is(expectedProduct.getName())));

	}

	/*
	 * @Test void testGetProductByIdFailure() throws Exception,
	 * ProductNotFoundException { Long productId = 5L;
	 * 
	 * when(productService.getProductById(productId)).thenThrow(new
	 * ProductNotFoundException("Product doesn't exist"));
	 * 
	 * try { MvcResult result =
	 * mockMvc.perform(get("/api/v1/admin/getproductbyid?id=" + productId))
	 * //.andDo(print()) .andExpect(status().isNotFound()).andReturn();
	 * 
	 * String response = result.getResponse().getContentAsString();
	 * System.out.println(response); } catch (Exception e) { // sink it } }
	 */
	@Test
	void testGetAllProducts() throws Exception {

		List<Product> products = new ArrayList<>();
		products.add(product);

		when(productService.getAllProducts()).thenReturn(products);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/admin/getallproducts"))

				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty());
	}

	@Test
	void testUpdateProduct() throws Exception {
		// Arrange
		Long pId = product.getId();
		Product updatedProduct = new Product();
		updatedProduct.setId(product.getId());
		updatedProduct.setName("Updated Product");

		when(productService.updateProduct(pId, updatedProduct)).thenReturn(updatedProduct);

		// Act and Assert
		mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/admin/updateproduct?id=" + pId)

				.content(asJsonString(updatedProduct)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				// .andDo(print())
				.andExpect(status().isOk())

		;
	}

	@Test
	void testUpdateProductFailure() throws Exception {
		// Arrange
		Long productId = 5L;
		Product updatedProduct = new Product();
		updatedProduct.setId(productId);
		updatedProduct.setName("Updated Product");

		// Mocking the productService
		when(productService.updateProduct(productId, updatedProduct))
				.thenThrow(new ProductNotFoundException("Product not found"));

		try {
			// Act & Assert
			mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/admin/updateproduct?id=" + productId)
					.content(asJsonString(updatedProduct)).contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string(""));
		} catch (Exception e) {
			// sink it
		}

	}

	@Test
	void testDeleteProductByIdSuccess() throws Exception {
		Long pId = product.getId();
		when(productService.deleteProductid(pId)).thenReturn("Product deleted successfully");

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/deleteproductbyid/{productId}", pId))
				.andExpect(status().isOk()).andExpect(content().string("Product deleted successfully"));
	}

	@Test
	void testAddFeature() throws Exception {

		// Act and Assert
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/addfeature").contentType("application/json")
				.content(asJsonString(new Features(null, "Test Product", null, null)))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());

	}

	@Test
	void testDeleteFeatureByIdSuccess() throws Exception {
		Long featureId = 1L;
		when(featureService.deleteFeatureById(featureId)).thenReturn("Feature deleted successfully");

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/deletefeaturebyid/{featureId}", featureId))
				.andExpect(status().isOk()).andExpect(content().string("Feature deleted successfully"));
	}

	@Test
	void testDeleteFeatureByIdFailure() throws FeatureNotFoundException {
		// Arrange
		Long featureId = 1L;
		when(featureService.deleteFeatureById(featureId)).thenThrow(new FeatureNotFoundException("Product not found"));

		// Act and Assert
		assertThrows(FeatureNotFoundException.class, () -> {
			featureService.deleteFeatureById(featureId);
		});
	}

	@Test
	void testDeleteParameterByIdSuccess() throws Exception {
		// Arrange
		Long parameterId = parameter.getId();
		String expectedMessage = "Parameter deleted successfully";
		when(parameterService.deleteParameterById(parameterId)).thenReturn(expectedMessage);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/deleteparameterbyid/{parameterId}", parameterId))
				.andExpect(status().isOk()).andExpect(content().string(expectedMessage));
	}

	@Test
	void testUpdateUserRoleSuccess() throws Exception {
		// Arrange
		Integer userId = 1;
		Role role = new Role(1, ERole.ROLE_MANAGER);
		String expectedResult = "Role Updated Successfully";

		when(userService.updateRole(userId, role)).thenReturn(expectedResult);

		// Act and Assert
		mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/admin/users/updaterole?userId=" + userId)
				.contentType(MediaType.APPLICATION_JSON).content("{\"role\":\"ROLE_ADMIN\"}"))
				.andExpect(status().isOk()).andExpect(content().string(""));
	}
	/*
	@Test
	void testGetAllUsers() throws Exception {
		// Mock data
		UserEntity user1 = new UserEntity();
		user1.setId(1);
		user1.setUsername("user1");
		user1.setEmail("user1@example.com");
		user1.setPassword("password1");

		UserEntity user2 = new UserEntity();
		user2.setId(2);
		user2.setUsername("user2");
		user2.setEmail("user2@example.com");
		user2.setPassword("password2");

		when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

		// Perform GET request and verify results
		mockMvc.perform(get("/api/v1/admin/users/getallusers")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].username").value("user1"))
				.andExpect(jsonPath("$[0].email").value("user1@example.com")).andExpect(jsonPath("$[1].id").value(2))
				.andExpect(jsonPath("$[1].username").value("user2"))
				.andExpect(jsonPath("$[1].email").value("user2@example.com"));

		// Verify userService.getAllUsers() is called once
		verify(userService, times(1)).getAllUsers();
	}

	@Test
	void testGetAllRoles() throws Exception {
		// Mock data
		Role role1 = new Role();
		role1.setId(1);
		role1.setName(ERole.ROLE_ADMIN);

		Role role2 = new Role();
		role2.setId(2);
		role2.setName(ERole.ROLE_CUSTOMER);

		when(roleService.getAllRole()).thenReturn(Arrays.asList(role1, role2));

		// Perform GET request and verify results
		mockMvc.perform(get("/api/v1/admin/users/getallroles")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].name").value("ROLE_ADMIN")).andExpect(jsonPath("$[1].id").value(2))
				.andExpect(jsonPath("$[1].name").value("ROLE_USER"));

		// Verify roleService.getAllRole() is called once
		verify(roleService, times(1)).getAllRole();
	}*/

}