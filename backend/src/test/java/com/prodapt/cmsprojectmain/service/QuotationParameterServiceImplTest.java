package com.prodapt.cmsprojectmain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.prodapt.cmsprojectmain.entities.QuotationParameter;
import com.prodapt.cmsprojectmain.repositories.QuotationParameterRepository;

@SpringBootTest
public class QuotationParameterServiceImplTest {
	@Mock
	private QuotationParameterRepository repo;

	@InjectMocks
	private QuotationParameterServiceImpl service;

	private QuotationParameter quotationParameter;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		quotationParameter = new QuotationParameter();
		quotationParameter.setId(1L);
		quotationParameter.setName("Test Parameter");
		quotationParameter.setType("Type1");
		quotationParameter.setValue("Value1");
	}

	@Test
	public void testAddQuotationParameter() {
		when(repo.save(quotationParameter)).thenReturn(quotationParameter);

		QuotationParameter savedQuotationParameter = service.addQuotationParameter(quotationParameter);

		assertEquals(quotationParameter, savedQuotationParameter);
	}

	@Test
	public void testAddQuotationParameterSuccess() {
		when(repo.save(quotationParameter)).thenReturn(quotationParameter);

		QuotationParameter savedQuotationParameter = service.addQuotationParameter(quotationParameter);

		assertEquals(quotationParameter, savedQuotationParameter);
	}

	@Test
	public void testAddQuotationParameterNullParameter() {
		assertThrows(IllegalArgumentException.class, () -> service.addQuotationParameter(null));
	}

	@Test
	public void testAddQuotationParameterRepositoryFailure() {
		when(repo.save(quotationParameter)).thenThrow(RuntimeException.class);

		assertThrows(RuntimeException.class, () -> service.addQuotationParameter(quotationParameter));
	}
}
