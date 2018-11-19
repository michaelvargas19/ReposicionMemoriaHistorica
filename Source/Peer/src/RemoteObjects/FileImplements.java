/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RemoteObjects;


import Model.ClientPeer;
import Model.Piece;
import Model.enumStateFile;
import RMI.InterfaceAddresses;
import RMI.InterfaceFile;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Michael
 */
public class FileImplements extends UnicastRemoteObject implements InterfaceFile {

    private ClientPeer clientPeer;
    
    public FileImplements (ClientPeer clientPeer) throws RemoteException{
        super();
        this.clientPeer = clientPeer;
    }
    
    @Override
    public Piece downloadPiece(String nameFile, int n) throws Exception {
        Piece p = new Piece(nameFile);
        p.setState(enumStateFile.COMPLETE);
        
       return p;
       //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Piece exchangesPiece(String namePiece, int n, int[] changes) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
