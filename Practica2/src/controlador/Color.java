package controlador;

import java.io.*;
import java.util.*;
import java.util.LinkedList; //Metodo de java para listas ligadas
 
// Representa un grafo no-dirigido con lista de adyacencia
class Grafo
{
    private int V;   // No. de vertices
    private LinkedList<Integer> adj[]; //Lista de adyacencia
 
    //Constructor
    Grafo(int v)
    {
        V = v;
        adj = new LinkedList[v];
        for (int i=0; i<v; ++i)
            adj[i] = new LinkedList();
    }
 
    //Añadir una arista
    void addArista(int v,int w)
    {
        adj[v].add(w);
        adj[w].add(v); 
    }
 
    //Colorea el grafo. Empezando color en 0
    void Coloracion()
    {
        int result[] = new int[V];
 
        // AAsignar primer color al primer vértice
        result[0]  = 0;
 
        // Initialize remaining V-1 vertices as unassigned
        for (int u = 1; u < V; u++)
            result[u] = -1;  // no color is assigned to u
 
        //Arry temporal para guardar los colores disponibles
        // Si cr=0 entonces el color está diponible
        // Asigna a uno de los vértices adyacentes
        boolean available[] = new boolean[V];
        for (int cr = 0; cr < V; cr++)
            available[cr] = false;
 
        // Asigna color a los restantes V-1 vertices
        for (int u = 1; u < V; u++)
        {
            // Process all adjacent vertices and flag their colors
            // as unavailable
            Iterator<Integer> it = adj[u].iterator() ;
            while (it.hasNext())
            {
                int i = it.next();
                if (result[i] != -1)
                    available[result[i]] = true;
            }
 
            // Encuentra el primer color disponible
            int cr;
            for (cr = 0; cr < V; cr++)
                if (available[cr] == false)
                    break;
 
            result[u] = cr; // Assign the found color
 
            // Reset the values back to false for the next iteration
            it = adj[u].iterator() ;
            while (it.hasNext())
            {
                int i = it.next();
                if (result[i] != -1)
                    available[result[i]] = false;
            }
        }
 
        //Resultado
        for (int u = 0; u < V; u++)
            System.out.println("Vertice " + u + " --->  Color "
                                + result[u]);
        System.out.println("");
        for (int i = 0; i < result.length; i++){
            System.out.println(result[i]);
        }
    }
 
    //Main
    public static void main(String args[])
    {
        
        
        Grafo g1 = new Grafo(5);
        g1.addArista(0, 1);
        g1.addArista(0, 2);
        g1.addArista(1, 2);
        g1.addArista(1, 3);
        g1.addArista(2, 3);
        g1.addArista(3, 4);
        System.out.println("Grafo 1");
        g1.Coloracion();
 
        System.out.println();
        Grafo g2 = new Grafo(5);
        g2.addArista(0, 1);
        g2.addArista(0, 2);
        g2.addArista(1, 2);
        g2.addArista(1, 4);
        g2.addArista(2, 4);
        g2.addArista(4, 3);
        System.out.println("Grafo 2 ");
        g2.Coloracion();
        
        
    }
}

