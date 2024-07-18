package com.prodapt.cmsprojectmain.service;

import java.io.IOException;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodapt.cmsprojectmain.entities.Features;
import com.prodapt.cmsprojectmain.entities.Parameter;
import com.prodapt.cmsprojectmain.entities.Product;
import com.prodapt.cmsprojectmain.repositories.FeatureRepository;
import com.prodapt.cmsprojectmain.repositories.ParameterRepository;
import com.prodapt.cmsprojectmain.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JsonImportService {
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private FeatureRepository featureRepository;
	@Autowired
	private ParameterRepository parameterRepository;

	public void importJsonData(String jsonContent)  throws IOException{
		
			// Parse JSON content
			JsonNode root = objectMapper.readTree(jsonContent);
			JsonNode products = root.path("products");
			Iterator<JsonNode> elements = products.elements();

			while (elements.hasNext()) {
				JsonNode product = elements.next();
				String productName = product.path("name").asText();
				JsonNode features = product.path("features");

				// Create and save the product
				Product savedProduct = saveProduct(productName);

				for (JsonNode feature : features) {
					String featureName = feature.path("name").asText();
					JsonNode parameters = feature.path("parameters");

					// Create and save the feature
					Features savedFeature = saveFeature(featureName, savedProduct);

					for (JsonNode parameter : parameters) {
						String paramName = parameter.path("name").asText();
						String paramType = parameter.path("type").asText();
						String paramValue = parameter.path("value").asText();

						// Create and save the parameter
						saveParameter(paramName, paramType, paramValue, savedFeature);
					}
				}
			}
	}

	private Product saveProduct(String name) {
		Product product = new Product();
		product.setName(name);
		return productRepository.save(product);
	}

	private Features saveFeature(String name, Product product) {
		Features feature = new Features();
		feature.setName(name);
		feature.setProduct(product);
		return featureRepository.save(feature);
	}

	private Parameter saveParameter(String name, String type, String value, Features feature) {
		Parameter parameter = new Parameter();
		parameter.setName(name);
		parameter.setType(type);
		parameter.setValue(value);
		parameter.setFeature(feature);
		return parameterRepository.save(parameter);
	}
}
