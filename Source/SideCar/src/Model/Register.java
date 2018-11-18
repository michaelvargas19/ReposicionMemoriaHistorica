/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Michael
 */
public class Register implements Serializable {
 
    private String ipClient;
    private enumTypeRegister typeRegister;
    private enumStateUser stateUser;
    private enumStateFile stateFile;
    private List<File> files;
    private File file;
    
    public Register (){
        this.files = new ArrayList<File>();
    }

    public String getIpClient() {
        return ipClient;
    }

    public void setIpClient(String ipClient) {
        this.ipClient = ipClient;
    }

    public enumTypeRegister getTypeRegister() {
        return typeRegister;
    }

    public void setTypeRegister(enumTypeRegister typeRegister) {
        this.typeRegister = typeRegister;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }



    public enumStateUser getStateUser() {
        return stateUser;
    }

    public void setStateUser(enumStateUser stateUser) {
        this.stateUser = stateUser;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    
    
}
