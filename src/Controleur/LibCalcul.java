package Controleur;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import Modele.Calcul;
import Modele.Capteurs;
import Modele.Pieces;
import Modele.ImplCalcul.Max;
import Modele.ImplCalcul.Min;
import Modele.ImplCalcul.Moyenne;

/**
 * Classe LibCalcul.
 * @author Groupe PTUT SimulMI
 *
 */
public class LibCalcul {
	private LibCalcul() {}
	
	/**
	 * Attribut nombreCalcul � modifier si une nouvelle classe Calcul a �t� cr��e. 
	 */
	private static final int nombreCalcul = 3;
	private static Calcul[] calculs = new Calcul[nombreCalcul];
	
	/**
	 * Initialise le tableau de calcul.
	 */
	public static void initCalculs() {
		calculs[0] = new Max();
		calculs[1] = new Min();
		calculs[2] = new Moyenne();
	}
	
	/**
	 * Effectue le calcul sur les valeurs des capteurs re�ues.
	 * @param nomCalcul HashMap dont la cl� est le type du capteur et dont la valeur est le nom du calcul
	 * @param lignes lignes du fichier de log filtr� et pars�
	 * @return une HashMap&lt;String,Double&gt; dont la cl� est le type de capteur concat�n� � la pi�ece, et la valeur est la valeur de la combinaison 
	 * @throws ParseException erreur de parse du fichier
	 */
	public static HashMap<String,Double> calculer(HashMap<String, String> nomCalcul, ArrayList<String[]> lignes) throws ParseException {
		boolean isCalculated = false;
		HashMap<String,Double> res = new HashMap<String, Double>();
		HashMap<String,ArrayList<Double>> valeurs = getValeurs(lignes);
		
		for (Capteurs cap : Capteurs.values()) {
			for(Pieces piece : Pieces.values()) {
				if(valeurs.containsKey(cap.getType() + "-" + piece.getId())) {
					for (int j = 0; j < calculs.length && !isCalculated; j++) {
						if(calculs[j].nom().equals(nomCalcul.get(cap.getType()))) {
							res.put(cap.getType() + "-" + piece.getId(), calculs[j].calculer(valeurs.get(cap.getType() + "-" + piece.getId())));
							isCalculated = true;
						}
					}
					isCalculated = false;
				}
			}
		}
		
		return res;
	}
	
	/**
	 * Retourne une HashMap dont la cl� est le type de capteur concat�n� avec la pi�ce o� se trouve le capteur.
	 * @param lignes les lignes du fichier de log filtr� et pars�
	 * @return une HashMap dont la cl� est le type de capteur concat�n� avec la pi�ce o� se trouve le capteur
	 */
	private static HashMap<String, ArrayList<Double>> getValeurs(ArrayList<String[]> lignes) {
		HashMap<String, ArrayList<Double>> valeurs = new HashMap<String, ArrayList<Double>>();
		NumberFormat formateur = NumberFormat.getInstance();
		
		for (int i = 0; i < lignes.size(); i++) {
			try {
				if(!valeurs.containsKey(getTypeCapteur(lignes.get(i)[1]) + "-" + getIdPiece(lignes.get(i)[1])))
					valeurs.put(getTypeCapteur(lignes.get(i)[1]) + "-" + getIdPiece(lignes.get(i)[1]), new ArrayList<Double>());
				else
					valeurs.get(getTypeCapteur(lignes.get(i)[1]) + "-" + getIdPiece(lignes.get(i)[1])).add(formateur.parse(lignes.get(i)[2]).doubleValue());
			}catch(Exception e){System.out.println("Erreur : au moins une valeur n'est pas un nombre.");}
		}
		return valeurs;
	}
	
	/**
	 * Retourne le type du capteur.
	 * @param nom nom du capteur (piece, �tage, type, ...)
	 * @return le type du capteur
	 */
	private static String getTypeCapteur(String nom) {
		for(Capteurs cap : Capteurs.values()) {
			if(nom.contains(cap.getType()))
				return cap.getType();
		}
		
		return null;
	}
	
	/**
	 * Retourne la pi�ce du capteur.
	 * @param nom nom du capteur (piece, �tage, type, ...)
	 * @return la pi�ce du capteur
	 */
	private static String getIdPiece(String nom) {
		for(Pieces piece: Pieces.values()) {
			if(nom.contains(piece.getId()))
				return piece.getId();
		}
		
		return null;
	}
}