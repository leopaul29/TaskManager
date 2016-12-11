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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;

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
    private List<Task> selectedTask = new ArrayList<>();
    private Task task;
    private String title;
    private StatusEnum status;
    private String description;

    public List<Task> getSelectedTask() {
        return selectedTask;
    }

    public void setSelectedTask(List<Task> selectedTask) {
        this.selectedTask = selectedTask;
    }
    
    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public StatusEnum[] getAllStatus() {
        return StatusEnum.values();
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String updateTask(Task task) {
        System.out.println("Update Task  : " + task.getId());
        try {
            tm.update(task);
            refreshCache();
        } catch (Exception ex) {
            Logger.getLogger(TasksMBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "listTask?faces=redirect=true";
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
    private LazyDataModel<Task> modele;

    public LazyDataModel<Task> getModele() {
        return modele;
    }

    public TasksMBean() {
        modele = new LazyDataModel<Task>() {

            @Override
            public List<Task> load(int start, int nb, String nomColonne, org.primefaces.model.SortOrder orderTri, Map<String, Object> filters) {
                // On va requeter pour récupérer
                // les comptes correspondant aux paramètres
                // recus
                System.out.println("LOAD start=" + start + " - nb=" + nb);
                System.out.println("Nom col=" + nomColonne + " - tri=" + orderTri);

                return tm.findRange(start, nb, nomColonne, filters, orderTri.toString());
            }

            @Override
            public int getRowCount() {
                return tm.count();
            }
        };
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
        System.out.println("TASK : " + listTask.size());
    }

    public String createTask() {
        System.out.println("TASK : JSF BEAN CREATETASK");

        tm.createTask(title, status, description);

        refreshCache();
        return "listTask?faces=redirect=true";
    }

    public String removeTask(Task task) {
        System.out.println("Remove task: " + task.getId());
        try {
            tm.removeTask(task);
            refreshCache();
        } catch (Exception ex) {
            Logger.getLogger(TasksMBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "listTask?faces=redirect=true";
    }

    public String removeTask() {
        System.out.println("Remove task: " + task.getId());
        try {
            tm.removeTask(task);
            refreshCache();
        } catch (Exception ex) {
            Logger.getLogger(TasksMBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "listTask?faces=redirect=true";
    }

    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Task Edited", "" + ((Task) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Task Cancelled", "" + ((Task) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
