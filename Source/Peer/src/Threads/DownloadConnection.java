/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import Model.ClientPeer;
import Model.File;
import Model.Peer;
import Model.Piece;
import utils.Utils;

import Model.enumStateSwarm;
import RMI.InterfaceAddresses;
import RMI.InterfaceFile;
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
        
    }
    private ClientPeer mipeer;

    public ClientPeer getMipeer() {
        return mipeer;
    }

    public void setMipeer(ClientPeer mipeer) {
        this.mipeer = mipeer;
    }
    
    
@Override
    public void run() {
        
        Registry myRegistry; 
        try {
            //System.out.println("Durmió -----");
            Thread.sleep(3000);
            //System.out.println("Despertó -----");
            
                System.out.println("Thread had downloaded");
                
                myRegistry = LocateRegistry.getRegistry(peer.getIp(),888);
                InterfaceRunTime interRunTime = (InterfaceRunTime) myRegistry.lookup("RunTime");
                
                interRunTime.start();    
            
        }catch(Exception e){
        }
          System.out.println("Clients Started -----");  
        
    }    
    
        public String download(String nameFile,int linea) throws RemoteException, NotBoundException, Exception {
                Registry myRegistry = LocateRegistry.getRegistry(peer.getIp(),888); 
      InterfaceFile interFile = (InterfaceFile) myRegistry.lookup("File");
            Piece p=interFile.downloadPiece(nameFile, linea);
               
            return p.getData();
    }
 
        
}
