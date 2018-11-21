/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RemoteObjects;


import Business.ClientPeer;
import Model.File;
import Model.Piece;
import Model.enumStateFile;
import Persistence.ManejoArchivos;
import RMI.InterfaceAddresses;
import RMI.InterfaceFile;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

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
        String rute = "Data//Files//"+nameFile+".txt";
        String d= ManejoArchivos.readClientFile(rute).get(n-1);
        Piece p = new Piece(d);
        p.setData(d);
        p.setPercentage(100.0);
        p.setState(enumStateFile.COMPLETE);
       return p;
       
       //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Piece exchangesPiece(String namePiece, int n, int[] changes) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean receiveFile(List<String> datas, String path, String name) throws Exception {
        
        String nameFile = name;
        nameFile = nameFile.substring(0,nameFile.length()-4);
        nameFile = nameFile+"-Shared";
        
        System.out.println("Se recivió --> "+nameFile);
                
         try {

            ManejoArchivos.writeFileNew("Data//nameFiles.txt", nameFile);
            clientPeer.getFiles().add(new File(nameFile, enumStateFile.COMPLETE));
            clientPeer.getHome().updateFiles(clientPeer.getFiles());

            int l = 1;
            for(String s : datas){
                ManejoArchivos.writeFile("Data//Files//"+nameFile, l, s);
                l++;
            }
            
            JOptionPane.showMessageDialog(this.clientPeer.getHome(),"File "+nameFile+" Received");
            clientPeer.updateOnServer(nameFile);

        } catch (Exception e) {}
        return true;
    }
    
}
