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
    
    public Task findWithId(int id) {
        Query q = em.createQuery("select t from Task t where t.id = :id");
        q.setParameter("id", id);
        
        return (Task) q.getSingleResult();
    }
    
    public List<Task> findAll() {
        Query q = em.createQuery("select t from Task t");
        
        return q.getResultList();
    }
    
    public List<Task> findWithStatus(StatusEnum status){
        Query q = em.createQuery("select t from Task t where t.status = :status");
        q.setParameter("status", status);
        
        return q.getResultList();
    }
    
    public List<Task> findRange(int start, int nb, String nomColonne, String so) {
        System.out.println("TM nom col=" + nomColonne + " - sortOrder=" + so);
        if (nomColonne == null) {
            nomColonne = "id";
        }
        if (so.equals("ASCENDING")) {
            so = "ASC";
        } else {
            so = "DESC";
        }
        
        String req = "select t from Task t order by t." + nomColonne + " " + so;
        System.out.println("req : " + req);
        Query q = em.createQuery(req);
        q.setFirstResult(start);
        q.setMaxResults(nb);
        
        return q.getResultList();
    }
    
    public List<Task> findRangeWithStatus(StatusEnum status, int start, int nb, String nomColonne, String so) {
        System.out.println("TM nom col=" + nomColonne + " - sortOrder=" + so);
        if (nomColonne == null) {
            nomColonne = "id";
        }
        if (so.equals("ASCENDING")) {
            so = "ASC";
        } else {
            so = "DESC";
        }
        
        String req = "select t from Task t where t.status = :status order by t." + nomColonne + " " + so;
        System.out.println("req : " + req);
        Query q = em.createQuery(req);
        q.setParameter("status", status);
        q.setFirstResult(start);
        q.setMaxResults(nb);
        
        return q.getResultList();
    }
    
    public int count() {
        Query q = em.createQuery("select count(t) from Task t");
        Number n = (Number) q.getSingleResult();
        return n.intValue();
    }
    
    public int countStatus(StatusEnum status){
        Query q = em.createQuery("select count(t) from Task t where t.status = :status");
        q.setParameter("status", status);
        
        Number n = (Number) q.getSingleResult();
        return n.intValue();
    }
    
    public void removeTask(Task task) throws Exception{
        Task t = em.merge(task);
        em.remove(t);
    }
    
    public void setStatus(Integer id, StatusEnum status) throws Exception{
        Task task = em.find(Task.class, id);
        task.setStatus(status);
    }
    
    public Task update(Task task) throws Exception{
        return em.merge(task);
    }
}
