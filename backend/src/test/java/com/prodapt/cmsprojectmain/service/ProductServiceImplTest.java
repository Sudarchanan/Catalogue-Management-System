package com.prodapt.cmsprojectmain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.prodapt.cmsprojectmain.entities.Features;
import com.prodapt.cmsprojectmain.entities.Parameter;
import com.prodapt.cmsprojectmain.entities.Product;
import com.prodapt.cmsprojectmain.exceptions.ProductNotFoundException;
import com.prodapt.cmsprojectmain.repositories.ProductRepository;
import com.prodapt.cmsprojectmain.utility.QUERYMAPPER;

@SpringBootTest
public class ProductServiceImplTest {
//	private static final Logger loggers = LoggerFactory.getLogger(ParameterServiceImpl.class);

	@Mock
	private ProductRepository repo;

	@InjectMocks
	private ProductServiceImpl productService;

	private Product product;
	private Product existingProduct;
    private Product updatedProduct;

	@BeforeEach
	public void setup() {

		product = new Product();
		product.setId(1L);
		product.setName("Test Product");
		
		existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setName("Existing Product");

        Features existingFeature = new Features();
        existingFeature.setId(1L);
        existingFeature.setName("Existing Feature");
        existingFeature.setProduct(existingProduct);

        Parameter existingParameter = new Parameter();
        existingParameter.setId(1L);
        existingParameter.setName("Existing Parameter");
        existingParameter.setType("String");
        existingParameter.setValue("Value");
        existingParameter.setFeature(existingFeature);

        existingFeature.getParameters().add(existingParameter);
        existingProduct.getFeatures().add(existingFeature);

        updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setName("Updated Product");

        Features updatedFeature = new Features();
        updatedFeature.setId(2L);
        updatedFeature.setName("Updated Feature");
        updatedFeature.setProduct(updatedProduct);

        Parameter updatedParameter = new Parameter();
        updatedParameter.setId(2L);
        updatedParameter.setName("Updated Parameter");
        updatedParameter.setType("Integer");
        updatedParameter.setValue("100");
        updatedParameter.setFeature(updatedFeature);

        updatedFeature.getParameters().add(updatedParameter);
        updatedProduct.getFeatures().add(updatedFeature);

	}

	@Test
	void testCreateProduct() {

		when(repo.save(product)).thenReturn(product);

		Product result = productService.createproduct(product);

		assertNotNull(product);
		assertEquals(product, result);
	}

	@Test
	void testGetProductById() throws ProductNotFoundException {

		// Arrange
		when(repo.findById(1L)).thenReturn(Optional.of(product));

		// Act
		Product result = productService.getProductById(1L);

		// Assert
		assertNotNull(product);
		assertEquals(product, result);

	}

	@Test
	public void testGetProductByIdNotFound() {
		// Arrange
		when(repo.findById(1L)).thenReturn(Optional.empty());

		// Act and Assert
		assertThrows(ProductNotFoundException.class, () -> productService.getProductById(1L));
	}

	@Test
	public void testGetProductByName() throws ProductNotFoundException {

		// Arrange
		when(repo.findByName("Test Product")).thenReturn(Optional.of(product));

		// Act
		Product result = productService.getProductByName("Test Product");

		// Assert
		assertNotNull(product);
		assertEquals(product, result);

	}

	@Test
	public void testGetProductByNameNotFound() throws ProductNotFoundException {
		if (productService != null) {
			// Arrange
			when(repo.findByName("NoProduct")).thenReturn(Optional.empty());

			// Act
			assertThrows(ProductNotFoundException.class, () -> productService.getProductByName("NoProduct"));
		}
	}

	@Test
	public void testGetAllProducts() throws ProductNotFoundException {

		// Arrange
		List<Product> products = new ArrayList<>();
		products.add(product);

		when(repo.findAll()).thenReturn(products);

		// Act
		List<Product> result = productService.getAllProducts();

		// Assert
		assertNotNull(product);
		assertEquals(products, result);

	}
	@Test
	public void testGetAllProductsFailure() throws ProductNotFoundException {

	    // Arrange
	    List<Product> products = new ArrayList<>();

	    // Mocking the scenario where the repository returns an empty list
	    when(repo.findAll()).thenReturn(products);

	    // Act
	    List<Product> result = productService.getAllProducts();

	    // Assert
	    assertNotNull(result);
	    assertTrue(result.isEmpty()); // Assert that the result is an empty list
	}


	@Test
	public void testUpdateProductFailure() {
	    // Arrange
	    when(repo.findById(1L)).thenReturn(Optional.empty());

	    // Act and Assert
	    assertNotNull(product);
	    assertThrows(ProductNotFoundException.class, () -> {
	        productService.updateProduct(1L, product);
	    }, "Product not found with id: 1");

	    
	}
	

    /*
	@Test
	void testUpdateProductSuccess() throws ProductNotFoundException {

		Product existingProduct = product;
		existingProduct.setId(2L);
		when(repo.findById(2L)).thenReturn(Optional.of(existingProduct));

		Product updatedProduct = new Product();
		updatedProduct.setId(2L);
		updatedProduct.setName("UpdatedProduct");

		productService.updateProduct(2L, updatedProduct);
		verify(repo).save(updatedProduct);
	}
	*/
	@Test
	public void testDeleteSuccess() throws ProductNotFoundException {

		// Arrange
		when(repo.findById(1L)).thenReturn(Optional.of(product));

		// Act
		String result = productService.deleteProductid(1L);

		// Assert
		assertNotNull(product);
		assertEquals(QUERYMAPPER.RECORD_DELETED_SUCCESSFULLY, result);
	}

	@Test
	public void testDeleteProductFailure() throws ProductNotFoundException {

		// Arrange
		when(repo.findById(1L)).thenReturn(Optional.empty());

		// Act
		assertNotNull(product);
		assertThrows(ProductNotFoundException.class, () -> productService.deleteProductid(1L));

	}
	
	@Test
    void testUpdateProduct() throws ProductNotFoundException {
        // Arrange
        when(repo.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(repo.save(any(Product.class))).thenReturn(updatedProduct);

        // Act
        Product result = productService.updateProduct(1L, updatedProduct);

        // Assert
        verify(repo, times(1)).findById(1L);
        verify(repo, times(1)).save(any(Product.class));

        assertEquals("Updated Product", result.getName());
        assertEquals(1, result.getFeatures().size());
        assertEquals("Updated Feature", result.getFeatures().get(0).getName());
        assertEquals(1, result.getFeatures().get(0).getParameters().size());
        assertEquals("Updated Parameter", result.getFeatures().get(0).getParameters().get(0).getName());
        assertEquals("Integer", result.getFeatures().get(0).getParameters().get(0).getType());
        assertEquals("100", result.getFeatures().get(0).getParameters().get(0).getValue());
    }
}
