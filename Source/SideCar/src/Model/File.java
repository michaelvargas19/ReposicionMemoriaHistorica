/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Michael
 */
public class File implements Serializable{
    private String name;
    private enumStateFile state;
    private Map<Integer,Piece> pieces;
    
    
    
    public File(){
        this.pieces = new HashMap<Integer,Piece>();
    }
    public File (String name, enumStateFile state){
        this.name = name;
        this.state = state;
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Integer, Piece> getPieces() {
        return pieces;
    }

    public void setPieces(Map<Integer, Piece> pieces) {
        this.pieces = pieces;
    }

    public enumStateFile getState() {
        return state;
    }

    public void setState(enumStateFile state) {
        this.state = state;
    }
    
    
    
    
    
}
