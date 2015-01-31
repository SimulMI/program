package Modele;

/**
 * Enumération des pièces. Ajouter une énumération pour ajouter une pièce.
 * @author Groupe PTUT SimulMI
 */
public enum Pieces {
	entree("ENTREE", "Entrée"),
	hall("HALL", "Hall"),
	salon("SALON", "Salon"),
	cuisine("CUISINE", "Cuisine"),
	WC("WC", "WC"),
	salleDEau("SDB", "Salle de bain"),
	chambre("CHAMBRE", "Chambre"),
	exterieur("EXT", "Extérieur");
	
	
	private String libelle;
	private String id;
	
	/**
	 * Constructeur paramétré. 
	 * @param pId identifiant de la pièce
	 * @param pLibelle libelle de la pièce
	 */
	private Pieces(String pId, String pLibelle) {
		id = pId;
		libelle = pLibelle;
	}
	
	/**
	 * Retourne l'id de la pièce.
	 * @return l'id de la pièce
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Retourne le libellé de la pièce.
	 * @return le libellé de la pièce 
	 */
	public String toString() {
		return libelle;
	}
}
