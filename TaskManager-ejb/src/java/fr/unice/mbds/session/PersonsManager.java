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
import javax.persistence.Query;

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
    
    public List<Person> createTestPersons(){
        List<Person> personList = new ArrayList();
        
        for(int i = 0; i < 80; i++){
            Person person = new Person("login" + i , "password" + i);
            personList.add(person);
            createPerson(person);
        }
        
        return personList;
    }
    
    public List<Person> findAll() {
        Query q = em.createQuery("select p from Person p");
        
        return q.getResultList();
    }
    
    public List<Person> findRange(int start, int nb) {
        Query q = em.createQuery("select p from Person p");
        q.setFirstResult(start);
        q.setMaxResults(nb);
        
        return q.getResultList();
    }
        
}
