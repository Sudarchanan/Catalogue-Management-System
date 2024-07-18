package com.prodapt.cmsprojectmain.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "quotation_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuotationProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String productName;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "quotation_id")
	private Quotation quotation;


	@OneToMany(mappedBy ="quotationProduct",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<QuotationFeature> quotationFeatures = new ArrayList<>();
}
