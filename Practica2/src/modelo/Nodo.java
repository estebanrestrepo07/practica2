/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

public class Nodo {
    
    
    private Nodo liga;
    private int exp = 0;
    private int coe = 0;
    
    
    public Nodo(int c, int e) {
        
        coe = c;
        exp = e;
        liga = null;
    }
    
    public int getExponente() {
        return exp;
    }
    
    public int getCoeficiente() {
        return coe;
    }

    
    public Nodo getLiga() {
        return liga;
    }
    
    public void setExponente(int e) {
        exp = e;
    }
    
    public void setCoeficiente(int c) {
        coe = c;
    }
  
    public void setLiga(Nodo x) {
        liga = x;
    }
}
