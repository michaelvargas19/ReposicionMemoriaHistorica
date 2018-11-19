/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import Model.ClientPeer;
import Model.Piece;
import RMI.InterfaceFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michael
 */
public class DownloadPieceConnection extends Thread{
    
    private InterfaceFile interFile;
    private ClientPeer clientPeer;
    private String nameFile;
    private int n;
    private DownloadConnection downConnection;

    
    
    public DownloadPieceConnection(InterfaceFile inter, ClientPeer cl, String nameFile, int n, DownloadConnection dwC){
        this.interFile = inter;
        this.clientPeer = cl;
        this.nameFile = nameFile;
        this.n = n;
        this.downConnection = dwC;
       this.start();
    }
    
    @Override
    public void run(){
        
        try {
            Piece p = interFile.downloadPiece(nameFile, n);
            System.err.println("Downloaded " + p.getData() + " ....#########3");
            downConnection.pieceDownloaded(n);
        } catch (Exception ex) {
            try {
                downConnection.errorDownloadPiece(n, nameFile);
            } catch (Exception ex1) {
                Logger.getLogger(DownloadPieceConnection.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        
    }
            
}
