/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RemoteObjects;

import Business.Tracker;
import Model.Peer;
import Model.Register;
import Model.enumTypeRegister;
import RMI.InterfaceRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michael
 */
public class RegistryImplements extends UnicastRemoteObject implements InterfaceRegistry{
    
    private Tracker tracker;

    public RegistryImplements (Tracker tracker) throws RemoteException{
        super();
        this.tracker = tracker;
        
    }
    
    private String name;
    
    @Override
    public boolean register(Register register) throws RemoteException {
    
        if(register.getTypeRegister() == enumTypeRegister.REGISTER){
            
            return tracker.register(register);
            
        }
        
        if(register.getTypeRegister() == enumTypeRegister.UPDATE){
            
            return tracker.updateFile(register);    
        }
        
        return false;
    }

//------------------------------------------------------------------------------------
    public Tracker getTracker() {
        return tracker;
    }

    public void setTracker(Tracker tracker) {
        this.tracker = tracker;
    }

  

    
    
}
