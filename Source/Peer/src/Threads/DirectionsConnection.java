/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import Model.File;
import Model.Peer;
import RMI.InterfaceAddresses;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michael
 */
public class DirectionsConnection extends Thread{
    
    private List<File> files;
    private String ipServer;
    
    public DirectionsConnection(List<File> fls, String ip){
        this.files = fls;
        this.ipServer = ip;
        this.start();
    }
    
       public void run() {
        try {
           // System.out.println("corriendo directions conections");
            System.out.println(getDirectionsToFile("file1").toString());
            
        } catch (NotBoundException ex) {
            Logger.getLogger(DirectionsConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DirectionsConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
       }
    
     public List<Peer> getDirectionsToFile(String nameFile) throws RemoteException, NotBoundException, Exception{
    
        Registry myRegistry = LocateRegistry.getRegistry(this.ipServer,777); 
        InterfaceAddresses interAddress = (InterfaceAddresses) myRegistry.lookup("Addresses");
    
        List<File> fls = new ArrayList<File>();
        
        for(File f : files){
            fls.add(new File(f.getName(), f.getState()));
        }
        
           return interAddress.addressesToFile(nameFile);
        
    }
    
}
