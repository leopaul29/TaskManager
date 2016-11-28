/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.mbds.jsf;

import fr.unice.mbds.entities.Task;
import fr.unice.mbds.session.TasksManager;
import fr.unice.mbds.status.StatusEnum;
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
    
    private String title;
    private StatusEnum status;
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public String createTask() {
        System.out.println("TASK : JSF BEAN CREATETASK");
        
        tm.createTask(title, status, description);
        
        refreshCache();
        return "listTask?faces=redirect=true";
    }

}
