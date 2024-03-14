package com.oc.safetynetalerts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oc.safetynetalerts.controller.PersonController;
import com.oc.safetynetalerts.model.Person;
import com.oc.safetynetalerts.repository.PersonRepository;
import com.oc.safetynetalerts.service.PersonService;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTests {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private PersonRepository personRepository;

    @Autowired
    private PersonController personController;


    @Test
    public void addPersonTest() throws Exception {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setAddress("123 Main St");
        person.setCity("Anytown");
        person.setZip("12345");
        person.setEmail("john.doe@example.com");
        person.setPhone("0102030405");

        Mockito.when(personRepository.save(person)).thenReturn(person);
        Person createdPerson = personController.create(person);
        assertNotNull(createdPerson); // Assert the controller returns a person
        Mockito.verify(personRepository).save(person); // Assert the repository is called with the person object

        Person mockPerson = new Person();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/person")
                        .content(new ObjectMapper().writeValueAsString(mockPerson))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updatePersonTest() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders
//                    .patch("/person")
//                    .content(new ObjectMapper().writeValueAsString(new Person()))
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
    }

    @Test
    public void deletePersonTest() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders
//                    .delete("/person")
//                    .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
    }
}
