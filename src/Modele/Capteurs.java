package Modele;


/**
 * Enumération des capteurs. Ajouter une énumération pour ajouter un type.
 * @author Groupe PTUT SimulMI
 */
public enum Capteurs {
	luiminosite("LUM", "Luminosité"),
	temp("TEM", "Température");

	private String type;
	private String nom;
	
	/**
	 * Constructeur paramétré. 
	 * @param pNom nom du capteur
	 * @param pType type du capteur
	 */
	private Capteurs(String pType, String pNom) {
		type = pType;
		nom = pNom;
	}
	
	/**
	 * Retourne le type du capteur
	 * @return le type du capteur
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Retourne le nom du capteur.
	 * @return le nom du capteur 
	 */
	public String toString() {
		return nom;
	}
}
