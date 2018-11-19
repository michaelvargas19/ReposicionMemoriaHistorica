/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import GUI.Home;
import Model.*;
import RMI.InterfaceAddresses;
import RMI.InterfaceRegistry;
import RemoteObjects.FileImplements;
import RemoteObjects.RunTimeImplements;
import Threads.DirectionsConnection;
import Threads.DownloadConnection;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Michael
 */
public class ClientPeer {
    
    private Home home;
    private String ipLocal;
    private List<File> files;
    private Registry myRegistry;
    private String ipServer;

    public ClientPeer(Home home) throws RemoteException{
        this.home = home;
        this.files = new ArrayList<File>();
        try {
            this.ipServer = "127.0.0.1";
            this.ipLocal = getLocalIp();
        } catch (java.net.UnknownHostException ex) {
            Logger.getLogger(ClientPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
        uploadFiles(); 
        
    }
    
    //-------------------------------------------------------------------------------------------------
    private String getLocalIp() throws java.net.UnknownHostException{
        InetAddress ip;
        ip = InetAddress.getLocalHost();
        //return ip.getHostAddress();
        return "127.0.0.1";
        
    }
    
    
    //-------------------------------------------------------------------------------------------------

    public String getIpLocal() {
        return ipLocal;
    }

    public void setIpLocal(String ipLocal) {
        this.ipLocal = ipLocal;
    }
    
    public void registryOnServer() throws RemoteException, NotBoundException{
    
        Registry myRegistry = LocateRegistry.getRegistry("127.0.0.1",777); 
        InterfaceRegistry interRegistry = (InterfaceRegistry) myRegistry.lookup("Registry");
    
        List<File> fls = new ArrayList<File>();
        
        for(File f : files){
            fls.add(new File(f.getName(), f.getState()));
        }
        
            Map<Integer,Piece> pieces = new HashMap<>();
            
            pieces.put(0,new Piece("piece0"));
            pieces.put(1,new Piece("piece1"));
            pieces.put(2,new Piece("piece2"));
                
            File f = new File();
            f.setName("file1");
            f.setPieces(pieces);
            
            Register r = new Register();
            r.setFiles(fls);
            r.setTypeRegister(enumTypeRegister.REGISTER);
            r.setIpClient(getIpLocal());

            interRegistry.register(r);
        /*
        Register r = new Register();
        
            r.setFile(files.get(2));
            r.getFile().setState(enumStateFile.COMPLETE);
            r.setTypeRegister(enumTypeRegister.UPDATE);
            r.setIpClient(getIpLocal());
            
            interRegistry.register(r);
        */
    }
    
    
   
    
    private void uploadFiles() throws RemoteException{
    
        File f = new File();
        f.setName("file1");
        f.setState(enumStateFile.COMPLETE);
        this.files.add(f);
        
        f = new File();
        f.setName("file2");
        f.setState(enumStateFile.COMPLETE);
        this.files.add(f);
        
        f = new File();
        f.setName("file3");
        f.setState(enumStateFile.INCOMPLETE);
        this.files.add(f);
        init();
        home.updateFiles(files);
        
        
        
    }
    
    public void start() throws NotBoundException, Exception{
        answerRequest();
    }
    
    private void answerRequest(){
        
        try {
            
            Map<Integer,Piece> pieces = new HashMap<>();
            
            pieces.put(0,new Piece("piece0"));
            pieces.put(1,new Piece("piece1"));
            pieces.put(2,new Piece("piece2"));
                
            File f = new File();
            f.setName("file1");
            f.setPieces(pieces);
            
            List<File> fls = new ArrayList<>();

            fls.add(f);
            
             new DirectionsConnection(fls, ipServer,this)
                    //System.out.println(getDirectionsToFile("file1").toString()+"RunTime xd xd ");
;
            
        } catch (Exception ex) {
            Logger.getLogger(ClientPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void init() throws RemoteException{
        myRegistry = LocateRegistry.createRegistry(888); 
        myRegistry.rebind("RunTime", new RunTimeImplements(this));
        
        for(File f : this.files){    
            myRegistry.rebind("File"+f.getName(), new FileImplements(this));
            System.out.println("Client Interface F"+f.getName()+" On...!!!");
            
        }
        
        
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public String getIpServer() {
        return ipServer;
    }

    public void setIpServer(String ipServer) {
        this.ipServer = ipServer;
    }
    
    
    
    
}

