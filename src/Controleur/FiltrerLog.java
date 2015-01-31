package Controleur;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * La classe FiltrerLog permet de filtrer les fichiers de log en fonction de la date, des pièces et des capteurs saisis par l'utilisateur.
 * @author Groupe PTUT SimulMI
 *
 */
public class FiltrerLog {
	
	private FiltrerLog() {}
	
	/**
	 * Filtre le fichier de log et stocke les lignes parsées. Les lignes correspondant à un type de capteur sont rangées dans une ArrayList&lt;String[]&gt; qui est elle-même rangée dans une ArrayList.
	 * Tableau de String :
	 *  Indice 0 = la date
	 * 	Indice 1 = le nom du capteur
	 * 	Indice 2 = la valeur du capteur
	 * 	Indice 3 = l'heure
	 * @param dateDebut date de début du tri
	 * @param dateFin date de fin du tri
	 * @param tabIdPieces tableau de String correspondant à l'ID des pièces dont on relèvera les log
	 * @param tabIdCapteur tableau de String correspondant à l'ID des capteurs dont on relèvera les log
	 * @return une ArrayList&lt;ArrayList&lt;String[]&gt;&gt; : chaque ArrayList&lt;String[]&gt; correspond aux lignes filtrées du fichier de log d'un type de capteur
	 * @throws Exception si la connexion à la machine échoue
	 */
	public static ArrayList<ArrayList<String[]>> getLogTrier(String dateDebut, String dateFin, String[] tabIdPieces, String[] tabIdCapteur) throws Exception {
		// Début de la fonction
		BufferedReader br;
		String ligne = "";
		String ligneParser[];
		ArrayList<ArrayList<String[]>> logTrier = new ArrayList<ArrayList<String[]>>();
		
		for (int i = 0; i < tabIdCapteur.length; i++) {
			logTrier.add(new ArrayList<String[]>());
		}
		
		String nomFichier = "knx1.log";
		LibSSH.getFile("etu2", "9a3bRRzK", "mi.lab.iut-blagnac.fr", "/var/log/" + nomFichier, 2230);
		br = new BufferedReader(new FileReader(nomFichier));
		
		while ((ligne = br.readLine()) != null) {
			ligneParser = parseLigne(ligne);
			if(contientCapteur(ligneParser[1], tabIdCapteur) && contientPiece(ligneParser[1], tabIdPieces) && estEntre(ligneParser[0], dateDebut, dateFin))
				logTrier.get(indiceTab(tabIdCapteur, ligneParser[1])).add(ligneParser);
		}
		
		br.close();
		
		return logTrier;
	}
	
	/**
	 * Retourne l'indice du tableau dans lequel la ligne doit être rangée.
	 * @param tabTypeCapteur le tableau des types de capteur
	 * @param ligne la ligne dont on cherche l'indice où la ranger
	 * @return l'indice du tableau dans lequel la ligne doit être rangée
	 */
	private static int indiceTab(String[] tabTypeCapteur, String ligne) {
		for (int i = 0; i < tabTypeCapteur.length; i++) {
			if(ligne.contains(tabTypeCapteur[i]))
				return i;
		}
		
		return -1;
	}
	
	/**
	 * Retourne true si l'une des pièces du tableau est présente dans le nom du capteur, sinon false.
	 * @param nomCapteur nom du capteur
	 * @param tabIdPiece les id des pièces
	 * @return true si l'une des pièces du tableau est présente dans le nom du capteur, sinon false
	 */
	private static boolean contientPiece(String nomCapteur, String[] tabIdPiece) {
		boolean exist = false;
		
		for (int i = 0; i < tabIdPiece.length && !exist; i++) {
			if(nomCapteur.contains(tabIdPiece[i]))
				exist = true;
		}
		
		return exist;
	}
	
	/**
	 * Retourne true si l'un des capteurs du tableau est présent dans le nom du capteur, sinon false.
	 * @param nomCapteur nom du capteur
	 * @param tabIdPiece les capteurs
	 * @return true si l'un des capteurs du tableau est présent dans le nom du capteur, sinon false
	 */
	private static boolean contientCapteur(String nomCapteur, String[] tabIdPiece) {
		boolean exist = false;
		
		for (int i = 0; i < tabIdPiece.length && !exist; i++) {
			if(nomCapteur.contains(tabIdPiece[i]))
				exist = true;
		}
		
		return exist;
	}
	
	/**
	 * Parse une ligne du fichier de log
	 * Retourne un tableau contenant 4 String :
	 * 	Indice 0 = la date
	 * 	Indice 1 = le nom du capteur
	 * 	Indice 2 = la valeur du capteur
	 * 	Indice 3 = l'heure
	 * @param ligne la ligne à parser
	 * @return un tableau de String
	 */
	private static String[] parseLigne(String ligne) {
		String[] ligneLog = new String[4]; 
		
		// date
		ligne = ligne.replaceFirst("<evenement date=\"", "");
		ligneLog[0] = (String) ligne.subSequence(0, ligne.indexOf("\""));
		
		// nomCapteur
		ligne = ligne.replaceFirst(ligneLog[0] + "\" nomCapteur=\"", "");
		ligneLog[1] = (String) ligne.subSequence(0, ligne.indexOf("\""));
		
		// valeur
		ligne = ligne.replaceFirst(ligneLog[1] + "\" valeur=\"", "");
		ligneLog[2] = (String) ligne.subSequence(0, ligne.indexOf("\""));
		ligneLog[2] = ligneLog[2].replace('.', ','); 
		
		// sépare la date de l'heure
		ligneLog[3] = ligneLog[0].substring(ligneLog[0].indexOf(" ") + 1, ligneLog[0].length());
		ligneLog[0] = ligneLog[0].substring(0, ligneLog[0].indexOf(" "));
		
		return ligneLog;
	}
	
	/**
	 * Détermine si une date est comprise entre 2 autres dates.
	 * @param date date à vérifier
	 * @param dateDeb date de début
	 * @param dateFin date de fin
	 * @return true si date est comprise entre dateDeb et dateFin, false sinon
	 */
	public static boolean estEntre(String date, String dateDeb, String dateFin) {
		String tabDate[] = date.split("-");
		String tabDateDeb[] = dateDeb.split("-");
		String tabDateFin[] = dateFin.split("-");
		
		if(Integer.parseInt(tabDateDeb[0]) <= Integer.parseInt(tabDate[0]) && Integer.parseInt(tabDate[0]) <= Integer.parseInt(tabDateFin[0])) {
			if(Integer.parseInt(tabDateDeb[1]) <= Integer.parseInt(tabDate[1]) && Integer.parseInt(tabDate[1]) <= Integer.parseInt(tabDateFin[1])) {
				if(Integer.parseInt(tabDateDeb[2]) <= Integer.parseInt(tabDate[2]) && Integer.parseInt(tabDate[2]) <= Integer.parseInt(tabDateFin[2]))
					return true;
				else
					return false;
			}
			else
				return false;
		}
		
		else
			return false;
	}
}