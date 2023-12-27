package Laby;
//Par Sylvain Lobry, pour le cours "IF05X040 Algorithmique avanc�e"
//de l'Universit� de Paris, 11/2020

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

//Classe d�finissant un graphe pond�r�.

import java.util.ArrayList;
import java.util.LinkedList;

public class WeightedGraph {
   public ArrayList<Vertex> vertexlist;
   static int num_v = 0;

   public void printGraph() {
	   for (Vertex v : vertexlist) {
	       System.out.print("Vertex " + v.num + ": ");
	       for (Edge e : v.adjacencylist) {
	           System.out.print("-> " + e.destination.num);
	       }
	       System.out.println();
	   }
	}
   
   public WeightedGraph() {
       vertexlist = new ArrayList<Vertex>();
   }

   public ArrayList<Vertex> getVertexList() {
       return this.vertexlist;
   }

   public void addVertex(double indivTime) {
       Vertex v = new Vertex(num_v);
       v.indivTime = indivTime;
       vertexlist.add(v);
       num_v = num_v + 1;
   }

   public void addEgde(Vertex source, Vertex destination, double weight) {
       Edge edge = new Edge(source, destination, weight);
       source.addNeighbor(edge);
   }
// Sous-classe pour un sommet.
 static class Vertex implements Comparable<Vertex> {
	 
     double indivTime;
     double timeFromSource;
     double heuristic;
     Vertex prev;
     LinkedList<Edge> adjacencylist;
     int num;
     int firePosition;
     
     @Override
     public int compareTo(Vertex other) {
         return Integer.compare(this.num, other.num);
     }

     public Vertex(int num) {
         this.indivTime = Double.POSITIVE_INFINITY;
         this.timeFromSource = Double.POSITIVE_INFINITY;
         this.heuristic = -1;
         this.prev = null;
         this.adjacencylist = new LinkedList<Edge>();
         this.num = num;
         this.firePosition = -1;
     }

     public void addNeighbor(Edge e) {
         this.adjacencylist.addFirst(e);
     }
     
 }

//Classe d�finissant une arr�te.
static class Edge {
 Vertex source;
 Vertex destination;
 double weight;

 public Edge(Vertex destination, double weight) {
     this.destination = destination;
     this.weight = weight;
 }
 
 public Edge(Vertex source, Vertex destination, double weight) {
     this.source = source;
     this.destination = destination;
     this.weight = weight;
 }
}

//Sous-classe pour le graphe.
static class Graph {
 ArrayList<Vertex> vertexlist;
 static int num_v = 0;

 Graph() {
     vertexlist = new ArrayList<Vertex>();
 }

 public void addVertex(double indivTime)
 {
     Vertex v = new Vertex(num_v);
     v.indivTime = indivTime;
     vertexlist.add(v);
     num_v = num_v + 1;
 }
 
 public void addEgde(Vertex source, Vertex destination, double weight) {
     Edge edge = new Edge(source, destination, weight);
     source.addNeighbor(edge);
 }
 

 public void moveFire(Direction direction, int speed) {
	   for (Vertex v : vertexlist) {
	       v.firePosition += speed * direction.vector.x; // Update the fire position
	   }
	}




 
 // Test de la classe.
//Test de la classe.
		public static void main(String[] args) {
			 int vertices = 6;
			 Graph graph = new Graph();
			 graph.addVertex(10);
			 graph.addVertex(10);
			 graph.addVertex(10);
			 graph.addVertex(10);
			 graph.addVertex(10);
			 graph.addVertex(10);
			 graph.addEgde(graph.vertexlist.get(0), graph.vertexlist.get(1), 4);
			 graph.addEgde(graph.vertexlist.get(0), graph.vertexlist.get(2), 3);
			 graph.addEgde(graph.vertexlist.get(1), graph.vertexlist.get(3), 2);
			 graph.addEgde(graph.vertexlist.get(1), graph.vertexlist.get(2), 5);
			 graph.addEgde(graph.vertexlist.get(2), graph.vertexlist.get(3), 7);
			 graph.addEgde(graph.vertexlist.get(3), graph.vertexlist.get(4), 2);
			 graph.addEgde(graph.vertexlist.get(4), graph.vertexlist.get(0), 4);
			 graph.addEgde(graph.vertexlist.get(4), graph.vertexlist.get(1), 4);
			 graph.addEgde(graph.vertexlist.get(4), graph.vertexlist.get(5), 6);
			 
			// Création d'une instance de AStar avec le nœud de départ et le nœud d'arrivée
			  AStar astar = new AStar(graph.vertexlist.get(0), graph.vertexlist.get(5)); // Supposons que le nœud de départ est le premier nœud et que l'arrivée est le dernier nœud

			  // Appel de la méthode solve et impression du résultat
			  List<Vertex> path = astar.solve();
			  if (path == null) {
			      System.out.println("No solution found.");
			  } else {
			      System.out.println("Solution found:");
			      for (Vertex v : path) {
			          System.out.println("Vertex " + v.num);
			      }
			  }
		}
	}



}
