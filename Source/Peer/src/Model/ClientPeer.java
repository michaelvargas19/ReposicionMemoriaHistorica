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
import RemoteObjects.RunTimeImplements;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
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

    public ClientPeer(Home home) throws RemoteException{
        this.home = home;
        this.files = new ArrayList<File>();
        try {
            this.ipLocal = getLocalIp();
        } catch (java.net.UnknownHostException ex) {
            Logger.getLogger(ClientPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        init();
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
        
        
            File f = new File();
            f.setName("file1");
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
    
    
    public List<Peer> getDirectionsToFile(String nameFile) throws RemoteException, NotBoundException, Exception{
    
        Registry myRegistry = LocateRegistry.getRegistry("127.0.0.1",777); 
        InterfaceAddresses interAddress = (InterfaceAddresses) myRegistry.lookup("Addresses");
    
        List<File> fls = new ArrayList<File>();
        
        for(File f : files){
            fls.add(new File(f.getName(), f.getState()));
        }
        
           return interAddress.addressesToFile(nameFile);
        
    }
    
    private void uploadFiles(){
    
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
        
        home.updateFiles(files);
        
    }
    
    public void start() throws NotBoundException, Exception{
        answerRequest();
    }
    
    private void answerRequest(){
        
        try {
            
            System.out.println(getDirectionsToFile("file1").toString()+"RunTime xd xd ");
            
        } catch (NotBoundException ex) {
            Logger.getLogger(ClientPeer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ClientPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void init() throws RemoteException{
        myRegistry = LocateRegistry.createRegistry(888); 
        myRegistry.rebind("RunTime", new RunTimeImplements(this));
        System.out.println("Client Interface On...!!!");
        uploadFiles();    
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
    
    
    
    
}

