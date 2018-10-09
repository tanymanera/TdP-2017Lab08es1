package it.polito.tdp.dizionario.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.TraversalListenerAdapter;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import com.mysql.jdbc.StringUtils;

import it.polito.tdp.dizionario.db.WordDAO;

public class Model {
	private WordDAO dao;
	private SimpleGraph<String, DefaultEdge> grafoParoleSimili;
	private int numeroLettere = 0;

	public Model() {
		dao = new WordDAO();
	}

	public List<String> createGraph(int numeroLettere) {
		List<String> parole = dao.getAllWordsFixedLength(numeroLettere);
		
//		List<String> parole = new ArrayList<>();
//		parole = Arrays.asList("casa,case,cara,care,caro,cure,fila,pila,pile".split(",") );  

		grafoParoleSimili = new SimpleGraph<>(DefaultEdge.class);
		this.numeroLettere = numeroLettere;
		// Aggiunge tutti i vertici
		Graphs.addAllVertices(grafoParoleSimili, parole);
		
		// Aggiunge tutti gli archi
		for (String parola : parole) {
			
			//metodo lento con Card(parole) chiamate a db
			//List<String> paroleAdiacenti = dao.getAllSimilarWords(parola, numeroLettere);
			//metodo senza chiamata a db
			List<String> paroleAdiacenti = Utils.getAllSimilarWords(parole, parola, numeroLettere);

			for (String parolaAdiacente : paroleAdiacenti) {
				grafoParoleSimili.addEdge(parola, parolaAdiacente);
			}
		}

		return parole;
	}

	public List<String> displayNeighbours(String parolaInserita) {
		if (numeroLettere != parolaInserita.length())
			throw new RuntimeException("La parola inserita ha una lunghezza differente rispetto al numero dato.");

		List<String> paroleSimili = new ArrayList<String>();
		paroleSimili.addAll(Graphs.neighborListOf(grafoParoleSimili, parolaInserita));
		
		return paroleSimili;
	}
	
	public List<String> listTuttiVicini(String parolaInserita){
		if (numeroLettere != parolaInserita.length())
			throw new RuntimeException("La parola inserita ha una lunghezza differente rispetto al numero dato.");
		
		List<String> tuttiVicini = new ArrayList<String>();
		
		BreadthFirstIterator<String, DefaultEdge> bfi = 
				new BreadthFirstIterator<>(grafoParoleSimili, parolaInserita);

		bfi.addTraversalListener(new TraversalListenerAdapter<String, DefaultEdge>());
			
		while( bfi.hasNext()) {
			tuttiVicini.add(bfi.next());
		}
		
		return tuttiVicini;
	}

	public String findMaxDegree() {
		int maxDegre = 0;
		String result = null;
		for(String parola: grafoParoleSimili.vertexSet()) {
			int gradoCorrente = Graphs.neighborListOf(grafoParoleSimili, parola).size();
			if(gradoCorrente > maxDegre) {
				maxDegre = gradoCorrente;
				result = parola;
			}
		}
		
		if (maxDegre != 0) {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("Max degree: %d from vertex: %s\n", maxDegre, result));

			for (String v : Graphs.neighborListOf(grafoParoleSimili, result))
				sb.append(v + "\n");

			return sb.toString();
		}
		return "Non trovato.";
	}
}
