package com.college.visitorgatepass;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class GatePassFlowIntegrationTest {

    private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void healthEndpointIsPublic() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    void protectedEndpointRequiresToken() throws Exception {
        mockMvc.perform(get("/api/visitors")).andExpect(status().isUnauthorized());
    }

    @Test
    void loginWithWrongPasswordIsRejected() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"admin@college.edu\",\"password\":\"wrong-password\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void fullGatePassLifecycle() throws Exception {
        String adminToken = login("admin@college.edu");
        String securityToken = login("security@college.edu");

        long hostId = objectMapper.readTree(mockMvc.perform(get("/api/users/hosts").header("Authorization", adminToken))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString())
                .get(0).get("id").asLong();

        String body = """
                {
                  "visitor": {"fullName":"Ravi Kumar","phone":"9876543210","idProofType":"Aadhaar",
                              "idProofNumber":"1234-5678-9012"},
                  "hostId": %d,
                  "purpose": "Project discussion",
                  "expectedEntry": "%s",
                  "expectedExit": "%s",
                  "numberOfVisitors": 2
                }
                """.formatted(hostId, LocalDateTime.now().plusHours(1).format(ISO),
                LocalDateTime.now().plusHours(4).format(ISO));

        JsonNode created = objectMapper.readTree(mockMvc.perform(post("/api/gate-passes")
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andReturn().getResponse().getContentAsString());

        long passId = created.get("id").asLong();
        String passCode = created.get("passCode").asText();
        assertThat(passCode).startsWith("VP-");

        mockMvc.perform(post("/api/gate-passes/" + passId + "/approve")
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"remarks\":\"Approved by admin\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("APPROVED"));

        mockMvc.perform(post("/api/gate-passes/code/" + passCode + "/check-in")
                        .header("Authorization", securityToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CHECKED_IN"))
                .andExpect(jsonPath("$.checkInTime").isNotEmpty());

        mockMvc.perform(post("/api/gate-passes/code/" + passCode + "/check-in")
                        .header("Authorization", securityToken))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/api/gate-passes/code/" + passCode + "/check-out")
                        .header("Authorization", securityToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CHECKED_OUT"));

        mockMvc.perform(get("/api/dashboard/stats").header("Authorization", adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPasses").value(1));
    }

    @Test
    void securityUserCannotApprovePasses() throws Exception {
        String securityToken = login("security@college.edu");
        mockMvc.perform(post("/api/gate-passes/1/approve").header("Authorization", securityToken))
                .andExpect(status().isForbidden());
    }

    private String login(String email) throws Exception {
        String response = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"%s\",\"password\":\"admin123\"}".formatted(email)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        return "Bearer " + objectMapper.readTree(response).get("token").asText();
    }
}
