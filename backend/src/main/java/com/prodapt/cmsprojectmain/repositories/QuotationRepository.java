package com.prodapt.cmsprojectmain.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.prodapt.cmsprojectmain.entities.Quotation;

@Repository
public interface QuotationRepository extends CrudRepository<Quotation, Long> {
}
