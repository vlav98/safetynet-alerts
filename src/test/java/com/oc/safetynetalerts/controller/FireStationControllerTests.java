package com.oc.safetynetalerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oc.safetynetalerts.model.FireStation;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    void shouldThrowBadRequestExceptionOnAddition() throws Exception {
        // GIVEN

        // WHEN
        Mockito.when(fireStationService.createFireStation(fireStation)).thenReturn(false);
        MvcResult mvcResult  = mockMvc.perform(post("/fireStation")
                        .content(objectMapper.writeValueAsString(fireStation))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isBadRequest()).andReturn();
        var message = mvcResult.getResponse().getContentAsString();
        Assertions.assertThat(message).isEqualTo("Can not add this fire station: this fire station you are trying to add is a duplicate.");
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
    void shouldThrowBadRequestExceptionOnEdition() throws Exception {
        // GIVEN

        // WHEN
        Mockito.when(fireStationService.createFireStation(fireStation)).thenReturn(false);
        MvcResult mvcResult  = mockMvc.perform(patch("/fireStation")
                        .content(objectMapper.writeValueAsString(fireStation))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isBadRequest()).andReturn();
        var message = mvcResult.getResponse().getContentAsString();
        Assertions.assertThat(message).isEqualTo("Can not edit this fire station: this fire station you are trying to update doesn't exist.");
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


    @Test
    public void getAllFireStation() throws Exception {
        // WHEN
        MvcResult mvcResult  = mockMvc.perform(get("/fireStation"))
                // THEN
                .andExpect(status().isOk()).andReturn();
    }
}
