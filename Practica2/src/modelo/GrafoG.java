package modelo;

import java.util.ArrayList;


public class GrafoG {
    
    private NodoG vecinos[];
    private NodoG dato;
    private int numVertices;
    private int numAristas;
    
    public int getNumVertices(){
        return this.numVertices;
    }
    
    public void setNumVertices(int v) {
        this.numVertices = v;
    }
    
    public int getNumAristas(){
        return this.numAristas;
    }
    
    public void setNumAristas(int a) {
        this.numAristas = a;
    }
    
    public GrafoG(int v, int a) {
        setNumVertices(v);
        setNumAristas(a);
        vecinos = new NodoG[v+1];
        vecinos[0] = new NodoG(a,null);
        for(int i = 1; i < v; i++){
            vecinos[i] = null;
        }
    }
    
    public boolean aristaExiste(int va, int vb){
        NodoG actual = vecinos[va];
        while(actual!= null){
            if(actual.getDato() == vb ){
                return true;
            }
            actual = actual.getLiga();
        }
        return false;
    }
    
    public void aggArista(int va, int vb){ //Recibo vertice a y vertice b: Están conectados.
       if(!aristaExiste(va,vb)){
           NodoG nuevito = new NodoG(vb,null);
           if(vecinos[va]==null){
               vecinos[va] = nuevito;
               aggArista(vb, va);
           }
           else{
               NodoG actual = vecinos[va];
               while(actual.getLiga() != null){
                   actual = actual.getLiga();
               }
               actual.setLiga(nuevito);
               aggArista(vb,va);
           }
       }
    }
    
    public void borraArista(int va,int vb){
        if(aristaExiste(va, vb)){
            if(vecinos[va].getDato() == vb){
                vecinos[va] = vecinos[va].getLiga();
                borraArista(vb,va);
                return;
            }
            NodoG actual = vecinos[va].getLiga();
            NodoG anterior = vecinos[va];
            
            while(actual !=null){
                if(actual.getDato() == vb){
                   anterior.setLiga(actual.getLiga());
                    borraArista(vb, va);
                }
                anterior = actual;
                actual = actual.getLiga();    
            }
        }        
    }
    
    public int[]  buscarAristaBorrar(){
        int[] ret = new int[2];
        for (int i = 1; i < vecinos.length; i++) {
            NodoG actual = vecinos[i];
            if(actual != null){
                ret[0] = i;
                ret[1] = actual.getDato();
                break;
            }
        
        }
       return ret;
    }
    
    public void mostrandoGrafo(){
        for (int i = 1; i < vecinos.length; i++) {
             NodoG actual = vecinos[i];
             System.out.printf("%d", i);             
             while(actual != null){
                 System.out.printf("-> %d", actual.getDato());                 
                 actual = actual.getLiga();
             }
             System.out.println("\n");
        }
    }
    
    public boolean esDisperso(GrafoG g){ 
        int aristas = g.contarLasAristas();
        g.setNumAristas(aristas);
        int calc = (g.getNumVertices()*(g.getNumVertices()-1))/2; 
        return (g.getNumAristas() <= (calc/2));
    }
    public boolean esCompleto(GrafoG g){
        int aristas = g.contarLasAristas();
        g.setNumAristas(aristas);
        System.out.println("Aristas " + aristas);
        int calc = (g.getNumVertices()*(g.getNumVertices()-1))/2; 
        return (g.getNumAristas() == calc);
    }
    public void fundirVertices(int va, int vb) {
        int[] visitados = new int[numVertices+1];
        int vertice;
        for(int i = 1; i < visitados.length; i++) {
            visitados[i] = 0;
        }
        for(int i = 1; i < vecinos.length; i++) {
           vertice = i;         
           NodoG actuali = vecinos[vertice];
           if(actuali != null) {
               System.out.println("vertice"+vertice);
                if(i == va) {
                    while(actuali != null) {
                        if(actuali.getDato() == vb) {
                            this.borraArista(va, vb);
                        }
                        if((actuali.getDato() != vb)&& (actuali.getDato() != va)) {
                            if(actuali.getDato() > vb){
                                actuali.setDato(actuali.getDato()-1);
                            }
                        }
                        System.out.println(actuali.getDato());
                        actuali = actuali.getLiga();
                        
                    }
                } else if(i == vb) {
                    if(visitados[vertice] == 0) {
                        while(actuali != null) {
                            if(actuali.getDato() != va) {
                                aggArista(va, actuali.getDato());
                                borraArista(actuali.getDato(), vb);
                            }
                            System.out.println(actuali.getDato());
                            actuali = actuali.getLiga();
                            visitados[vertice] = 1;
                            
                            vecinos[vertice]=vecinos[vertice+1];                           
                        }
                    } else {
                        while(actuali != null) {
                            if(actuali.getDato() > vb){
                                actuali.setDato(actuali.getDato()-1);
                            }else if(actuali.getDato() == vb){
                                borraArista(actuali.getDato(), vb);
                            }
                            System.out.println(actuali.getDato());
                            actuali = actuali.getLiga();                            
                        }
                    }
                } else {
                    while(actuali != null) {
                        if(actuali.getDato() != va) {
                            if(actuali.getDato() > vb){
                                actuali.setDato(actuali.getDato()-1);
                            }else if(actuali.getDato() == vb){
                                borraArista(actuali.getDato(), vb);
                            }
                        }
                        System.out.println(actuali.getDato());
                        actuali = actuali.getLiga();
                    }
                }
                if(vertice > vb ) {
                    if(vertice+1 == vecinos.length) {
                        vecinos[vertice] = null;
                    } else {
                        vecinos[vertice] = vecinos[vertice+1];
                    }
                    
                }
                visitados[vertice] = 1;
                
            }        
        }
    }
    
    public String genPoliCrom(){
        int[] borrar = new int[2];
        int numV = this.getNumVertices();
        GrafoG nuevo = this;
        if(esCompleto(nuevo)){
            String poli ="x";
            for (int i = 1; i <= numV; i++) {
                poli = poli+"(x-"+i+")";
            }
            return poli;
        }
        else if(esDisperso(nuevo)){
            String poli;
            poli = "x"+numV;
            return poli;
        }else{
            borrar = nuevo.buscarAristaBorrar();
            GrafoG b = nuevo;
            b.borraArista(borrar[0], borrar[1]);
            GrafoG f = nuevo;
            f.fundirVertices(borrar[0], borrar[1]);           
            return (b.genPoliCrom() + "-" +f.genPoliCrom());
        }
    }
    
    public int contarLasAristas(){
        int contarAristas = 0;
        for (int i = 1; i < vecinos.length; i++) {
            NodoG actual = vecinos[i];
            System.out.println("Vertice " + (i));
            while  (actual !=null){
                System.out.println("Arista " + actual.getDato()+ " numero " + contarAristas);
                contarAristas = contarAristas +1;
                actual = actual.getLiga();
            }
        }
        
        return (contarAristas/2);
    }
    public int contarLosVertices(){
        int contarVertices = 0;
        for (int i = 1; i < this.vecinos.length; i++) {
            NodoG actual = vecinos[i];
            if(actual!= null) {
                contarVertices = contarVertices + 1;
            }
        }
        
        return (contarVertices);
    }
}
