/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.mbds.status;

/**
 *
 * @author 53js-Seb
 */
public enum StatusEnum {
    NO_ATTRIBUTED("En attente"),
    IN_PROGRESS("En cours"),
    COMPLETED("Complète");
    
    private String name;
    
    private StatusEnum(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
}
