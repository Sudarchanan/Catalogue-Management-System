package com.prodapt.cmsprojectmain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuotationDTO {
	private String customerName;
	private Double totalAmount;
	private Double discount;
	private Integer quantity;
	private List<QuotationProductDTO> products;

}