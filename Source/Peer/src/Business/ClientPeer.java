/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Presentation.Home;
import Model.File;
import Model.Peer;
import Model.Piece;
import Model.Register;
import Model.enumStateFile;
import Model.enumStateUser;
import Model.enumTypeRegister;
import Persistence.ManejoArchivos;
import RMI.InterfaceAddresses;
import RMI.InterfaceRegistry;
import RemoteObjects.FileImplements;
import RemoteObjects.RunTimeImplements;
import Threads.DownloadConnection;
import Threads.ShareFileConnection;
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
    private Registry registryPeer;
    private Registry registryServer;
    private String ipServer;

    public ClientPeer(Home home) throws RemoteException{

            this.home = home;
            this.files = new ArrayList<File>();
            try {
            this.ipServer = "10.192.101.31";
            this.ipLocal = getLocalIp();
            
            System.setProperty("java.rmi.server.hostname","10.192.101.21");
            registryPeer = LocateRegistry.createRegistry(888);
            registryPeer.rebind("RunTime", new RunTimeImplements(this));
            registryPeer.rebind("File", new FileImplements(this));
            System.out.println("Client "+this.ipLocal+" Interface On...!!!");
            
                uploadFiles();
                registryFilesOnServer();
            
            } catch (java.net.UnknownHostException ex) {
                Logger.getLogger(ClientPeer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
            Logger.getLogger(ClientPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
        
    }
    
    //-------------------------------------------------------------------------------------------------
    private String getLocalIp() throws java.net.UnknownHostException{
        InetAddress ip;
        ip = InetAddress.getLocalHost();
        //return ip.getHostAddress();
        return "10.192.101.21";
        
    }
    
    
    //-------------------------------------------------------------------------------------------------

    public String getIpLocal() {
        return ipLocal;
    }

    public void setIpLocal(String ipLocal) {
        this.ipLocal = ipLocal;
    }
    
    public void registryFilesOnServer() throws RemoteException, NotBoundException{
    
        registryServer = LocateRegistry.getRegistry(ipServer,777); 
        InterfaceRegistry interRegistry = (InterfaceRegistry) registryServer.lookup("Registry");
    
        List<File> fls = new ArrayList<File>();
        
        for(File f : files){
            fls.add(new File(f.getName(), f.getState()));
        }
        
            Map<Integer,Piece> pieces = new HashMap<>();
            Register r = new Register();
            r.setFiles(fls);
            r.setTypeRegister(enumTypeRegister.REGISTER);
            r.setIpClient(getIpLocal());

            interRegistry.register(r);
    }
    
    
   
    
    private void uploadFiles() throws RemoteException{
    
        
        ArrayList<String> names = new ArrayList<String>();

        try {
        names = ManejoArchivos.readClientFile("Data//nameFiles.txt");
            
        } catch (Exception e) {
            System.out.println("error cargando archivos: "+e);
        }
	
        files.clear();
        for (String s: names) {
            File f = new File();
            f.setName(s);
            f.setState(enumStateFile.COMPLETE);
            this.files.add(f);
           
        }
        
        home.updateFiles(files);
    }
    
    public void start() throws NotBoundException, Exception{
        answerRequest();
    }
    
    private void answerRequest(){
        new DownloadConnection("Data//Description.txt", this, ipServer);

    }
    
    private void init() throws RemoteException{
        registryPeer = LocateRegistry.createRegistry(888); 
        registryPeer.rebind("RunTime", new RunTimeImplements(this));
        
        for(File f : this.files){    
            registryPeer.rebind("File"+f.getName(), new FileImplements(this));
            System.out.println("Client Interface F"+f.getName()+" On...!!!");
            
        }
        
        
    }
    
    
    
    public void updateOnServer(String name) throws RemoteException, NotBoundException{
    
        InterfaceRegistry interRegistry;
        interRegistry = (InterfaceRegistry) registryServer.lookup("Registry");
       /*
        List<File> fls = new ArrayList<File>();
        
        for(File f : files){
            fls.add(new File(f.getName(), f.getState()));
        }
        */
            File file = new File();
            file.setName(name);
            Register r = new Register();
            r.setFile(file);
            
            r.setStateUser(enumStateUser.SEED);
            r.setTypeRegister(enumTypeRegister.UPDATE);
            
            r.setIpClient(getIpLocal());
             System.out.println(r.getIpClient());
            interRegistry.register(r);
     }
    
    //----------------------------------------------------------------

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

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }
    
    public void downloadFile(String path){
        new DownloadConnection(path, this, ipServer);
    }
    
    public void shareFile(String path, String name){
        new ShareFileConnection(path, name, this, ipServer, ipLocal);
    }
    
    
    
}

