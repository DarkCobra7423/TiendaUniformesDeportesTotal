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
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CARLOS
 */
public class Venta extends javax.swing.JInternalFrame {

    DefaultTableModel md;
    String data[][]={};
    String cabeza[]={"Cantidad","Codigo","Descripcion","Total"};
    
    
    /**
     * Creates new form Venta
     */
    public Venta() {
        initComponents();
        this.setLocation(200, 50);
        jlFecha.setText(fechaact());
        Reloj hilo1 = new Reloj(jlHora);
        hilo1.start();
        NumeroBoleta();
        ControlAcceso ca=new ControlAcceso();
        jlCajero.setText(ca.nomGlobal+" "+ca.apellidoGlobal);
        
        /////////PROPIEDADES DE LA TABLA DE VENTAS////////
        md=new DefaultTableModel(data, cabeza);
        tbVentas.setModel(md);
        tbVentas.getColumnModel().getColumn(0).setPreferredWidth(50);
        tbVentas.getColumnModel().getColumn(1).setPreferredWidth(50);
        tbVentas.getColumnModel().getColumn(2).setPreferredWidth(300);
        tbVentas.getColumnModel().getColumn(3).setPreferredWidth(50);
        //tbVentas.getColumnModel().addColumn(4).setPreferredWidth(50);
        /////////////////////////////////////////////////
    }
    
    public static String fechaact(){
        java.util.Date fecha = new java.util.Date();
        SimpleDateFormat formatofecha=new SimpleDateFormat("dd/MM/YYYY");
        return formatofecha.format(fecha);
    }   //CONCLUIDO
    
    void DescontarExistencia(String codigo, String cantidad){
        int des=Integer.parseInt(cantidad);
        String cap="";
        int desfinal;
        String consul="SELECT * FROM productos WHERE cod_pro='"+codigo+"'";
        
        try{
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery(consul);
            
            while(rs.next()){
                cap=rs.getString(5);
            }
            
        }catch(Exception e){
            
        }
        desfinal=Integer.parseInt(cap)-des;
        String modi="UPDATE productos SET exist_pro='"+desfinal+"'WHERE cod_pro='"+codigo+"'";
        try{
            PreparedStatement pst=cn.prepareStatement(modi);
            pst.executeUpdate();
        }catch(Exception e){
            
        }
    }   //CONCLUIDO
    
    void NumeroBoleta(){
        String c="";
        String SQL="SELECT MAX(`codboleta_ven`)FROM ventas";
        
        try{
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery(SQL);
            
            if(rs.next()){
                c=rs.getString(1);
            }
            if(c==null){
                jlFolio.setText("00000001");
            }else{
                int j=Integer.parseInt(c);
                GenradorCodigosSeriales gen = new GenradorCodigosSeriales();
                gen.generar(j);
                jlFolio.setText(gen.serie());  
            }

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al cargar el folio");
        }
    }   //CONCLUIDO
    
    void Calcular(){
        String pre;
        String can;
        double igv=0;
        double total=0;
        double subtotal=0;
        double precio;
        int cantidad;
        double imp=0.0;
        double efevo=0, cambio=0, totall=0;
        
        for(int i=0; i<tbVentas.getRowCount(); i++){
            pre=tbVentas.getValueAt(i, 3).toString();
            can=tbVentas.getValueAt(i, 0).toString();
            precio=Double.parseDouble(pre);
            cantidad=Integer.parseInt(can);
            imp=precio*cantidad;
            subtotal=subtotal+imp;
            
            //tbVentas.setValueAt("$"+Math.rint(imp*100)/100, i, 5);
            jlTotal.setText("$"+Math.rint(subtotal*100)/100);
            
        }
        
        //txtSubtotal.setText("$"+Math.rint(subtotal));
        //jlTotal.setText("$"+Math.rint(subtotal*100)/100);
        
        totall=Math.rint(subtotal*100)/100;
        efevo=Double.parseDouble(txtEfevo.getText());
        cambio=efevo-totall;
        if(efevo<totall){
            jlCambio.setText("Efectivo Insuficiente");
            JOptionPane.showMessageDialog(null, "Efectivo Insuficiente");
        }else{
            jlCambio.setText("$"+String.valueOf(cambio));
        }
        
        
    }   //CONCLUIDO
    
    void Ventas(){
        String InsertarSQL="INSERT INTO `ventas` (`cod_ven`, `horafecha_ven`, `codboleta_ven`, `subtotal_ven`, `total_ven`) VALUES (?,?,?,?,?)";
        String cod_venta="0";
        String fech_hora=jlFecha.getText()+"_"+jlHora.getText();
        String cod_boleta=jlFolio.getText();
        String subtotal=jlTotal.getText();
        String total=jlTotal.getText();
        
        try{
            PreparedStatement pst = cn.prepareStatement(InsertarSQL);
            pst.setString(1, cod_venta);
            pst.setString(2, fech_hora);
            pst.setString(3, cod_boleta);
            pst.setString(4, subtotal);
            pst.setString(5, total);
            
            int n=pst.executeUpdate();
            
            if(n>0){
                int confirmar=JOptionPane.showConfirmDialog(null, "Venta registrada con exito", "Registro de venta", JOptionPane.CLOSED_OPTION);
                
                if(confirmar==JOptionPane.YES_OPTION){ 
                    txtCod.setText("");
                    jlFolio.setText("");
                    jlTotal.setText("");
                    txtEfevo.setText("");
                    jlCambio.setText("");
                    NumeroBoleta();
                    DefaultTableModel modelo = (DefaultTableModel) tbVentas.getModel();
                    int a=tbVentas.getRowCount()-1;
                    int i;
                    for(i=a;i>=0;i--){
                        modelo.removeRow(i);
                    }
                }
                
            }
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Metodo Venta Fallando "+ex);
        
        }
    }   //CONCLUIDO
    
    String id="",id1="",fechahora="",codigo="",descripcion="",cantidad="",preciounitario="",total="";
    //id horafecha codigo descripcion cantidad preciounitario total
    void EnviarRegistro(){  //SE ENVIA A LA BITACORA TABLA CONSULTA
        String InsertarSQL="INSERT INTO consultas (`id_con`, `horafecha_con`, `cod_con`, `desc_con`, `cant_con`, `preu_con`, `total_con`) VALUES (?,?,?,?,?,?,?)";
       
        for(int i=0;i<tbVentas.getRowCount();i++){
            id="0";
            fechahora=jlFecha.getText()+" "+jlHora.getText();
            codigo=tbVentas.getValueAt(i, 1).toString();
            descripcion=tbVentas.getValueAt(i, 2).toString();
            cantidad=tbVentas.getValueAt(i, 0).toString();
            preciounitario=tbVentas.getValueAt(i, 3).toString();
            total=tbVentas.getValueAt(i, 3).toString();
                    
        try{
            PreparedStatement pst = cn.prepareStatement(InsertarSQL);
            
            pst.setString(1, id);
            pst.setString(2, fechahora);
            pst.setString(3, codigo);
            pst.setString(4, descripcion);
            pst.setString(5, cantidad);
            pst.setString(6, preciounitario);
            pst.setString(7, total);
            
            int n=pst.executeUpdate();
            if(n>0){ 
               // JOptionPane.showMessageDialog(null, "Consulta registrada");
                
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Enviar Registros "+ex);
            //Logger.getLogger(Venta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }///fin del metodo enviar rg
    
     void EnviarRegistro1(){  //SE ENVIA A LA BITACORA TABLA CONSULTA
       // String InsertarSQL="INSERT INTO consultas (`id_con`, `horafecha_con`, `cod_con`, `desc_con`, `cant_con`, `preu_con`, `total_con`) VALUES (?,?,?,?,?,?,?)";
        String InsertarSQL="INSERT INTO diaria (`idcod_dia`, `id_dia`, `codboleta_dia`, `cod_dia`, `horafecha_dia`, `desc_dia`, `cant_dia`, `preu_dia`, `total_dia`) VALUES (?,?,?,?,?,?,?,?)";
        for(int i=0;i<tbVentas.getRowCount();i++){
            id="0";
            id1="1213123";
            fechahora=jlFecha.getText()+" "+jlHora.getText();
            codigo=tbVentas.getValueAt(i, 1).toString();
            descripcion=tbVentas.getValueAt(i, 2).toString();
            cantidad=tbVentas.getValueAt(i, 0).toString();
            preciounitario=tbVentas.getValueAt(i, 3).toString();
            total=tbVentas.getValueAt(i, 3).toString();
                    
        try{
            PreparedStatement pst = cn.prepareStatement(InsertarSQL);
            
            pst.setString(1, id);
            pst.setString(2, id1);
            pst.setString(3, fechahora);
            pst.setString(4, codigo);
            pst.setString(5, descripcion);
            pst.setString(6, cantidad);
            pst.setString(7, preciounitario);
            pst.setString(8, total);
            
            int n=pst.executeUpdate();
            if(n>0){ 
               // JOptionPane.showMessageDialog(null, "Consulta registrada");
                
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Enviar Registros1 "+ex);
            //Logger.getLogger(Venta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }///fin del metodo enviar rg
    
    void DetalleVenta(){
        for(int i=0; i<tbVentas.getRowCount(); i++){
            String InsertarSQl="INSERT INTO `detalleventa` (`codboleta_detventa`, `cod_detventa`, `cod_pro_detventa`, `desc_detventa`, `cant_detventa`, `pre_detventa`) VALUES (?,?,?,?,?,?);";
            String cod_detventa="0";
            String cod_bol=jlFolio.getText();
            String desc_ven=tbVentas.getValueAt(i, 2).toString();
            String cod_pro_detven=tbVentas.getValueAt(i, 1).toString();
            String cant_ven=tbVentas.getValueAt(i, 0).toString();
            String total=tbVentas.getValueAt(i, 3).toString();
            
            try{
                PreparedStatement pst =cn.prepareStatement(InsertarSQl);
                pst.setString(1, cod_detventa);
                pst.setString(2, cod_bol);
                pst.setString(3, cod_pro_detven);
                pst.setString(4, desc_ven);
                pst.setString(5, cant_ven);
                pst.setString(6, total);
                
                pst.executeUpdate();
                
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(null, "Metodo Detalle Boleta "+ex);
            }
        }
    }   //CONCLUIDO
    
    void CargarProducto(int cod){
        try{
           
            String cons="SELECT cod_pro, desc_pro, sexo_pro, talla_pro, exist_pro, pre_pro FROM productos WHERE cod_pro='"+cod+"'";
        
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery(cons);
            
           String cant="",codi="",descrip="",total="";
           // String cant="",cod="";
            while(rs.next()){
                //String cabeza[]={"Cantidad","Codigo","Descripcion","Precio Unitario","Total"};
            
                cant="1";
                codi=rs.getString(1);
                descrip=rs.getString(2)+" "+rs.getString(3)+" Talla "+rs.getString(4);
                total=rs.getString(6);
                
                String datos[]={cant,codi,descrip,total};
                md.addRow(datos);
               
            }
            
            txtCod.setText("");
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "txtCodigo relasse "+ex);
        }
    }   //CONCLUIDO
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbVentas = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jlCajero = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtCod = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jlTotal = new javax.swing.JLabel();
        jlCambio = new javax.swing.JLabel();
        txtEfevo = new javax.swing.JTextField();
        jlFecha = new javax.swing.JLabel();
        jlHora = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jlFolio = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Caja");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tbVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cantidad", "Codigo", "Descripcon", "Precio Unitario", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbVentas.setAutoscrolls(false);
        jScrollPane2.setViewportView(tbVentas);
        if (tbVentas.getColumnModel().getColumnCount() > 0) {
            tbVentas.getColumnModel().getColumn(0).setResizable(false);
            tbVentas.getColumnModel().getColumn(0).setPreferredWidth(30);
            tbVentas.getColumnModel().getColumn(1).setResizable(false);
            tbVentas.getColumnModel().getColumn(1).setPreferredWidth(50);
            tbVentas.getColumnModel().getColumn(2).setResizable(false);
            tbVentas.getColumnModel().getColumn(2).setPreferredWidth(300);
            tbVentas.getColumnModel().getColumn(3).setResizable(false);
            tbVentas.getColumnModel().getColumn(3).setPreferredWidth(40);
            tbVentas.getColumnModel().getColumn(4).setResizable(false);
            tbVentas.getColumnModel().getColumn(4).setPreferredWidth(30);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/mini_atlas_12.jpg"))); // NOI18N

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/mini_leon_14.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setText("Cajero:");

        jlCajero.setText("Burro Berto Burrieta Burron");

        txtCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodActionPerformed(evt);
            }
        });
        txtCod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setText("Total:");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setText("Efevo:");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setText("Cambio:");

        jlTotal.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jlTotal.setForeground(new java.awt.Color(0, 204, 204));
        jlTotal.setText("000,000.00");

        jlCambio.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jlCambio.setForeground(new java.awt.Color(0, 0, 204));
        jlCambio.setText("000,000.00");

        txtEfevo.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtEfevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEfevoActionPerformed(evt);
            }
        });
        txtEfevo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEfevoKeyTyped(evt);
            }
        });

        jlFecha.setText("dd/mm/aaaa");

        jlHora.setText("hh:mm:ss");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(211, 211, 211)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtEfevo, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(jlCambio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 192, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlFecha, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlHora, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jlHora)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlFecha)
                .addGap(9, 9, 9))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3))
                    .addComponent(jlTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtEfevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jlCambio))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setText("Folio:");

        jlFolio.setText("0000000000");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(205, 205, 205)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlCajero, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlFolio, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlCajero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addComponent(jlFolio)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodKeyReleased
        // TODO add your handling code here:
        //buscar producto por codigo
        
        
    }//GEN-LAST:event_txtCodKeyReleased

    private void txtEfevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEfevoActionPerformed
        // TODO add your handling code here:
        // calcular cambio y realizar la venta
        try{
            String capcod="", capcan="";
        for(int i=0;i<Venta.tbVentas.getRowCount();i++){
            capcod=Venta.tbVentas.getValueAt(i, 1).toString();
            capcan=Venta.tbVentas.getValueAt(i, 0).toString();
            DescontarExistencia(capcod, capcan);
        }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "txtEfevo "+e);
        }
        
        //CUANDO SE LE DA ENTER A TXT EFECTIVO
        try{
            Calcular();
            EnviarRegistro();
            //EnviarRegistro1();
            DetalleVenta();
            Ventas();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Try txtEfevo Enter "+e);
        }
        
        
    }//GEN-LAST:event_txtEfevoActionPerformed

    private void txtCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodActionPerformed
        // TODO add your handling code here:
        String codi="", codAct=txtCod.getText();
        int codInt=0, codActInt=0;
        
        int cod=Integer.parseInt(txtCod.getText());
        CargarProducto(cod);
        
        for(int i=0;i<tbVentas.getRowCount();i++){
            codi=tbVentas.getValueAt(i, 1).toString();
            codInt=Integer.parseInt(codi);
            codActInt=Integer.parseInt(codAct);
          /*
            if(codInt==codActInt){
                System.out.println("funciona en true");
            }else{
                System.out.println("Funciona en false");
            }
            */
        }
        if(codInt==codActInt){
                System.out.println("funciona en true");
                
            }else{
                System.out.println("Funciona en false");
                //CargarProducto(cod);
            }
        //Pendiente para despues de revision @07/10/2019
        /*
        for (int i = 0; i < tbVentas.getRowCount(); i++) {
            //CargarProducto(0);
            System.out.println("este es 1 " +tbVentas.getValueAt(i, 0)+" este es 2"+tbVentas.getValueAt(i, 1));
       //original     //if(tbVentas.getValueAt(i, 1).toString().equals(txtCod.getText())){
            if(tbVentas.getValueAt(i, 1)==txtCod.getText()){
                System.out.println("El número si existe");
            }else{
                System.out.println("El número no existe");
                CargarProducto(cod);
                }
            }
            */
        ////////////////////////////////////////////////
        String pre;
        String can;
        double igv=0;
        double total=0;
        double subtotal=0;
        double precio;
        int cantidad;
        double imp=0.0;
        double efevo=0, cambio=0, totall=0;
        
        for(int i=0; i<tbVentas.getRowCount(); i++){
            pre=tbVentas.getValueAt(i, 3).toString();
            can=tbVentas.getValueAt(i, 0).toString();
            precio=Double.parseDouble(pre);
            cantidad=Integer.parseInt(can);
            imp=precio*cantidad;
            subtotal=subtotal+imp;
            
            //tbVentas.setValueAt("$"+Math.rint(imp*100)/100, i, 5);
            jlTotal.setText("$"+Math.rint(subtotal*100)/100);
            
        }
        
    }//GEN-LAST:event_txtCodActionPerformed

    private void txtEfevoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEfevoKeyTyped
        // TODO add your handling code here:
        char num=evt.getKeyChar();
        if((num<'0'||num>'9')){
            evt.consume();
        }
    }//GEN-LAST:event_txtEfevoKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel jlCajero;
    private javax.swing.JLabel jlCambio;
    private javax.swing.JLabel jlFecha;
    private javax.swing.JLabel jlFolio;
    private javax.swing.JLabel jlHora;
    private javax.swing.JLabel jlTotal;
    public static javax.swing.JTable tbVentas;
    private javax.swing.JTextField txtCod;
    private javax.swing.JTextField txtEfevo;
    // End of variables declaration//GEN-END:variables
    Conectar cc=new Conectar();
    Connection cn=cc.conexion();
}
