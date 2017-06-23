package elezioni;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class Lista {
	private String nomeLista;
	private String motto;
	private Cittadino capolista = null;
	
	private List<Candidato> candidati = new ArrayList<>();
	

	public Lista(String nome, String motto){
		this.nomeLista = nome;
		this.motto = motto;
	}
	
	public String getNome(){
		return nomeLista;
	}

	public String getMotto(){
		return motto;
	}
	
	public void assegnaCapolista(Cittadino capolista) throws CandidatoNonValido {
		if(capolista != null || ((Candidato)capolista).getLista()!= this){
			throw new CandidatoNonValido("esiste già un capolista");
		}
		this.capolista = capolista;
		((Candidato) capolista).setCapolista();
		((Elettore) capolista).setCapolista();
		((Elettore) capolista).setCandidato();
		((Candidato) capolista).setListaAppartenenza(this);
	}

	public void aggiungiCandidato(Cittadino candidato) throws CandidatoNonValido {
		if(((Candidato) candidato).getLista() != null && ((Candidato) candidato).getLista() != this){
			throw new CandidatoNonValido("candidato già appartenente a una lista");
		}
		((Candidato) candidato).setListaAppartenenza(this);
		candidati.add((Candidato) candidato);
	}

	public Cittadino getCapolista(){
		return capolista;
	}

	/**
	 * Restuisce la collezione dei candidati
	 * (NON include il capolista)
	 */
	public Collection getCandidati(){
		return candidati.stream()
				.filter(x -> x != x.getLista().getCapolista())
				.collect(Collectors.toList());
	}
	
	
	public long getNumeroVoti(){
		return -1;
	}

	public double getPercentualeVoti(){
		return -1.1;
	}
}
