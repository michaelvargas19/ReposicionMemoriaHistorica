/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RemoteObjects;

import Model.ClientPeer;
import RMI.InterfaceRunTime;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Michael
 */
public class RunTimeImplements extends UnicastRemoteObject implements InterfaceRunTime{

    private ClientPeer clientPeer;
    
    public RunTimeImplements(ClientPeer cp) throws RemoteException{
        super();
        this.clientPeer = cp;
    }
    
    
    @Override
    public boolean start() throws Exception {
        clientPeer.start();
        return true;
    }

    @Override
    public boolean inOnLine() throws Exception {
        System.out.println("Online...");
        return true;
    }
    
}
