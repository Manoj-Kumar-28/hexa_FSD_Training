package com.service;

import com.exception.InvalidPersonException;
import com.model.Person;

import java.util.List;

public class PersonService {
    List<Person> list;
    {
        list=registerPersons();
    }
    public int countNumberOfPersons(List<Person> list){
        if(list==null)
            throw new RuntimeException("List cannot be null");

        return list.size();
    }

    public void validatePerson(Person person){
        if(person==null){
            throw new NullPointerException("Person ref cannot be null");
        }
        if(person.getName().length()<2){
            throw new InvalidPersonException("Length must be greater than 1");
        }
        if(person.getAge()<=18){
            throw new InvalidPersonException("Age must be greater than 18");
        }
    }

    public List<Person> registerPersons(){
        Person p1=new Person(1,"Manoj",22,"chennai");
        Person p2=new Person(2,"Kumar",24,"mumbai");
        Person p3=new Person(3,"Varun",16,"pune");

        return List.of(p1,p2,p3);
    }

    public List<Person> getAdultPersons(){
        return list.stream()
                .filter(person -> person.getAge()>18)
                .toList();
    }
}
