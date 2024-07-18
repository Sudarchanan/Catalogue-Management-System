package com.prodapt.cmsprojectmain.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.prodapt.cmsprojectmain.entities.QuotationProduct;

@Repository
public interface QuotationProductRepository extends CrudRepository<QuotationProduct, Long> {

}
