package com.prodapt.cmsprojectmain.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.prodapt.cmsprojectmain.entities.Product;
import com.prodapt.cmsprojectmain.service.ProductService;

@SpringBootTest
class CustomerControllerTest {

	@Mock
	private ProductService productService;

	@InjectMocks
	private CustomerController customerController;

	private MockMvc mockMvc;

	private Product product;
	private List<Product> products;

	@BeforeEach
	public void setup() {
		product = new Product();
		product.setName("Test Product");

		products = new ArrayList<>();
		products.add(product);
		mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
	}

	@Test
	void testGetProductByNameSuccess() throws Exception {
		String productName = "Test Product";
		Product expectedProduct = new Product();
		expectedProduct.setName(productName);

		when(productService.getProductByName(productName)).thenReturn(product);

		// Perform MockMvc request
		mockMvc.perform(get("/api/v1/customer/getproductbyname?name=" + productName)).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(expectedProduct.getName())));
	}

	@Test
	void testGetAllProducts() throws Exception {

		List<Product> products = new ArrayList<>();
		products.add(product);

		when(productService.getAllProducts()).thenReturn(products);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customer/getallproducts"))

				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty());
	}

}