/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;

/**
 *
 * @author Michael
 */
public class Piece implements Serializable{
    
    private String data;
    private enumStateFile state;
    private Double percentage;

    public Piece(String data){
        this.data = data;
        this.state = enumStateFile.INCOMPLETE;
    }
    
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public enumStateFile getState() {
        return state;
    }

    public void setState(enumStateFile state) {
        this.state = state;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }
    
       
    
}
