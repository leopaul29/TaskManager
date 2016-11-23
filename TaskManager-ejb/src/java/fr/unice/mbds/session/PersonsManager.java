/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.mbds.session;

import fr.unice.mbds.entities.Person;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 53js-Seb
 */
@Stateless
@LocalBean
public class PersonsManager {
    @PersistenceContext(unitName = "TaskManager-ejbPU")
    private EntityManager em;

    public void createPerson(Person person) {
        em.persist(person);
    }
    
    public List<Person> createTestsPersons(){
        List<Person> personList = new ArrayList();
        
        for(int i = 0; i < 80; i++){
            Person person = new Person("login" + i , "password" + i);
            personList.add(person);
            createPerson(person);
        }
        
        return personList;
    }
    
}
