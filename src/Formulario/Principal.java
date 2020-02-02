/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formulario;

import Conectar.Conectar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author CARLOS
 */
public class Principal extends javax.swing.JFrame {
   // public String var="";//................................................------------------------

    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
        jlFecha.setText(fechaact());
        Reloj hilo = new Reloj(jlHora);
        hilo.start();
        Bloquear();
        AccesoNivel();
        //--------------------------------------------------------------------------------------------
        //login lg=new login();
        //var=lg.usuarioGlobalLogin;
       // System.out.println("variable var principal -->"+var+"<--");
        //AccesoNivel("");/////////////////////////---------------------------------------------------
        
    }
    
    void Bloquear(){
        btnCaja.setEnabled(false);
        btnConsulta.setEnabled(false);
        btnBitacora.setEnabled(false);
        jmNuevoAcceso.setEnabled(false);///-<-<-<-<-<-<-<
        jmAgregarProducto.setEnabled(false);
        jmDiaria.setEnabled(false);
        jmSemanal.setEnabled(false);
        jmMensual.setEnabled(false);
        jmNuevoProveedor.setEnabled(false);
        jmMostrarProveedor.setEnabled(false);
        jmCodBarra.setEnabled(false);
        jmAgregarClienteSA.setEnabled(false);
        jmSistemaApartado.setEnabled(false);
        jmCorteCaja.setEnabled(false);
       
    }
    
   
    public static String fechaact(){
        java.util.Date fecha = new java.util.Date();
        SimpleDateFormat formatofecha=new SimpleDateFormat("dd/MM/YYYY");
        return formatofecha.format(fecha);
    }
    
    public void AccesoNivel(){//METODO PUBLIC PARA ACCESO
        ControlAcceso ca=new ControlAcceso();
        
        String tipo_usuario=ca.tipoGlobal;
        
        if(tipo_usuario.equals("Administrador")){
            System.out.println("if-Administrador ");//----------
            btnCaja.setEnabled(true);
            btnConsulta.setEnabled(true);
            btnBitacora.setEnabled(true);
            jmNuevoAcceso.setEnabled(true);
            jmAgregarProducto.setEnabled(true);
            jmDiaria.setEnabled(true);
            jmSemanal.setEnabled(true);
            jmMensual.setEnabled(true);
            jmNuevoProveedor.setEnabled(true);
            jmMostrarProveedor.setEnabled(true);
            jmCodBarra.setEnabled(true);
            jmAgregarClienteSA.setEnabled(true);
            jmSistemaApartado.setEnabled(true);
            jmCorteCaja.setEnabled(true);
           
            /*
            ACCESO GLOBAL AL SISTEMA
            */
        }else if(tipo_usuario.equals("Gerente")){
            System.out.println("if-Gerente ");///---------------
            btnCaja.setEnabled(true);
            btnConsulta.setEnabled(true);
            btnBitacora.setEnabled(true);
            //jmNuevoAcceso.setEnabled(false);
            //jmAgregarProducto.setEnabled(true);
            jmDiaria.setEnabled(true);
            jmSemanal.setEnabled(true);
            jmMensual.setEnabled(true);
            jmNuevoProveedor.setEnabled(true);
            //jmMostrarProveedor.setEnabled(true);
            //jmCodBarra.setEnabled(true);
            //jmAgregarClienteSA.setEnabled(true);
            //jmSistemaApartado.setEnabled(true);
            jmCorteCaja.setEnabled(true);
           
            /*
            --ACCESO 
            VENDER UNIFORMES ------
            CONSULTAR UNIFORMES ----
            CONTROL DE VENTAS UNIFORMES --
            CORTE CAJA---
            CONSULTAR FUERA DE SISTEMA------------
            AGREGAR PROVEEDORES--
            */
        }else if(tipo_usuario.equals("Mostrador")){
            System.out.println("if-Mostrador ");//----------------
            btnCaja.setEnabled(true);
            btnConsulta.setEnabled(true);
            btnBitacora.setEnabled(true);
            //jmNuevoAcceso.setEnabled(false);
            //jmAgregarProducto.setEnabled(true);
            //jmDiaria.setEnabled(true);
            //jmSemanal.setEnabled(true);
            //jmMensual.setEnabled(true);
            //jmNuevoProveedor.setEnabled(true);
            //jmMostrarProveedor.setEnabled(true);
            //jmCodBarra.setEnabled(true);
            jmAgregarClienteSA.setEnabled(true);
            jmSistemaApartado.setEnabled(true);
            //jmCorteCaja.setEnabled(true);
            /*
            SISTEMA DE APARTADO
            VENDER-------
            CONSULTAR---------
            CONSULTA FUERA DE SISTEMA---------
            */
        }else if(tipo_usuario.equals("Paqueteria")){
            System.out.println("if-Paqueteria ");//-------------
            btnConsulta.setEnabled(true);
            //jmNuevoAcceso.setEnabled(false);
            jmAgregarProducto.setEnabled(true);
            //jmDiaria.setEnabled(true);
            //jmSemanal.setEnabled(true);
            //jmMensual.setEnabled(true);
            //jmNuevoProveedor.setEnabled(true);
            jmMostrarProveedor.setEnabled(true);
            jmCodBarra.setEnabled(true);
            //jmAgregarClienteSA.setEnabled(true);
            //jmSistemaApartado.setEnabled(true);
            //jmCorteCaja.setEnabled(true);
            /*
            REGISTRO PRODUCTO
            IMPRESION DE CODIGO DE BARRA---
            SOLO BODEGA-------------------------
            MOSTRAR PROVEEDORES-----
            */
        }
    }//FIN DEL METODO ACCESO NIVEL  */
    
 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlHora = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jlFecha = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jdpEscritorio = new javax.swing.JDesktopPane();
        btnCaja = new javax.swing.JButton();
        btnConsulta = new javax.swing.JButton();
        btnBitacora = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jmiArchivoCerrarSesion = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jmNuevoAcceso = new javax.swing.JMenuItem();
        jmAgregarProducto = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jmDiaria = new javax.swing.JMenuItem();
        jmSemanal = new javax.swing.JMenuItem();
        jmMensual = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jmNuevoProveedor = new javax.swing.JMenuItem();
        jmMostrarProveedor = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jmCodBarra = new javax.swing.JMenuItem();
        jmAgregarClienteSA = new javax.swing.JMenuItem();
        jmSistemaApartado = new javax.swing.JMenuItem();
        jmCorteCaja = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Deportes Total");

        jlHora.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlHora.setForeground(new java.awt.Color(255, 255, 255));
        jlHora.setText("mm:ss:mm");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 0));
        jLabel1.setText("Hora:");

        jlFecha.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlFecha.setForeground(new java.awt.Color(255, 255, 255));
        jlFecha.setText("dd/mm/aaaa");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 0));
        jLabel3.setText("Fecha:");

        jdpEscritorio.setBackground(new java.awt.Color(25, 153, 153));

        btnCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/movimiento.png"))); // NOI18N
        btnCaja.setText("Caja");
        btnCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCajaActionPerformed(evt);
            }
        });
        jdpEscritorio.add(btnCaja);
        btnCaja.setBounds(20, 20, 140, 50);

        btnConsulta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/sql.png"))); // NOI18N
        btnConsulta.setText("Consultas");
        btnConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaActionPerformed(evt);
            }
        });
        jdpEscritorio.add(btnConsulta);
        btnConsulta.setBounds(20, 80, 140, 50);

        btnBitacora.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/informe.png"))); // NOI18N
        btnBitacora.setText("Bitacora");
        btnBitacora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBitacoraActionPerformed(evt);
            }
        });
        jdpEscritorio.add(btnBitacora);
        btnBitacora.setBounds(20, 140, 140, 50);

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jdpEscritorio.add(btnSalir);
        btnSalir.setBounds(20, 200, 140, 50);

        jMenuBar1.setForeground(new java.awt.Color(0, 102, 255));

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/archivo.png"))); // NOI18N
        jMenu1.setText("Archivo");

        jmiArchivoCerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/User.png"))); // NOI18N
        jmiArchivoCerrarSesion.setText("Cerrar Sesion");
        jmiArchivoCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiArchivoCerrarSesionActionPerformed(evt);
            }
        });
        jMenu1.add(jmiArchivoCerrarSesion);

        jMenuItem13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logout (2).png"))); // NOI18N
        jMenuItem13.setText("Salir");
        jMenu1.add(jMenuItem13);

        jMenuBar1.add(jMenu1);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/mini_faq-lector-pinpad.png"))); // NOI18N
        jMenu2.setText("Registro");

        jmNuevoAcceso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/user-info.png"))); // NOI18N
        jmNuevoAcceso.setText("Nuevo Acceso");
        jmNuevoAcceso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmNuevoAccesoActionPerformed(evt);
            }
        });
        jMenu2.add(jmNuevoAcceso);

        jmAgregarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Reservas.png"))); // NOI18N
        jmAgregarProducto.setText("Agregar Productos");
        jmAgregarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmAgregarProductoActionPerformed(evt);
            }
        });
        jMenu2.add(jmAgregarProducto);

        jMenuBar1.add(jMenu2);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/movimiento.png"))); // NOI18N
        jMenu3.setText("Venta");

        jmDiaria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/movimiento_1.png"))); // NOI18N
        jmDiaria.setText("Diaria");
        jmDiaria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmDiariaActionPerformed(evt);
            }
        });
        jMenu3.add(jmDiaria);

        jmSemanal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/movimiento_1.png"))); // NOI18N
        jmSemanal.setText("Semanal");
        jmSemanal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmSemanalActionPerformed(evt);
            }
        });
        jMenu3.add(jmSemanal);

        jmMensual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/movimiento_1.png"))); // NOI18N
        jmMensual.setText("Mensual");
        jmMensual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmMensualActionPerformed(evt);
            }
        });
        jMenu3.add(jmMensual);

        jMenuBar1.add(jMenu3);

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/shipping.png"))); // NOI18N
        jMenu4.setText("Proveedores");

        jmNuevoProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/lol3.png"))); // NOI18N
        jmNuevoProveedor.setText("Nuevo Proveedor");
        jmNuevoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmNuevoProveedorActionPerformed(evt);
            }
        });
        jMenu4.add(jmNuevoProveedor);

        jmMostrarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/lol2.png"))); // NOI18N
        jmMostrarProveedor.setText("Mostrar Proveedores");
        jmMostrarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmMostrarProveedorActionPerformed(evt);
            }
        });
        jMenu4.add(jmMostrarProveedor);

        jMenuBar1.add(jMenu4);

        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Herramientas.png"))); // NOI18N
        jMenu6.setText("Herramientas");

        jmCodBarra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/mini_58274.png"))); // NOI18N
        jmCodBarra.setText("Generar Cod. Barras");
        jmCodBarra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmCodBarraActionPerformed(evt);
            }
        });
        jMenu6.add(jmCodBarra);

        jmAgregarClienteSA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/contact-new_1.png"))); // NOI18N
        jmAgregarClienteSA.setText("Agregar Cliente S.A.");
        jmAgregarClienteSA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmAgregarClienteSAActionPerformed(evt);
            }
        });
        jMenu6.add(jmAgregarClienteSA);

        jmSistemaApartado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/informe.png"))); // NOI18N
        jmSistemaApartado.setText("Sistema De Apartado");
        jmSistemaApartado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmSistemaApartadoActionPerformed(evt);
            }
        });
        jMenu6.add(jmSistemaApartado);

        jmCorteCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pagos.png"))); // NOI18N
        jmCorteCaja.setText("Corte De Caja");
        jmCorteCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmCorteCajaActionPerformed(evt);
            }
        });
        jMenu6.add(jmCorteCaja);

        jMenuBar1.add(jMenu6);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Ayuda.png"))); // NOI18N
        jMenu5.setText("Sobre Nosotros");

        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/trabajadores.png"))); // NOI18N
        jMenuItem8.setText("¿Quienes Somos?");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem8);

        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(407, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlFecha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlHora, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jdpEscritorio, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 388, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlHora)
                    .addComponent(jLabel1)
                    .addComponent(jlFecha)
                    .addComponent(jLabel3)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jdpEscritorio, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmiArchivoCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiArchivoCerrarSesionActionPerformed
        // TODO add your handling code here:
       
        login lg=new login();
        lg.setVisible(true);
        this.dispose();
        lg.pack();
       
    }//GEN-LAST:event_jmiArchivoCerrarSesionActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void jmCodBarraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmCodBarraActionPerformed
        // TODO add your handling code here:
        GenerarCodigo gc = new GenerarCodigo();
        jdpEscritorio.add(gc);
        gc.show();
        
    }//GEN-LAST:event_jmCodBarraActionPerformed

    private void jmNuevoProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmNuevoProveedorActionPerformed
        // TODO add your handling code here:
        RegistroProveedores rp=new RegistroProveedores();
        jdpEscritorio.add(rp);
        rp.show();
    }//GEN-LAST:event_jmNuevoProveedorActionPerformed

    private void btnCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCajaActionPerformed
        // TODO add your handling code here:
        Venta vt=new Venta();
        jdpEscritorio.add(vt);
        vt.show();
    }//GEN-LAST:event_btnCajaActionPerformed

    private void jmAgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmAgregarProductoActionPerformed
        // TODO add your handling code here:
        RegistroProducto rgp=new RegistroProducto();
        jdpEscritorio.add(rgp);
        rgp.show();
    }//GEN-LAST:event_jmAgregarProductoActionPerformed

    private void jmMostrarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmMostrarProveedorActionPerformed
        // TODO add your handling code here:
        Proveedores pvr=new Proveedores();
        jdpEscritorio.add(pvr);
        pvr.show();
    }//GEN-LAST:event_jmMostrarProveedorActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        sobre_nosotros sn=new sobre_nosotros();
        jdpEscritorio.add(sn);
        sn.show();
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void btnBitacoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBitacoraActionPerformed
        // TODO add your handling code here:
        Bitacora bt=new Bitacora();
        jdpEscritorio.add(bt);
        bt.show();
    }//GEN-LAST:event_btnBitacoraActionPerformed

    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaActionPerformed
        // TODO add your handling code here:
        ConsultaBodegasSuc cbs=new ConsultaBodegasSuc();
        jdpEscritorio.add(cbs);
        cbs.show();
    }//GEN-LAST:event_btnConsultaActionPerformed

    private void jmAgregarClienteSAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmAgregarClienteSAActionPerformed
        // TODO add your handling code here:
        IngresoClientesSA csa=new IngresoClientesSA();
        jdpEscritorio.add(csa);
        csa.show();
    }//GEN-LAST:event_jmAgregarClienteSAActionPerformed

    private void jmSistemaApartadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmSistemaApartadoActionPerformed
        // TODO add your handling code here:
        SistemaApartado sa=new SistemaApartado();
        jdpEscritorio.add(sa);
        sa.show();
    }//GEN-LAST:event_jmSistemaApartadoActionPerformed

    private void jmCorteCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmCorteCajaActionPerformed
        // TODO add your handling code here:
        CorteCaja cc=new CorteCaja();
        jdpEscritorio.add(cc);
        cc.show();
    }//GEN-LAST:event_jmCorteCajaActionPerformed

    private void jmNuevoAccesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmNuevoAccesoActionPerformed
        // TODO add your handling code here:
        Registro_usuario rg=new Registro_usuario();
        jdpEscritorio.add(rg);
        rg.show();
    }//GEN-LAST:event_jmNuevoAccesoActionPerformed

    private void jmDiariaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmDiariaActionPerformed
        // TODO add your handling code here:
        Diaria d=new Diaria();
        jdpEscritorio.add(d);
        d.show();
    }//GEN-LAST:event_jmDiariaActionPerformed

    private void jmSemanalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmSemanalActionPerformed
        // TODO add your handling code here:
        Semanal s=new Semanal();
        jdpEscritorio.add(s);
        s.show();
    }//GEN-LAST:event_jmSemanalActionPerformed

    private void jmMensualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmMensualActionPerformed
        // TODO add your handling code here:
        Mensual m=new Mensual();
        jdpEscritorio.add(m);
        m.show();
    }//GEN-LAST:event_jmMensualActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBitacora;
    private javax.swing.JButton btnCaja;
    private javax.swing.JButton btnConsulta;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem8;
    public static javax.swing.JDesktopPane jdpEscritorio;
    private javax.swing.JLabel jlFecha;
    private javax.swing.JLabel jlHora;
    private javax.swing.JMenuItem jmAgregarClienteSA;
    private javax.swing.JMenuItem jmAgregarProducto;
    private javax.swing.JMenuItem jmCodBarra;
    private javax.swing.JMenuItem jmCorteCaja;
    private javax.swing.JMenuItem jmDiaria;
    private javax.swing.JMenuItem jmMensual;
    private javax.swing.JMenuItem jmMostrarProveedor;
    private javax.swing.JMenuItem jmNuevoAcceso;
    private javax.swing.JMenuItem jmNuevoProveedor;
    private javax.swing.JMenuItem jmSemanal;
    private javax.swing.JMenuItem jmSistemaApartado;
    private javax.swing.JMenuItem jmiArchivoCerrarSesion;
    // End of variables declaration//GEN-END:variables
    Conectar cc=new Conectar();
    Connection cn=cc.conexion();

}
