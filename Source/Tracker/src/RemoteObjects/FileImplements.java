/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RemoteObjects;

import Business.Tracker;
import Model.Piece;
import RMI.InterfaceAddresses;
import RMI.InterfaceFile;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Michael
 */
public class FileImplements extends UnicastRemoteObject implements InterfaceFile {

    private Tracker tracker;
    
    public FileImplements (Tracker tracker) throws RemoteException{
        super();
        this.tracker = tracker;
    }
    
    @Override
    public Piece downloadPiece(String nameFile, int n) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Piece exchangesPiece(String namePiece, int n, int[] changes) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
