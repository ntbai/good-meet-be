package com.practice.goodmeet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.practice.goodmeet.bootstrap.web.CorrelationIdFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class GoodMeetApplicationTests {

  private final MockMvc mockMvc;

  @Autowired
  GoodMeetApplicationTests(MockMvc mockMvc) {
    this.mockMvc = mockMvc;
  }

  @Test
  void contextLoads() {}

  @Test
  void exposesHealthWithoutAuthentication() throws Exception {
    mockMvc
        .perform(get("/actuator/health"))
        .andExpect(status().isOk())
        .andExpect(header().exists(CorrelationIdFilter.HEADER_NAME));
  }

  @Test
  void protectsBusinessEndpoints() throws Exception {
    mockMvc
        .perform(get("/api/v1/meetings"))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.code").value("UNAUTHORIZED"))
        .andExpect(jsonPath("$.message").value("Authentication is required"))
        .andExpect(jsonPath("$.timestamp").exists())
        .andExpect(jsonPath("$.traceId").exists());
  }
}
