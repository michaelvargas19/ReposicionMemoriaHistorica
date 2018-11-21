/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import Business.ClientPeer;
import Model.Peer;
import Model.Piece;
import RMI.InterfaceAddresses;
import RMI.InterfaceFile;
import RMI.InterfaceRegistry;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michael
 */
public class DownloadPieceConnection extends Thread{
    
    
    private ClientPeer clientPeer;
    private String nameFile;
    private int n;
    private List<Peer> peers;
    private DownloadConnection downConnection;
    private Registry registryPeer;
    private String ipServer;

    
    
    public DownloadPieceConnection(ClientPeer cl, String nameFile, int n, DownloadConnection dwC, String ipServer){
        this.ipServer = ipServer;
        this.clientPeer = cl;
        this.nameFile = nameFile;
        this.n = n;
        this.downConnection = dwC;
       this.start();
    }
    
    @Override
    public void run(){
        
        try {
            peers = getDirectionsToFile(nameFile);
            boolean done = false;
            for(Peer p : peers){
                Piece piece = this.downLoadData(p);
                
                if(piece != null){
                    downConnection.pieceDownloaded(n,nameFile,piece.getData());
                    done = true;
                    break;
                }
            }
            
            if(!done){
                downConnection.errorDownloadPiece(n, nameFile);
            }
            
            
        } catch (Exception ex) {
            System.out.println(ex);
            try {
                
                downConnection.errorDownloadPiece(n, nameFile);
            } catch (Exception ex1) {
                Logger.getLogger(DownloadPieceConnection.class.getName()).log(Level.SEVERE, null, ex1);
            }
            
        }
        
        
    }
        
    public List<Peer> getDirectionsToFile(String nameFile) throws RemoteException, NotBoundException, Exception{
    
        List<Peer> list = null;
        Registry myRegistry = LocateRegistry.getRegistry(this.ipServer,777); 
        InterfaceAddresses interAddress = (InterfaceAddresses) myRegistry.lookup("Addresses");
        
        do{    
            list = interAddress.addressesToFile(nameFile);
        }while(list.size() == 0);
        
           
           return list;

    } 
    
    private Piece downLoadData(Peer p){
                
        try {
            Registry myRegistry = LocateRegistry.getRegistry(p.getIp(),888);
            InterfaceFile interFile = (InterfaceFile) myRegistry.lookup("File");
            
            return interFile.downloadPiece(nameFile, n);
        }
        catch (Exception ex) {
            System.out.println("Error:"+ex);
            return null;
        }
        
    }
}
