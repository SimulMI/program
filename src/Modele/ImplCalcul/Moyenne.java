package Modele.ImplCalcul;

import java.util.ArrayList;

import Modele.Calcul;

/**
 * Classe Moyenne.
 * @author Groupe PTUT SimulMI
 *
 */
public class Moyenne implements Calcul {
	@Override
	/**
	 * Retourne la valeur moyenne d'une liste de valeurs.
	 * @param valeurs les valeurs sur lesquelles sont appliquées le calcul
	 * @return la valeur moyenne
	 */
	public double calculer(ArrayList<Double> valeurs) {
		double res = 0;
		
		for (int i = 0; i < valeurs.size(); i++) {
			res += valeurs.get(i);
		}
		
		return res / valeurs.size();
	}

	@Override
	/**
	 * Retourne le nom du calcul.
	 * @return le nom du calcul
	 */
	public String nom() {
		return "Moyenne";
	}
}
