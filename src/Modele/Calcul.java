package Modele;

import java.util.ArrayList;

/**
 * Interface Calcul permettant l'impl�mentation de classe de calcul.
 * @author Groupe PTUT SimulMI
 *
 */
public interface Calcul {
	/**
	 * Effectue un calcul sur un tableau de valeurs.
	 * @param valeurs ArrayList de valeurs qui seront utilis�es pour le calcul
	 * @return Le r�sultat du cacul
	 */
	public double calculer(ArrayList<Double> valeurs);
	
	/**
	 * Retourne le nom du calcul.
	 * @return le nom du calcul
	 */
	public String nom();
}
