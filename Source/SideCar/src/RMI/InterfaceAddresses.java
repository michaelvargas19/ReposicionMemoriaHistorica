/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import Model.Peer;
import Model.enumStateFile;
import java.rmi.Remote;
import java.util.List;

/**
 *
 * @author Michael
 */
public interface InterfaceAddresses extends Remote{
    
    public List<Peer> addressesToFile(String nameFile) throws Exception;

    public List<Peer> allAddresses() throws Exception;

    public boolean updateStateUser(String nameFile,enumStateFile satateFile) throws Exception;
    
}
