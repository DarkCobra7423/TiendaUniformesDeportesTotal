/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formulario;

import Conectar.Conectar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author TAMY
 */
public class CorteCaja extends javax.swing.JInternalFrame {
    
    DefaultTableModel model;

    /**
     * Creates new form CorteCaja
     */
    public CorteCaja() {
        initComponents();
        cargar("");
    }
    
    void cargar(String valor){
        try{
            String [] titulos={"ID","CAJERO","APERTURA","HORA","SALDO FINAL"};
            String [] registros=new String[7];
            model=new DefaultTableModel(null, titulos);
            String cons="SELECT * FROM `cortecaja` WHERE CONCAT (idCajero,'',no_caja,'',cajero,'',fecha_inicial,'',fecha_final,'',fecha_corte,'',saldoinicial,'',saldofinal,'',hora) LIKE '%"+valor+"%'";
            
            Statement st = cn.createStatement();
            ResultSet rs= st.executeQuery(cons);
            
            while(rs.next()){
                registros[0]=rs.getString(1);//ID
                registros[1]=rs.getString(3);//CAJERO
                registros[2]=rs.getString(4);//APERTURA
                registros[3]=rs.getString(9);//HORA
                registros[4]=rs.getString(8);//SALDO FINAL
                
                
                model.addRow(registros);
            }
            
            tbCorteCaja.setModel(model);
            /*
            tbProductos.getColumnModel().getColumn(0).setPreferredWidth(50);
            tbProductos.getColumnModel().getColumn(1).setPreferredWidth(200);
            tbProductos.getColumnModel().getColumn(2).setPreferredWidth(10);
            tbProductos.getColumnModel().getColumn(3).setPreferredWidth(20);
            tbProductos.getColumnModel().getColumn(4).setPreferredWidth(5);
            tbProductos.getColumnModel().getColumn(5).setPreferredWidth(20);
            */
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error en el metodo de cargar Clase Registro "+e);
        }
    }
    
    void Guardar(){
        String idcajero, fechainicial, fechacorte, cajero, saldoinicial, saldofinal, hora, sql;
        
        idcajero="0";
        fechainicial=txtFechaInicial.getText();
        fechacorte=txtFechaCorte.getText();
        cajero=txtCajero.getText();
        saldoinicial=txtSaldoInicial.getText();
        saldofinal=txtSaldoFinal.getText();
        hora="12:34:50";
        
        //vacios
        String nocaja="1", fechafinal=fechainicial;
        
        
        sql="INSERT INTO cortecaja (`idCajero`, `no_caja`, `cajero`, `fecha_inicial`, `fecha_final`, `fecha_corte`, `saldoinicial`, `saldofinal`, `hora`) VALUES (?,?,?,?,?,?,?,?,?)";
        
        try{
         PreparedStatement pst =cn.prepareStatement(sql);
         pst.setString(1, idcajero);
         pst.setString(2, nocaja);
         pst.setString(3, cajero);
         pst.setString(4, fechainicial);
         pst.setString(5, fechafinal);
         pst.setString(6, fechacorte);
         pst.setString(7, saldoinicial);
         pst.setString(8, saldofinal);
         pst.setString(9, hora);
         
         int n=pst.executeUpdate();
         
         if(n>0){
             JOptionPane.showMessageDialog(null, "Corte registrado con exito");
             Limpiar();
         }
         cargar("");
         
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error en el metodo de Guardar Clase Rregistros \n"+ex);
        }
        
    }
    
    void Limpiar(){
        txtFechaInicial.setText("");
        txtFechaCorte.setText("");
        txtCajero.setText("");
        txtSaldoInicial.setText("");
        txtSaldoFinal.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txtFechaInicial = new javax.swing.JTextField();
        txtFechaCorte = new javax.swing.JTextField();
        txtCajero = new javax.swing.JTextField();
        txtSaldoInicial = new javax.swing.JTextField();
        txtSaldoFinal = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbCorteCaja = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Revision De Corte De Caja");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Descripcion Del Corte De Caja"));

        jLabel1.setText("Fecha Inicial:");

        jLabel2.setText("Fecha De Corte:");

        jLabel3.setText("Nombre Cajero");

        jLabel4.setText("Saldo inicial:");

        jLabel5.setText("Saldo Final:");

        jButton1.setText("Realizar Corte");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Salir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 44, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCajero)
                            .addComponent(txtSaldoInicial)
                            .addComponent(txtSaldoFinal)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFechaCorte)
                            .addComponent(txtFechaInicial))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtFechaCorte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtCajero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtSaldoInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtSaldoFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 78, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        tbCorteCaja.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "CAJERO", "APERTURA", "HORA", "SALDO FINAL"
            }
        ));
        jScrollPane1.setViewportView(tbCorteCaja);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Guardar();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbCorteCaja;
    private javax.swing.JTextField txtCajero;
    private javax.swing.JTextField txtFechaCorte;
    private javax.swing.JTextField txtFechaInicial;
    private javax.swing.JTextField txtSaldoFinal;
    private javax.swing.JTextField txtSaldoInicial;
    // End of variables declaration//GEN-END:variables
    Conectar cc = new Conectar();
    Connection cn=cc.conexion();
}