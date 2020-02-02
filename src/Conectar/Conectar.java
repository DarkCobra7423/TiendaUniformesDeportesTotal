/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conectar;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author TAMY
 */
public class Conectar {
    
    Connection conect=null;
    
    public Connection conexion(){
        try{
        Class.forName("com.mysql.jdbc.Driver");
        
        conect = DriverManager.getConnection("jdbc:mysql://127.0.0.1/deportestotal","root","");
        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "La Base De Datos Deportes Total No Se Encuentra En Linea \n"+e);
        }
        return conect;
    }
}