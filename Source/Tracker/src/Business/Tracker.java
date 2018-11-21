/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import GUI.Home;
import Model.*;
import Model.enumStateUser;
import RemoteObjects.AddressesImplements;

import java.rmi.*;
import RemoteObjects.RegistryImplements;

import Threads.StartConnection;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author Michael
 */
public class Tracker {
    
    private Home home;
    private Map<Peer,enumStateSwarm> swarm;
    private Map<String, Map<Peer,enumStateUser> > mapFiles;
    private Registry myRegistry;
    private Map<String,Peer> mapCache;

    public Tracker(Home home){
        this.mapFiles = new HashMap<>();
        this.swarm = new HashMap<>();
        this.mapCache = new HashMap<>();        
        this.home = home;
        init();

    }
    
    
    
    private void init(){
        try{
            //RegistryImplements registry = new RegistryImplements();
            //InterfaceRegistry  Iregistry = (InterfaceRegistry) UnicastRemoteObject.exportObject(registry,0);
            
            System.setProperty("java.rmi.server.hostname","10.192.101.21");
            
            myRegistry = LocateRegistry.createRegistry(777); 
            
            
            myRegistry.rebind("Addresses", new AddressesImplements(this));

            myRegistry.rebind("Registry", new RegistryImplements(this));
            
            
            
            System.out.println("Server On...!!!");
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public boolean register(Register register){
        boolean exist = false;
        List<Peer> peers = new ArrayList<Peer>();
        
        Peer peer = new Peer();
        peer.setIp(register.getIpClient());
              
           
        for (Map.Entry<Peer, enumStateSwarm> entry : swarm.entrySet()) {
            peers.add(entry.getKey());
            if(entry.getKey().getIp().compareTo(peer.getIp()) == 0)
                exist = true;
        }
        
        
        if(!exist){
            swarm.put(peer,enumStateSwarm.ONLINE);
            peers.add(peer);
            home.updatePeers(peers);
            
            enumStateUser state;
            
            
            for(File f : register.getFiles()){
                
                if(f.getState() == enumStateFile.COMPLETE){
                    state = enumStateUser.SEED;
                }else{
                    state = enumStateUser.LEECHER;
                }
                
                Map<Peer,enumStateUser> map= new HashMap<Peer,enumStateUser>();
                map.put(peer, state);
            
                mapFiles.put(f.getName(),map);
                
                
                
            }
            
            System.out.println(mapFiles.toString()+"----");
            home.updateFiles(mapFiles);
            
            
            return true;
        }
        
        return false;
    }
    
    
    public boolean updateFile(Register register){
        boolean exist = false;
                
        Peer p = getPeer(register.getIpClient());
            
        Map<Peer, enumStateUser> map = (Map<Peer, enumStateUser>) mapFiles.get(register.getFile().getName());
            
            if (map == null) {
                Map<Peer, enumStateUser> map2 = new HashMap<Peer, enumStateUser>();

                Peer peer = new Peer();
                
                peer.setIp(register.getIpClient());
                map2.put(peer, enumStateUser.SEED);
                
                mapFiles.put(register.getFile().getName(), map2);

                home.updateFiles(mapFiles);

                return false;
        }
        System.out.println(register.getFile().getName()+ "--"+ p);
                
        if(register.getFile().getState() == enumStateFile.COMPLETE){
            map.replace(p,enumStateUser.SEED );
        }else{
            map.replace(p,enumStateUser.LEECHER );
        }
        
        home.updateFiles(mapFiles);
        
        return true;
        
    }
    
    
//------------------------------------------------------------------------------------------------
    
    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public Map<Peer, enumStateSwarm> getSwarm() {
        return swarm;
    }

    public void setSwarm(Map<Peer, enumStateSwarm> swarm) {
        this.swarm = swarm;
    }
    
    private Peer getPeer(String ip){
        
        for (Map.Entry<Peer, enumStateSwarm> entry : swarm.entrySet()) {
            Peer p = (Peer) entry.getKey();
            
            if(p.getIp().compareTo(ip) == 0){
                return p;
            }
        }
        
        return null;
    }
    

    public List<Peer> getPeersToFile(String nameFile){
        
    List<Peer> peers = new ArrayList<Peer> ();
    for (Map.Entry<String, Map<Peer,enumStateUser> > entry : mapFiles.entrySet()) {
        if(entry.getKey().compareTo(nameFile) == 0)
        for (Map.Entry<Peer,enumStateUser> entryFile : entry.getValue().entrySet()) {
            if(entryFile.getValue() == enumStateUser.SEED)
                peers.add(entryFile.getKey());

        }
            
    }
        System.out.println("Directions had sended to File -->> "+nameFile);
        
        Collections.shuffle(peers);
        return peers;
    }
    
    public List<Peer> getPeersToShare(){
        
    List<Peer> peers = new ArrayList<Peer> ();
    for (Map.Entry<Peer, enumStateSwarm > entry : swarm.entrySet()) {
        peers.add(entry.getKey());
    }
        
        return peers;
    }
    

    public Map<String, Map<Peer, enumStateUser>> getMapFiles() {
        return mapFiles;
    }

    public void setMapFiles(Map<String, Map<Peer, enumStateUser>> mapFiles) {
        this.mapFiles = mapFiles;
    }
    
    
    public void startClients(){
        new StartConnection(this);
    }
    
    
    
}
