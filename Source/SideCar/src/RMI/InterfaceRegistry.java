/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import Model.Register;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Michael
 */
public interface InterfaceRegistry extends Remote{
    
    public boolean register(Register register) throws RemoteException;
    
}
