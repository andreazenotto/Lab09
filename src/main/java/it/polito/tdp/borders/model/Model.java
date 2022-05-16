package it.polito.tdp.borders.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	private BordersDAO dao;
	private Map<Integer, Country> countries;
	private Graph<Country, DefaultEdge> grafo;
	
	public Model() {
		this.dao = new BordersDAO();
		this.countries = new HashMap<>();
		for(Country c: this.dao.loadAllCountries()) {
			this.countries.put(c.getCCode(), c);
		}
	}
	
	public Set<Country> creaGrafo(int anno) {
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		for(Border b: this.dao.getCountryPairs(anno)) {
			Country state1 = this.countries.get(b.getState1no());
			Country state2 = this.countries.get(b.getState2no());
			if(!this.grafo.containsVertex(state1)) {
				this.grafo.addVertex(state1);
			}
			if(!this.grafo.containsVertex(state2)) {
				this.grafo.addVertex(state2);
			}
			if(!this.grafo.containsEdge(state1, state2)) {
				this.grafo.addEdge(state1, state2);
			}
		}
		return this.grafo.vertexSet();
	}
	
	public String descriviGrafo() {
		String stringa = "";
		for(Country c: this.grafo.vertexSet()) {
			int countConfini = this.grafo.degreeOf(c);
			stringa += c.toString() + " | Stati confinanti: " + countConfini + "\n";
		}
		ConnectivityInspector<Country, DefaultEdge> ci = new ConnectivityInspector<>(this.grafo);
		stringa += "\nNumero di componenti connesse nel grafo: " + ci.connectedSets().size();
		return stringa;
	}
	
	public Collection<Country> getCountries(){
		return this.countries.values();
	}
	
	public Set<Country> trovaCountriesRaggiungibili(Country partenza) {
		Map<Country, Country> alberoInverso = visitaGrafo(partenza);
		return alberoInverso.keySet();
	}
	
	public Map<Country, Country> visitaGrafo(Country partenza) {
		GraphIterator<Country, DefaultEdge> visita = new BreadthFirstIterator<>(this.grafo, partenza);
		
		Map<Country, Country> alberoInverso = new HashMap<>() ;
		alberoInverso.put(partenza, null) ;
		
		visita.addTraversalListener(new RegistraAlberoDiVisita(alberoInverso, this.grafo));
		while (visita.hasNext()) {
			visita.next();
//			System.out.println(f);
		}
		
		return alberoInverso ;
	}

}
