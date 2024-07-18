package com.prodapt.cmsprojectmain.service;

import java.util.List;

import com.prodapt.cmsprojectmain.dto.QuotationDTO;
import com.prodapt.cmsprojectmain.entities.Quotation;

public interface QuotationService {

	public Quotation addQuotation(Quotation quotation);

	public Quotation addQuotationDTO(QuotationDTO quotationDTO);

	public List<Quotation> getAllQuotation();

}