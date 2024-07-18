package com.prodapt.cmsprojectmain.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
public class TestApiControllerTest {

	private MockMvc mvc;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.standaloneSetup(new TestApiController()).build();
	}
	

	@Test
	public void testAllAccess() throws Exception {
		MvcResult result = mvc.perform(get("/api/test/all")).andExpect(status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		assertEquals("Products fetched", content);
	}

	@Test
	public void testUserAccess() throws Exception {
		MvcResult result = mvc.perform(get("/api/test/user")).andExpect(status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		assertEquals("User Content.", content);
	}

	@Test
	public void testAdminAccess() throws Exception {
		MvcResult result = mvc.perform(get("/api/test/admin")).andExpect(status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		assertEquals("Admin Board.", content);
	}

	@Test
	public void testManagerAccess() throws Exception {
		MvcResult result = mvc.perform(get("/api/test/manager")).andExpect(status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		assertEquals("Manager Board.", content);
	}
}
