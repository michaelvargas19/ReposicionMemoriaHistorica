/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Business.Tracker;
import Model.File;
import Model.Peer;
import Model.enumStateSwarm;
import Model.enumStateUser;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Michael
 */
public class Home extends java.awt.Frame {

    private Tracker tracker;
    
    /**
     * Creates new form Home
     */
    public Home() {
        this.tracker = new Tracker(this);
        initComponents();
        
        
        String[] columnNamesPeers = {"IP","State"};
        
        this.modelPeers = new DefaultTableModel(columnNamesPeers, 0);
        this.jTable1.setModel(modelPeers);
        
        String[] columnNamesFiles = { "Name File", "IP - Client","State"};
        
        this.modelFiles = new DefaultTableModel(columnNamesFiles, 0);
        this.jTable2.setModel(modelFiles);
              
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setName("Peers"); // NOI18N
        jScrollPane1.setViewportView(jTable1);
        jTable1.getAccessibleContext().setAccessibleName("");

        jTabbedPane2.addTab("tab1", jScrollPane1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jLayeredPane1.add(jScrollPane2);
        jScrollPane2.setBounds(30, 30, 350, 200);

        jLabel1.setText("jLabel1");
        jLayeredPane1.add(jLabel1);
        jLabel1.setBounds(30, 10, 34, 14);

        jTabbedPane2.addTab("tab2", jLayeredPane1);

        add(jTabbedPane2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Exit the Application
     */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
    private DefaultTableModel modelPeers;
    private DefaultTableModel modelFiles;
    
    
    
    //----------------------------------------------------------------------------------------
    
    
    public void updatePeers(List<Peer> swarm) {

        //JOptionPane.showMessageDialog(this, swarm.size());
        
        vaciarModelo(this.modelPeers);
        
        for (Peer p : swarm) {           
            String [] datos={p.getIp(),""};
            this.modelPeers.addRow(datos);
        }

    }
    
    public void updateFiles(Map<String, Map<Peer,enumStateUser> > mapFiles) {

        //JOptionPane.showMessageDialog(this, swarm.size());
        
        vaciarModelo(this.modelFiles);
        
        for (Map.Entry<String, Map<Peer,enumStateUser> > entry : mapFiles.entrySet()) {
            
            for (Map.Entry<Peer,enumStateUser> entryFile : entry.getValue().entrySet()) {
                String [] datos={entry.getKey(),entryFile.getKey().getIp(),entryFile.getValue().toString()};
                this.modelFiles.addRow(datos);
            }
            
        }
    
    }
    
    private void vaciarModelo(DefaultTableModel m){

        int numDatos = m.getRowCount();

        while(m.getRowCount()>0) {
                m.removeRow(0);
        }
    }


}


