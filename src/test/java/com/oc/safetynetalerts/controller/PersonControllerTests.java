package com.oc.safetynetalerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oc.safetynetalerts.controller.PersonController;
import com.oc.safetynetalerts.model.Person;
import com.oc.safetynetalerts.repository.PersonRepository;
import com.oc.safetynetalerts.service.PersonService;
import org.apache.coyote.BadRequestException;
import org.assertj.core.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PersonController personController;

    private final Person person = new Person();

    @BeforeEach
    public void setUp() {
        // GIVEN
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setAddress("123 Main St");
        person.setCity("Anytown");
        person.setZip("12345");
        person.setEmail("john.doe@example.com");
        person.setPhone("0102030405");
    }

    @Test
    public void addPersonTest() throws Exception {
        // GIVEN

        // WHEN
        Mockito.when(personService.createPerson(person)).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(post("/person")
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isCreated()).andReturn();

        Person resultPerson = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Person.class);

        Mockito.verify(personService).createPerson(person); // Assert the repository is called with the person object
        Assertions.assertThat(resultPerson).isNotNull().extracting(Person::getFirstName, Person::getLastName).containsExactly(person.getFirstName(), person.getLastName());
    }

    @Test
    public void addDuplicatePersonTest() throws Exception {

        // WHEN
        Mockito.when(personService.createPerson(person)).thenReturn(false);
        Exception exception = assertThrows(BadRequestException.class, () -> personController.create(person));
        assertEquals(exception.getClass(), BadRequestException.class);
//        mockMvc.perform(post("/person")
//                        .content(objectMapper.writeValueAsString(person))
//                        .contentType(MediaType.APPLICATION_JSON))
//                // THEN
//                .andExpect(status().isBadRequest()).andExpect(result -> assertInstanceOf(BadRequestException.class, result.getResolvedException()));
    }

    @Test
    public void updatePersonTest() throws Exception {
        Mockito.when(personService.updatePerson(person)).thenReturn(person);
        Person updatedPerson = personController.update(person);

        Mockito.verify(personService).updatePerson(person);
        Assertions.assertThat(updatedPerson)
                .isNotNull()
                .extracting(Person::getFirstName, Person::getLastName)
                .containsExactly(person.getFirstName(), person.getLastName());
    }

    @Test
    public void deletePersonTest() throws Exception {
        personController.delete(person);

        Mockito.verify(personService).deletePerson(person);
//        mockMvc.perform(MockMvcRequestBuilders
//                    .delete("/person")
//                    .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
    }
}
