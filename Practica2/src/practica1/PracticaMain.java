package practica1;

import controlador.Controlador;
import modelo.ListaSimple;
import vista.Polinomios;

public class PracticaMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("inicio");
        
        Polinomios vistaPoli = new Polinomios();
        Controlador controlPoli = new Controlador(vistaPoli);
        
        vistaPoli.setVisible(true);
        
//        Botones y Lebels visibles
        //vistaPoli.cbOpciones.setEnabled(false);
        vistaPoli.txtPol2.setVisible(false);
        vistaPoli.btnAccion.setVisible(false);
        vistaPoli.Resultado.setVisible(false);
        vistaPoli.jLabelPoli2.setVisible(false);
        vistaPoli.ResultadoFin.setVisible(false);
        vistaPoli.ResultadoFin.setVisible(false);

        
        vistaPoli.setLocationRelativeTo(null);
        
    }
    
}
