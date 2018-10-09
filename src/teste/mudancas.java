package teste;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.connectivity.KosarajuStrongConnectivityInspector;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.*;
import org.jgrapht.io.*;

/**
 * Classe responsavel por gerar os resultados da questão 2 da segunda pratica de
 * grafos.
 *
 */

public class mudancas {
	public static void main(String[] args) {
		System.out.println("questão 1:\n");
		/**
		 * Metodo responsavel por gerar a saida do primeiro item da questão dois. Nele ,
		 * é pedido para verificar se o grafo é conectado. Depois e imprimido quais os
		 * componentes não sao acessiveis entre si.
		 */
		VertexProvider<DefaultVertex> vp1 = (label, attributes) -> new DefaultVertex(label, attributes);
		EdgeProvider<DefaultVertex, RelationshipDirectedEdge> ep1 = (from, to, label,
				attributes) -> new RelationshipDirectedEdge(from, to, attributes);
		GmlImporter<DefaultVertex, RelationshipDirectedEdge> gmlImporter = new GmlImporter<>(vp1, ep1);
		DefaultDirectedGraph<DefaultVertex, RelationshipDirectedEdge> graphgml = new DefaultDirectedGraph<>(
				RelationshipDirectedEdge.class);
		try {
			gmlImporter.importGraph(graphgml, ImportGraph.readFile("grid/grid.gml"));
		} catch (ImportException e) {
			throw new RuntimeException(e);
		}

		Set<Object> V = new HashSet<Object>(graphgml.vertexSet());
		Set<DefaultEdge> E = new HashSet<DefaultEdge>(graphgml.edgeSet());

		KosarajuStrongConnectivityInspector<DefaultVertex, RelationshipDirectedEdge> k = new KosarajuStrongConnectivityInspector<>(
				graphgml);

		// algoritmo de kosaraju para detectar componentes fortementes conexos.
		System.out.println("É possível trafegar em ambos os sentidos de um cruzamento para qualquer outro? "
				+ k.isStronglyConnected());
		System.out.println("\nOs seguintes componentes não são acessiveis entre si:");
		for (Set<DefaultVertex> aux : k.stronglyConnectedSets()) {
			System.out.println(aux);
		}
		System.out.println("\nquestão 2:\n");

		/**
		 * No segundo item da questão 2, é pedido para verificar se existem vertices que
		 * não possuem grau de entrada gerando assim vertices que são inacessiveis
		 */
		AllDirectedPaths<DefaultVertex, RelationshipDirectedEdge> p = new AllDirectedPaths<>(graphgml);
		for (teste.DefaultVertex a : graphgml.vertexSet()) {
			boolean aux = true;
			for (teste.DefaultVertex b : graphgml.vertexSet()) {
				if (p.getAllPaths(b, a, true, 10000).size() > 0 && !a.equals(b))
					aux = false;
			}
			if (aux)
				System.out.println("o seguinte vertice é inacessível: " + a);
		}

		System.out.println("\nquestão 3:\n");
		/**
		 * No terceiro item da questão dois, é pedido os caminhos dentre dois vertices
		 * diferentes. Para isso, é verificado varias vezes os caminhos entre dois
		 * vertices, resultando em um maior caminho, menor caminho e a media de todos os
		 * caminhos.
		 */
		Integer maior;
		Integer menor;

		for (teste.DefaultVertex a : graphgml.vertexSet()) {
			for (teste.DefaultVertex b : graphgml.vertexSet()) {
				List<GraphPath<DefaultVertex, RelationshipDirectedEdge>> grafo = p.getAllPaths(a, b, true, 1000000);
				if (grafo.size() > 0 && !a.equals(b)) {

					maior = grafo.get(0).getEdgeList().size();
					menor = grafo.get(0).getEdgeList().size();
					for (GraphPath<DefaultVertex, RelationshipDirectedEdge> c : grafo) {
						if (c.getEdgeList().size() > maior)
							maior = c.getEdgeList().size();
						if (c.getEdgeList().size() < menor)
							menor = c.getEdgeList().size();

					}

					System.out.println("os tamanhos dos caminhos entre " + a + " e " + b + " são -> maior: " + maior
							+ " menor: " + menor + " media: " + (maior + menor) / 2);
				}
			}

		}

	}
}
