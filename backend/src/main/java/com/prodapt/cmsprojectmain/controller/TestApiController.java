package com.prodapt.cmsprojectmain.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin("*")
@Tag(name = " Test API")
@RequestMapping("/api/test")
public class TestApiController {

	@GetMapping("/all")
	public String allAccess() {
		return "Products fetched";
	}

	@GetMapping("/user")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/admin")
	public String adminAccess() {
		return "Admin Board.";
	}

	@GetMapping("/manager")
	public String managerAccess() {
		return "Manager Board.";
	}
}