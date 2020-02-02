/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formulario;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

/**
 *
 * @author TAMY
 */
public class Guardar {
    
    public boolean guardarImagen(){
        JFileChooser fileChooser = new JFileChooser();
        
        fileChooser.setDialogTitle("Guardar Codigo");
        int userSelection = fileChooser.showSaveDialog(new RegistroProducto());
        if(userSelection==JFileChooser.APPROVE_OPTION){
            File guardarBarras=fileChooser.getSelectedFile();
            if(!guardarBarras.toString().endsWith(".png")){
                guardarBarras=new File(fileChooser.getSelectedFile()+".PNG");
            }
            
            try{
                ImageIO.write(RegistroProducto.imagen, "png", guardarBarras);
                return true;
            }catch(IOException ex){
                Logger.getLogger(Guardar.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }else{
            return false;
        }
        return false;
    }
}
