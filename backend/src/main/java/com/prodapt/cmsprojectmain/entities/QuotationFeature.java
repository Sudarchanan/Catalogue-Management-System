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
@Table(name = "quotation_features")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuotationFeature {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String featureName;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "product_id")
	private QuotationProduct quotationProduct;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "feature_id")
	private List<QuotationParameter> quotationParameters = new ArrayList<>();
}
