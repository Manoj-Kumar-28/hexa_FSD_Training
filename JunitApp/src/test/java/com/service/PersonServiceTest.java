package com.service;

import com.exception.InvalidPersonException;
import com.model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PersonServiceTest {
    private PersonService personService;

    @BeforeEach
    public void registerPersonsTest(){
        personService=new PersonService();

    }

    @AfterEach
    public void close(){
        personService=null;
    }

    @Test
    public void countNumberOfPersonsTest(){
        List<Person> list=new ArrayList<>();

        Person p1=new Person(1,"Manoj",22,"Nellore");
        Person p2=new Person(2,"Varun",19,"Chittor");
        list.add(p1);
        list.add(p2);

        List<Person> list1=new ArrayList<>();
        list1.add(p1);

        List<Person> list2=null;

        Assertions.assertEquals(2,personService.countNumberOfPersons(list));

        Assertions.assertEquals(1,personService.countNumberOfPersons(list1));

        try{
            Assertions.assertEquals(0,personService.countNumberOfPersons(list2));
        }catch(RuntimeException e){
            Assertions.assertEquals("List cannot be null",e.getMessage());
        }
    }

    @Test
    public void validatePersonTest(){


        //checking for null
        NullPointerException e=Assertions.assertThrows(NullPointerException.class,
                ()->personService.validatePerson(null));

        //checking for message

        Assertions.assertEquals("Person ref cannot be null",e.getMessage());

        Person p1=new Person(1,"M",22,"Nellore");
        //checking for the length
        InvalidPersonException e1=Assertions.assertThrows(InvalidPersonException.class,
                ()->personService.validatePerson(p1));

        //checking for message
        Assertions.assertEquals("Length must be greater than 1",e1.getMessage());

        Person p2=new Person(1,"Manoj",16,"Nellore");
        //checking for age
        InvalidPersonException e2=Assertions.assertThrows(InvalidPersonException.class,
                ()->personService.validatePerson(p2));

        Person p3=new Person(1,"Kumar",20,"Nellore");
        Assertions.assertDoesNotThrow(()->personService.validatePerson(p3));

        //checking for message
        Assertions.assertEquals("Age must be greater than 18",e2.getMessage());
    }

    @Test
    public void getAdultPersonCountTest(){


        //Size of the list of adult Persons
        //Assertions.assertEquals(2,personService.registerPersons().stream().filter(person -> person.getAge()>18).toList().size());
        Assertions.assertEquals(2,personService.getAdultPersons()
                .size());

        //checking if city exists -exception
        boolean status=personService.registerPersons().stream().anyMatch(person -> person.getCity().equals("mumbai"));
        Assertions.assertTrue(status);

        boolean status2=personService.registerPersons().stream().anyMatch(person -> person.getAge()<18);
        Assertions.assertTrue(status2);
        close();
    }



}
