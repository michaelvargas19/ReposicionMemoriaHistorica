/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import Model.Piece;
import java.rmi.Remote;

/**
 *
 * @author Michael
 */
public interface InterfaceFile  extends Remote{
    
    public Piece downloadPiece(String nameFile, int n) throws Exception;
    
    public Piece exchangesPiece(String namePiece, int n, int []changes ) throws Exception;
    
    
}
