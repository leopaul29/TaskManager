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
import fr.unice.mbds.status.StatusEnum;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        try {
            List<Task> tasks = tm.createTestsTasks();
            List<Person> persons = pm.createTestsPersons();
            
            //Add relation between persons and tasks
            for( int i = 0; i < tasks.size()/2; i++){
                Person person = null;
                Task task = null;
                
                do{
                    person = persons.get( (int) (Math.random()*persons.size()) );
                    task = tasks.get( (int) (Math.random()*tasks.size()) );
                    System.out.println(!person.getTasks().contains(task)+ " ");
                }while(person.getTasks().contains(task));
                
                //Random status
                int indexStatus = (int) (Math.random() * StatusEnum.values().length);
                task.setStatus(StatusEnum.values()[indexStatus]);
                
                System.out.println(task.getTitle() + " " + person.getLogin());
                pm.addTaskToPerson(person, task);
                tm.addPersonToTask(task, person);
                System.out.println(person);
            }
        } catch (Exception ex) {
            Logger.getLogger(InitDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
