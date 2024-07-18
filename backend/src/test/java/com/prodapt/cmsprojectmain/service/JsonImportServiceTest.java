package com.prodapt.cmsprojectmain.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodapt.cmsprojectmain.entities.Features;
import com.prodapt.cmsprojectmain.entities.Parameter;
import com.prodapt.cmsprojectmain.entities.Product;
import com.prodapt.cmsprojectmain.repositories.FeatureRepository;
import com.prodapt.cmsprojectmain.repositories.ParameterRepository;
import com.prodapt.cmsprojectmain.repositories.ProductRepository;

@SpringBootTest
public class JsonImportServiceTest {

	@Mock
	private ProductRepository productRepository;

	@Mock
	private FeatureRepository featureRepository;

	@Mock
	private ParameterRepository parameterRepository;

	@Mock
	private ObjectMapper objectMapper;

	@InjectMocks
	private JsonImportService jsonImportService;

	@Test
	void testImportJsonData_Success() throws IOException {
		// Sample JSON content
		String jsonContent = "{ \"products\": [ { \"name\": \"Product1\", \"features\": [ { \"name\": \"Feature1\", \"parameters\": [ { \"name\": \"Param1\", \"type\": \"String\", \"value\": \"Value1\" } ] } ] } ] }";

		// Prepare mock responses for successful parsing and saving
		Product product = new Product();
		product.setName("Product1");
		when(productRepository.save(any(Product.class))).thenReturn(product);

		Features feature = new Features();
		feature.setName("Feature1");
		feature.setProduct(product);
		when(featureRepository.save(any(Features.class))).thenReturn(feature);

		Parameter parameter = new Parameter();
		parameter.setName("Param1");
		parameter.setType("String");
		parameter.setValue("Value1");
		parameter.setFeature(feature);
		when(parameterRepository.save(any(Parameter.class))).thenReturn(parameter);

		// Stub objectMapper.readTree to return parsed JSON node
		ObjectMapper realObjectMapper = new ObjectMapper();
		when(objectMapper.readTree(jsonContent)).thenReturn(realObjectMapper.readTree(jsonContent));

		// Call the method to test
		jsonImportService.importJsonData(jsonContent);

		// Verify interactions with the repositories
		verify(productRepository, times(1)).save(any(Product.class));
		verify(featureRepository, times(1)).save(any(Features.class));
		verify(parameterRepository, times(1)).save(any(Parameter.class));
	}

}
