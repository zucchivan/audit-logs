package com.zucchivan.auditlogs;

import com.zucchivan.auditlogs.data.configuration.MongoConfiguration;
import com.zucchivan.auditlogs.data.repository.LogRepository;
import com.zucchivan.auditlogs.model.Log;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Import(MongoConfiguration.class)
class AuditLogsApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private LogRepository logRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before()
	public void setup()	{
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Before
	public void deleteAllBeforeTests() throws Exception {
		logRepository.deleteAll();
	}

	@Test
	public void should_haveRepositoryIndex_whenHomeIndexIsPrompted() throws Exception {
		mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk()).andExpect(
				jsonPath("$._links."+ LogRepository.COLLECTION_NAME).exists());
	}

	@Test
	public void should_createEntity_whenPostEntityRequested() throws Exception {
		mockMvc.perform(post("/" + LogRepository.COLLECTION_NAME).content(
				"{\"dateCreated\": \"2019-10-10\", \"userId\":\"9a8ds92241bC\", " +
						"\"data\": \"testdata\"}")).andExpect(status().isCreated())
				.andExpect(header().string("Location", containsString(
						LogRepository.COLLECTION_NAME)));
	}

	@Test
	public void should_retrieveEntity_whenFindAllRequested() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/" + LogRepository.COLLECTION_NAME)
				.content("{\"dateCreated\": \"2019-10-10\", \"userId\":\"9a8ds92241bC\", " +
						"\"data\": \"testdata\"}")).andExpect(status().isCreated()).andReturn();

		String location = mvcResult.getResponse().getHeader("Location");
		mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
				jsonPath("$.userId").value("9a8ds92241bC")).andExpect(
				jsonPath("$.data").value("testdata"));
	}

	@Test
	public void should_retrieveEntity_whenFindByIdRequested() throws Exception {

		mockMvc.perform(post("/" + LogRepository.COLLECTION_NAME)
				.content("{\"dateCreated\": \"2019-10-10\", \"userId\":\"9a8ds92241bC\", " +
						"\"data\": \"testdata\"}")).andExpect(status().isCreated());

		mockMvc.perform(get("/"+ LogRepository.COLLECTION_NAME +
						"/search/findByUserId?userId={userId}", "9a8ds92241bC"))
				.andExpect(status().isOk());
	}

	// TODO: Kafka tests
}
