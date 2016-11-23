/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.mbds.tools;

import fr.unice.mbds.entities.Person;
import fr.unice.mbds.entities.Task;
import fr.unice.mbds.session.PersonsManager;
import fr.unice.mbds.session.TasksManager;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;

/**
 *
 * @author 53js-Seb
 */
@Singleton
@LocalBean
@Startup
public class InitDB {
    
    @EJB
    private TasksManager tm;
    
    
    @EJB
    private PersonsManager pm;
    
    @PostConstruct
    public void createDataBase(){
        List<Task> tasks = tm.createTestTasks();
        List<Person> persons = pm.createTestsPersons();
        
        //TODO ADD task for persons
    }
}
