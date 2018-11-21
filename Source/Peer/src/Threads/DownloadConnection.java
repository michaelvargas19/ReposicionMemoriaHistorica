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
public class DownloadConnection extends Thread{

    private List<Peer> peers;
    private String nameFile;
    private String title;
    private Registry myRegistry;
    private File file;
    private ClientPeer clienPeer;
    private String ipServer;
    private Map<String,enumStateFile> pieces;
    private Map<String,String> lines;
    
    public DownloadConnection(String name, ClientPeer cp, String ipServer){
        this.pieces = new HashMap<String,enumStateFile>();
        this.lines = new HashMap<String,String>();
        this.ipServer = ipServer;
        this.clienPeer = cp;
        this.nameFile = name;
        
        //this.pieces = pieces;
        this.start();
    }
    
@Override
    public void run() {
        
    System.out.println("Downloading...");
        //DirectionsConnection dc= new DirectionsConnection(new ArrayList<>(), ipServer);
        List<String> datas=new  ArrayList<String>();
        try {
            datas =ManejoArchivos.readClientFile(this.nameFile);
        } catch(Exception e){
            System.out.println("error leyendo archivo "+file);
        }
        title = datas.get(0);
        datas.remove(0);
        int i =0;
        for(String l:datas){
            i++;
            String f=l.split("-")[0];
            String linea=l.split("-")[1];
            f = f.substring(0,f.length()-4);
            List<Peer> peers=null;
            
            lines.put(f+linea, "");
            pieces.put(f+linea, enumStateFile.INCOMPLETE);
            
            new DownloadPieceConnection(clienPeer, f, Integer.parseInt(linea.trim()), this, ipServer);
            
          /*  try {
            peers=dc.getDirectionsToFile(archivo);
            } catch (Exception e){
                System.out.println("Error obteniendo peers");
            }
          */  
        }
        
        
        System.out.println("loading "+title+".txt");
        try {
       // clienPeer.updateOnServer();
            
        } catch (Exception e) {
            System.out.println("erroe in update "+e);
           
        }
        
    }    
    
    public void errorDownloadPiece( int n, String name) throws NotBoundException, Exception{
        System.out.println("Error descargando Piece " +n );
        new DownloadPieceConnection(clienPeer, name, n, this, this.ipServer);
    }
    
    public void pieceDownloaded(int n, String nameF, String data){
        /*for ( Map.Entry<Integer, Piece> entry : file.getPieces().entrySet()) {
            if(entry.getKey() == n)
                entry.getValue().setState(enumStateFile.COMPLETE);
       
        }*/
        pieces.put(nameF+n, enumStateFile.COMPLETE);
        lines.put(nameF+n, data);
        
        boolean complete = true;
        
        for (Map.Entry<String, enumStateFile> piece : pieces.entrySet()) {
            if(piece.getValue() == enumStateFile.INCOMPLETE){
                complete = false;
                break;
            }
        }
        
        if(complete){
            
            try {

                ManejoArchivos.writeFileNew("Data//nameFiles.txt", title);
                clienPeer.getFiles().add(new File(title, enumStateFile.COMPLETE));
                clienPeer.getHome().updateFiles(clienPeer.getFiles());
   
                int l = 1;
                for (Map.Entry<String, String> line : lines.entrySet()) {
                    ManejoArchivos.writeFile("Data//Files//"+title, l, line.getValue());
                    l++;
                }
                JOptionPane.showMessageDialog(this.clienPeer.getHome(),"File "+title +" Created");
                clienPeer.updateOnServer(title);
           
            } catch (Exception e) {
            }
                System.out.println("--- File: "+lines.toString());
            }
        
    }
        
    public List<Peer> getDirectionsToFile(String nameFile) throws RemoteException, NotBoundException, Exception{
    
        Registry myRegistry = LocateRegistry.getRegistry(this.ipServer,777); 
        InterfaceAddresses interAddress = (InterfaceAddresses) myRegistry.lookup("Addresses");
    
           return interAddress.addressesToFile(nameFile);

    }
    
    
    
}
