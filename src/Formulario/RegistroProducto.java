/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formulario;

import Conectar.Conectar;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import jbarcodebean.JBarcodeBean;
import javax.swing.table.DefaultTableModel;
import net.sourceforge.jbarcodebean.model.Interleaved25;

/**
 *
 * @author TAMY
 */
public class RegistroProducto extends javax.swing.JInternalFrame {
         DefaultTableModel model;
         JBarcodeBean barcode = new JBarcodeBean();
    public static BufferedImage imagen=null;
    /**
     * Creates new form RegistroProducto
     */
    public RegistroProducto() {
        this.setLocation(300, 10);
        initComponents();
        bloquear();
        cargar("");
        
    }
    
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Imagenes/itvh.png"));
        return retValue;
    }
    
    public void generarCodigo(String codigo){
        barcode.setCodeType(new Interleaved25());
        
        barcode.setCode(codigo);
        barcode.setCheckDigit(true);
                                            //370,150
        imagen=barcode.draw(new BufferedImage(200,100, BufferedImage.TYPE_INT_RGB));
        
        ImageIcon barras = new ImageIcon(imagen);
        this.jlBarras.setIcon(barras);
    }  
    
    void GenerarId(){
        String c="";
        String SQL="SELECT MAX(cod_pro)FROM productos";
        
        try{
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery(SQL);
            
            if(rs.next()){
                c=rs.getString(1);
            }
            
            if(c==null){                //    | 
                txtCodigo.setText("750120000");
//                txtIdProveedor.setText("7501253000000");
            }else{
                int j=Integer.parseInt(c);
                GenerarId gi=new GenerarId();
                gi.generar(j);
                txtCodigo.setText(gi.serie());
                /*
                GenerarCodigosBarras gcb = new GenerarCodigosBarras();
                gcb.generar(j);
                txtIdProveedor.setText(gcb.serie());
                */
            }
            
        }catch(SQLException ex){
            System.out.println("Error en metodo generarid"+ex);
        }
    }

    void bloquear(){
        txtCodigo.setEnabled(false);
        txtDescripcion.setEnabled(false);
        cbSexo.setEnabled(false);
        txtTalla.setEnabled(false);
        txtPrecio.setEnabled(false);
        txtExistencia.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnActualizar.setEnabled(false);
        
        btnNuevo.setEnabled(true);
        btnImprimirCodigo.setEnabled(false);
                
    }
    
    void limpiar(){
        //txtCodigo.setText("");
        txtDescripcion.setText("");
        jlBarras.setText("");
        txtTalla.setText("");
        txtPrecio.setText("");
        txtExistencia.setText("");
        
    }
    
    void desbloquear(){
        txtCodigo.setEnabled(true);
        GenerarId();
        txtDescripcion.setEnabled(true);
        cbSexo.setEnabled(true);
        txtTalla.setEnabled(true);
        txtPrecio.setEnabled(true);
        txtExistencia.setEnabled(true);
        
        btnGuardar.setEnabled(true);
        btnNuevo.setEnabled(false);
        btnCancelar.setEnabled(true);
        btnImprimirCodigo.setEnabled(true);
    }
    
    void cargar(String valor){
        try{
            String [] titulos={"Codigo","Descripcion","Sexo","Talla","Existencia","Precio"};
            String [] registros=new String[6];
            model=new DefaultTableModel(null, titulos);
            String cons="SELECT * FROM productos WHERE CONCAT (cod_pro,'',desc_pro,'',sexo_pro,'',talla_pro,'',pre_pro,'',exist_pro) LIKE '%"+valor+"%'";
            
            Statement st = cn.createStatement();
            ResultSet rs= st.executeQuery(cons);
            
            while(rs.next()){
                registros[0]=rs.getString(1);
                registros[1]=rs.getString(2);
                registros[2]=rs.getString(3);
                registros[3]=rs.getString(4);
                registros[4]=rs.getString(5);
                registros[5]=rs.getString(6);
                
                model.addRow(registros);
            }
            
            tbProductos.setModel(model);
            tbProductos.getColumnModel().getColumn(0).setPreferredWidth(50);
            tbProductos.getColumnModel().getColumn(1).setPreferredWidth(200);
            tbProductos.getColumnModel().getColumn(2).setPreferredWidth(10);
            tbProductos.getColumnModel().getColumn(3).setPreferredWidth(20);
            tbProductos.getColumnModel().getColumn(4).setPreferredWidth(5);
            tbProductos.getColumnModel().getColumn(5).setPreferredWidth(20);
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error en el metodo de cargar Clase Registro "+e);
        }
    }
    
    void BuscarProductoEditar(String cod){
        try{
            String codigo="", descripcion="", sexo="", talla="",precio="", existencia="";
            String cons = "SELECT * FROM productos WHERE cod_pro='"+cod+"'";
            Statement st = cn.createStatement();
            ResultSet rs= st.executeQuery(cons);
            
            while(rs.next()){
                codigo=rs.getString(1);
                descripcion=rs.getString(2);
                sexo=rs.getString(3);
                talla=rs.getString(4);
                existencia=rs.getString(5);
                precio=rs.getString(6);
                
            }
            
            txtCodigo.setText(codigo);
            txtDescripcion.setText(descripcion);
            cbSexo.setToolTipText(sexo);
            txtTalla.setText(talla);
            txtExistencia.setText(existencia);
            txtPrecio.setText(precio);
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error en el metodo BuscarProductoEditar de la Clase Registro \n "+e);
            
        }
    }
    
    void Guardar(){
        String codigo, descripcion, sexo, talla, precio, existencia;
        String sql="";
        String sex0 = String.valueOf(cbSexo.getSelectedItem());
        codigo=txtCodigo.getText();
        descripcion=txtDescripcion.getText();
        sexo=sex0;
        talla=txtTalla.getText();
        precio=txtPrecio.getText();
        existencia=txtExistencia.getText();
        sql="INSERT INTO productos (cod_pro, desc_pro, sexo_pro, talla_pro, pre_pro, exist_pro) VALUES (?,?,?,?,?,?)";
        
        try{
         PreparedStatement pst =cn.prepareStatement(sql);
         pst.setString(1, codigo);
         pst.setString(2, descripcion);
         pst.setString(3, sexo);
         pst.setString(4, talla);
         pst.setString(5, precio);
         pst.setString(6, existencia);
         
         int n=pst.executeUpdate();
         
         if(n>0){
             JOptionPane.showMessageDialog(null, "Producto guardado con exito");
             bloquear();
             limpiar();
             //btnNuevo.setEnabled(true); ////PENDIENTE
         }
         cargar("");
         this.jlBarras.setIcon(null);
         this.txtCodigo.setText("");
         
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error en el metodo de Guardar Clase Rregistros \n"+ex);
        }
        
    }
    
    void VerificarRegistro(String codigo1){
        String codigo11="", descripcion1="", sexo1="", talla1="", precio1="", existencia1="";
        String nivel ="SELECT * FROM productos WHERE cod_pro='"+codigo1+"'";
        
        try{
            Statement st =cn.createStatement();
            ResultSet rs=st.executeQuery(nivel);
            
            while(rs.next()){
                codigo1=rs.getString(1);
                descripcion1=rs.getString(2);
                sexo1=rs.getString(2);
                talla1=rs.getString(3);
                existencia1=rs.getString(4);
                precio1=rs.getString(5);
                
                if(codigo11==txtCodigo.getText()){
                    JOptionPane.showMessageDialog(null, "El codigo ya existe en la base de datos");
                    
                }else{
                    Guardar();
                }
                
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Error en el Metodo VerificarRegitro Clase Registros");
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbProductos = new javax.swing.JTable();
        btnEliminar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txtPrecio = new javax.swing.JTextField();
        txtExistencia = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        txtTalla = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cbSexo = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnMostrarTodo = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        txtCodigo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jlBarras = new javax.swing.JLabel();
        btnImprimirCodigo = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);

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

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Registrar"));

        txtPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioActionPerformed(evt);
            }
        });

        txtExistencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtExistenciaActionPerformed(evt);
            }
        });

        jLabel2.setText("Descripcion:");

        jLabel3.setText("Talla:");

        jLabel4.setText("Precio");

        jLabel5.setText("Existencia:");

        txtDescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionActionPerformed(evt);
            }
        });

        txtTalla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTallaActionPerformed(evt);
            }
        });

        jLabel8.setText("Sexo:");

        cbSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Niño", "Niña", "Masculino", "Femenino" }));
        cbSexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSexoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPrecio)
                    .addComponent(txtExistencia)
                    .addComponent(txtDescripcion)
                    .addComponent(txtTalla)
                    .addComponent(cbSexo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTalla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtExistencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
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
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(btnNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnActualizar)
                .addGap(11, 11, 11)
                .addComponent(btnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSalir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setText("Buscar");

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        btnMostrarTodo.setText("Mostrar Todo");
        btnMostrarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarTodoActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Codigo"));

        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoKeyTyped(evt);
            }
        });

        jLabel1.setText("Codigo:");

        btnImprimirCodigo.setText("Imprimir Codigo");
        btnImprimirCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirCodigoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlBarras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnImprimirCodigo)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlBarras, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnImprimirCodigo))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81))
            .addGroup(layout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMostrarTodo)
                .addContainerGap(227, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMostrarTodo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminar)
                    .addComponent(btnModificar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        int filase1=tbProductos.getSelectedRow();
        int confirmar=JOptionPane.showConfirmDialog(null, "¿Eliminar Producto?", "Confirmar Eliminacion", JOptionPane.YES_NO_OPTION);

        if(confirmar==JOptionPane.YES_OPTION){
            try{
                if(filase1==1){
                    JOptionPane.showMessageDialog(null, "Seleccione el producto a eliminar");
                }else{
                    String cod=(String)tbProductos.getValueAt(filase1, 0);
                    String eliminarSQL="DELETE FROM productos WHERE cod_pro='"+cod+"'";

                    try{
                        PreparedStatement pst = cn.prepareStatement(eliminarSQL);
                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Producto Eliminado");
                        cargar("");
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null, "Error en btnEliminar Clase Registro \n"+e);
                    }
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error en la condicional Confirmar btnElimiar Clase Registro \n"+e);
            }
        }//end if confirmar
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        try{
            int filaMod=tbProductos.getSelectedRow();
            if(filaMod==-1){
                JOptionPane.showMessageDialog(null, "Seleccione un producto");
            }else{
                btnActualizar.setEnabled(true);
                String cod=(String)tbProductos.getValueAt(filaMod, 0);
                desbloquear();
                btnGuardar.setEnabled(false);
                BuscarProductoEditar(cod);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error en btnModificar Clase Regiostros \n"+e);
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void txtPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioActionPerformed
        // TODO add your handling code here:
        txtPrecio.transferFocus();
    }//GEN-LAST:event_txtPrecioActionPerformed

    private void txtExistenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtExistenciaActionPerformed
        // TODO add your handling code here:
        txtExistencia.transferFocus();
        Guardar();
    }//GEN-LAST:event_txtExistenciaActionPerformed

    private void txtDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionActionPerformed
        // TODO add your handling code here:
        txtDescripcion.transferFocus();
    }//GEN-LAST:event_txtDescripcionActionPerformed

    private void txtTallaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTallaActionPerformed
        // TODO add your handling code here:
        txtTalla.transferFocus();
    }//GEN-LAST:event_txtTallaActionPerformed

    private void cbSexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSexoActionPerformed
        // TODO add your handling code here:
        cbSexo.transferFocus();
    }//GEN-LAST:event_cbSexoActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        desbloquear();
        limpiar();
        txtCodigo.requestFocus();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        String codigo, descripcion, sexo, talla, existencia, precio;
        String sql="";
        String sex=String.valueOf(cbSexo.getSelectedItem());
        codigo=txtCodigo.getText();
        descripcion=txtDescripcion.getText();
        sexo=sex;
        talla=txtTalla.getText();
        existencia=txtExistencia.getText();
        precio=txtPrecio.getText();

        sql="INSERT INTO productos(cod_pro, desc_pro, sexo_pro, talla_pro, exist_pro, pre_pro) VALUES (?,?,?,?,?,?)";
        try{
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, codigo);
            pst.setString(2, descripcion);
            pst.setString(3, sexo);
            pst.setString(4, talla);
            pst.setString(5, existencia);
            pst.setString(6, precio);

            int n=pst.executeUpdate();
            if(n>0){
                JOptionPane.showMessageDialog(null, "Producto Guardado Con Exito");
                bloquear();
                limpiar();
                btnNuevo.setEnabled(true);
            }
            cargar("");
            this.jlBarras.setIcon(null);
            this.txtCodigo.setText("");
                
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error en el btnGuardar Clase Registro \n"+ex);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        limpiar();
        bloquear();
        btnNuevo.setEnabled(true);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        // TODO add your handling code here:
        cargar(txtBuscar.getText());
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void btnMostrarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarTodoActionPerformed
        // TODO add your handling code here:
        cargar("");
    }//GEN-LAST:event_btnMostrarTodoActionPerformed

    private void txtCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyReleased
        // TODO add your handling code here:
        generarCodigo(this.txtCodigo.getText());
        txtCodigo.transferFocus();
    }//GEN-LAST:event_txtCodigoKeyReleased

    private void txtCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyTyped
        // TODO add your handling code here:
        char num=evt.getKeyChar();
        if((num<'0'||num>'9')){
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoKeyTyped

    private void btnImprimirCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirCodigoActionPerformed
        // TODO add your handling code here:
        
        if(imagen == null){
            JOptionPane.showMessageDialog(null, "Debe Ingresar Un Numero A Generar");
        }else if(new Guardar().guardarImagen()){
            imagen=null;
            this.jlBarras.setIcon(null);
            this.txtCodigo.setText("");
            JOptionPane.showMessageDialog(null, "Codigo Guardado");
        }
        btnImprimirCodigo.transferFocus();
        
        //////////////////////////////////
        /*
        GenerarCodigo gc=new GenerarCodigo();
        gc.GuardarCodigo(imagen);
        */
    }//GEN-LAST:event_btnImprimirCodigoActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        String sex1=String.valueOf(cbSexo.getSelectedItem());
        String sql="UPDATE productos SET desc_pro='"+txtDescripcion.getText()+"',sexo_pro='"+sex1+"',talla_pro='"+txtTalla.getText()+"',exist_pro='"+txtExistencia.getText()+"',pre_pro='"+txtPrecio.getText()+"' WHERE cod_pro ='"+txtCodigo.getText()+"'";

        try{
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Producto Actualizado");
            cargar("");
            bloquear();
            limpiar();

        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error en btnActualizar de la Clase Registro \n"+e);
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnImprimirCodigo;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnMostrarTodo;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> cbSexo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jlBarras;
    private javax.swing.JTable tbProductos;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtExistencia;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtTalla;
    // End of variables declaration//GEN-END:variables
    Conectar cc= new Conectar();
    Connection cn=cc.conexion();
}
