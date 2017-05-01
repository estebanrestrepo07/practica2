/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1;

import controlador.Controlador;
import modelo.ListaSimple;
import vista.Polinomios;

/**
 *
 * @author Santiago
 */
public class PracticaMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("inicio");
        
        Polinomios vistaPoli = new Polinomios();
        Controlador controlPoli = new Controlador(vistaPoli);
        
        vistaPoli.setVisible(true);
        
//        Botones y Lebels visibles
        //vistaPoli.cbOpciones.setEnabled(false);
        vistaPoli.txtPol2.setVisible(false);
        vistaPoli.btnAccion.setVisible(false);
        vistaPoli.PoliUser.setVisible(false);
        vistaPoli.PoliUser2.setVisible(false);
        vistaPoli.Resultado.setVisible(false);
        vistaPoli.txtPolAux.setVisible(false);
        vistaPoli.jLabelPoliAux.setVisible(false);
        vistaPoli.jLabelPoli2.setVisible(false);
        vistaPoli.ResultadoFin.setVisible(false);
        vistaPoli.ResultadoFin.setVisible(false);
        
        vistaPoli.jTextArea.setEnabled(false);
        
        vistaPoli.setLocationRelativeTo(null);
        
    }
    
}
