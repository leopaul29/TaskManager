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
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
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

    public String createPerson() {
        System.out.println("PERSON : JSF BEAN CREATEPERSON");
        
        pm.createPerson(login, password);
        
        refreshCache();
        return "listPerson?faces=redirect=true";
    }
    
    public void removePerson(Person person){
        System.out.println("Remove person: " + person.getId());
        try {
            pm.removePerson(person);
            refreshCache();
        } catch (Exception ex) {
            Logger.getLogger(PersonsMBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
