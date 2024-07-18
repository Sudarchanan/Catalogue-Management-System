package com.prodapt.cmsprojectmain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.prodapt.cmsprojectmain.entities.QuotationFeature;
import com.prodapt.cmsprojectmain.repositories.QuotationFeatureRepository;

@SpringBootTest
public class QuotationFeatureServiceImplTest {
	@Mock
	private QuotationFeatureRepository repo;

	@InjectMocks
	private QuotationFeatureServiceImpl service;

	private QuotationFeature quotationFeature;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		quotationFeature = new QuotationFeature();
		quotationFeature.setId(1L);
		quotationFeature.setFeatureName("Test Feature");
		// Set other fields as needed
	}

	@Test
	public void testAddQuotationFeatureSuccess() {
		when(repo.save(quotationFeature)).thenReturn(quotationFeature);

		QuotationFeature savedQuotationFeature = service.addQuotationFeature(quotationFeature);

		assertNotNull(savedQuotationFeature);
		assertEquals(quotationFeature.getId(), savedQuotationFeature.getId());
		assertEquals(quotationFeature.getFeatureName(), savedQuotationFeature.getFeatureName());
		// Add more assertions for other fields as needed
	}

	@Test
	public void testAddQuotationFeatureNullFeature() {
		assertThrows(IllegalArgumentException.class, () -> service.addQuotationFeature(null));
	}

}
