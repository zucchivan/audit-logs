package com.zucchivan.auditlogs;

import com.zucchivan.auditlogs.data.repository.LogRepository;
import com.zucchivan.auditlogs.model.Log;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuditLogsApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private LogRepository logRepository;

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
				"{\"dateCreated\": \"22-Oct-2019\", \"userId\":\"9a8ds92241bC\", " +
						"\"data\": \"testdata\"}")).andExpect(status().isCreated())
				.andExpect(header().string("Location", containsString(
						LogRepository.COLLECTION_NAME)));
	}

	@Test
	public void should_retrieveEntity_whenFindAllRequested() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/" + LogRepository.COLLECTION_NAME)
				.content("{\"dateCreated\": \"22-Oct-2019\", \"userId\":\"9a8ds92241bC\", " +
						"\"data\": \"testdata\"}")).andExpect(status().isCreated()).andReturn();

		String location = mvcResult.getResponse().getHeader("Location");
		mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
				jsonPath("$.userId").value("9a8ds92241bC")).andExpect(
				jsonPath("$.data").value("testdata"));
	}

	@Test
	public void should_retrieveEntity_whenFindByIdRequested() throws Exception {

		mockMvc.perform(post("/" + LogRepository.COLLECTION_NAME)
				.content("{\"dateCreated\": \"22-Oct-2019\", \"userId\":\"9a8ds92241bC\", " +
						"\"data\": \"testdata\"}")).andExpect(status().isCreated());

		mockMvc.perform(get("/"+ LogRepository.COLLECTION_NAME +
						"/search/findByUserId?userId={userId}", "9a8ds92241bC"))
				.andExpect(status().isOk());
	}

}
