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
public interface InterfaceRunTime  extends Remote{
    
    public boolean start() throws Exception;
    
    public boolean inOnLine() throws Exception;
}
