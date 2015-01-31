package Controleur;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.*;

import javax.swing.JOptionPane;

/**
 * Classe SSH permettant d'interagir avec le serveur de la MI.
 * @author Groupe PTUT SimulMI
 */
public class LibSSH {
	private LibSSH(){}
	
	/**
	 * Ecrit un fichier de log r�cup�r� en SSH sur un serveur.
	 * @param login login de connexion
	 * @param password mot de passe de connexion
	 * @param host adresse du serveur
	 * @param chemin chemin du fichier
	 * @param port port attribu� au SSH
	 * @throws JSchException exception JSch
	 * @throws SftpException exception SFTP
	 * @throws IOException exception I/O
	 */
	public static void getFile(String login, String password, String host, String chemin, int port) throws JSchException, SftpException, IOException {
		JSch jsch = new JSch();
		Session session;
		String ligne;
		InputStream out;
		BufferedReader br;
		ChannelSftp sftpChannel;
		FileWriter fw;
		
		// Connexion
		session = jsch.getSession(login, host, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        try {
	        session.connect(10000); // Attend 10 seconde avant d'arr�ter la tentative de connexion
	        sftpChannel = (ChannelSftp) session.openChannel("sftp");
	        sftpChannel.connect(10000); // Attend 10 seconde avant d'arr�ter la tentative de connexion
	        
	        //Ecriture du fichier
	        out = sftpChannel.get(chemin);
	        br = new BufferedReader(new InputStreamReader(out));
	        ligne = br.readLine();
	        
	        fw = new FileWriter("./" + chemin.substring(chemin.lastIndexOf('/') + 1));
	        
	        while(ligne != null) {
	        	fw.write(ligne + "\n");
	        	fw.flush();
	        	ligne = br.readLine();
	        }
	
	        br.close();
	        fw.close();
	        sftpChannel.disconnect();
	        session.disconnect();
	        JOptionPane.showConfirmDialog(null, "Le fichier de log a �t� mis � jour.\n", "Connexion r�ussie.", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception e){JOptionPane.showConfirmDialog(null, "Le fichier de log n'a pas pu �tre mis � jour.\nL'ancien fichier de log sera utilis� pour la simulation.", "Echec de connexion.", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);}
	}
}