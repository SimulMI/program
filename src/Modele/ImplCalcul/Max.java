package Modele.ImplCalcul;

import java.util.ArrayList;

import Modele.Calcul;

/**
 * Classe Max.
 * @author Groupe PTUT SimulMI
 *
 */
public class Max implements Calcul {
	@Override
	/**
	 * Retourne la valeur maximum d'une liste de valeurs.
	 * @param valeurs les valeurs sur lesquelles sont appliquées le calcul
	 * @return la valeur maximum des valeurs
	 */
	public double calculer(ArrayList<Double> valeurs) {
		double res = valeurs.get(0);
		
		for (int i = 1; i < valeurs.size(); i++) {
			if(res < valeurs.get(i))
				res = valeurs.get(i);
		}
		
		return res;
	}

	@Override
	/**
	 * Retourne le nom du calcul.
	 * @return le nom du calcul
	 */
	public String nom() {
		return "Maximum";
	}
}
