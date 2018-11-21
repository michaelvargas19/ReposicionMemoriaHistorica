/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RemoteObjects;

import Business.Tracker;
import Model.Peer;
import Model.enumStateFile;
import RMI.InterfaceAddresses;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 *
 * @author Michael
 */
public class AddressesImplements extends UnicastRemoteObject implements InterfaceAddresses{

    private Tracker tracker;
    
    
    public AddressesImplements (Tracker tracker) throws RemoteException{
        super();
        this.tracker = tracker;
    }

    @Override
    public List<Peer> addressesToFile(String nameFile) throws Exception {
        return tracker.getPeersToFile(nameFile);
    }

    @Override
    public boolean updateStateUser(String nameFile, enumStateFile satateFile) throws Exception {
        return true;
    }

    @Override
    public List<Peer> allAddresses() throws Exception {
        return tracker.getPeersToShare();
    }
    
    
}

