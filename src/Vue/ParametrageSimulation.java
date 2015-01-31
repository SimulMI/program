package Vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import Controleur.FiltrerLog;
import Controleur.LibCalcul;
import Modele.Capteurs;
import Modele.DateLabelFormatter;
import Modele.Pieces;

/**
 * Classe permettant l'affichage de l'interface principale.
 * @author Groupe PTUT SimulMI
 *
 */
public class ParametrageSimulation extends JFrame
{
	private static final long serialVersionUID = -7119738795360853893L;
	
	private JTextField champDossierDestination = new JTextField();
	
	private JCheckBox[] typesCapteurs;
	private JCheckBox[] piecesUtilisees;
	
	private JDatePickerImpl dateDebut;
	private JDatePickerImpl dateFin;
	
	private ConfigurationCalculs configCalcul;
	
	public ParametrageSimulation() throws IOException
	{
		super("SimulMI");

		this.setContentPane(panneauContenu());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	private JPanel panneauContenu() throws IOException
	{
		// Panneau principal
		BorderLayout layoutPrincipal = new BorderLayout();
		JPanel panneauPrincipal = new JPanel();
		panneauPrincipal.setLayout(layoutPrincipal);

		// Panneau de titre
		BorderLayout layoutTitre = new BorderLayout();
		JPanel panneauTitre = new JPanel();
		panneauTitre.setLayout(layoutTitre);

		// Panneau de sélection des capteurs
		BorderLayout layoutSelectionCapteurs = new BorderLayout();
		JPanel panneauSelectionCapteurs = new JPanel();
		panneauSelectionCapteurs.setLayout(layoutSelectionCapteurs);
		panneauSelectionCapteurs.setBorder(BorderFactory
				.createTitledBorder("Types de capteurs"));

		// Panneau de sélection des pièces
		BorderLayout layoutSelectionPieces = new BorderLayout();
		JPanel panneauSelectionPieces = new JPanel();
		panneauSelectionPieces.setLayout(layoutSelectionPieces);
		panneauSelectionPieces.setBorder(BorderFactory
				.createTitledBorder("Pièces de la MI"));

		// Panneau de la liste des capteurs
		JPanel panneauListeCapteurs = new JPanel();
		panneauListeCapteurs.setLayout(new BoxLayout(panneauListeCapteurs,
				BoxLayout.Y_AXIS)); // alignement vertical
		panneauListeCapteurs.setBorder(BorderFactory.createEmptyBorder(5, 5, 5,
				5));

		// Panneau de la liste des pieces
		JPanel panneauListePieces = new JPanel();
		panneauListePieces.setLayout(new BoxLayout(panneauListePieces,
				BoxLayout.Y_AXIS)); // alignement vertical
		panneauListePieces.setBorder(BorderFactory
				.createEmptyBorder(5, 5, 5, 5));

		// Titre
		JLabel titre = new JLabel("Paramétrage de la simulation");
		titre.setFont(new Font("Tahoma", Font.BOLD, 14));
		titre.setHorizontalAlignment(SwingConstants.CENTER);

		// Boutons
		JButton start = new JButton("Démarrage de la simulation");
		start.setFont(new Font("Tahoma", Font.BOLD, 12));
		start.addActionListener(start());
		JButton configurer = new JButton("Configurer les calculs à effectuer");
		configurer.addActionListener(configurer());

		// Remplissage types capteurs
		typesCapteurs = new JCheckBox[Capteurs.values().length];
		int cptCapteur = 0;
		for (Capteurs c : Capteurs.values())
		{
			typesCapteurs[cptCapteur] = new JCheckBox(c.toString());
			panneauListeCapteurs.add(typesCapteurs[cptCapteur]);
			cptCapteur += 1;
		}

		// Remplissage pieces de la MI
		piecesUtilisees = new JCheckBox[Pieces.values().length];
		int cptPiece = 0;
		for (Pieces p : Pieces.values())
		{
			piecesUtilisees[cptPiece] = new JCheckBox(p.toString());
			panneauListePieces.add(piecesUtilisees[cptPiece]);
			cptPiece += 1;
		}

		// Dates de la simulation : panneau principal
		BorderLayout layoutDatesSimulation = new BorderLayout();
		JPanel panneauDatesSimulation = new JPanel();
		panneauDatesSimulation.setLayout(layoutDatesSimulation);

		// Dates de la simulation : panneau "date de début"
		FlowLayout layoutDateDebut = new FlowLayout();
		JPanel panneauDateDebut = new JPanel();
		panneauDateDebut.setLayout(layoutDateDebut);

		// Dates de la simulation : panneau "date de fin"
		FlowLayout layoutDateFin = new FlowLayout();
		JPanel panneauDateFin = new JPanel();
		panneauDateFin.setLayout(layoutDateFin);

		// Contenu du panneau des dates
		JLabel labelDateDebut = new JLabel("Traiter les données entre le : ");
		JLabel labelDateFin = new JLabel("et le : ");
		labelDateDebut.setHorizontalAlignment(SwingConstants.RIGHT);
		labelDateFin.setHorizontalAlignment(SwingConstants.RIGHT);
		labelDateFin.setPreferredSize(labelDateDebut.getPreferredSize());

		// Champs de sélection de dates
		Properties p = new Properties(); // Nécessaire pour l'implémentation de JDatePicker (bibliothèque externe)
		p.put("text.today", "Aujourd'hui");
		p.put("text.month", "Mois");
		p.put("text.year", "Année");
		dateDebut = new JDatePickerImpl(new JDatePanelImpl(
				new UtilDateModel(), p), new DateLabelFormatter());
		dateFin = new JDatePickerImpl(new JDatePanelImpl(
				new UtilDateModel(), p), new DateLabelFormatter());

		// Ajout du label et de l'élément de sélection de la date
		panneauDateDebut.add(labelDateDebut);
		panneauDateDebut.add(dateDebut);
		panneauDateFin.add(labelDateFin);
		panneauDateFin.add(dateFin);

		// Ajout des sous-panneaux au panneau principal de sélection des dates
		panneauDatesSimulation.add(panneauDateDebut, BorderLayout.NORTH);
		panneauDatesSimulation.add(panneauDateFin, BorderLayout.SOUTH);

		// Formation des panneaux de l'interface
		panneauTitre.add(titre);
		panneauTitre.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		panneauSelectionCapteurs.add(panneauListeCapteurs, BorderLayout.CENTER);
		panneauSelectionPieces.add(panneauListePieces, BorderLayout.CENTER);
		
		// Panneau de sélection du dossier de destination
		FlowLayout layoutDossierDestination = new FlowLayout();
		JPanel panneauDossierDestination = new JPanel();
		panneauDossierDestination.setLayout(layoutDossierDestination);
		
		// Dossier de destination : champ texte contenant le dossier
		JLabel labelDossierDestination = new JLabel("Enregistrer sous : ");
		this.champDossierDestination.setPreferredSize(new Dimension(150, 20));
		
		// Dossier de destination : bouton parcourir
		JButton boutonParcourir = new JButton("Parcourir...");
		boutonParcourir.addActionListener(new SaveDialog());
		
		panneauDossierDestination.add(labelDossierDestination);
		panneauDossierDestination.add(this.champDossierDestination);
		panneauDossierDestination.add(boutonParcourir);
		
		// Panneau des boutons du bas
		BorderLayout layoutBoutons = new BorderLayout();
		JPanel panneauBoutons = new JPanel();
		panneauBoutons.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panneauBoutons.setLayout(layoutBoutons);
		panneauBoutons.add(configurer, BorderLayout.NORTH);
		panneauBoutons.add(start, BorderLayout.SOUTH);

		// Panneau central
		BorderLayout layoutCentre = new BorderLayout();
		JPanel panneauCentre = new JPanel();
		panneauCentre.setLayout(layoutCentre);
		panneauCentre.add(panneauSelectionCapteurs, BorderLayout.WEST);
		panneauCentre.add(panneauSelectionPieces, BorderLayout.EAST);

		// Panneau sud
		BorderLayout layoutSud = new BorderLayout();
		JPanel panneauSud = new JPanel();
		panneauSud.setLayout(layoutSud);
		panneauSud.add(panneauDatesSimulation, BorderLayout.NORTH);
		panneauSud.add(panneauDossierDestination, BorderLayout.CENTER);
		panneauSud.add(panneauBoutons, BorderLayout.SOUTH);

		panneauPrincipal.add(panneauCentre, BorderLayout.CENTER);
		panneauPrincipal.add(panneauTitre, BorderLayout.NORTH);
		panneauPrincipal.add(panneauSud, BorderLayout.SOUTH);

		return panneauPrincipal;
	}

	private ActionListener configurer()
	{
		ActionListener listener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String[] tabTypeCapteur = getTypeSelectedCapteurs();
				
				configCalcul = new ConfigurationCalculs(ParametrageSimulation.this, tabTypeCapteur);// envoyer un tableau d'id de capteur
			}
		};
		
		return listener;
	}

	private ActionListener start() throws IOException {
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(configCalcul == null || dateDebut.getModel().getValue() == null || dateFin.getModel().getValue() == null || champDossierDestination.getText().equals(""))
					JOptionPane.showConfirmDialog(null, "Veuillez configurer tous les paramètres de la simulation.\n", "Warning", JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
				else {
					String[] tabTypeCapteur = getTypeSelectedCapteurs();
					String[] tabIdPiece = getIdSelectedPiece();
					Date date;
					DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					HashMap<String, String> hmCalcul = configCalcul.getHmCalcul();
					
					date = (Date)dateDebut.getModel().getValue();
					String dateDebutStr = formatter.format(date);
					date = (Date)dateFin.getModel().getValue();
					String dateFinStr = formatter.format(date);
					
					try {
						FileWriter fw = new FileWriter(champDossierDestination.getText());
						ArrayList<ArrayList<String[]>> logFiltre = new ArrayList<ArrayList<String[]>>();
						
						logFiltre.addAll(FiltrerLog.getLogTrier(dateDebutStr, dateFinStr, tabIdPiece, tabTypeCapteur));
						
						LibCalcul.initCalculs();
						for(int i = 0 ; i < logFiltre.size() ; i++) {
							fw.write(LibCalcul.calculer(hmCalcul, logFiltre.get(i)).toString() + "\n");
							fw.flush();
						}
						fw.close();
					} catch (Exception e1) {e1.printStackTrace();}
					
					JOptionPane.showConfirmDialog(null, "Le fichier de simulation a été crée.\n", "Fichier créé", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
				}
			}
		};
		
		return al;
	}
	
	private String[] getTypeSelectedCapteurs() {
		String[] tabTypeCapteur;
		int nbCapteur = 0;
		
		for (int i = 0; i < typesCapteurs.length; i++) {
			if(typesCapteurs[i].isSelected())
				nbCapteur++;
		}
		
		tabTypeCapteur = new String[nbCapteur];
		
		nbCapteur = 0;
		for (Capteurs c : Capteurs.values()) {
			for (int i = 0; i < typesCapteurs.length; i++) {
				if(typesCapteurs[i].isSelected() && typesCapteurs[i].getText().equals(c.toString())) {
					tabTypeCapteur[nbCapteur] = c.getType();
					nbCapteur++;
				}
			}
		}
		
		return tabTypeCapteur;
	}
	
	private String[] getIdSelectedPiece() {
		String[] tabIdPiece;
		int nbPiece = 0;
		
		for (int i = 0; i < piecesUtilisees.length; i++) {
			if(piecesUtilisees[i].isSelected())
				nbPiece++;
		}
		
		tabIdPiece = new String[nbPiece];
		
		nbPiece = 0;
		for (Pieces p : Pieces.values()) {
			for (int i = 0; i < piecesUtilisees.length; i++) {
				if(piecesUtilisees[i].isSelected() && piecesUtilisees[i].getText().equals(p.toString())) {
					tabIdPiece[nbPiece] = p.getId();
					nbPiece++;
				}
			}
		}
		
		return tabIdPiece;
	}
	
	class SaveDialog implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			JFileChooser jfc = new JFileChooser();
			
			int selected = jfc.showSaveDialog(ParametrageSimulation.this);
			
			if (selected == JFileChooser.APPROVE_OPTION) {
				ParametrageSimulation.this.champDossierDestination.setText(jfc.getSelectedFile().getAbsolutePath());
			}
		}
	}
}
