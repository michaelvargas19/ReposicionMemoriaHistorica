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
import Model.enumStateFile;
import Model.enumStateSwarm;
import RMI.InterfaceAddresses;
import RMI.InterfaceFile;
import RMI.InterfaceRunTime;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michael
 */
public class DownloadConnection extends Thread{

    private List<Peer> peers;
    private String nameFile;
    private List<Piece> pieces;
    private Registry myRegistry;
    private File file;
    private ClientPeer clienPeer;
    private String ipServer;
    
    public DownloadConnection(List<Peer> p, File file, ClientPeer cp, String ipServer){
        this.peers = p;
        this.ipServer = ipServer;
        this.clienPeer = cp;
        this.nameFile = nameFile;
        this.file = file;
        //this.pieces = pieces;
        this.start();
    }
    
@Override
    public void run() {
        
        Registry myRegistry;
       try{
        for(Peer p : peers){
        try {      
                myRegistry = LocateRegistry.getRegistry(p.getIp(),888);
                InterfaceFile interFile = (InterfaceFile) myRegistry.lookup("File"+this.file.getName());        
                
               
                for ( Map.Entry<Integer, Piece> entry : file.getPieces().entrySet()) {
                    if(entry.getValue().getState() == enumStateFile.INCOMPLETE)
                        new DownloadPieceConnection(interFile, clienPeer, this.file.getName(),entry.getKey(),this);    
                // do something with key and/or tab
                }
                
                
                
                
                //Random aleatorio = new Random(System.currentTimeMillis());

 
                //System.out.println(interFile.inOnLine()+"--->> "+"File"+nameFile);
                //System.err.println("Downloaded " + nameFile + " de " + p.getIp() +"...");
                
            }catch (java.rmi.RemoteException e){
                System.out.println("RemoteException catched  xd xd xd");
            } catch (NotBoundException ex) {
                
                peers.remove(p);
                
                if(peers.size() != 0){
                    
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex1) {
                        Logger.getLogger(DownloadConnection.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    new DownloadConnection(peers, file, clienPeer, ipServer);
  //                  System.out.println("new Download Connection ++++++++++++++++++++++++");
                    this.destroy();
                }else{

                    List<File> fls = new ArrayList<>();
                    fls.add(this.file);
                    new DirectionsConnection(fls, clienPeer.getIpServer(), clienPeer);
//                    System.out.println("Requesting file "+file.getName());
                }
                
            }  catch (Exception ex) {
                Logger.getLogger(DownloadConnection.class.getName()).log(Level.SEVERE, null, ex);
            } 

        }
        }catch (java.util.ConcurrentModificationException ex) {
                //System.err.println(ex +"***************************************");
                //this.destroy();
            }
            
                /*
                myRegistry = LocateRegistry.getRegistry(entry.getKey().getIp(),888);
                InterfaceRunTime interRunTime = (InterfaceRunTime) myRegistry.lookup("RunTime");
                
                interRunTime.start();   */ 
            
        
          System.out.println("Clients Started -----");  
        
    }    
    
    public void errorDownloadPiece( int n, String name) throws NotBoundException, Exception{
        System.out.println("Error descargando Piece " +n );
        
        Peer p = getDirectionsToFile(name).get(0);
        
        myRegistry = LocateRegistry.getRegistry(p.getIp(),888);
        //InterfaceFile interFile = (InterfaceFile) myRegistry.lookup("File"+this.file.getName());        
        InterfaceFile interFile = (InterfaceFile) myRegistry.lookup("File"+name);        
                
        new DownloadPieceConnection(interFile, clienPeer, this.file.getName(),n,this);    
        
             
        
    }
    
    public void pieceDownloaded(int n){
        for ( Map.Entry<Integer, Piece> entry : file.getPieces().entrySet()) {
            if(entry.getKey() == n)
                entry.getValue().setState(enumStateFile.COMPLETE);
       
        }
        
    }
        
    public List<Peer> getDirectionsToFile(String nameFile) throws RemoteException, NotBoundException, Exception{
    
        Registry myRegistry = LocateRegistry.getRegistry(this.ipServer,777); 
        InterfaceAddresses interAddress = (InterfaceAddresses) myRegistry.lookup("Addresses");
    
           return interAddress.addressesToFile(nameFile);

    }
}
