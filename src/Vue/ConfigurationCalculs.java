package Vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import Modele.Capteurs;

/**
 * Classe permettant l'affichage de l'interface de configuration des calculs de la simulation.
 * @author Groupe PTUT SimulMI
 *
 */
public class ConfigurationCalculs extends JDialog
{
	private static final long serialVersionUID = -433095734295714593L;
	
	private ArrayList<JComboBox<String>> listeTypesCalculs;
	private JPanel[] tableauVuesOnglets;
	private JTabbedPane panneauOngletsTypesCapteurs;
	private String[] tabTypeCapteur;
	
	public ConfigurationCalculs(JFrame parent, String[] pTabTypeCapteur) {
		super(parent, "SimulMI - Configuration calculs");
		
		tabTypeCapteur = pTabTypeCapteur;
		
		this.setContentPane(panneauContenu(tabTypeCapteur));
		this.pack();
		this.setVisible(true);
	}

	private JPanel panneauContenu(String[] tabTypeCapteur) {
		BorderLayout layoutPrincipal = new BorderLayout();
		JPanel panneauPrincipal = new JPanel();
		panneauPrincipal.setLayout(layoutPrincipal);
		
		listeTypesCalculs = new ArrayList<JComboBox<String>>();
		tableauVuesOnglets = new JPanel[tabTypeCapteur.length];
		for (int i = 0; i < tabTypeCapteur.length; i++)
		{
			BorderLayout layoutOnglet = new BorderLayout();
			tableauVuesOnglets[i] = new JPanel();
			tableauVuesOnglets[i].setLayout(layoutOnglet);
			
			BorderLayout layoutTypeCalcul = new BorderLayout();
			JPanel panneauTypeCalcul = new JPanel();
			panneauTypeCalcul.setLayout(layoutTypeCalcul);
			panneauTypeCalcul.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			
			JLabel labelTypeCalcul = new JLabel("Calcul à réaliser :");
			labelTypeCalcul.setFont(new Font("Tahoma", Font.BOLD, 12));
			
			listeTypesCalculs.add(new JComboBox<String>());
			listeTypesCalculs.get(i).setPreferredSize(new Dimension(250, 20));
			listeTypesCalculs.get(i).addItem(new String("Minimum"));
			listeTypesCalculs.get(i).addItem(new String("Maximum"));
			listeTypesCalculs.get(i).addItem(new String("Moyenne"));
			
			JButton boutonOk = new JButton("OK");
			boutonOk.addActionListener(actionOk());
			
			panneauTypeCalcul.add(labelTypeCalcul, BorderLayout.NORTH);
			panneauTypeCalcul.add(listeTypesCalculs.get(i), BorderLayout.CENTER);
			panneauTypeCalcul.add(boutonOk, BorderLayout.SOUTH);
			
			tableauVuesOnglets[i].add(panneauTypeCalcul);
		}
		
		panneauOngletsTypesCapteurs = new JTabbedPane();
		String[] tabNomCapteur = getNomCapteur(tabTypeCapteur);
		for (int i = 0; i < tabTypeCapteur.length; i++)
		{
			panneauOngletsTypesCapteurs.addTab(tabNomCapteur[i], tableauVuesOnglets[i]);
		}
		
		panneauPrincipal.add(panneauOngletsTypesCapteurs);
		
		return panneauPrincipal;
	}

	private String[] getNomCapteur(String[] tabTypeCapteur) {
		String[] tabNomCapteur = new String[tabTypeCapteur.length];
		
		for (int i = 0; i < tabTypeCapteur.length; i++) {
			for(Capteurs c : Capteurs.values()) {
				if(c.getType().equals(tabTypeCapteur[i]))
					tabNomCapteur[i] = c.toString();
			}
		}
		
		return tabNomCapteur;
	}
	
	private String[] getIdCapteur(String[] tabTypeCapteur) {
		String[] tabIdCapteur = new String[tabTypeCapteur.length];
		
		for (int i = 0; i < tabTypeCapteur.length; i++) {
			for(Capteurs c : Capteurs.values()) {
				if(c.toString().equals(panneauOngletsTypesCapteurs.getTitleAt(i)))
					tabIdCapteur[i] = c.getType();
			}
		}
		
		return tabIdCapteur;
	}
	
	private ActionListener actionOk() {
		
		ActionListener actionOk = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		};
		
		return actionOk;
	}
	
	public HashMap<String, String> getHmCalcul() {
		HashMap<String, String> calcul = new HashMap<String,String>();
		String[] tabIdCapteur = getIdCapteur(tabTypeCapteur);
		
		for (int i = 0; i < tableauVuesOnglets.length; i++) {
			calcul.put(tabIdCapteur[i], listeTypesCalculs.get(i).getSelectedItem().toString());
		}
		
		return calcul;
	}
}
