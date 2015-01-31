package Modele;

import java.util.ArrayList;

/**
 * Interface Calcul permettant l'implémentation de classe de calcul.
 * @author Groupe PTUT SimulMI
 *
 */
public interface Calcul {
	/**
	 * Effectue un calcul sur un tableau de valeurs.
	 * @param valeurs ArrayList de valeurs qui seront utilisées pour le calcul
	 * @return Le résultat du cacul
	 */
	public double calculer(ArrayList<Double> valeurs);
	
	/**
	 * Retourne le nom du calcul.
	 * @return le nom du calcul
	 */
	public String nom();
}
