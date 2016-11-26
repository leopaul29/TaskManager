/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.mbds.jsf;

import fr.unice.mbds.entities.Person;
import fr.unice.mbds.session.PersonsManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author MBDS
 */
@Named(value = "personsMBean")
@ViewScoped
public class PersonsMBean implements Serializable {

    @EJB
    private PersonsManager pm;
    private List<Person> listPerson = new ArrayList<>();
    
    /**
     * Creates a new instance of PersonsMBean
     */
    public PersonsMBean() {
    }
    
    public List<Person> getPersons() {
        if (listPerson.isEmpty()) {
            refreshCache();
        } else {
            System.out.println("PERSON : USE DATA CACHE");
        }

        return listPerson;
    }

    public void refreshCache() {
        System.out.println("PERSON : REGENERATE CACHE");
        listPerson = pm.findAll();
        System.out.println("PERSON : " + listPerson.size());
    }

    public String createTestData() {
        System.out.println("PERSON : JSF BEAN CREATETESTDATA");
        // create
        refreshCache();
        return "index?faces=redirect=true";
    }
    
}
