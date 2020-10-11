package com.codedude.springbootdynamodb.controller;

import com.codedude.springbootdynamodb.entity.Person;
import com.codedude.springbootdynamodb.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {
    @Autowired
    private PersonRepository personRepository;

    @PostMapping("/addPerson")
    public Person addPerson(@RequestBody Person person) {
        return personRepository.addPerson(person);
    }

    @GetMapping("/findPerson/{personId}")
    public Person findPerson(@PathVariable String personId) {
        return personRepository.findPersonByPersonId(personId);
    }

    @DeleteMapping("/deletePerson/{personId}")
    public String deletePerson(@PathVariable String personId) {
        Person person = personRepository.findPersonByPersonId(personId);
        return personRepository.deletePerson(person);
    }

    @PutMapping("/updatePerson")
    public String updatePerson(@RequestBody Person person) {
        return personRepository.updatePerson(person);
    }
}
