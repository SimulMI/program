package Modele;

/**
 * Enum�ration des pi�ces. Ajouter une �num�ration pour ajouter une pi�ce.
 * @author Groupe PTUT SimulMI
 */
public enum Pieces {
	entree("ENTREE", "Entr�e"),
	hall("HALL", "Hall"),
	salon("SALON", "Salon"),
	cuisine("CUISINE", "Cuisine"),
	WC("WC", "WC"),
	salleDEau("SDB", "Salle de bain"),
	chambre("CHAMBRE", "Chambre"),
	exterieur("EXT", "Ext�rieur");
	
	
	private String libelle;
	private String id;
	
	/**
	 * Constructeur param�tr�. 
	 * @param pId identifiant de la pi�ce
	 * @param pLibelle libelle de la pi�ce
	 */
	private Pieces(String pId, String pLibelle) {
		id = pId;
		libelle = pLibelle;
	}
	
	/**
	 * Retourne l'id de la pi�ce.
	 * @return l'id de la pi�ce
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Retourne le libell� de la pi�ce.
	 * @return le libell� de la pi�ce 
	 */
	public String toString() {
		return libelle;
	}
}
