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
//       NodoG a,b,aux = null; //Nuevos nodos, les doy el valor en campo dato los enteros que entraron por parámetro.
//       a = new NodoG(va, null);
//       b = new NodoG(vb, null);
//       
//       //Inserción de b como vecino de a
//       if(vecinos[va].getLiga()==null){
//           vecinos[va].setLiga(b);
//           aux=a;
//       }
//       else{
//          vecinos[va].setLiga(b);
//          a.setLiga(aux);
//       }
//       
//       //Inserción de a como vecino de b
//       if(vecinos[vb].getLiga()==null){
//           vecinos[vb].setLiga(a);
//           aux=b;
//       }
//       else{
//          vecinos[vb].setLiga(a);
//          b.setLiga(aux);
//       }
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
        return (g.getNumAristas() == 0);
    }
    public boolean esCompleto(GrafoG g){
        int calc = (g.getNumVertices()*(g.getNumVertices()-1))/2; 
        return (g.getNumAristas() == calc);
    }
    public void fundirVertices(int va, int vb) {
        int[] visitados = new int[this.numVertices];
        for(int i = 1; i < vecinos.length; i++) {
           visitados[i] = 0;
           NodoG actual = vecinos[i];
           if(actual.getDato() == va) {
               while(actual != null) {
                   if((actual.getDato() != vb)&& (actual.getDato() != va)) {
                       actual.setDato(actual.getDato()-1);
                   }
                   actual = actual.getLiga();
               }
           } else if(actual.getDato() == vb) {
               if(visitados[i] == 0) {
                   while(actual != null) {
                       if(actual.getDato() != va) {
                           this.aggArista(va, actual.getDato());
                       }
                       actual = actual.getLiga();
                       visitados[i] = 1;
                       i = i - 1;
                   }
               } else {
                   while(actual != null) {
                       actual.setDato(actual.getDato()-1);
                       actual = actual.getLiga();
                   }
               }
           } else {
               while(actual != null) {
                   if(actual.getDato() != va) {
                       actual.setDato(actual.getDato()-1);
                   }
                   actual = actual.getLiga();
               }
           }
           visitados[i] = 1;
        }
    }
}