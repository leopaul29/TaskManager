/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.mbds.jsf;

import fr.unice.mbds.session.TasksManager;
import java.io.Serializable;
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
    
    /**
     * Creates a new instance of TasksMBean
     */
    public TasksMBean() {
    }
    
}
