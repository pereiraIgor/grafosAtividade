package teste;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.connectivity.KosarajuStrongConnectivityInspector;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.*;
import org.jgrapht.io.*;



public class main {
	public static void main(String[] args) {
	    VertexProvider <DefaultVertex> vp1 = 
	    		(label,attributes) -> new DefaultVertex (label,attributes);
	    EdgeProvider <DefaultVertex,RelationshipDirectedEdge> ep1 = 
	    		(from,to,label,attributes) -> new RelationshipDirectedEdge(from,to,attributes);
		GmlImporter <DefaultVertex,RelationshipDirectedEdge> gmlImporter = new GmlImporter <> (vp1,ep1);
	    DefaultDirectedGraph<DefaultVertex,RelationshipDirectedEdge> graphgml = new DefaultDirectedGraph<>(RelationshipDirectedEdge.class);
   	    try {
   	    	gmlImporter.importGraph(graphgml, ImportGraph.readFile("grid/grid.gml"));
	      } catch (ImportException e) {
	        throw new RuntimeException(e);
	      }	    		
 	    
	    Set <Object> V = new HashSet <Object>(graphgml.vertexSet());
	    Set <DefaultEdge> E = new HashSet <DefaultEdge>(graphgml.edgeSet());
	   

	    KosarajuStrongConnectivityInspector <DefaultVertex,RelationshipDirectedEdge> k = 
	    		new KosarajuStrongConnectivityInspector <> (graphgml);
	    
	    //algoritmo de kosaraju para detectar componentes fortementes conexos.
	   
	    
	    System.out.println("questão 1:\n");
	    System.out.println("É possível trafegar em ambos os sentidos de um cruzamento para qualquer outro? " + k.isStronglyConnected());
	    System.out.println("\nOs seguintes componentes não são acessiveis entre si:");
	    for(Set<DefaultVertex> aux : k.stronglyConnectedSets()) {
	    	System.out.println(aux);
	    }
	    
	    System.out.println("\nquestão 2:\n");
	    
	    
	    AllDirectedPaths <DefaultVertex,RelationshipDirectedEdge> p = new AllDirectedPaths <> (graphgml);
	    for(teste.DefaultVertex a: graphgml.vertexSet()) {
	    	boolean aux = true;
	    	for(teste.DefaultVertex b: graphgml.vertexSet()) {
	    		if(p.getAllPaths(b, a, true, 10000).size() > 0 && !a.equals(b))
	    			aux = false;
	    	}
	    	if(aux) System.out.println("o seguinte vertice é inacessível: " + a);
	    }
	    
	    
	    
	}
}
