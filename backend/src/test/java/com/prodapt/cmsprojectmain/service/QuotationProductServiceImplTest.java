package com.prodapt.cmsprojectmain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.prodapt.cmsprojectmain.entities.QuotationProduct;
import com.prodapt.cmsprojectmain.repositories.QuotationProductRepository;

public class QuotationProductServiceImplTest {

	@Mock
	private QuotationProductRepository quotationProductRepository;

	@InjectMocks
	private QuotationProductServiceImpl quotationProductService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testAddQuotationProduct() {
		// Create a sample QuotationProduct
		QuotationProduct quotationProduct = new QuotationProduct();
		quotationProduct.setId(1L);
		quotationProduct.setProductName("Product A");

		// Mock repository save method to return the quotation product with ID set
		when(quotationProductRepository.save(any(QuotationProduct.class))).thenReturn(quotationProduct);

		// Call the service method to add the quotation product
		QuotationProduct savedQuotationProduct = quotationProductService.addQuotationProduct(quotationProduct);

		// Verify the result
		assertNotNull(savedQuotationProduct);
		assertEquals(1L, savedQuotationProduct.getId());
		assertEquals("Product A", savedQuotationProduct.getProductName());

		// Verify that save method of repository was called once
		verify(quotationProductRepository, times(1)).save(any(QuotationProduct.class));
	}
}
