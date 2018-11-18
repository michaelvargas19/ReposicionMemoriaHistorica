/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import Model.File;
import Model.Peer;
import Model.Piece;
import Model.enumStateSwarm;
import RMI.InterfaceAddresses;
import RMI.InterfaceRunTime;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Michael
 */
public class DownloadConnection extends Thread{

    private Peer peer;
    private List<Piece> pieces;
    
    public DownloadConnection(Peer p, List<Piece> pieces){
        this.peer = p;
        this.pieces = pieces;
        this.start();
    }
    
@Override
    public void run() {
        
        Registry myRegistry; 
        try {
            //System.out.println("Durmió -----");
            Thread.sleep(3000);
            //System.out.println("Despertó -----");
            
                System.out.println("Thread had downloaded");
                /*
                myRegistry = LocateRegistry.getRegistry(entry.getKey().getIp(),888);
                InterfaceRunTime interRunTime = (InterfaceRunTime) myRegistry.lookup("RunTime");
                
                interRunTime.start();   */ 
            
        }catch(Exception e){
        }
          System.out.println("Clients Started -----");  
        
    }    
    
    
        
}
