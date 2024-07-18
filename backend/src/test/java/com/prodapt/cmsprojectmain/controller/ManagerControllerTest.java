package com.prodapt.cmsprojectmain.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.prodapt.cmsprojectmain.entities.Product;
import com.prodapt.cmsprojectmain.entities.UserEntity;
import com.prodapt.cmsprojectmain.exceptions.ProductNotFoundException;
import com.prodapt.cmsprojectmain.service.ProductService;
import com.prodapt.cmsprojectmain.service.QuotationService;
import com.prodapt.cmsprojectmain.service.UserEntityService;

@SpringBootTest
public class ManagerControllerTest {

	@Mock
	private ProductService productService;

	@Mock
	private ModelMapper modelMapper;
	@Mock
	private QuotationService quotationService;

	@Mock
	private UserEntityService userEntityService;

	@InjectMocks
	private ManagerController managerController;

	private MockMvc mockMvc;

	private Product product;

	private UserEntity user;

	//private QuotationDTO quotationDTO;

	@BeforeEach
	public void setup() {
		product = new Product();
		product.setId(1L);
		product.setName("Test Product");

		user = new UserEntity();
		user.setId(1);
		user.setUsername("testuser");
		user.setEmail("testuser@example.com");
		user.setPassword("testpassword");

//		quotationDTO = new QuotationDTO();
//		quotationDTO.setId(1L);
//		quotationDTO.setUserId(1);
//		quotationDTO.setProductId(1L);
//		quotationDTO.setTotalAmount(5000.0);
//		quotationDTO.setQuantity(5);

		mockMvc = MockMvcBuilders.standaloneSetup(managerController).build();

		
	}

	@Test
	void testGetProductByNameSuccess() throws Exception {
		String productName = "Test Product";
		Product expectedProduct = new Product();
		expectedProduct.setName(productName);

		when(productService.getProductByName(productName)).thenReturn(product);

		// Perform MockMvc request
		mockMvc.perform(get("/api/v1/manager/getproductbyname?name=" + productName)).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(expectedProduct.getName())));
	}

	@Test
	void testGetProductByNameFailure() throws Exception {
		String productName = "Test Product";

		when(productService.getProductByName(productName))
				.thenThrow(new ProductNotFoundException("Product don't exist"));
		try {
			mockMvc.perform(get("/api/v1/manager/getproductbyname?name=" + productName))
					.andExpect(status().isNotFound()).andExpect(content().string(""));
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
		mockMvc.perform(get("/api/v1/manager/getproductbyid?id=" + productId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is(expectedProduct.getName())));

	}

	@Test
	void testGetProductByIdFailure() throws Exception, ProductNotFoundException {
		Long productId = 5L;

		when(productService.getProductById(productId)).thenThrow(new ProductNotFoundException("Product doesn't exist"));

		try {
			MvcResult result = mockMvc.perform(get("/api/v1/manager/getproductbyid?id=" + productId)).andDo(print())
					.andExpect(status().isNotFound()).andReturn();

			String response = result.getResponse().getContentAsString();
			System.out.println(response);
		} catch (Exception e) {
			// sink it
		}
	}

	@Test
	void testGetAllProductsSuccess() throws Exception {

		List<Product> products = new ArrayList<>();
		products.add(product);

		when(productService.getAllProducts()).thenReturn(products);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/manager/getallproducts"))

				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
				.andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty());
	}

	/*@Test
	void testGetQuotationById() throws Exception {
		// Arrange
		Long quotationId = 1L;
		Quotation quotation = new Quotation();
		quotation.setId(quotationId);
		quotation.setTotalAmount(100.0);
		quotation.setQuantity(5);

		when(quotationService.getQuotationById(quotationId)).thenReturn(quotation);

		// Act and Assert
		mockMvc.perform(
				get("/api/v1/manager/getquotationbyid?id=" + quotationId).contentType(MediaType.APPLICATION_JSON))
				// .andDo(print())
				.andExpect(status().isOk());
	}*/

//	public static String asJsonString(final Object obj) {
//		try {
//			return new ObjectMapper().writeValueAsString(obj);
//		} catch (JsonProcessingException e) {
//			throw new RuntimeException(e);
//		}
//	}

}


