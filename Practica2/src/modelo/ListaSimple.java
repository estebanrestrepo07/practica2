/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;


public class ListaSimple {
    private Nodo cabeza;
    private Nodo primero;
    private Nodo ultimo;
    private int evaluar;
    private int total = 0;

    private Nodo auxiliar;
    private Nodo a;
    private Nodo b;
    
    
    public ListaSimple() {
        primero = null;
        ultimo = null;
        cabeza = new Nodo(0,0);
        cabeza.setLiga(primero);
    }
    
    public boolean esVacia() {
        return primero == null;
    }
    public Nodo cabeza(){
        return cabeza;
    }    
    public Nodo primerNodo() {
        return primero;
    }
    
    public Nodo ultimoNodo() {
        return ultimo;
    }
    
    public boolean finRecorrido(Nodo x) {        
        return x  == null;
    }
    
    //evaluar un polinomio listado con valor v
    public int evalua(int v) {
        Nodo p = primerNodo();
        total = 0; evaluar = 0;
        Nodo q = ultimoNodo();
        while(!finRecorrido(p)){ 
            evaluar = p.getCoeficiente();
            evaluar = evaluar * (int)Math.pow(v, p.getExponente());
            total = total + evaluar;
            p = p.getLiga();
            
        }
        return (total);
    }
    
    //sumar dos polinomios listados
    public ListaSimple sumaPolinomios(ListaSimple q) {
            a=this.primerNodo();
            b=q.primerNodo();
            ListaSimple suma = new ListaSimple();

            while(!finRecorrido(a) && !q.finRecorrido(b)){
              if(a!= null && b!=null && a.getExponente()>b.getExponente()){
                suma.insertarNodo(a.getCoeficiente(),a.getExponente());
                a=a.getLiga();
              }

              if(a!= null && b!=null && a.getExponente()<b.getExponente()){
                suma.insertarNodo(b.getCoeficiente(),b.getExponente() );
                b=b.getLiga();
              }

              if(a!= null && b!=null && a.getExponente()==b.getExponente()){
                suma.insertarNodo(a.getCoeficiente()+b.getCoeficiente(),a.getExponente() );
                a=a.getLiga();
   
                b=b.getLiga();
               
              }
              
            }
            while(!finRecorrido(a) ){
                suma.insertarNodo(a.getCoeficiente(),a.getExponente());
                a=a.getLiga();
            }

            while(!q.finRecorrido(b) ){
                suma.insertarNodo(b.getCoeficiente(),b.getExponente());
                b=b.getLiga();
            }

            return suma;
    }
    
    
      
  
    //se ordena la lista para mejor manejo de las listas en los metodos de suma y multiplicacion
    public void ordenar(){
        Nodo p;
        Nodo pp ;
        Nodo q;
        Nodo pq;
        Nodo max;
        Nodo pmax;
        
        p = primerNodo();
        pp = null;
        while(p != ultimoNodo()){
            max = p;
            pmax = pp;
            q = p.getLiga();
            pq = p;
            
            while(!finRecorrido(q)){
                if(q.getExponente() > max.getExponente()){
                    max = q;
                    pmax = pq;
                }
                pq = q;
                q = q.getLiga();
            }
            if(max == p){
                pp = p;
                p = p.getLiga();
            }else{
                desconectar(max, pmax);
                conectar(max,pp);
                pp = max;
            } 
        }
    }
    
    //se inserta un nodo al final
    public void insertarNodo(int c, int e){
        Nodo nuevo = new Nodo(c,e);
        if(this.esVacia()){
            cabeza.setExponente(e);
            cabeza.setCoeficiente(cabeza.getCoeficiente()+1);
            primero = nuevo;
            ultimo = nuevo;
            cabeza.setLiga(primero);
        }else{
            if(cabeza.getExponente() < e){
                cabeza.setExponente(e);
            }
            cabeza.setCoeficiente(cabeza.getCoeficiente()+1);
            auxiliar = this.ultimoNodo();              
            auxiliar.setLiga(nuevo);
            ultimo = nuevo;
        } 
    }
    //conecta un nodo x con un nodo y
    private void conectar(Nodo x, Nodo y) {
        if (y != null) {
            x.setLiga(y.getLiga());
            y.setLiga(x);
            if( y == ultimo) {
                ultimo = x;
            }
        } else {
            x.setLiga(primero);
            if(primero == null) {
                ultimo = x;
            }
            if(cabeza.getExponente() < x.getExponente()){
                cabeza.setExponente(x.getExponente());
            }
            cabeza.setCoeficiente(cabeza.getCoeficiente()+1);
            primero = x;
            cabeza.setLiga(x);
        }
    }
    
    //desconecta un nodo x con un nodo y
    private void desconectar(Nodo x, Nodo y) {
        if (x != primero) {
            y.setLiga(x.getLiga());
            if(x == ultimo) {
                ultimo = y;
            }
        } else {    
            primero = primero.getLiga();
            System.out.println("el nuevo primero es:"+primero.getExponente());
            cabeza.setLiga(primero);
            if(primero == null) {
                ultimo = null;
            }
        }
    }
    //borrar lista
    public void borrar() {
        if (this == null) {
            return;
        }
        a = this.primerNodo();
        while(!finRecorrido(a)){
            desconectar(a, a.getLiga());
        }
        
    }
    //retorna el nodo anterior a x
    public Nodo anterior(Nodo x) {
        Nodo p;
        Nodo q;
        p = primerNodo();
        q = null;
        
        while(p != x) {
            q = p;
            p = p.getLiga();
        }
        return q;
    }
    
    //retorna la lista en string para mostrarla en la vista
    public String mostrar(){
        Nodo cambielo = this.primerNodo();

        String paraMostrar = "";
        while(!finRecorrido(cambielo)){
            paraMostrar = paraMostrar +cambielo.getCoeficiente() + "x" + cambielo.getExponente() + " ";
           
            cambielo = cambielo.getLiga();
        }
        return paraMostrar;
    }  
    
}
