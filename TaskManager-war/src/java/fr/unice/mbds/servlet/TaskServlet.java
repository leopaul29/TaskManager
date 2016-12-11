/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.mbds.servlet;

import fr.unice.mbds.entities.Task;
import fr.unice.mbds.session.TasksManager;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author MBDS
 */
@WebServlet(name = "TaskServlet", urlPatterns = {"/faces/displayTask.xhtml"})
public class TaskServlet extends HttpServlet {
    
    @EJB
    private TasksManager tm;

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Task task;
        try{
            int id = Integer.parseInt(request.getParameter("id"));
            task = tm.findWithId(id);
        }
        catch( NumberFormatException e){
            task = null;
        }

        if( task == null ){
            request.setAttribute("error", true);
        }
        else{
            request.setAttribute("task", task);
        }

        RequestDispatcher dp = request.getRequestDispatcher("faces/displayTask.xhtml");
        dp.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
