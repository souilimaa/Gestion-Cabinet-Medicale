package swingInterfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import DAO.dao;
import Models.Client;
import Models.Medecin;
import daoImpl.clientDAOImpl;
import daoImpl.medecinDAOImpl;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class UpdateClient extends JFrame {

	private JPanel contentPane;
	private JTextField TitreInput;
	private JTextField NomInput;
	private JTextField PrenomInput;
	private Client client;
	
	

	/**
	 * Create the frame.
	 */
	public UpdateClient(Client client) {
		this.client=client;

		System.out.println(client.getTITRE());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 472);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(661, 405, -582, -350);
		contentPane.add(panel_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(128, 30, 414, 37);
		contentPane.add(panel);
		
		JLabel lblNewLabel = new JLabel("Update Client :");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 19));
		lblNewLabel.setBackground(Color.RED);
		panel.add(lblNewLabel);
		
		
		JLabel lblNewLabel_1 = new JLabel("Titre :");
		lblNewLabel_1.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(204, 118, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Nom :");
		lblNewLabel_2.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(204, 175, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		
		
		JLabel lblNewLabel_3 = new JLabel("Prenom :");
		lblNewLabel_3.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(204, 235, 68, 14);
		contentPane.add(lblNewLabel_3);
		
	
		JLabel promptLabel = new JLabel("Tous les champs sont obligatoires !!");
		promptLabel.setForeground(UIManager.getColor("ToolBar.dockingForeground"));
		promptLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		promptLabel.setBounds(204, 78, 276, 20);
		promptLabel.setVisible(false);
		contentPane.add(promptLabel);
		JLabel labelPromptErr = new JLabel(" Une erreur dans Update Client  !!");
		labelPromptErr.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelPromptErr.setForeground(UIManager.getColor("ToolBar.dockingForeground"));
		labelPromptErr.setBounds(204, 335, 276, 14);
		labelPromptErr.setVisible(false);
		contentPane.add(labelPromptErr);
		
		TitreInput = new JTextField();
		TitreInput.setColumns(10);
		TitreInput.setBounds(291, 118, 189, 20);
		contentPane.add(TitreInput);
		
		NomInput = new JTextField();
		NomInput.setColumns(10);
		NomInput.setBounds(291, 175, 189, 20);
		contentPane.add(NomInput);
		
		PrenomInput = new JTextField();
		PrenomInput.setColumns(10);
		PrenomInput.setBounds(291, 235, 189, 20);
		contentPane.add(PrenomInput);
		TitreInput.setText(client.getTITRE());
		NomInput.setText(client.getNOM());
		PrenomInput.setText(client.getPRENOM());
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(TitreInput.getText().isEmpty()||NomInput.getText().isEmpty()||PrenomInput.getText().isEmpty()) {
					promptLabel.setVisible(true);
					
				}
				else {
				promptLabel.setVisible(false);
				int version=client.getVERSION()+1;
				String titre=TitreInput.getText();
				String nom=NomInput.getText();
				String prenom=PrenomInput.getText();
				Client cliente=new Client(client.getID(),version,titre,nom,prenom);

				dao dao=new clientDAOImpl();
				
				try {
					labelPromptErr.setVisible(false);
					int rowsAffected=dao.update(cliente);
					TitreInput.setText("");
					NomInput.setText("");
					PrenomInput.setText("");
                    JOptionPane.showMessageDialog(UpdateClient.this, "Client Updated avec succès !", "Success", JOptionPane.INFORMATION_MESSAGE);
                    GetAllClients ALLClientFrame = new GetAllClients();
                    
                    dispose();

                    
                    ALLClientFrame.setVisible(true);
				} catch (SQLException e1) {
					labelPromptErr.setVisible(true);
					System.out.println("error had occured in Update Client"+e);
					e1.printStackTrace();
					
				}
				}
				
				
			}
		});
		btnUpdate.setForeground(Color.WHITE);
		btnUpdate.setFont(new Font("Segoe UI Historic", Font.BOLD, 14));
		btnUpdate.setBackground(SystemColor.textHighlight);
		btnUpdate.setBounds(204, 295, 276, 29);
		contentPane.add(btnUpdate);
		
		
		
		
		JButton btnAjouterMedecin = new JButton("Ajouter");
	}

}
