/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.mbds.jsf;

import fr.unice.mbds.entities.Task;
import fr.unice.mbds.session.TasksManager;
import fr.unice.mbds.status.StatusEnum;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author MBDS
 */
@Named(value = "chartsMBean")
@Dependent
public class ChartsMBean {

    @EJB
    private TasksManager tm;

    private PieChartModel pieModel1;

    /**
     * Creates a new instance of ChartsMBean
     */
    public ChartsMBean() {
    }

    public PieChartModel getPieModel1() {
        if (pieModel1 == null) {
            createChartPie();
        } else {
            refreshChart();
        }

        return pieModel1;
    }

    public String updateTask(Task task) {
        refreshChart();

        return "listTask?faces=redirect=true";
    }

    private void createChartPie() {
        pieModel1 = new PieChartModel();
        pieModel1.setTitle("Status Tasks");
        pieModel1.setLegendPosition("n");
        refreshChart();
    }

    public void refreshChart() {
        if (pieModel1 == null) {
            createChartPie();
        }
        pieModel1.set("non attribuée", tm.countStatus(StatusEnum.NO_ATTRIBUTED));
        pieModel1.set("en cours", tm.countStatus(StatusEnum.IN_PROGRESS));
        pieModel1.set("complète", tm.countStatus(StatusEnum.COMPLETED));
    }

    public String updateChart() {
        System.out.println("UPDATE CHART");
        try {
            refreshChart();
        } catch (Exception ex) {
            Logger.getLogger(ChartsMBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "listTask?faces=redirect=true";
    }
}
