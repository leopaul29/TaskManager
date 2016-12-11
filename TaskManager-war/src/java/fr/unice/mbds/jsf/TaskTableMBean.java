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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author 53js-Seb
 */
@Named(value = "taskTableMBean")
@ViewScoped
public class TaskTableMBean implements Serializable{
    
    @EJB
    private TasksManager tm;

    private int taskOnDrag;
    
    private Task task;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
    
    private Map<StatusEnum, LazyDataModel<Task>> modeles = new HashMap<>();
    
    
    /**
     * Creates a new instance of TaskTableMBean
     */
    public TaskTableMBean() {
        for(StatusEnum statusEnum: StatusEnum.values()){
            System.out.println("Constructor : " + statusEnum);
            createLazyDataModel(statusEnum);
        }
    }

    
    private LazyDataModel createLazyDataModel(StatusEnum status){
        final LazyDataModel<Task> model = new LazyDataModel<Task>() {
            @Override
            public List<Task> load(int start, int nb, String nomColonne, org.primefaces.model.SortOrder orderTri, Map<String, Object> filters) {
                // On va requeter pour récupérer
                // les comptes correspondant aux paramètres
                // recus
                System.out.println("LOAD start=" + start + " - nb=" + nb);
                System.out.println("Nom col=" + nomColonne + " - tri=" + orderTri);
                
                List<Task> list =  tm.findRangeWithStatus(status, start, nb, nomColonne, orderTri.toString());
                System.out.println("OnLoad " + status.name() + " " + list.size());
                return list;
            }
            
            @Override
            public int getRowCount() {
                int nb = tm.countStatus(status);
                System.out.println("get row count " + status.name() + " " + nb);
                return nb;
            }
        };
        modeles.put(status, model);
        
        return model;
    }
    
    
    public LazyDataModel getModel(StatusEnum status){
        System.out.println("Get model " + status);
        
        LazyDataModel model =  modeles.get(status);
        return model;
    }
    
    public StatusEnum[] getAllStatus(){
        System.out.println("all status : " + StatusEnum.values());
        return StatusEnum.values();
    }
    
    public List<Task> getTasksWithStatus(StatusEnum status){
        System.out.println("Get task with status: " + status);
        return tm.findWithStatus(status);
    }
    
    //Ref http://forum.primefaces.org/viewtopic.php?f=3&t=37966
    public void onDragTask(){
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        
        taskOnDrag = Integer.parseInt(params.get("idTask"));
        System.out.println("OnDragTask " +taskOnDrag);
    }
    
    public void onTaskDrop(StatusEnum status) {
        System.out.println("onTaskDrop " + taskOnDrag + " " + status);
        try {
            tm.setStatus(taskOnDrag, status);
        } catch (Exception ex) {
            Logger.getLogger(TaskTableMBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public String getIdUpdateAll(){
        StringBuilder result = new StringBuilder();
        
        for( StatusEnum status : getAllStatus() ){
            result.append(":form_").append(status).append(":table_").append(status).append(" ");
        }
        
        return  result.toString();
    }
    
    
}
