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
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author TAMY
 */
public class SistemaApartado extends javax.swing.JInternalFrame {

    DefaultTableModel model;
    /**
     * Creates new form SistemaApartado
     */
    public SistemaApartado() {
        initComponents();
        cargar("");
    }
    
     void cargar(String valor){
        try{
            String [] titulos={"ID","NOMBRE","APELLIDOS","TELCEL","DIRECCION","PRO. APARTADO","TIPO ABONO","TOTAL","FECHA","ACUERDO", "ABONO", "DEUDA ACTUAL", "OBSERVACIONES"};
            String [] registros=new String[13];
            model=new DefaultTableModel(null, titulos);
            String cons="SELECT * FROM sistemaapartado WHERE CONCAT (idCliente,'',nombre,'',apellidos,'',telcel,'',direccion,'',productoApartado,'',tipoAbono,'',total,'',fecha,'',acuerdoApagar,'',observaciones) LIKE '%"+valor+"%'";
            
            Statement st = cn.createStatement();
            ResultSet rs= st.executeQuery(cons);
            
            while(rs.next()){
                registros[0]=rs.getString(1);
                registros[1]=rs.getString(2);
                registros[2]=rs.getString(3);
                registros[3]=rs.getString(4);
                registros[4]=rs.getString(5);
                registros[5]=rs.getString(6);
                registros[6]=rs.getString(7);
                registros[7]=rs.getString(8);
                registros[8]=rs.getString(9);
                registros[9]=rs.getString(10);
                registros[10]=rs.getString(11);
                registros[11]=rs.getString(12);
                registros[12]=rs.getString(13);
                
                
                model.addRow(registros);
            }
            
            tbProductos.setModel(model);
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
    }   //CONCLUIDO 
     
     void BuscarAbonar(String cod){
        try{
            String idCliente="", nombre="", apellido="", abono="",telcel="", deudactual="", observaciones="";
            String cons = "SELECT * FROM `sistemaapartado` WHERE `idCliente`='"+cod+"'";
            Statement st = cn.createStatement();
            ResultSet rs= st.executeQuery(cons);
            
            while(rs.next()){
                idCliente=rs.getString(1);
                nombre=rs.getString(2);
                apellido=rs.getString(3);
                abono=rs.getString(11);
                telcel=rs.getString(4);
                deudactual=rs.getString(12);
                observaciones=rs.getString(13);
                
            }
            
            txtIdCliente.setText(idCliente);
            txtNombre.setText(nombre);
            txtApellidos.setText(apellido);
            //txtAbono.setText(abono);
            txtTelcel.setText(telcel);
            txtDeudaActual.setText(deudactual);
            txtObservaciones.setText(observaciones);
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error en el metodo BuscarAbonar de la Clase S.A. \n "+e);
            
        }
    }
     
     void Realizar_Abono(){
         
        String sql="UPDATE sistemaapartado SET `telcel`='"+txtTelcel.getText()+"', `abono`='"+txtAbono.getText()+"', `deuda_actual`='"+txtDeudaActual.getText()+"', `observaciones`='"+txtObservaciones.getText()+"' WHERE `idCliente`='"+txtIdCliente.getText()+"'";

        try{
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Abono Actualizado");
            cargar("");
            Limpiar();

        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error en metodo abonar de la Clase sa \n"+e);
        }
     }
     
     void Limpiar(){
         txtIdCliente.setText("");
         txtNombre.setText("");
         txtApellidos.setText("");
         txtAbono.setText("");
         txtTelcel.setText("");
         txtObservaciones.setText("");
         txtDeudaActual.setText("");
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tbProductos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtIdCliente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtAbono = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtApellidos = new javax.swing.JTextField();
        txtTelcel = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnRealizarAbono = new javax.swing.JButton();
        txtDeudaActual = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtObservaciones = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Sistema De Apartado");

        tbProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tbProductos);

        jLabel1.setText("Buscar:");

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        jLabel2.setText("Clientes Registrados");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Abonar Al Cliente"));

        jLabel3.setText("Id Cliente");

        txtIdCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdClienteActionPerformed(evt);
            }
        });

        jLabel4.setText("Abono:");

        jLabel5.setText("Nombre:");

        jLabel6.setText("Apellidos:");

        jLabel7.setText("Tel.Cel.:");

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        jLabel8.setText("Deuda Actual");

        btnRealizarAbono.setText("Realizar Abono");
        btnRealizarAbono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRealizarAbonoActionPerformed(evt);
            }
        });

        jLabel9.setText("Observaciones:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(15, 15, 15)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                            .addComponent(txtIdCliente))
                        .addGap(47, 47, 47)
                        .addComponent(jLabel6)))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDeudaActual, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRealizarAbono)
                        .addGap(22, 22, 22))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtAbono))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTelcel)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtAbono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelcel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtDeudaActual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRealizarAbono))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void txtIdClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdClienteActionPerformed
        // TODO add your handling code here:
        BuscarAbonar(txtIdCliente.getText());
    }//GEN-LAST:event_txtIdClienteActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
        BuscarAbonar(txtNombre.getText());
    }//GEN-LAST:event_txtNombreActionPerformed

    private void btnRealizarAbonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRealizarAbonoActionPerformed
        // TODO add your handling code here:
        Realizar_Abono();
    }//GEN-LAST:event_btnRealizarAbonoActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        // TODO add your handling code here:
        cargar(txtBuscar.getText());
    }//GEN-LAST:event_txtBuscarKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRealizarAbono;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbProductos;
    private javax.swing.JTextField txtAbono;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtDeudaActual;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtObservaciones;
    private javax.swing.JTextField txtTelcel;
    // End of variables declaration//GEN-END:variables
    Conectar cc= new Conectar();
    Connection cn=cc.conexion();
}
