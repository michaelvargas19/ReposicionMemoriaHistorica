/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import Business.ClientPeer;
import Model.Peer;
import Model.Piece;
import Persistence.ManejoArchivos;
import RMI.InterfaceAddresses;
import RMI.InterfaceFile;
import RMI.InterfaceRegistry;
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
public class ShareFilePeerConnection extends Thread{
    
    
    private ClientPeer clientPeer;
    private String path;
    private String name;
    private Peer peer;
    private ShareFileConnection loadConnection;
    private String ipServer;
    
    
    
    public ShareFilePeerConnection(String path, String name, ShareFileConnection ldC, Peer p){
        this.peer = p;
        this.path = path;
        this.name = name;
        this.loadConnection = ldC;
        this.start();
    }
    
    @Override
    public void run(){
        
        if(shareData(peer, path)){
            loadConnection.fileLoaded(peer);
        }else{
            
            try {
                loadConnection.errorLoadFile(peer);
            } catch (Exception ex) {
                Logger.getLogger(ShareFilePeerConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
        
    
    private boolean shareData(Peer p, String path){
        
        ArrayList<String> datas = new ArrayList<String>();

        try {
            datas = ManejoArchivos.readClientFile(path);
            
        } catch (Exception e) {
            System.out.println("error cargando archivos: "+e);
        }
        
                
        try {
            Registry myRegistry = LocateRegistry.getRegistry(p.getIp(),888);
            InterfaceFile interFile = (InterfaceFile) myRegistry.lookup("File");
            
            return interFile.receiveFile(datas, path, name);
        }
        catch (Exception ex) {
            System.out.println("Error:"+ex);
            return false;
        }
        
    }
}
