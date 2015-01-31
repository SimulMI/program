package Modele;


/**
 * Enum�ration des capteurs. Ajouter une �num�ration pour ajouter un type.
 * @author Groupe PTUT SimulMI
 */
public enum Capteurs {
	luiminosite("LUM", "Luminosit�"),
	temp("TEM", "Temp�rature");

	private String type;
	private String nom;
	
	/**
	 * Constructeur param�tr�. 
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
