package modelo;

public class NodoG {
    private int dato;
    private NodoG liga;
    
    public NodoG(int dato, NodoG n){
        this.dato= dato;
        this.liga=(NodoG)liga;
    }

    public int getDato() {
        return dato;
    }

    public void setDato(int dato) {
        this.dato = dato;
    }

    public NodoG getLiga() {
        return liga;
    }

    public void setLiga(NodoG liga) {
        this.liga = liga;
    }
    
    
}
