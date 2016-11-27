/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.mbds.jsf;

import fr.unice.mbds.entities.Task;
import fr.unice.mbds.session.TasksManager;
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
@Named(value = "tasksMBean")
@ViewScoped
public class TasksMBean implements Serializable {

    @EJB
    private TasksManager tm;
    private List<Task> listTask = new ArrayList<>();

    /**
     * Creates a new instance of TasksMBean
     */
    public TasksMBean() {
    }

    public List<Task> getTasks() {
        if (listTask.isEmpty()) {
            refreshCache();
        } else {
            System.out.println("TASK : USE DATA CACHE");
        }

        return listTask;
    }

    public void refreshCache() {
        System.out.println("TASK : REGENERATE CACHE");
        listTask = tm.findAll();
        System.out.println("PERSON : " + listTask.size());
    }

    public String createTestData() {
        System.out.println("TASK : JSF BEAN CREATETESTDATA");
        // create
        refreshCache();
        return "index?faces=redirect=true";
    }
    
    public void addTask() {
        System.out.println("TASK : ADD TASK");
        
        refreshCache();
    }
    
    public void deleteTask() {
        System.out.println("TASK : DELETE TASK");
        
        refreshCache();
    }

}
