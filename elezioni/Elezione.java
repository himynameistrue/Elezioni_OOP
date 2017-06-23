package elezioni;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class Elezione {
	
	Map<String, Elettore> elettori = new HashMap<>();
	Map<String, Lista> liste = new HashMap<>();

	public Elezione(){
		
	}
	
	public Cittadino aggiungiElettore(String nome, String cognome){
		Elettore c = new Elettore(nome, cognome);
		elettori.put(nome+ " " +cognome, c);
		return c;
	}
	
	public Collection getElettori(){
		return elettori.values();
	}
	
	public Cittadino getElettore(String nome, String cognome){
		return elettori.get(nome + " " + cognome) ;
	}
	
	public void registraLista(Lista lista){
		if(liste.containsKey(lista.getNome())!= false){
			liste.put(lista.getNome(), lista);
		}
		
	}

    /**
     * Il cittadino votante esprime un voto per la lista ed 
     * un voto di preferenza per il candidato identificato
     * da nome e cognome
     * @throws TentatoDoppioVoto se il cittadino ha gi� votato
     * @throws TaglioNonPermesso se il candidato per cui si esprime
     * 							la preferenza non appartiene alla lista
     */	
	public void vota(Cittadino votante, String lista, String nome, String cognome)
		throws TentatoDoppioVoto, TaglioNonPermesso{
	}

	/**
	 * Il cittadino votante esprime un voto per la lista
	 * il voto di preferenza va automaticamente al capolista
	 * @throws TentatoDoppioVoto se il cittadino ha gi� votato
	 */	
	public void vota(Cittadino votante, String lista)
		throws TentatoDoppioVoto{
	}
	
	public long getNumeroVotanti(){
		return -1;
	}
	
	public Collection getRisultatiListe(){
		return null;
	}

	public Collection getRisultatiCandidati(){
		return null;
	}
	
	
}
