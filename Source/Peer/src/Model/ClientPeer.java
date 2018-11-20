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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import utils.Utils;
import static utils.Utils.readClientFile;

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
        
        
            Register r = new Register();
            r.setFiles(fls);
            r.setTypeRegister(enumTypeRegister.REGISTER);
            r.setIpClient(getIpLocal());

            interRegistry.register(r);
     }
    
    
   
    
    private void uploadFiles(){
    
        ArrayList<String> names = new ArrayList<String>();

        try {
        names = Utils.readClientFile("cliente.txt");
            
        } catch (Exception e) {
            System.out.println("error cargando archivos");
        }
	
        files.clear();
        for (String s: names) {
            File f = new File();
            f.setName(s);
            f.setState(enumStateFile.COMPLETE);
            this.files.add(f);
            System.out.println("comprobando ... "+s);
        }
        
        home.updateFiles(files);
        
        
        //subir el servicio de descarga
       /*DownloadConnection dc= new DownloadConnection(null, null);
       dc.start();;
        */
    }
    
    public void start() throws NotBoundException, Exception{
        System.out.println("anser request .........");
        answerRequest();
    }
    
    
    private void answerRequest(){
        
        try {
          
            DirectionsConnection dc= new DirectionsConnection(new ArrayList<>(), ipServer)
                    //System.out.println(getDirectionsToFile("file1").toString()+"RunTime xd xd ");
;   
              // System.out.println(dc.getDirectionsToFile(ipLocal));
            
        } catch (Exception ex) {
            Logger.getLogger(ClientPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void downloadFile(String file){
        
        DirectionsConnection dc= new DirectionsConnection(new ArrayList<>(), ipServer);
        List<String> datos=new  ArrayList<String>();
        try {
            datos =Utils.readClientFile(file);
        } catch(Exception e){
            System.out.println("error leyendo archivo client");
        }
        
        for(String l:datos){
            String archivo=l.split("-")[0];
            String linea=l.split("-")[1];
            archivo=archivo.substring(0,archivo.length()-4);
            List<Peer> peers=null;
            try {
            peers=dc.getDirectionsToFile(archivo);
            } catch (Exception e){
                System.out.println("Error obteniendo peers");
            }
            for (Peer p:peers){
          //      System.out.println("peer "+p.getIp());
                try {
                    
                String res=  new DownloadConnection(p, null).download(archivo, Integer.valueOf(linea));
                    System.out.println("respuesta--->"+res);
                    Utils.writeFile("archivo", Integer.valueOf(linea), res);
                    
                } catch (Exception e) {
                    System.out.println("error download"+e);
                }
            }
            
        }
        //dc.getDirectionsToFile(file);
        
    }
    private void init() throws RemoteException{
        myRegistry = LocateRegistry.createRegistry(888); 
        myRegistry.rebind("RunTime", new RunTimeImplements(this));
        myRegistry.rebind("File", new FileImplements(this));
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

