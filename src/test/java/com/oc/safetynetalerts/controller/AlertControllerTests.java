package com.oc.safetynetalerts.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AlertControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getChildAlertListTest() throws Exception {
        // WHEN
        MvcResult mvcResult = mockMvc.perform(
                get("/childAlert")
                        .param("address", "address")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json")).andReturn();
    }

    @Test
    public void getPhoneAlertListTest() throws Exception {
        // WHEN
        MvcResult mvcResult = mockMvc.perform(
                    get("/phoneAlert")
                            .param("stationNumber", "2")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json")).andReturn();
    }

    @Test
    public void getResidentListTest() throws Exception {
        // WHEN
        MvcResult mvcResult = mockMvc.perform(
                        get("/fire")
                                .param("address", "")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json")).andReturn();
    }

    @Test
    public void getHouseholdsListTest() throws Exception {
        // WHEN
        MvcResult mvcResult = mockMvc.perform(
                        get("/flood/stations")
                                .param("stations", "1,2")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json")).andReturn();
    }

    @Test
    public void getPersonInfoTest() throws Exception {
        // WHEN
        MvcResult mvcResult = mockMvc.perform(
                        get("/personInfo")
                                .param("lastName", "Boyd")
                                .param("firstName", "John")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json")).andReturn();
    }

    @Test
    public void getCommunityEmailListTest() throws Exception {
        // WHEN
        MvcResult mvcResult = mockMvc.perform(
                        get("/communityEmail")
                                .param("city", "Paris")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json")).andReturn();
    }
}
