/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formulario;

/**
 *
 * @author CARLOS
 */
public class GenerarId {
    private int dato;
    private int cont=1;
    private String num="";
    
    public void generar(int dato){
        this.dato=dato;
        if((this.dato>=100000000) || (this.dato<10000000)){
            int can=cont+this.dato;
            num = "" + can;
        }
        if((this.dato>=1000000) || (this.dato<100000000)){
            int can=cont+this.dato;
            num = "0" + can;
        }
        if((this.dato>=100000) || (this.dato<1000000)){
            int can=cont+this.dato;
            num = "00" + can;
        }
        if((this.dato>=10000) || (this.dato<100000000)){
            int can=cont+this.dato;
            num = "" + can;//000
        }
        if((this.dato>=1000) || (this.dato<10000)) 
           {
               int can=cont+this.dato;
               num = "" + can; //0000
           }
           if((this.dato>=100) || (this.dato<1000))
           {
               int can=cont+this.dato;
               num = "" + can; //00000
           }
           if((this.dato>=9) || (this.dato<100)) 
           {
                int can=cont+this.dato;
               num = "" + can;//000000 
           }
           if (this.dato<9)
           {
           
              int can=cont+this.dato;
               num = "" + can;//0000000 
           }
           
    }
    
    public String serie(){
        return this.num;
    }
}
