package elezioni;

import static java.util.Comparator.comparing;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;



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
	public void vota(Cittadino votante, String lista, String nome, String cognome) throws TentatoDoppioVoto, TaglioNonPermesso{
		if(elettori.get(nome + " " + cognome).isCandidato()){
			liste.get(lista).incVoti();
			boolean candidatoInLista = false;
			for(Candidato c : liste.get(lista).getTuttiCandidati()){
				if(c.getNome() == nome && c.getCognome() == cognome){
					c.incVoto();
					candidatoInLista = true;
				}
			}
			if(candidatoInLista == false){
				throw new TaglioNonPermesso("candidato non il lista");
			}
			if(((Elettore) votante).haVotato()){
				throw new TentatoDoppioVoto("l'elettore ha già votato");
			}
			((Elettore) votante).vota();
			
				
		}
	}

	/**
	 * Il cittadino votante esprime un voto per la lista
	 * il voto di preferenza va automaticamente al capolista
	 * @throws TentatoDoppioVoto se il cittadino ha gi� votato
	 */	
	public void vota(Cittadino votante, String lista) throws TentatoDoppioVoto{
		if(((Elettore) votante).haVotato()){
			throw new TentatoDoppioVoto("l'elettore ha già votato");
		}
		liste.get(lista).incVoti();
		((Candidato) liste.get(lista).getCapolista()).incVoto();
	}
	
	public long getNumeroVotanti(){
		return elettori.values().stream()
				.filter(x -> x.haVotato() == true)
				.count();
	}
	
	
	private Comparator<Candidato> cmp= new Comparator<Candidato>(){
		@Override
		public int compare(Candidato l1, Candidato l2){
			long sub = l2.getNumeroVoti() - l1.getNumeroVoti();
			if (sub > 0){ return -1;}
		if(sub < 0){return 1;}
			return 0;
		}
	};
	
	public Collection getRisultatiListe(){
		return liste.values().stream()
//				.sorted(cmp(l1, l2))
				.sorted(comparing((Lista l)->l.getNumeroVoti()).reversed())
				.collect(Collectors.toList());
	}

	public Collection getRisultatiCandidati(){		
		return liste.values().stream()
				.flatMap( l -> l.getTuttiCandidati().stream())
				.sorted(cmp)
				.collect(Collectors.toList());
	}
	
	
}
