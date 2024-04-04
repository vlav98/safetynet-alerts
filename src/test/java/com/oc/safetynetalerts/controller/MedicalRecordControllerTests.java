package com.oc.safetynetalerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oc.safetynetalerts.controller.MedicalRecordController;
import com.oc.safetynetalerts.model.MedicalRecord;
import com.oc.safetynetalerts.model.Person;
import com.oc.safetynetalerts.service.MedicalRecordService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerTests {
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private MedicalRecordService medicalRecordService;

    @Autowired
    private MedicalRecordController medicalRecordController;

    @Autowired
    private ObjectMapper objectMapper;

    private final MedicalRecord medicalRecord = new MedicalRecord();

    @BeforeEach
    public void setUp() {
        medicalRecord.setFirstName("John");
        medicalRecord.setLastName("Doe");
        medicalRecord.setBirthdate("01/01/1980");
        medicalRecord.setAllergies(List.of(""));
        medicalRecord.setMedications(Arrays.asList("ibupurin:200mg", "hydrapermazol:400mg"));
    }

    @Test
    public void addMedicalRecordTest() throws Exception {
        // GIVEN

        // WHEN
        Mockito.when(medicalRecordService.createMedicalRecord(medicalRecord)).thenReturn(true);
        MvcResult mvcResult  = mockMvc.perform(post("/medicalRecord")
                        .content(objectMapper.writeValueAsString(medicalRecord))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isCreated()).andReturn();

        MedicalRecord resultMedicalRecord = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), MedicalRecord.class);

        Mockito.verify(medicalRecordService).createMedicalRecord(medicalRecord);
        Assertions.assertThat(resultMedicalRecord).isNotNull().extracting(MedicalRecord::getFirstName, MedicalRecord::getLastName).containsExactly(medicalRecord.getFirstName(), medicalRecord.getLastName());
    }

    @Test
    void shouldThrowBadRequestExceptionOnAddition() throws Exception {
        // GIVEN

        // WHEN
        Mockito.when(medicalRecordService.createMedicalRecord(medicalRecord)).thenReturn(false);
        MvcResult mvcResult  = mockMvc.perform(post("/medicalRecord")
                        .content(objectMapper.writeValueAsString(medicalRecord))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isBadRequest()).andReturn();
        var message = mvcResult.getResponse().getContentAsString();
        Assertions.assertThat(message).isEqualTo("Can not add this medical record: this medical record you are trying to add is a duplicate.");
    }

    @Test
    public void updateMedicalRecordByFullNameTest() throws Exception {
        // WHEN
        Mockito.when(medicalRecordService.updateMedicalRecord(medicalRecord)).thenReturn(true);
        MvcResult mvcResult  = mockMvc.perform(patch("/medicalRecord")
                        .content(objectMapper.writeValueAsString(medicalRecord))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isOk()).andReturn();

        MedicalRecord resultMedicalRecord = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), MedicalRecord.class);

        Mockito.verify(medicalRecordService).updateMedicalRecord(medicalRecord);
        Assertions.assertThat(resultMedicalRecord)
                .isNotNull()
                .extracting(MedicalRecord::getFirstName, MedicalRecord::getLastName)
                .containsExactly(medicalRecord.getFirstName(), medicalRecord.getLastName());
    }

    @Test
    void shouldThrowBadRequestExceptionOnEdition() throws Exception {
        // GIVEN

        // WHEN
        Mockito.when(medicalRecordService.updateMedicalRecord(medicalRecord)).thenReturn(false);
        MvcResult mvcResult  = mockMvc.perform(patch("/medicalRecord")
                        .content(objectMapper.writeValueAsString(medicalRecord))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isBadRequest()).andReturn();
        var message = mvcResult.getResponse().getContentAsString();
        Assertions.assertThat(message).isEqualTo("Can not edit this medical record: the medical record you are trying to update doesn't exist.");
    }

    @Test
    public void deleteMedicalRecordByFullNameTest() throws Exception {
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/medicalRecord")
                        .content(objectMapper.writeValueAsString(medicalRecord))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        Mockito.verify(medicalRecordService).deleteMedicalRecord(medicalRecord);
    }


    @Test
    public void getAllMedicalRecords() throws Exception {
        // WHEN
        MvcResult mvcResult  = mockMvc.perform(get("/medicalRecord"))
                // THEN
                .andExpect(status().isOk()).andReturn();
    }
}
