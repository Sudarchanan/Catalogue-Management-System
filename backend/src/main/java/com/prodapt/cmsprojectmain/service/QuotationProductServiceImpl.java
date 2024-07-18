package com.prodapt.cmsprojectmain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.cmsprojectmain.entities.QuotationProduct;
import com.prodapt.cmsprojectmain.repositories.QuotationProductRepository;


@Service
public class QuotationProductServiceImpl implements QuotationProductServices {

	@Autowired
	private QuotationProductRepository repo;
	
	@Override
	public QuotationProduct addQuotationProduct(QuotationProduct quotationProduct) {
		return repo.save(quotationProduct);
	}

}
