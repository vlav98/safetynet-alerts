package com.oc.safetynetalerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oc.safetynetalerts.model.FireStation;
import com.oc.safetynetalerts.service.FireStationService;
import com.oc.safetynetalerts.service.FireStationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
public class FireStationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService fireStationService;

    @Autowired
    private FireStationController fireStationController;

    @Autowired
    private ObjectMapper objectMapper;

    private final FireStation fireStation = new FireStation();

    @BeforeEach
    public void setUp() {
        fireStation.setAddress("1 rue Lafayette");
        fireStation.setStation(2);
    }

    @Test
    public void addFireStationTest() throws Exception {
        // GIVEN

        // WHEN
        Mockito.when(fireStationService.createFireStation(fireStation)).thenReturn(true);
        MvcResult mvcResult  = mockMvc.perform(post("/fireStation")
                        .content(objectMapper.writeValueAsString(fireStation))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isCreated()).andReturn();

        FireStation resultFireStation = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), FireStation.class);

        Mockito.verify(fireStationService).createFireStation(fireStation);
        Assertions.assertThat(resultFireStation).isNotNull().extracting(FireStation::getAddress).isEqualTo(fireStation.getAddress());
    }

    @Test
    public void updateFireStationByFullNameTest() throws Exception {
        // WHEN
        Mockito.when(fireStationService.updateFireStation(fireStation)).thenReturn(true);
        MvcResult mvcResult  = mockMvc.perform(patch("/fireStation")
                        .content(objectMapper.writeValueAsString(fireStation))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isOk()).andReturn();

        FireStation resultFireStation = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), FireStation.class);

        Mockito.verify(fireStationService).updateFireStation(fireStation);
        Assertions.assertThat(resultFireStation)
                .isNotNull()
                .extracting(FireStation::getAddress, FireStation::getStation)
                .containsExactly(fireStation.getAddress(), fireStation.getStation());
    }

    @Test
    public void deleteFireStationByFullNameTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/fireStation")
                        .content(objectMapper.writeValueAsString(fireStation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        Mockito.verify(fireStationService).deleteFireStation(fireStation);
    }
}
