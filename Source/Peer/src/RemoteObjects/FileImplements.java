/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RemoteObjects;
;
import Model.ClientPeer;
import Model.Piece;
import Model.enumStateFile;
import utils.Utils;
import RMI.InterfaceAddresses;
import RMI.InterfaceFile;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Michael
 */
public class FileImplements extends UnicastRemoteObject implements InterfaceFile {

    private ClientPeer tracker;
    
    public FileImplements (ClientPeer tracker) throws RemoteException{
        super();
        this.tracker = tracker;
    }
    
    @Override
    public Piece downloadPiece(String nameFile, int n) throws Exception {
       String d=Utils.readClientFile(nameFile+".txt").get(n-1);
       Piece p=new Piece();
       p.setData(d);
       p.setPercentage(100.0);
       p.setState(enumStateFile.COMPLETE);
        return p;
    }

    @Override
    public Piece exchangesPiece(String namePiece, int n, int[] changes) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
