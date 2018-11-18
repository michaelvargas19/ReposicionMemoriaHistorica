/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import Business.Tracker;

/**
 *
 * @author Michael
 */
public class AddressesConnection implements Runnable {

    private Tracker tracker;
    private String operation;
    
    public AddressesConnection (Tracker tracker, String operation){
        this.tracker = tracker;
        this.operation = operation;
        
    }
            
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
