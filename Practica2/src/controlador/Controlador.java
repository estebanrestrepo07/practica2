/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import vista.*;
import modelo.*;

/**
 *
 * @author Santiago
 */
public class Controlador implements ActionListener {
    Polinomios vistaPoli = new Polinomios();
    
    public javax.swing.JTextField txtPol2;
    

    public Controlador(Polinomios vistaPoli) {
        
        this.vistaPoli = vistaPoli;
        this.vistaPoli.btnBorrar.addActionListener(this);
        this.vistaPoli.btnSalir.addActionListener(this);
        this.vistaPoli.cbOpciones.addActionListener(this);
        this.vistaPoli.btnAccion.addActionListener(this);
        
        
        
    }
    
    //metodo para quitar los espacios al string ingresado
    public String sinEspacios(String polinomio){
        String aux = "";
        char espacio = ' ';
        for(int i = 0; i < polinomio.length(); i++){
            if(polinomio.charAt(i) != espacio){
                aux = aux + polinomio.charAt(i);
            }
        }
        
        return aux;
    }
    
    public GrafoG toGraph(File file) {
        GrafoG g = null;
        FileReader fr = null;
        BufferedReader br = null;
        int numVectores = 0;
        int numAristas = 0;
        int count = 0;

        try {
           // Apertura del fichero y creacion de BufferedReader para poder
           // hacer una lectura comoda (disponer del metodo readLine()).           
           fr = new FileReader (file);
           br = new BufferedReader(fr);

           // Lectura del fichero
           String linea;
           while((linea=br.readLine())!=null) {
                switch(linea.charAt(0)) {
                    case 'p':
                        String[] tamaño = linea.split(" ");
                        numVectores = Integer.parseInt(tamaño[2]);
                        numAristas = Integer.parseInt(tamaño[3]);
                        g = new GrafoG(numVectores, numAristas);
                        break;
                    case 'e':
                        count ++;
                        if(count<=numAristas){
                            String[] arista = linea.split(" ");
                            int va = Integer.parseInt(arista[1]);
                            int vb = Integer.parseInt(arista[2]);
                            if((va<= numVectores)&&(vb<=numVectores)){
                                g.aggArista(va, vb);
                            }
                        }  
                        break;
                    default:
                        break;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
           // En el finally cerramos el fichero, para asegurarnos
           // que se cierra tanto si todo va bien como si salta 
           // una excepcion.
           try{                    
              if( null != fr ){   
                 fr.close();     
              }                  
           }catch (Exception e2){ 
              e2.printStackTrace();
           }
        }
        return g;
    }
    
    //metodo para listar el polinomio 
    public ListaSimple toList(String polinomio){
        ListaSimple pol = new ListaSimple(); 
        String auxC=""; //auxiliar coeficiente para detectar el coeficiente correspondiente a cada monomio
        String auxE="0"; //auxiliar exponente para detectar el coeficiente correspondiente a cada monomio
        
        //variable coe y exp para saber si el numero es un coeficiente o exponente
        boolean coe = true; 
        boolean exp = false;
        //recorremos todo el polinomio 
        for(int i = 0; i < polinomio.length(); i++) {
            System.out.println(polinomio.charAt(i));
            //Detectaremos los posibles caracteres que se encontrará, dependiendo de cada caracter,
            //se sabrá si es coeficiente, exponente, variable 'x' y su correspondiente signo
            //al detectar cada monomio se listará.
            
            switch(polinomio.charAt(i)) {
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case '0':
                case '.':
                    if(coe) {
                        auxC=auxC+polinomio.charAt(i);
                    } else if (exp) {
                        auxE = auxE + polinomio.charAt(i);
                    }
                    if(polinomio.charAt(i) == '.' && polinomio.charAt(i-1) == '.'){
                       auxE = auxE;
                    }
                    break;
                case 'x':
                    if(i > 0 ){
                        if( polinomio.charAt(i-1) == '-' || polinomio.charAt(i-1) == '+') {
                            auxC =auxC + "1";
                        }
                        if(i >= polinomio.length()-1) {
                            auxE = auxE + "1";
                        }
                    } else if(i==0) {
                        auxC = auxC + "1";
                        if(i+1 == polinomio.length()) {
                            auxE = auxE + "1";
                        }                       
                    }
                    coe = false;
                    exp = true;
                    break;
                case '+':
                case '-':
                    if(i > 0 ){
                        if(polinomio.charAt(i-1) == 'x') {
                            auxE = "1";
                        }                       
                    }
                    if(exp) {                       
                        pol.insertarNodo(Integer.parseInt(auxC), Integer.parseInt(auxE));
                        System.out.println(auxC);
                        System.out.println(auxC+"x"+auxE);
                        auxC = "";
                        auxC=auxC+polinomio.charAt(i); 
                        auxE = "0";                       
                    } else {
                        auxC=auxC+polinomio.charAt(i);                       
                    }
                    coe = true;
                    exp = false;
                    break;    
                default:
                    auxE="";
                    auxC="";        
                    break;
            }
            
        }
        if(auxE != "" && auxC != ""){
            pol.insertarNodo(Integer.parseInt(auxC), Integer.parseInt(auxE));
            pol.ordenar();
        }

        //System.out.println("mi lista es: "+ pol.mostrar());
        return pol;
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        ListaSimple list = new ListaSimple();
//        String aux1 = sinEspacios(vistaPoli.txtPol1.getText());
        String aux1 = "x";
        if(aux1 == ""){
            list = toList("x0");
        }else{
            list = toList(aux1);
        }
        list.ordenar();

        if(e.getSource() == vistaPoli.cbOpciones){
            
            switch (vistaPoli.cbOpciones.getSelectedIndex()) {
                //default seleccionar
                case 0: 
                    //Restablecer las variables por si el usuario quiere realizar otra operación
                    //------------------------------
                    vistaPoli.txtPol2.setVisible(false);
                    vistaPoli.txtPol2.setText("");
                    vistaPoli.btnAccion.setVisible(false);
                    vistaPoli.PoliUser.setVisible(false);
                    vistaPoli.PoliUser.setText("");
                    vistaPoli.PoliUser2.setVisible(false);
                    vistaPoli.Resultado.setVisible(false);
                    vistaPoli.txtPolAux.setVisible(false);
                     vistaPoli.txtPolAux.setText("");
                    vistaPoli.jLabelPoliAux.setVisible(false);
                    vistaPoli.jLabelPoliAux.setText("");
                    vistaPoli.jLabelPoli2.setVisible(false);
                    vistaPoli.jLabelPoli2.setText("");
                    vistaPoli.ResultadoFin.setVisible(false);
                    vistaPoli.ResultadoFin.setText("");
                    vistaPoli.ResultadoFin.setVisible(false);
                    vistaPoli.jTextArea.setEnabled(false);
                //evaluar Polinomio
                case 1:
                    //Restablecer las variables por si el usuario quiere realizar otra operación
                    //------------------------------
                    vistaPoli.PoliUser.setVisible(true);
                    vistaPoli.PoliUser.setText("Polinomio ingresado: "+list.mostrar());
                    vistaPoli.txtPol2.setVisible(true);
                    vistaPoli.txtPol2.setText("");
                    vistaPoli.jLabelPoli2.setText("Ingrese un número c");
                    vistaPoli.jLabelPoli2.setVisible(true);
                    vistaPoli.btnAccion.setText("Determinar");
                    vistaPoli.btnAccion.setVisible(true);
                    vistaPoli.ResultadoFin.setVisible(false);
                    vistaPoli.Resultado.setVisible(false);
                    vistaPoli.PoliUser2.setVisible(false);
                    vistaPoli.jLabelPoliAux.setVisible(false);
                    vistaPoli.txtPolAux.setVisible(false);


                break;

                //Sumar Polinomios
                case 2:
                    //Restablecer las variables por si el usuario quiere realizar otra operación
                    //------------------------------
                    vistaPoli.PoliUser.setVisible(true);
                    vistaPoli.PoliUser.setText("Polinomio ingresado: "+list.mostrar());
                    vistaPoli.txtPol2.setVisible(true);
                    vistaPoli.txtPol2.setText("");
                    vistaPoli.jLabelPoli2.setText("Ingrese segundo polinomio");
                    vistaPoli.jLabelPoli2.setVisible(true);
                    vistaPoli.btnAccion.setText("Sumar");
                    vistaPoli.btnAccion.setVisible(true);
                    vistaPoli.ResultadoFin.setVisible(false);
                    vistaPoli.Resultado.setVisible(false);
                    vistaPoli.PoliUser2.setVisible(false);
                    vistaPoli.jLabelPoliAux.setVisible(false);
                    vistaPoli.txtPolAux.setVisible(false);

                break;

                //Multiplicar Polinomios 
                case 3:
                    //Restablecer las variables por si el usuario quiere realizar otra operación
                    //------------------------------
                    vistaPoli.PoliUser.setVisible(true);
                    vistaPoli.PoliUser.setText("Polinomio ingresado: "+list.mostrar());
                    vistaPoli.txtPol2.setVisible(true);
                    vistaPoli.txtPol2.setText("");
                    vistaPoli.jLabelPoli2.setText("Ingrese segundo polinomio");
                    vistaPoli.jLabelPoli2.setVisible(true);
                    vistaPoli.btnAccion.setText("Multiplicar");
                    vistaPoli.btnAccion.setVisible(true);
                    vistaPoli.ResultadoFin.setVisible(false);
                    vistaPoli.Resultado.setVisible(false);
                    vistaPoli.PoliUser2.setVisible(false);
                    vistaPoli.jLabelPoliAux.setVisible(false);
                    vistaPoli.txtPolAux.setVisible(false);

                break;

                //Determinar (x-c) es factor de P(X)
                case 4:
                    //Restablecer las variables por si el usuario quiere realizar otra operación
                    //------------------------------
                    vistaPoli.PoliUser.setVisible(true);
                    vistaPoli.PoliUser.setText("Polinomio ingresado: "+list.mostrar());
                    vistaPoli.txtPol2.setVisible(true);
                    vistaPoli.txtPol2.setText("");
                    vistaPoli.jLabelPoli2.setText("Ingrese un número C para determinar si el (x - c) es factor de P(x) ");
                    vistaPoli.jLabelPoli2.setVisible(true);
                    vistaPoli.btnAccion.setText("Determinar");
                    vistaPoli.btnAccion.setVisible(true);
                    vistaPoli.ResultadoFin.setVisible(false);
                    vistaPoli.Resultado.setVisible(false);
                    vistaPoli.PoliUser2.setVisible(false);
                    vistaPoli.jLabelPoliAux.setVisible(false);
                    vistaPoli.txtPolAux.setVisible(false);
                    
                break;
            }
        }
            
        if(e.getSource()== vistaPoli.btnAccion){
            vistaPoli.btnAccion.setVisible(false);
            vistaPoli.PoliUser.setVisible(true);   
            vistaPoli.txtPolAux.setVisible(false);
            vistaPoli.jLabelPoliAux.setVisible(false);
            vistaPoli.jLabelPoli2.setVisible(true);
            vistaPoli.ResultadoFin.setVisible(true);
            vistaPoli.Resultado.setVisible(true);
            vistaPoli.jLabelPoli2.setVisible(false);
            vistaPoli.txtPol2.setVisible(false);
            
            
            
            
            //segun la opcion seleccionada evaluará y mostrará el resultado de lo que se desea:
            switch(vistaPoli.cbOpciones.getSelectedIndex()){
                
                //evaluar
                case 1:
                    try{
                        int a = Integer.parseInt(vistaPoli.txtPol2.getText());                    
                        int Result = list.evalua(a);
                        vistaPoli.PoliUser2.setVisible(true);
                        vistaPoli.PoliUser2.setText("valor c: "+a);
//                        vistaPoli.Resultado.setText(Result.toString());
                    }catch(Exception eo){
                        
                        vistaPoli.cbOpciones.setSelectedIndex(0);
                        JOptionPane.showMessageDialog(null, "Valor no válido, por favor digitar valor numérico");

                    }                  
                    break;
                    
                //Suma entre dos listas
                case 2:
                    String auxSum = sinEspacios(vistaPoli.txtPol2.getText());
                    ListaSimple listToSum = toList(auxSum);
                    listToSum.ordenar();
                    vistaPoli.PoliUser.setVisible(true);
                    vistaPoli.PoliUser.setText("Polinomio ingresado: "+list.mostrar());
                    vistaPoli.PoliUser2.setVisible(true);
                    vistaPoli.PoliUser2.setText("Segundo polinomio ingresado: "+listToSum.mostrar());
                    vistaPoli.Resultado.setText(list.sumaPolinomios(listToSum).mostrar());
                    break;
                    
                //Multiplicacion entre dos listas
                case 3:
                    String auxMult = sinEspacios(vistaPoli.txtPol2.getText());
                    ListaSimple listMult = toList(auxMult);
                    listMult.ordenar();
                    vistaPoli.PoliUser.setVisible(true);
                    vistaPoli.PoliUser.setText("Polinomio ingresado: "+list.mostrar());
                    vistaPoli.PoliUser2.setVisible(true);
                    vistaPoli.PoliUser2.setText("Segundo polinomio ingresado: "+listMult.mostrar());
                    vistaPoli.Resultado.setText(list.multiplcacion(listMult).mostrar());
                      break;
                //Determinar (x-c) es factor de P(X)
                case 4:
                    try{
                        int c = Integer.parseInt(vistaPoli.txtPol2.getText());
                        vistaPoli.PoliUser2.setVisible(true);
                        vistaPoli.PoliUser2.setText("valor c ingresado: "+c);
                    if(list.detFactor(c) == true){
                        vistaPoli.Resultado.setText("(x - "+c+") es factor de P(x)");
                    }else{
                        vistaPoli.Resultado.setText("(x - "+c+") no es factor de P(x)");
                    }
                    }catch(Exception eo){
                        vistaPoli.cbOpciones.setSelectedIndex(0);
                        JOptionPane.showMessageDialog(null, "Valor no válido, por favor digitar valor numérico");
                    }
                    
                    
                    break;
                       
                    
            }
            
        }
        if(e.getSource() == vistaPoli.btnSalir){
            JOptionPane.showMessageDialog(null, "Hasta luego");
            System.exit(0);
        }
        if(e.getSource() == vistaPoli.btnBorrar) {
            JFileChooser fc = new JFileChooser();
            File file;
            String x="";
            String y="";

            int result = fc.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION){
                file = fc.getSelectedFile();
                GrafoG g = toGraph(file);
//                g.mostrandoGrafo();
//                g.borraArista(1, 7);
//                g.mostrandoGrafo();
                GrafoG h = g;
                h.mostrandoGrafo();
                int[] tu = h.buscarAristaBorrar();
                System.out.println("tu: "+tu[0]+ " jaja"+tu[1]);
                System.out.println("voy a fundir hpta \n");
                h.fundirVertices(tu[0], tu[1]);
                h.mostrandoGrafo();
                System.out.println(g.esCompleto(g));
                System.out.println(g.esDisperso(g));
                
                System.out.println("jaja :P "+ h.contarLasAristas());
                
            }
            
        }
//    public void actionPerformed(ActionEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    }   
}
