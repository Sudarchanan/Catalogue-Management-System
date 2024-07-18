package com.prodapt.cmsprojectmain.dto;

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
public class QuotationParameterDTO {
	private String name;
	private String type;
	private String value;
}
