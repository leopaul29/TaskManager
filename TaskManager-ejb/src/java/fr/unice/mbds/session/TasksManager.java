/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.mbds.session;

import fr.unice.mbds.entities.Task;
import fr.unice.mbds.status.StatusEnum;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author MBDS
 */
@Stateless
@LocalBean
public class TasksManager {
    @PersistenceContext(unitName = "TaskManager-ejbPU")
    private EntityManager em;

    public void createTask(String title, StatusEnum status, String description) {
        Task t = new Task(title, description);
        t.setStatus(status);
        createTask(t);
    }
    
    public void createTask(Task task) {
        em.persist(task);
    }
    
    public List<Task> createTestsTasks(){
        List<Task> taskList = new ArrayList();
        
        for(int i = 0; i < 500; i++){
            Task task = new Task("Title" + i , "Une petite description...");
            taskList.add(task);
            createTask(task);
        }
        
        return taskList;
    }
    
    public List<Task> findAll() {
        Query q = em.createQuery("select t from Task t");
        
        return q.getResultList();
    }
    
    public List<Task> findRange(int start, int nb, String nomColonne, String so) {
        System.out.println("TM nom col=" + nomColonne + " - sortOrder=" + so);
        // changer la requete avec la column a sorter + order ASC/DESC .?
        Query q = em.createQuery("select t from Task t");
        q.setFirstResult(start);
        q.setMaxResults(nb);
        
        return q.getResultList();
    }
    
    public int count() {
        Query q = em.createQuery("select count(t) from Task t");
        Number n = (Number) q.getSingleResult();
        return n.intValue();
    }
    
    public void removeTask(Task task) throws Exception{
        Task t = em.merge(task);
        em.remove(t);
    }
    
    public Task update(Task task) throws Exception{
        return em.merge(task);
    }
}
