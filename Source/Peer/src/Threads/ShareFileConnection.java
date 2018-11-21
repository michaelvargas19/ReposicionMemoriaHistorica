/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import Business.ClientPeer;
import Model.File;
import Model.Peer;
import Model.Piece;
import Model.enumStateFile;
import Model.enumStateSwarm;
import Persistence.ManejoArchivos;
import static Persistence.ManejoArchivos.writeFile;
import RMI.InterfaceAddresses;
import RMI.InterfaceFile;
import RMI.InterfaceRunTime;
import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Michael
 */
public class ShareFileConnection extends Thread{

    private List<Peer> peers;
    private String nameFile;
    private String title;
    private String path;
    private Registry myRegistry;
    private File file;
    private ClientPeer clienPeer;
    private String ipServer;
    private String ipLocal;
    private Map<Peer,enumStateFile> shared;
    
    
    public ShareFileConnection(String path, String name, ClientPeer cp, String ipServer, String ipLocal){
        this.shared = new HashMap<Peer,enumStateFile>();
        this.ipServer = ipServer;
        this.ipLocal = ipLocal;
        this.clienPeer = cp;
        this.nameFile = name;
        this.path = path;
        this.peers = new ArrayList <Peer>();
        
        //this.pieces = pieces;
        this.start();
    }
    
@Override
    public void run() {
        
        try {
            
            System.out.println("Sharing...");
            
            
            peers = getDirectionsToShare();
            
            boolean done = false;
            
            for(Peer p : peers){
                //if(p.getIp().compareTo(ipLocal) != 0){
                    shared.put(p, enumStateFile.INCOMPLETE);
                    new ShareFilePeerConnection(path, nameFile, this, p);
                //}
                
            }
            
            
        } catch (NotBoundException ex) {
            Logger.getLogger(ShareFileConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ShareFileConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }    
    
    public void errorLoadFile( Peer p) throws NotBoundException, Exception{
        
        //new ShareFilePeerConnection(nameFile,this,);
    }
    
    public void fileLoaded(Peer p){
        /*for ( Map.Entry<Integer, Piece> entry : file.getPieces().entrySet()) {
            if(entry.getKey() == n)
                entry.getValue().setState(enumStateFile.COMPLETE);
       
        }*/
        shared.replace(p, enumStateFile.COMPLETE);
        
        boolean complete = true;
        
        for (Map.Entry<Peer, enumStateFile> peer : shared.entrySet()) {
            if(peer.getValue() == enumStateFile.INCOMPLETE){
                complete = false;
                break;
            }
        }
        
        JOptionPane.showMessageDialog(clienPeer.getHome(),"File Shared with " +p.getIp());

        
    }
        
    public List<Peer> getDirectionsToShare() throws RemoteException, NotBoundException, Exception{
    
        List<Peer> list = null;
        Registry myRegistry = LocateRegistry.getRegistry(this.ipServer,777); 
        InterfaceAddresses interAddress = (InterfaceAddresses) myRegistry.lookup("Addresses");
        
        do{    
            list = interAddress.allAddresses();
        }while(list.size() == 0);
        
           
           return list;

    } 
    
    
    
}
