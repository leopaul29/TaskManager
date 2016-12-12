/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.mbds.session;

import fr.unice.mbds.entities.Person;
import fr.unice.mbds.entities.Task;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

    public void createPerson(String login, String password, String firstname, String lastname) {
        Person p = new Person(login, password, firstname, lastname);
        createPerson(p);
    }

    public void createPerson(Person person) {
        em.persist(person);
    }

    public Person addTaskToPerson(Person person, Task task) throws Exception {
        Person p = em.merge(person);
        p.addTask(task);
        return p;
    }

    public Person removeTaskToPerson(Person person, Task task) throws Exception {
        Person p = em.merge(person);
        p.removeTask(task);
        return p;
    }

    public List<Person> createTestsPersons() {
        List<Person> personList = new ArrayList();

        for (int i = 0; i < 80; i++) {
            Person person = new Person("login" + i, "password" + i, "firstname" + i, "lastname" + i);
            personList.add(person);
            createPerson(person);
        }

        return personList;
    }

    public List<Person> findAll() {
        Query q = em.createQuery("select p from Person p");

        return q.getResultList();
    }

    public List<Person> findRange(int start, int nb, String nomColonne, Map<String, Object> filters, String so) {
        System.out.println("PM nom col=" + nomColonne + " - sortOrder=" + so);
        if (nomColonne == null) {
            nomColonne = "id";
        }
        if (so.equals("ASCENDING")) {
            so = "ASC";
        } else {
            so = "DESC";
        }

        String req = "select p from Person p ";
        
        //Filter
        if(filters != null && !filters.isEmpty()){
            req += " where ";
            Iterator<String> i = filters.keySet().iterator();
            
            //first
            String key = i.next();
            req += " p." + key + " like '%" + filters.get(key) + "%' ";
            
            //other element
            while(i.hasNext()){
                key = i.next();
                req += " and p." + key + " like '%" + filters.get(key) + "%' ";
            }
        }
        
        //Order by
        req += " order by p." + nomColonne + " " + so;
        
        System.out.println("req : " + req);
        Query q = em.createQuery(req);
        q.setFirstResult(start);
        q.setMaxResults(nb);

        return q.getResultList();
    }

    public int count() {
        Query q = em.createQuery("select count(p) from Person p");
        Number n = (Number) q.getSingleResult();
        return n.intValue();
    }

    public void removePerson(Person person) throws Exception {
        Person p = em.merge(person);
        em.remove(p);
    }
    
    public Person update(Person person) throws Exception{
        return em.merge(person);
    }
}
