package modelo;

import java.util.ArrayList;

public class GrafoG {
    
    private NodoG[] vecinos;
    private NodoG dato;
    
    public GrafoG(int v, int a) {
        vecinos = new NodoG[v+1];
        vecinos[0].setDato(a); 
        }
    public void newArtista(int va, int vb){ //Recibo vertice a y vertice b: Están conectados.
        
       NodoG a,b,aux = null; //Nuevos nodos, les doy el valor en campo dato los enteros que entraron por parámetro.
       a = new NodoG(va, null);
       b = new NodoG(vb, null);
       
       //Inserción de b como vecino de a
       if(vecinos[va].getLiga()==null){
           vecinos[va].setLiga(b);
           aux=a;
       }
       else{
          vecinos[va].setLiga(b);
          a.setLiga(aux);
       }
       
       //Inserción de a como vecino de b
       if(vecinos[vb].getLiga()==null){
           vecinos[vb].setLiga(a);
           aux=b;
       }
       else{
          vecinos[vb].setLiga(a);
          b.setLiga(aux);
       }
        
    }
}