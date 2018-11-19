/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import Business.Tracker;
import Model.Peer;
import Model.enumStateSwarm;
import RMI.InterfaceAddresses;
import RMI.InterfaceRunTime;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Michael
 */
public class StartConnection extends Thread{

    private Tracker tracker;
    
    
            
    public StartConnection (Tracker tracker){
        this.tracker = tracker;
        
        this.start();
    }
    
    
    @Override
    public void run() {
        Registry myRegistry; 
        try {
            //System.out.println("Durmió -----");
            Thread.sleep(3000);
            //System.out.println("Despertó -----");
            for (Map.Entry<Peer, enumStateSwarm> entry : tracker.getSwarm().entrySet()) {
                System.out.println(entry.getKey().getIp());
                myRegistry = LocateRegistry.getRegistry(entry.getKey().getIp(),888);
                InterfaceRunTime interRunTime = (InterfaceRunTime) myRegistry.lookup("RunTime");
                
                interRunTime.start();    
            }
          System.out.println("Clients Started -----");  
            
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(StartConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(StartConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(StartConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
