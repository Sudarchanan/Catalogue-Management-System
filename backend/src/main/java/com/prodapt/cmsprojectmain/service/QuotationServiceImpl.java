package com.prodapt.cmsprojectmain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.cmsprojectmain.dto.QuotationDTO;
import com.prodapt.cmsprojectmain.dto.QuotationFeatureDTO;
import com.prodapt.cmsprojectmain.dto.QuotationParameterDTO;
import com.prodapt.cmsprojectmain.dto.QuotationProductDTO;
import com.prodapt.cmsprojectmain.entities.Quotation;
import com.prodapt.cmsprojectmain.entities.QuotationFeature;
import com.prodapt.cmsprojectmain.entities.QuotationParameter;
import com.prodapt.cmsprojectmain.entities.QuotationProduct;
import com.prodapt.cmsprojectmain.repositories.QuotationRepository;

import jakarta.transaction.Transactional;

@Service
public class QuotationServiceImpl implements QuotationService {

	@Autowired
	private QuotationRepository quotationRepository;

	@Override
	public Quotation addQuotation(Quotation quotation)  {
		return quotationRepository.save(quotation);
	}

	@Override
	public List<Quotation> getAllQuotation() {
		List<Quotation> quotations = new ArrayList<>();
		quotationRepository.findAll().forEach(quotations::add);
		return quotations;
	}

	@Transactional
	public Quotation addQuotationDTO(QuotationDTO quotationDTO) {
		Quotation quotation = new Quotation();
		quotation.setCustomerName(quotationDTO.getCustomerName());
		quotation.setTotalAmount(quotationDTO.getTotalAmount());
		quotation.setDiscount(quotationDTO.getDiscount());
		quotation.setQuantity(quotationDTO.getQuantity());

		for (QuotationProductDTO productDTO : quotationDTO.getProducts()) {
			QuotationProduct product = new QuotationProduct();
			product.setProductName(productDTO.getProductName());
			product.setQuotation(quotation);
			quotation.getQuotationProduct().add(product);

			for (QuotationFeatureDTO featureDTO : productDTO.getFeatures()) {
				QuotationFeature feature = new QuotationFeature();
				feature.setFeatureName(featureDTO.getFeatureName());
				feature.setQuotationProduct(product);
				product.getQuotationFeatures().add(feature);

				for (QuotationParameterDTO parameterDTO : featureDTO.getParameters()) {
					QuotationParameter parameter = new QuotationParameter();
					parameter.setName(parameterDTO.getName());
					parameter.setType(parameterDTO.getType());
					parameter.setValue(parameterDTO.getValue());
					parameter.setQuotationFeature(feature);
					feature.getQuotationParameters().add(parameter);
				}
			}
		}

		return quotationRepository.save(quotation);
	}

}
