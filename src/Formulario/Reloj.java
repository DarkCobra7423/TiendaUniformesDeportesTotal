/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formulario;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author CARLOS
 */
public class Reloj extends Thread{
    private JLabel hora;
    public Reloj(JLabel hora){
        this.hora=hora;
    }
    
    public void run(){
        while(true){
            Date hoy = new Date();
            SimpleDateFormat h=new SimpleDateFormat("hh:mm:ss");
            hora.setText(h.format(hoy));
            
            try{
                
            }catch(Exception ex){
                JOptionPane.showMessageDialog(hora, "¡¡¡Atencion!!! \n Falla De Tiempo"+ex);
            }
        }
    }
}
